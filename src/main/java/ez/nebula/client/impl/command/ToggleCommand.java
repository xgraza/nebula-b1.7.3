package ez.nebula.client.impl.command;

import ez.nebula.client.api.manager.command.Command;
import ez.nebula.client.api.manager.command.trait.CommandManifest;
import ez.nebula.client.api.manager.module.Module;

import java.util.List;

/**
 * @author xgraza
 * @since 6/9/26
 */
@CommandManifest(aliases = {"toggle", "t"},
        description = "Toggles a module on or off",
        syntax = "[module name]")
public final class ToggleCommand extends Command
{
    @Override
    public String dispatch(final List<String> args)
    {
        if (args.isEmpty())
        {
            return "Please provide a module name";
        }
        final Module module = parseModule(args.get(0));
        if (module == null)
        {
            return "Please provide a valid module name";
        }
        module.toggle();
        return String.format("Module %s toggled %s", module.getManifest().name(), module.isToggled() ? "on" : "off");
    }
}
