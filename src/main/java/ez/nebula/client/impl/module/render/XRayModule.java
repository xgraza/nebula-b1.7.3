package ez.nebula.client.impl.module.render;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import net.minecraft.src.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xgraza
 * @since 6/8/26
 */
@ModuleManifest(name = "XRay",
        description = "Allows you to see ores through walls",
        category = ModuleCategory.RENDER)
public final class XRayModule extends Module
{
    @ModuleInstance
    public static final XRayModule INSTANCE = new XRayModule();
    public static final List<Block> XRAY_BLOCK_LIST = new ArrayList<>();
    public static final int[] OLD_BLOCK_LIGHT_VALUES = new int[256];

    static
    {
        XRAY_BLOCK_LIST.add(Block.oreCoal);
        XRAY_BLOCK_LIST.add(Block.oreRedstone);
        XRAY_BLOCK_LIST.add(Block.oreRedstoneGlowing);
        XRAY_BLOCK_LIST.add(Block.oreGold);
        XRAY_BLOCK_LIST.add(Block.oreLapis);
        XRAY_BLOCK_LIST.add(Block.oreIron);
        XRAY_BLOCK_LIST.add(Block.oreDiamond);

        XRAY_BLOCK_LIST.add(Block.blockDiamond);
        XRAY_BLOCK_LIST.add(Block.blockGold);
        XRAY_BLOCK_LIST.add(Block.blockLapis);
        XRAY_BLOCK_LIST.add(Block.blockSteel);
        XRAY_BLOCK_LIST.add(Block.blockBed);

        XRAY_BLOCK_LIST.add(Block.chest);
        XRAY_BLOCK_LIST.add(Block.lockedChest);
        XRAY_BLOCK_LIST.add(Block.ladder);
        XRAY_BLOCK_LIST.add(Block.portal);
        XRAY_BLOCK_LIST.add(Block.tnt);
        XRAY_BLOCK_LIST.add(Block.torchWood);
    }

    @Override
    public void onEnable()
    {
        for (final Block block : XRAY_BLOCK_LIST)
        {
            final int id = block.blockID;
            OLD_BLOCK_LIGHT_VALUES[id] = Block.lightValue[id];
            Block.lightValue[id] = 15;
        }
        reloadRenders();
    }

    @Override
    public void onDisable()
    {
        for (final Block block : XRAY_BLOCK_LIST)
        {
            final int id = block.blockID;
            Block.lightValue[id] = OLD_BLOCK_LIGHT_VALUES[id];
        }
        reloadRenders();
    }

    private void reloadRenders()
    {
        if (MC.theWorld != null)
        {
            MC.renderGlobal.loadRenderers();
        }
    }
}
