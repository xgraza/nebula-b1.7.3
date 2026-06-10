package ez.nebula.client.api.listener.event;

import ez.nebula.client.api.listener.Event;

public class EventClickBlock extends Event
{
    private final int x, y, z, side;

    public EventClickBlock(int x, int y, int z, int side)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = side;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public int getSide()
    {
        return side;
    }
}
