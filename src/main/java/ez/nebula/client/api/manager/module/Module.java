package ez.nebula.client.api.manager.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ez.nebula.client.api.Initiable;
import ez.nebula.client.api.Toggleable;
import ez.nebula.client.api.config.IJSONSerializable;
import ez.nebula.client.api.listener.EventBus;
import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.api.manager.key.Key;
import ez.nebula.client.api.manager.key.trait.Device;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.api.setting.SettingProvider;
import ez.nebula.client.core.Nebula;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author xgraza
 * @since 6/8/26
 */
public class Module implements Toggleable, IJSONSerializable, SettingProvider
{
    public static final String DEFAULT_MODULE_DESCRIPTION =
            "No description provided for this module.";
    protected static final Minecraft MC = Minecraft.getMinecraft();

    private final Map<String, Setting<?>> settingNameMap = new LinkedHashMap<>();

    private final ModuleManifest manifest;
    private final Key key;
    private boolean toggled;

    public Module()
    {
        if (!getClass().isAnnotationPresent(ModuleManifest.class))
        {
            throw new RuntimeException(String.format("%s must contain @ModuleManifest", getClass().getName()));
        }
        manifest = getClass().getDeclaredAnnotation(ModuleManifest.class);
        key = new Key(Keyboard.KEY_NONE, Device.KEYBOARD)
        {
            @Override
            public void onToggled()
            {
                toggle();
            }
        };
    }

    @Override
    public void init()
    {
        Nebula.INSTANCE.getKeyManager().registerKey(key);

        for (final Field field : getClass().getDeclaredFields())
        {
            if (!Setting.class.isAssignableFrom(field.getType()))
            {
                continue;
            }

            try
            {
                field.setAccessible(true);
                final Setting<?> setting = (Setting<?>) field.get(this);
                if (setting != null)
                {
                    registerSetting(setting);
                }
            } catch (final IllegalAccessException e)
            {
                Logger.error("Failed to reflect setting %s", field, e);
            }
        }
    }

    @Override
    public void onEnable()
    {
        EventBus.subscribe(this);
    }

    @Override
    public void onDisable()
    {
        EventBus.unsubscribe(this);
    }

    public ModuleManifest getManifest()
    {
        return manifest;
    }

    public Key getKey()
    {
        return key;
    }

    @Override
    public void setToggled(boolean state)
    {
        toggled = state;
        if (state)
        {
            onEnable();
        } else
        {
            onDisable();
        }
    }

    @Override
    public void toggle()
    {
        setToggled(!toggled);
    }

    @Override
    public boolean isToggled()
    {
        return toggled;
    }

    @Override
    public void registerSetting(final Setting<?> setting)
    {
        settingNameMap.put(setting.getName(), setting);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Setting<T> getSetting(String name)
    {
        return (Setting<T>) settingNameMap.get(name);
    }

    @Override
    public Collection<Setting<?>> getSettings()
    {
        return settingNameMap.values();
    }

    @Override
    public void fromJSON(final JsonElement element)
    {
        if (!element.isJsonObject())
        {
            return;
        }
        final JsonObject object = element.getAsJsonObject();
        if (object.has("toggled"))
        {
            setToggled(object.get("toggled").getAsBoolean());
        }
        if (object.has("key"))
        {
            key.fromJSON(object.get("key"));
        }
        if (object.has("settings") && !settingNameMap.isEmpty())
        {
            final JsonElement settingsElement = object.get("settings");
            if (!settingsElement.isJsonObject())
            {
                return;
            }
            for (final String name : settingNameMap.keySet())
            {
                getSetting(name).fromJSON(settingsElement);
            }
        }
    }

    @Override
    public JsonElement toJSON()
    {
        final JsonObject object = new JsonObject();
        object.addProperty("toggled", toggled);
        object.add("key", key.toJSON());
        if (!settingNameMap.isEmpty())
        {
            final JsonObject settingsObject = new JsonObject();
            for (final String name : settingNameMap.keySet())
            {
                settingsObject.add(name, getSetting(name).toJSON());
            }
            object.add("settings", settingsObject);
        }
        return object;
    }
}
