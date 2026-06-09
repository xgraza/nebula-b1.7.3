package ez.nebula.client.impl.module.combat;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventPacket;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import net.minecraft.src.Packet28EntityVelocity;

/**
 * @author xgraza
 * @since 6/8/26
 */
@ModuleManifest(name = "AntiKnockback",
        description = "Negates velocity sent by the server",
        category = ModuleCategory.COMBAT)
public final class AntiKnockbackModule extends Module
{
    @ModuleInstance
    public static final AntiKnockbackModule INSTANCE = new AntiKnockbackModule();

    public final Setting<Boolean> explosionsSetting = builder("Explosions", true)
            .setDescription("If to ignore knockback from explosions")
            .build();

    @Subscribe
    private final EventListener<EventPacket> packetEventListener = event ->
    {
        if (event.isClient())
        {
            return;
        }
        if (event.getPacket() instanceof Packet28EntityVelocity)
        {
            final Packet28EntityVelocity packet = (Packet28EntityVelocity) event.getPacket();
            if (packet.entityId == MC.thePlayer.entityId)
            {
                event.cancel();
            }
        }
    };
}
