package ez.nebula.client.api.listener.event;

import ez.nebula.client.api.listener.Event;
import net.minecraft.src.ScaledResolution;

public final class EventRender2D extends Event
{
    private final ScaledResolution resolution;

    public EventRender2D(ScaledResolution resolution)
    {
        this.resolution = resolution;
    }

    public ScaledResolution getResolution()
    {
        return resolution;
    }
}
