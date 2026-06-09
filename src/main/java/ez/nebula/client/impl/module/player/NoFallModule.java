package ez.nebula.client.impl.module.player;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventPacket;
import ez.nebula.client.api.listener.event.EventUpdate;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.util.minecraft.MoveUtil;
import net.minecraft.src.Packet10Flying;

/**
 * @author xgraza
 * @since 6/8/26
 */
@ModuleManifest(name = "NoFall",
        description = "Negates fall damage",
        category = ModuleCategory.PLAYER)
public final class NoFallModule extends Module
{
    private final Setting<Mode> modeSetting = enumBuilder("Mode", Mode.VANILLA)
            .setDescription("How to negate fall damage")
            .build();

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        MC.thePlayer.fallDistance = 0.0f;
        if (modeSetting.getValue() == Mode.EXTRA_PACKETS && MC.isMultiplayerWorld())
        {
            MC.getSendQueue().addToSendQueue(new Packet10Flying(true));
        }
    };

    @Subscribe
    private final EventListener<EventPacket> packetEventListener = event ->
    {
        if (event.getPacket() instanceof Packet10Flying && event.isClient() && modeSetting.getValue() == Mode.PACKET)
        {
            final Packet10Flying packet = (Packet10Flying) event.getPacket();
            packet.yPosition = MoveUtil.getMathGround(packet.yPosition);
            packet.stance = MoveUtil.getMathGround(packet.stance);
            packet.onGround = true;
        }
    };

    private enum Mode
    {
        VANILLA, PACKET, EXTRA_PACKETS
    }
}
