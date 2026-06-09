package ez.nebula.client.api.listener.event;

import ez.nebula.client.api.listener.Event;

public final class EventKey extends Event
{
    private final int keyCode;

    public EventKey(int keyCode)
    {
        this.keyCode = keyCode;
    }

    public int getKeyCode()
    {
        return keyCode;
    }
}
