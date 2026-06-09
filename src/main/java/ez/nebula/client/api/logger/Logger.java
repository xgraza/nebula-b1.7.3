package ez.nebula.client.api.logger;

import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger
{
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd kk:mm");
    private static BufferedWriter fileWriter;

    static
    {
        final File logsFolder = Paths.get("", "nebula-logs").toFile();
        if (!logsFolder.exists())
        {
            if (!logsFolder.mkdir())
            {
                throw new RuntimeException("failed to create logs folder");
            }
        }

        final File LOG_FILE = new File(logsFolder,
                String.format("log_%s.txt", System.currentTimeMillis()));
        try
        {
            if (!LOG_FILE.createNewFile())
            {
                throw new RuntimeException("failed to create log file");
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        try
        {
            fileWriter = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new LogFileSaveThread());
    }

    public static void debug(final String line, final Object... format)
    {
        log(LogLevel.DEBUG, line, format);
    }

    public static void success(final String line, final Object... format)
    {
        log(LogLevel.SUCCESS, line, format);
    }

    public static void info(final String line, final Object... format)
    {
        log(LogLevel.INFO, line, format);
    }

    public static void error(final String line, final Object... format)
    {
        log(LogLevel.ERROR, line, format);
    }

    public static void error(final Throwable throwable)
    {
        error("", throwable);
    }

    public static void error(final String line, final Throwable throwable, final Object... format)
    {
        try (final StringWriter writer = new StringWriter())
        {
            final PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            error(line + "\n" + "\033[0;31m" + printWriter, format);
            printWriter.close();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void warn(final String line, final Object... format)
    {
        log(LogLevel.WARN, line, format);
    }

    private static void log(final LogLevel logLevel, String line, final Object... format)
    {
        final StringBuilder formatBuilder = new StringBuilder();
        formatBuilder.append("\033[0;36m");
        formatBuilder.append(DATE_FORMAT.format(new Date()));
        formatBuilder.append(" ");
        formatBuilder.append("\033[0;35m");
        formatBuilder.append(Thread.currentThread().getName());
        formatBuilder.append(" ");
        formatBuilder.append(logLevel.getAnsiColor());
        formatBuilder.append("[");
        formatBuilder.append(logLevel.name());
        formatBuilder.append("]");
        formatBuilder.append("\033[0m");
        formatBuilder.append(" ");

        line = String.format(line, format);

        formatBuilder.append(line);
        formatBuilder.append("\033[0m");
        formatBuilder.append("\n");
        final String formatted = formatBuilder.toString();

        System.out.print(formatted);

        if (fileWriter != null)
        {
            try
            {
                fileWriter.write(removeAnsiColors(formatted));
                fileWriter.flush();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveLogsToFile() throws IOException
    {
        if (fileWriter == null)
        {
            warn("fileWriter is null.");
            return;
        }
        fileWriter.flush();
        fileWriter.close();
        fileWriter = null;
    }

    private static String removeAnsiColors(final String input)
    {
        return input.replaceAll("\u001B\\[[\\d;]*[mG]", "");
    }
}