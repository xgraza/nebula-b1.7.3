package ez.nebula.client.impl.module.render;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;

/**
 * @author xgraza
 * @since 6/8/26
 * @see {@link net.minecraft.src.World#getBrightness(int, int, int, int)}
 * @see {@link net.minecraft.src.World#getLightBrightness(int, int, int)}
 * @see {@link net.minecraft.src.ChunkCache#getBrightness(int, int, int, int)}
 * @see {@link net.minecraft.src.ChunkCache#getLightBrightness(int, int, int)}
 * @see {@link net.minecraft.src.ChunkCache#getLightValueExt(int, int, int, boolean)}
 */
@ModuleManifest(name = "Fullbright",
        description = "Makes the world bright when otherwise dark",
        category = ModuleCategory.RENDER)
public final class FullbrightModule extends Module
{
    @ModuleInstance
    public static final FullbrightModule INSTANCE = new FullbrightModule();

    @Override
    public void onEnable()
    {
        reloadRenders();
    }

    @Override
    public void onDisable()
    {
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
