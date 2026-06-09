package ez.nebula.client.impl.module.world;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "AntiCactus",
        description = "Prevents you from getting pricked to death by a cactus",
        category = ModuleCategory.WORLD)
public final class AntiCactusModule extends Module
{
    @ModuleInstance
    public static final AntiCactusModule INSTANCE = new AntiCactusModule();
}
