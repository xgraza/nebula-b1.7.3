package ez.nebula.client.api.manager.key;

import ez.nebula.client.api.listener.EventBus;
import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventKey;
import ez.nebula.client.api.manager.ITypedManager;
import ez.nebula.client.api.manager.key.trait.Device;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class KeyManager implements ITypedManager<Key>
{
    private final List<Key> keyList = new ArrayList<>();

    @Subscribe
    private final EventListener<EventKey> keyEventListener = event ->
    {
        if (event.getKeyCode() <= Keyboard.KEY_NONE)
        {
            return;
        }
        for (final Key key : keyList)
        {
            if (key.getDevice() != Device.KEYBOARD || key.getKeyCode() != event.getKeyCode())
            {
                continue;
            }
            key.onToggled();
        }
    };

    @Override
    public void init()
    {
        EventBus.subscribe(this);
    }

    @Override
    public List<Key> getAll()
    {
        return keyList;
    }

    public void registerKey(final Key key)
    {
        keyList.add(key);
    }
}
