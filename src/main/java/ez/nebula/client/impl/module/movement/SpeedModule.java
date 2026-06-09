package ez.nebula.client.impl.module.movement;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventMove;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;

@ModuleManifest(name = "Speed",
        description = "Modifies how fast you go",
        category = ModuleCategory.MOVEMENT)
public final class SpeedModule extends Module
{
    private final Setting<Mode> modeSetting = enumBuilder("Mode", Mode.NO_CHEAT)
            .setDescription("The bypass method")
            .build();

    @Subscribe
    private final EventListener<EventMove> moveEventListener = event ->
    {
        switch (modeSetting.getValue())
        {
            case NO_CHEAT:
            {
                break;
            }
        }
    };

    private enum Mode
    {
        NO_CHEAT
    }
}
