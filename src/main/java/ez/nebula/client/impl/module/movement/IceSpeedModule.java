package ez.nebula.client.impl.module.movement;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import net.minecraft.src.Block;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "IceSpeed",
        description = "Makes you speedy on ice",
        category = ModuleCategory.MOVEMENT)
public final class IceSpeedModule extends Module
{
    @Override
    public void onEnable()
    {
        Block.ice.slipperiness = 0.391f;
    }

    @Override
    public void onDisable()
    {
        Block.ice.slipperiness = 0.98f;
    }
}
