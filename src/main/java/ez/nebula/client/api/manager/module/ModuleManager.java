package ez.nebula.client.api.manager.module;

import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.api.manager.ITypedManager;
import ez.nebula.client.api.manager.module.trait.ModuleInstance;
import ez.nebula.client.core.Nebula;
import ez.nebula.client.impl.module.combat.AntiKnockbackModule;
import ez.nebula.client.impl.module.combat.KillAuraModule;
import ez.nebula.client.impl.module.exploit.FastPortalModule;
import ez.nebula.client.impl.module.exploit.PortalGUIModule;
import ez.nebula.client.impl.module.exploit.TimerModule;
import ez.nebula.client.impl.module.movement.*;
import ez.nebula.client.impl.module.player.NoFallModule;
import ez.nebula.client.impl.module.render.*;
import ez.nebula.client.impl.module.world.AntiCactusModule;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class ModuleManager implements ITypedManager<Module>
{
    private final List<Module> moduleList = new ArrayList<>();
    private final ModuleConfig defaultConfig = new ModuleConfig(this, "default");

    @Override
    public void init()
    {
        if (!getSaveDirectory().exists() || !getSaveDirectory().isDirectory())
        {
            if (getSaveDirectory().mkdir())
            {
                Logger.info("Successfully created %s", getSaveDirectory().getAbsolutePath());
            } else
            {
                Logger.error("Failed to create configs directory");
                throw new RuntimeException("Failed to create " + getSaveDirectory());
            }
        }

        // Combat
        registerModule(AntiKnockbackModule.class);
        registerModule(KillAuraModule.class);

        // Exploit
        registerModule(FastPortalModule.class);
        registerModule(PortalGUIModule.class);
        registerModule(TimerModule.class);

        // Movement
        registerModule(FlyModule.class);
        registerModule(IceSpeedModule.class);
        registerModule(JesusModule.class);
        registerModule(NoPushModule.class);
        registerModule(SpeedModule.class);
        registerModule(StepModule.class);

        // Player
        registerModule(NoFallModule.class);

        // Render
        registerModule(ClickGUIModule.class);
        registerModule(FoVModule.class);
        registerModule(FullbrightModule.class);
        registerModule(HUDModule.class);
        registerModule(NoRenderModule.class);
        registerModule(NoWeatherModule.class);
        registerModule(XRayModule.class);

        // World
        registerModule(AntiCactusModule.class);

        Logger.info("Loaded %s modules", moduleList.size());

        Nebula.INSTANCE.getConfigManager().addConfiguration(defaultConfig);
        Logger.info("Added module config \"%s\" to config manager", defaultConfig.getFile());
    }

    @Override
    public List<Module> getAll()
    {
        return moduleList;
    }

    public void registerModule(final Class<? extends Module> clazz)
    {
        Module module = null;
        for (final Field field : clazz.getDeclaredFields())
        {
            if (field.isAnnotationPresent(ModuleInstance.class) && clazz.isAssignableFrom(field.getType()))
            {
                field.setAccessible(true);
                try
                {
                    module = (Module) field.get(null);
                    Logger.debug("Using %s for module instance", field);
                } catch (IllegalAccessException e)
                {
                    Logger.error("Failed to get instance from static method, creating new instance for %s",
                            clazz.getName(), e);
                }
                break;
            }
        }

        if (module == null)
        {
            try
            {
                module = (Module) clazz.getConstructors()[0].newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
            {
                Logger.error("Failed to create %s instance", clazz.getName(), e);
                throw new RuntimeException(e);
            }
        }

        module.init();
        moduleList.add(module);
    }

    public File getSaveDirectory()
    {
        return new File(Nebula.INSTANCE.getNebulaDirectory(), "configs");
    }
}
