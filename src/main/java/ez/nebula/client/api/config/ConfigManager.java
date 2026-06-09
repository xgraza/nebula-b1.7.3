package ez.nebula.client.api.config;

import ez.nebula.client.api.Initiable;
import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.util.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xgraza
 * @since 02/14/25
 */
public final class ConfigManager implements Initiable
{
    private final List<IConfig> configList = new ArrayList<>();

    @Override
    public void init()
    {
        Runtime.getRuntime().addShutdownHook(new ConfigSaveThread(this));
        try
        {
            loadConfigs();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void loadConfigs() throws IOException
    {
        if (configList.isEmpty())
        {
            Logger.warn("No configs to load!");
            return;
        }
        Logger.info("Loading %s configs...", configList.size());
        for (final IConfig configuration : configList)
        {
            final File file = configuration.getFile();
            if (!file.exists())
            {
                Logger.warn("Configuration file %s does not exist", file);
                continue;
            }
            final String data = FileUtil.read(file);
            if (!data.isEmpty())
            {
                configuration.load(data);
            }
        }
    }

    public void saveConfigs()
    {
        if (configList.isEmpty())
        {
            Logger.warn("No configs to save!");
            return;
        }
        Logger.info("Saving %s configs", configList.size());
        for (final IConfig configuration : getConfigList())
        {
            final File file = configuration.getFile();
            if (!file.getParentFile().exists())
            {
                if (!file.getParentFile().mkdir())
                {
                    Logger.error("Failed to create parent directory %s", file);
                    return;
                }
                Logger.info("Created parent directory %s", file);
            }
            if (!file.exists())
            {
                try
                {
                    if (!file.createNewFile())
                    {
                        Logger.error("Failed to create file %s", file);
                    }
                } catch (final IOException e)
                {
                    Logger.error(e);
                    continue;
                }
            }

            final String data = configuration.save();
            if (data == null || data.isEmpty())
            {
                Logger.warn("Save data for %s was empty", file);
                continue;
            }

            try
            {
                FileUtil.save(file, data);
            } catch (final IOException e)
            {
                Logger.error(e);
            }
        }
    }

    public void addConfiguration(final IConfig configuration)
    {
        configList.add(configuration);
    }

    public List<IConfig> getConfigList()
    {
        return configList;
    }
}
