package ez.nebula.client.api.listener.event;

import ez.nebula.client.api.listener.Event;
import net.minecraft.src.Packet;

public final class EventPacket extends Event
{
    private final boolean client;
    private Packet packet;

    public EventPacket(final boolean client, final Packet packet)
    {
        this.client = client;
        this.packet = packet;
    }

    public boolean isClient()
    {
        return client;
    }

    public Packet getPacket()
    {
        return packet;
    }

    public void setPacket(final Packet packet)
    {
        this.packet = packet;
    }
}
