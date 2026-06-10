package ez.nebula.client.api.manager.server;

import ez.nebula.client.api.Initiable;
import ez.nebula.client.api.listener.EventBus;
import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventChangeWorld;
import ez.nebula.client.api.listener.event.EventPacket;
import ez.nebula.client.api.listener.event.EventUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet16BlockItemSwitch;

/**
 * @author xgraza
 * @since 6/9/26
 */
public final class InventoryManager implements Initiable
{
    private static final Minecraft MC = Minecraft.getMinecraft();

    private int slot, oldSlot = -1;

    @Subscribe
    private final EventListener<EventPacket> packetEventListener = event ->
    {
        if (event.getPacket() instanceof Packet16BlockItemSwitch)
        {
            slot = ((Packet16BlockItemSwitch) event.getPacket()).id;
        }
    };

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        if (!MC.isMultiplayerWorld())
        {
            slot = MC.thePlayer.inventory.currentItem;
        }
    };

    @Subscribe
    private final EventListener<EventChangeWorld> changeWorldEventListener = event ->
    {
        slot = 0;
        oldSlot = -1;
    };

    @Override
    public void init()
    {
        EventBus.subscribe(this);
    }

    public void setSlot(final int slot)
    {
        if (oldSlot == -1)
        {
            oldSlot = this.slot;
        }
        this.slot = slot;
        if (MC.isMultiplayerWorld())
        {
            MC.getSendQueue().addToSendQueue(new Packet16BlockItemSwitch(slot));
        } else
        {
            MC.thePlayer.inventory.currentItem = slot;
        }
    }

    public void syncSlot()
    {
        if (!MC.isMultiplayerWorld() && oldSlot != -1)
        {
            setSlot(oldSlot);
            oldSlot = -1;
            return;
        }

        if (slot != MC.thePlayer.inventory.currentItem)
        {
            setSlot(MC.thePlayer.inventory.currentItem);
        }
    }

    public ItemStack getStack()
    {
        if (MC.isMultiplayerWorld())
        {
            return MC.thePlayer.inventory.getStackInSlot(slot);
        }
        return MC.thePlayer.inventory.getCurrentItem();
    }

    public int getSlot()
    {
        return slot;
    }
}
