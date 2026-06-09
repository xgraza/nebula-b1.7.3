package ez.nebula.client.core;

import ez.nebula.client.BuildConfig;
import ez.nebula.client.api.config.ConfigManager;
import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.api.manager.friend.FriendManager;
import ez.nebula.client.api.manager.key.KeyManager;
import ez.nebula.client.api.manager.macro.MacroManager;
import ez.nebula.client.api.manager.module.ModuleManager;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xgraza
 * @since 6/8/26
 */
public enum Nebula
{
    INSTANCE;

    private File nebulaDirectory;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final ConfigManager configManager = new ConfigManager();
    private final KeyManager keyManager = new KeyManager();
    private final MacroManager macroManager = new MacroManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final FriendManager friendManager = new FriendManager();

    public void init(final File workingDirectory)
    {
        Logger.info("Version: %s", ClientConfig.VERSION);
        Logger.info("Build Time: %s", BuildConfig.BUILD_TIME);

        final long startTime = System.nanoTime();

        nebulaDirectory = new File(workingDirectory, "nebula-client");
        if (!nebulaDirectory.exists())
        {
            if (nebulaDirectory.mkdir())
            {
                Logger.info("Successfully created %s", nebulaDirectory.getAbsolutePath());
            } else
            {
                Logger.error("Failed to create nebula directory");
                throw new RuntimeException("Failed to create " + nebulaDirectory);
            }
        }

        Logger.info("Initializing managers");
        keyManager.init();
        macroManager.init();
        moduleManager.init();
        friendManager.init();

        Logger.info("Loading configurations");
        configManager.init();

        final long endTime = System.nanoTime();
        Logger.info("Finished initializing Nebula in %.2fms", (endTime - startTime) / 1000000.0);
    }

    public ConfigManager getConfigManager()
    {
        return configManager;
    }

    public KeyManager getKeyManager()
    {
        return keyManager;
    }

    public ModuleManager getModuleManager()
    {
        return moduleManager;
    }

    public File getNebulaDirectory()
    {
        return nebulaDirectory;
    }

    public ExecutorService getExecutor()
    {
        return executorService;
    }
}
