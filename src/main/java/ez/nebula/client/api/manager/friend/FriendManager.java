package ez.nebula.client.api.manager.friend;

import ez.nebula.client.api.manager.ITypedManager;
import ez.nebula.client.core.Nebula;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class FriendManager implements ITypedManager<String>
{
    private final Set<String> friendNameSet = new HashSet<>();

    @Override
    public void init()
    {
        Nebula.INSTANCE.getConfigManager()
                .addConfiguration(new FriendConfig(this));
    }

    @Override
    public Collection<String> getAll()
    {
        return friendNameSet;
    }

    public boolean isFriend(final String name)
    {
        return friendNameSet.contains(name);
    }

    public void registerFriend(final String name)
    {
        friendNameSet.add(name);
    }

    public void unregisterFriend(final String name)
    {
        friendNameSet.add(name);
    }

    public void clearFriends()
    {
        friendNameSet.clear();
    }

    public File getSaveFile()
    {
        return new File(Nebula.INSTANCE.getNebulaDirectory(), "friends.json");
    }
}
