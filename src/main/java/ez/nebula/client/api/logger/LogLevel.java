package ez.nebula.client.api.logger;

public enum LogLevel
{
    DEBUG("\033[0;37m"),
    ERROR("\033[0;31m"),
    SUCCESS("\033[0;32m"),
    WARN("\033[0;33m"),
    INFO("\033[0;34m");

    private final String ansiColor;

    LogLevel(final String ansiColor)
    {
        this.ansiColor = ansiColor;
    }

    public String getAnsiColor()
    {
        return ansiColor;
    }
}