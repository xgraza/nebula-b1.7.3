package ez.nebula.client.impl.module.movement;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventUpdate;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "Step",
        description = "Allows you to step up blocks",
        category = ModuleCategory.MOVEMENT)
public final class StepModule extends Module
{
    private final Setting<Float> heightSetting = numberBuilder("Height", 1.0f)
            .setMin(1.0f)
            .setMax(5.0f)
            .setScale(0.5f)
            .setDescription("How high to step up")
            .build();

    @Override
    public void onDisable()
    {
        super.onDisable();
        if (MC.thePlayer != null)
        {
            MC.thePlayer.stepHeight = 0.5f;
        }
    }

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        MC.thePlayer.stepHeight = heightSetting.getValue();
    };
}
