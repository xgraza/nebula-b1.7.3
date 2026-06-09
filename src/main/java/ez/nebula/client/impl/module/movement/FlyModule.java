package ez.nebula.client.impl.module.movement;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventMove;
import ez.nebula.client.api.listener.event.EventPacket;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.util.minecraft.MoveUtil;
import net.minecraft.src.Packet10Flying;

/**
 * @author xgraza
 * @since 6/8/26
 */
@ModuleManifest(name = "Fly",
        description = "Allows you to fly like a little bird",
        category = ModuleCategory.MOVEMENT)
public final class FlyModule extends Module
{

    @Subscribe
    private final EventListener<EventPacket> packetEventListener = event ->
    {
        if (event.getPacket() instanceof Packet10Flying && event.isClient())
        {
            final Packet10Flying packet = (Packet10Flying) event.getPacket();
            packet.yPosition -= (packet.yPosition % 0.015625);
            packet.stance -= (packet.stance % 0.015625);
            packet.onGround = true;
        }
    };

    @Subscribe
    private final EventListener<EventMove> moveEventListener = event ->
    {
        double motionY = -0.1;
        if (MC.gameSettings.keyBindJump.isKeyDown())
        {
            motionY = 0.1;
        } else if (MC.gameSettings.keyBindSneak.isKeyDown())
        {
            motionY = 0.1;
        }

        MoveUtil.setVerticalSpeed(event, motionY);
        MoveUtil.setSpeed(event, MoveUtil.isMoving() ? 0.2 : 0.0);

        MC.thePlayer.onGround = true;
    };
}
