package ez.nebula.client.impl.module.player;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventClickBlock;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;

/**
 * @author xgraza
 * @since 6/10/26
 */
@ModuleManifest(name = "AutoTool",
        description = "Automatically switches to the best tool",
        category = ModuleCategory.PLAYER)
public final class AutoToolModule extends Module
{
    @Subscribe
    private final EventListener<EventClickBlock> clickBlockEventListener = event ->
    {
        final int slot = getBestTool(event.getX(), event.getY(), event.getZ());
        if (slot == -1)
        {
            return;
        }
        MC.thePlayer.inventory.currentItem = slot;
    };

    public static int getBestTool(final int x, final int y, final int z)
    {
        final int blockID = MC.theWorld.getBlockId(x, y, z);
        final Block block = Block.blocksList[blockID];
        if (block == null)
        {
            return -1;
        }

        float bestToolStrength = 0.0f;
        int slot = -1;

        for (int i = 0; i < 9; ++i)
        {
            final ItemStack itemStack = MC.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null)
            {
                continue;
            }

            float strength = getStrength(itemStack, block);
            if (strength > 0.0f && bestToolStrength < strength)
            {
                slot = i;
                bestToolStrength = strength;
            }
        }
        return slot;
    }

    public static float getStrength(final ItemStack itemStack, final Block block)
    {
        if (block.getHardness() < 0.0f)
        {
            return 0.0f;
        }
        float str = itemStack == null ? 1.0f : itemStack.getStrVsBlock(block);
        if (itemStack != null && itemStack.getItem().canHarvestBlock(block))
        {
            return str / block.getHardness() / 30.0f;
        }
        return 1.0f / block.getHardness() / 100.0f;
    }
}
