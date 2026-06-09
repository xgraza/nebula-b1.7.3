package ez.nebula.client.api.manager.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ez.nebula.client.api.config.IConfig;
import ez.nebula.client.util.io.FileUtil;

import java.io.File;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class ModuleConfig implements IConfig
{
    private final ModuleManager manager;
    private final String name;

    public ModuleConfig(final ModuleManager manager, final String name)
    {
        this.manager = manager;
        this.name = name;
    }

    @Override
    public String save()
    {
        final JsonObject object = new JsonObject();
        for (final Module module : manager.getAll())
        {
            object.add(module.getManifest().name(), module.toJSON());
        }
        return FileUtil.GSON.toJson(object);
    }

    @Override
    public void load(final String data)
    {
        if (data == null || data.isEmpty())
        {
            return;
        }
        final JsonElement element = JsonParser.parseString(data);
        if (element == null || !element.isJsonObject())
        {
            return;
        }
        final JsonObject modulesObject = element.getAsJsonObject();
        for (final Module module : manager.getAll())
        {
            final String name = module.getManifest().name();
            if (!modulesObject.has(name))
            {
                continue;
            }
            final JsonElement moduleElement = modulesObject.get(name);
            if (!modulesObject.isJsonObject())
            {
                continue;
            }
            module.fromJSON(moduleElement);
        }
    }

    @Override
    public File getFile()
    {
        return new File(manager.getSaveDirectory(), name + ".json");
    }
}
