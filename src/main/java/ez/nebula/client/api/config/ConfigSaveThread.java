package ez.nebula.client.api.config;

import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.util.io.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author xgraza
 * @since 02/14/25
 */
public final class ConfigSaveThread extends Thread
{
    private final ConfigManager manager;

    public ConfigSaveThread(final ConfigManager manager)
    {
        this.manager = manager;
        setName("Configuration Save Thread");
    }

    @Override
    public void run()
    {
        for (final IConfig configuration : manager.getConfigList())
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
}
