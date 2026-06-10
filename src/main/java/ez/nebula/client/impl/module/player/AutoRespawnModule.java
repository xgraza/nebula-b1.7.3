package ez.nebula.client.impl.module.player;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventUpdate;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import net.minecraft.src.Packet9Respawn;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "AutoRespawn",
        description = "Automatically respawns you when you die",
        category = ModuleCategory.PLAYER)
public final class AutoRespawnModule extends Module
{
    private final Setting<Dimension> dimensionSetting = enumBuilder("Dimension", Dimension.LAST)
            .setDescription("Which dimension to respawn you in")
            .build();

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        if (MC.thePlayer.isDead || MC.thePlayer.health <= 0)
        {
            byte dimension = (byte) MC.thePlayer.dimension;
            switch (dimensionSetting.getValue())
            {
                case OVERWORLD:
                    dimension = 0;
                    break;
                case NETHER:
                    dimension = -1;
                    break;
            }
            MC.getSendQueue().addToSendQueue(new Packet9Respawn(dimension));
        }
    };

    private enum Dimension
    {
        LAST, OVERWORLD, NETHER
    }
}
