package ez.nebula.client.impl.module.render;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;

/**
 * @author xgraza
 * @since 6/8/26
 * @see {@link net.minecraft.src.World#func_27162_g}
 * @see {@link net.minecraft.src.World#func_27166_f}
 */
@ModuleManifest(name = "NoWeather",
        description = "Removes the weather effects (rain, snow, thunder)",
        category = ModuleCategory.RENDER)
public final class NoWeatherModule extends Module
{
    @ModuleInstance
    public static final NoWeatherModule INSTANCE = new NoWeatherModule();

    public final Setting<Boolean> thunderSetting = builder("Thunder", true)
            .setDescription("If to also remove thunder from the world")
            .build();
}
