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
@ModuleManifest(name = "FoV",
        description = "Allows you to set your FoV",
        category = ModuleCategory.RENDER)
public final class FoVModule extends Module
{
    @ModuleInstance
    public static final FoVModule INSTANCE = new FoVModule();

    public final Setting<Integer> fovSetting = numberBuilder("FoV", 70)
            .setMin(70)
            .setMax(110)
            .setScale(1)
            .setDescription("The field of view")
            .build();
}
