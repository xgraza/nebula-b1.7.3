package ez.nebula.client.impl.module.movement;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "NoPush",
        description = "Prevents blocks from pushing you",
        category = ModuleCategory.MOVEMENT)
public final class NoPushModule extends Module
{
    @ModuleInstance
    public static final NoPushModule INSTANCE = new NoPushModule();

    public final Setting<Boolean> waterSetting = builder("Water", false)
            .setDescription("If to prevent flowing water from pushing you")
            .build();
    public final Setting<Boolean> blocksSetting = builder("Blocks", false)
            .setDescription("If to prevent blocks from pushing you")
            .build();
    public final Setting<Boolean> pistonsSetting = builder("Pistons", false)
            .setDescription("If to prevent extended pistons from pushing you")
            .build();
}
