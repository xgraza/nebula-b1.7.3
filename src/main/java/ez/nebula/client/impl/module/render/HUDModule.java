package ez.nebula.client.impl.module.render;

import ez.nebula.client.BuildConfig;
import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventRender2D;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.core.ClientConfig;

/**
 * @author xgraza
 * @since 6/8/26
 */
@ModuleManifest(name = "HUD",
        description = "Shows important client information",
        category = ModuleCategory.RENDER)
public final class HUDModule extends Module
{
    private final Setting<Boolean> watermarkSetting = builder("Watermark", true)
            .setDescription("If to show the client watermark")
            .build();

    public HUDModule()
    {
        // on by default
        setToggled(true);
    }

    @Subscribe
    private final EventListener<EventRender2D> render2DEventListener = event ->
    {
        if (watermarkSetting.getValue())
        {
            MC.fontRenderer.drawStringWithShadow(BuildConfig.NAME + " " + ClientConfig.VERSION, 2, 2, -1);
        }
    };
}
