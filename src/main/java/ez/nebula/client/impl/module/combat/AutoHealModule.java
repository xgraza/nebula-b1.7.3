package ez.nebula.client.impl.module.combat;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventUpdate;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.core.Nebula;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet15Place;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "AutoHeal",
        description = "Automatically eats food to heal your hunger",
        category = ModuleCategory.COMBAT)
public final class AutoHealModule extends Module
{
    private final Setting<Integer> minHealthSetting = numberBuilder("Health", 18)
            .setMin(1)
            .setMax(19)
            .setScale(1)
            .setDescription("The minimum health needed to heal")
            .build();
    private final Setting<Boolean> noWasteSetting = builder("No Waste", false)
            .setDescription("If to only use food if it'll use its full heal amount")
            .build();

    @Override
    public void onDisable()
    {
        super.onDisable();
        if (MC.thePlayer != null)
        {
            Nebula.INSTANCE.getInventoryManager().syncSlot();
        }
    }

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        if (minHealthSetting.getValue() < MC.thePlayer.health)
        {
            return;
        }
        final int slot = getNextFoodSlot();
        if (slot == -1)
        {
            return;
        }
        Nebula.INSTANCE.getInventoryManager().setSlot(slot);
        if (MC.isMultiplayerWorld())
        {
            MC.getSendQueue().addToSendQueue(new Packet15Place(-1, -1, -1, 255, null));
        } else
        {
            MC.playerController.sendUseItem(MC.thePlayer, MC.theWorld, Nebula.INSTANCE.getInventoryManager().getStack());
        }
        Nebula.INSTANCE.getInventoryManager().syncSlot();
    };

    private int getNextFoodSlot()
    {
        int slot = -1, healAmount = 0;
        for (int i = 0; i < 9; ++i)
        {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemFood))
            {
                continue;
            }
            final ItemFood food = (ItemFood) stack.getItem();
            final int heal = food.getHealAmount();
            if (noWasteSetting.getValue())
            {
                final int healthDiff = MC.thePlayer.health - heal;
                if (healthDiff > 1)
                {
                    continue;
                }
            }

            if (slot == -1 || heal > healAmount)
            {
                slot = i;
                healAmount = heal;
            }
        }
        return slot;
    }
}
