package ez.nebula.client.api.config;

import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.api.manager.IManager;
import ez.nebula.client.util.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xgraza
 * @since 02/14/25
 */
public final class ConfigManager implements IManager
{
    private final List<IConfig> configList = new ArrayList<>();

    @Override
    public void init()
    {
        Runtime.getRuntime().addShutdownHook(
                new ConfigSaveThread(this));
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

    public void addConfiguration(final IConfig configuration)
    {
        configList.add(configuration);
    }

    public List<IConfig> getConfigList()
    {
        return configList;
    }
}
