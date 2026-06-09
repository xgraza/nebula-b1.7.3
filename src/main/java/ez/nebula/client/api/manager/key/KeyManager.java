package ez.nebula.client.api.manager.key;

import ez.nebula.client.api.listener.EventBus;
import ez.nebula.client.api.manager.ITypedManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class KeyManager implements ITypedManager<Key>
{
    private final List<Key> keyList = new ArrayList<>();

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
}
