package ez.nebula.client.api.logger;

import java.io.IOException;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class LogFileSaveThread extends Thread
{
    public LogFileSaveThread()
    {
        setName("Log File Save Shutdown Thread");
        setDaemon(false);
    }

    @Override
    public void run()
    {
        try
        {
            Logger.saveLogsToFile();
            System.out.println("# Saved Nebula logs!");
        } catch (final IOException e)
        {
            Logger.error("Failed to save logs", e);
            throw new RuntimeException(e);
        }
    }
}
