package ez.nebula.client.impl.module.movement;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventUpdate;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.World;

/**
 * @author xgraza
 * @since 6/8/26
 * @see {@link net.minecraft.src.BlockFluid#getCollisionBoundingBoxFromPool(World, int, int, int)}
 * @see {@link net.minecraft.src.World#getCollidingBoundingBoxes(Entity, AxisAlignedBB)}
 */
@ModuleManifest(name = "Jesus",
        description = "Allows you to walk on water as Jesus did",
        category = ModuleCategory.MOVEMENT)
public final class JesusModule extends Module
{
    @ModuleInstance
    public static final JesusModule INSTANCE = new JesusModule();

    private boolean attemptExit;

    @Override
    public void onDisable()
    {
        super.onDisable();
        attemptExit = false;
    }

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        if (MC.thePlayer.isInWater())
        {
            MC.thePlayer.motionY = 0.11;
            attemptExit = true;
        } else
        {
            if (attemptExit)
            {
                MC.thePlayer.motionY = 0.3;
                attemptExit = false;
            }
        }
    };

    public boolean shouldMakeWaterSolid()
    {
        return !MC.thePlayer.isInWater() && MC.thePlayer.fallDistance <= 3.0f;
    }
}
