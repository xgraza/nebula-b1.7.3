package ez.nebula.client.impl.command;

import ez.nebula.client.api.manager.command.Command;
import ez.nebula.client.api.manager.command.trait.CommandManifest;

import java.util.List;

/**
 * @author xgraza
 * @since 6/9/26
 */
@CommandManifest(aliases = {"vclip"},
        description = "Offsets your player Y",
        syntax = "[blocks]")
public final class VClipCommand extends Command
{
    @Override
    public String dispatch(final List<String> args)
    {
        if (args.isEmpty())
        {
            return "Please provide the amount of blocks to clip vertically";
        }
        final Double blocks = parseDouble(args.get(0));
        if (blocks == null || Double.isNaN(blocks) || blocks == 0.0)
        {
            return "Please provide a valid number";
        }
        MC.thePlayer.setPosition(MC.thePlayer.posX, MC.thePlayer.posY + blocks, MC.thePlayer.posZ);
        return "Clipped " + blocks + " " + (blocks > 0 ? "up" : "down");
    }
}
