package ez.nebula.client.api.manager.macro;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ez.nebula.client.api.config.IConfig;
import ez.nebula.client.api.manager.key.trait.Device;
import ez.nebula.client.util.io.FileUtil;

import java.io.File;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class MacroConfig implements IConfig
{
    private final MacroManager manager;

    public MacroConfig(final MacroManager manager)
    {
        this.manager = manager;
    }

    @Override
    public String save()
    {
        final JsonObject object = new JsonObject();
        for (final String name : manager.getMacroNames())
        {
            final Macro macro = manager.getMacro(name);
            object.add(name, macro.toJSON());
        }
        return FileUtil.GSON.toJson(object);
    }

    @Override
    public void load(final String data)
    {
        final JsonElement element = JsonParser.parseString(data);
        if (element == null || !element.isJsonObject())
        {
            return;
        }
        manager.clearMacros();
        final JsonObject object = element.getAsJsonObject();
        for (final String name : object.keySet())
        {
            final JsonElement macroElement = object.get(name);
            if (macroElement.isJsonObject())
            {
                final Macro macro = new Macro(0, Device.KEYBOARD);
                macro.fromJSON(macroElement);
                manager.registerMacro(name, macro);
            }
        }
    }

    @Override public File getFile()
    {
        return manager.getSaveFile();
    }
}
