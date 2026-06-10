package ez.nebula.client.api.manager.command;

import ez.nebula.client.api.manager.command.trait.CommandManifest;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.core.Nebula;
import net.minecraft.client.Minecraft;

import java.util.List;

public abstract class Command
{
    public static final String DEFAULT_DESCRIPTION =
            "No description provided for this command";

    protected static final Minecraft MC = Minecraft.getMinecraft();
    protected static final String DEFAULT_DISPATCH = "Dispatched successfully";

    private final CommandManifest manifest;

    public Command()
    {
        if (!getClass().isAnnotationPresent(CommandManifest.class))
        {
            throw new RuntimeException(String.format("%s must contain @CommandManifest", getClass().getName()));
        }
        manifest = getClass().getDeclaredAnnotation(CommandManifest.class);
    }

    public abstract String dispatch(final List<String> args);

    protected Module parseModule(final String arg)
    {
        for (final Module module : Nebula.INSTANCE.getModuleManager().getAll())
        {
            if (module.getManifest().name().equals(arg))
            {
                return module;
            }
        }
        return null;
    }

    protected Double parseDouble(final String arg)
    {
        try
        {
            return Double.parseDouble(arg);
        } catch (NumberFormatException ignored)
        {
            return null;
        }
    }

    protected Integer parseInt(final String arg)
    {
        try
        {
            return Integer.parseInt(arg);
        } catch (NumberFormatException ignored)
        {
            return null;
        }
    }

    public CommandManifest getManifest()
    {
        return manifest;
    }
}
