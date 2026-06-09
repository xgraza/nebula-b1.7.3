package ez.nebula.client.impl.module.render;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "NoRender",
        description = "Removes annoying render effects",
        category = ModuleCategory.RENDER)
public final class NoRenderModule extends Module
{
    @ModuleInstance
    public static final NoRenderModule INSTANCE = new NoRenderModule();

    public final Setting<Boolean> cloudsSetting = builder("Clouds", false)
            .setDescription("If to disable cloud rendering")
            .build();
    public final Setting<Boolean> underWaterSetting = builder("Under Water", false)
            .setDescription("If to disable the water overlay when submerged")
            .build();
    public final Setting<Boolean> blocksSetting = builder("Blocks", false)
            .setDescription("If to remove the block overlay when suffocating")
            .build();
    public final Setting<Boolean> fireSetting = builder("Fire", false)
            .setDescription("If to remove the burning overlay when on fire")
            .build();
    public final Setting<Boolean> hurtCameraSetting = builder("Hurt Camera", false)
            .setDescription("If to disable the camera tilt when taking damage")
            .build();
    public final Setting<Boolean> pumpkinSetting = builder("Pumpkin", false)
            .setDescription("If to remove the pumpkin overlay when wearing a pumpkin")
            .build();
    public final Setting<Boolean> achievementsSetting = builder("Achievements", false)
            .setDescription("If to disable the render overlay for acheivements/tutorial")
            .build();
}
