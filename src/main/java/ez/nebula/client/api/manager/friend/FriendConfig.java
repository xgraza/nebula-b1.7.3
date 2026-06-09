package ez.nebula.client.api.manager.friend;

import ez.nebula.client.api.config.IConfig;

import java.io.File;
import java.util.StringJoiner;

public final class FriendConfig implements IConfig
{
    private final FriendManager manager;

    public FriendConfig(final FriendManager manager)
    {
        this.manager = manager;
    }

    @Override
    public String save()
    {
        final StringJoiner joiner = new StringJoiner("\n");
        for (final String name : manager.getAll())
        {
            joiner.add(name);
        }
        return joiner.toString();
    }

    @Override
    public void load(String data)
    {
        if (data == null || data.isEmpty())
        {
            return;
        }
        manager.clearFriends();
        final String[] lines = data.trim().split("\n");
        for (final String name : lines)
        {
            if (name.isEmpty())
            {
                continue;
            }
            manager.registerFriend(name);
        }
    }

    @Override
    public File getFile()
    {
        return manager.getSaveFile();
    }
}
