package ez.nebula.client.impl.command;

import ez.nebula.client.api.manager.command.Command;
import ez.nebula.client.api.manager.command.trait.CommandManifest;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

import java.util.List;

@CommandManifest(aliases = {"give"},
        description = "Gives a block to the player",
        syntax = "[block id] (amount)")
public final class GiveCommand extends Command
{
    @Override
    public String dispatch(final List<String> args)
    {
        if (MC.isMultiplayerWorld())
        {
            return "You must be in a singleplayer world";
        }
        if (args.isEmpty())
        {
            return "Please provide a block ID";
        }
        final Integer blockID = parseInt(args.remove(0));
        if (blockID == null || blockID < 0 || blockID >= 256 || Block.blocksList[blockID] == null)
        {
            return "Invalid block ID";
        }
        final Block block = Block.blocksList[blockID];
        int amount = 1;
        if (!args.isEmpty())
        {
            amount = parseInt(args.get(0));
        }

        final ItemStack itemStack = new ItemStack(block, amount, 0);
        MC.thePlayer.inventory.setInventorySlotContents(MC.thePlayer.inventory.currentItem, itemStack);
        return String.format("Gave %s %s", MC.thePlayer.username, itemStack);
    }
}
