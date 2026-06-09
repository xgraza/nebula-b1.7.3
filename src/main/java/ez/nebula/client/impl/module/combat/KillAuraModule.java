package ez.nebula.client.impl.module.combat;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventUpdate;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.core.Nebula;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;

import java.util.Comparator;
import java.util.List;

@ModuleManifest(name = "KillAura",
        description = "Attacks entities around you",
        category = ModuleCategory.COMBAT)
public final class KillAuraModule extends Module
{
    private final Setting<Mode> modeSetting = enumBuilder("Mode", Mode.SINGLE)
            .setDescription("How to pick a target")
            .build();
    private final Setting<Boolean> tickSetting = builder("Tick", true)
            .setDescription("If to hit the target every tick")
            .build();
    private final Setting<Double> rangeSetting = numberBuilder("Range", 4.5)
            .setMin(1.0)
            .setMax(6.0)
            .setScale(0.1)
            .setDescription("How far to reach")
            .build();
    private final Setting<Boolean> wallsSetting = builder("Walls", true)
            .setDescription("If to attack through solid blocks")
            .build();

    private EntityLiving target;

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        target = getNextTarget();
        if (target == null)
        {
            return;
        }

        if (canAttack())
        {
            MC.thePlayer.swingItem();
            MC.playerController.attackEntity(MC.thePlayer, target);
        }
    };

    private boolean canAttack()
    {
        if (target == null)
        {
            return false;
        }
        if (tickSetting.getValue())
        {
            return true;
        }
        return target.heartsLife <= target.heartsHalvesLife / 2.0f;
    }

    private EntityLiving getNextTarget()
    {
        if (modeSetting.getValue() == Mode.SWITCH || !isEntityValid(target))
        {
            return (EntityLiving) ((List<Entity>) MC.theWorld.loadedEntityList)
                    .stream()
                    .filter(this::isEntityValid)
                    .min(Comparator.comparingDouble((entity) -> MC.thePlayer.getDistanceToEntity(entity)))
                    .orElse(null);
        }
        return target;
    }

    private boolean isEntityValid(final Entity entity)
    {
        if (!(entity instanceof EntityLiving) || entity.isDead || ((EntityLiving) entity).health <= 0.0f || MC.thePlayer.equals(entity))
        {
            return false;
        }
        final double distance = MC.thePlayer.getDistanceToEntity(entity);
        if (distance > rangeSetting.getValue())
        {
            return false;
        }
        if (!wallsSetting.getValue() && !MC.thePlayer.canEntityBeSeen(entity))
        {
            return false;
        }
        if (entity instanceof EntityPlayer
                && Nebula.INSTANCE.getFriendManager().isFriend(((EntityPlayer) entity).username))
        {
            return false;
        }
        return true;
    }

    private enum Mode
    {
        SINGLE, SWITCH
    }
}
