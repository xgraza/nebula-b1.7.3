package ez.nebula.client.impl.module.world;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventUpdate;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.core.Nebula;
import net.minecraft.src.*;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "BlockFly",
        description = "Places blocks rapidly under you to make it look like your flying",
        category = ModuleCategory.WORLD)
public final class BlockFlyModule extends Module
{
    private static final int[][] OFFSETS = {
            { 0, -1, 0 }, // 0
            { 0, 1, 0 }, // 1
            { 0, 0, -1 }, // 2
            { 0, 0, 1 }, // 3
            { -1, 0, 0 }, // 4
            { 1, 0, 0 } // 5
    };

    private final Setting<Boolean> towerSetting = builder("Tower", false)
            .setDescription("If to use velocity to build up faster")
            .build();

    @Override
    public void onDisable()
    {
        super.onDisable();
        if (MC.thePlayer != null)
        {
            Nebula.INSTANCE.getInventoryManager().syncSlot();
        }
    }

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        final int slot = getSlot();
        if (slot == -1)
        {
            return;
        }
        final BlockData blockData = getBlockData();
        if (blockData == null || blockData.face == -1)
        {
            return;
        }
        Nebula.INSTANCE.getInventoryManager().setSlot(slot);
        if (MC.playerController.sendPlaceBlock(MC.thePlayer, MC.theWorld, MC.thePlayer.inventory.getStackInSlot(slot), blockData.x, blockData.y, blockData.z, blockData.face))
        {
            MC.thePlayer.swingItem();
            if (towerSetting.getValue() && MC.gameSettings.keyBindJump.isKeyDown())
            {
                MC.thePlayer.motionY = 0.42f;
            }
        }
        Nebula.INSTANCE.getInventoryManager().syncSlot();
    };

    private int getSlot()
    {
        for (int i = 0; i < 9; ++i)
        {
            final ItemStack itemStack = MC.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize != 0)
            {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                final Block block = Block.blocksList[itemBlock.blockID];
                if (block.blockMaterial.isSolid())
                {
                    return i;
                }
            }
        }
        return -1;
    }

    // absolutely INSANE code!!!
    private BlockData getBlockData()
    {
        final int originX = (int) Math.floor(MC.thePlayer.posX);
        final int originY = (int) Math.floor(MC.thePlayer.boundingBox.minY) - 1;
        final int originZ = (int) Math.floor(MC.thePlayer.posZ);

        for (int face = 0; face < OFFSETS.length; ++face)
        {
            final int[] offsets = OFFSETS[face];
            if (!isReplaceable(MC.theWorld.getBlockMaterial(originX + offsets[0], originY + offsets[1], originZ + offsets[2])))
            {
                final BlockData blockData = new BlockData();
                blockData.x = originX + offsets[0];
                blockData.y = originY + offsets[1];
                blockData.z = originZ + offsets[2];
                blockData.face = getOppositeFace(face);
                return blockData;
            }
        }

        for (final int[] offsets : OFFSETS)
        {
            if (isReplaceable(MC.theWorld.getBlockMaterial(originX + offsets[0], originY + offsets[1], originZ + offsets[2])))
            {
                for (int face2 = 0; face2 < OFFSETS.length; ++face2)
                {
                    final int[] offsets2 = OFFSETS[face2];
                    if (!isReplaceable(MC.theWorld.getBlockMaterial((originX + offsets[0]) + offsets2[0], (originY + offsets[1]) + offsets2[1], (originZ + offsets[2]) + offsets2[2])))
                    {
                        final BlockData blockData = new BlockData();
                        blockData.x = (originX + offsets[0]) + offsets2[0];
                        blockData.y = (originY + offsets[1]) + offsets2[1];
                        blockData.z = (originZ + offsets[2]) + offsets2[2];
                        blockData.face = getOppositeFace(face2);
                        return blockData;
                    }
                }
            }
        }

        return null;
    }

    private int getOppositeFace(final int face)
    {
        if (face == 0)
        {
            return 1;
        }
        if (face == 1)
        {
            return 0;
        }
        if (face == 2)
        {
            return 3;
        }
        if (face == 3)
        {
            return 2;
        }
        if (face == 4)
        {
            return 5;
        }
        if (face == 5)
        {
            return 4;
        }
        return -1;
    }

    private boolean isReplaceable(final Material material)
    {
        return material == Material.air || material == Material.water || material == Material.lava || material == Material.snow;
    }

    public static final class BlockData
    {
        public int x, y, z, face;
    }
}
