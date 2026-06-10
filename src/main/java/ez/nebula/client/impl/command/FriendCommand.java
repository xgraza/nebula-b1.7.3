package ez.nebula.client.impl.command;

import ez.nebula.client.api.manager.command.Command;
import ez.nebula.client.api.manager.command.trait.CommandManifest;
import ez.nebula.client.core.Nebula;

import java.util.Collection;
import java.util.List;

/**
 * @author xgraza
 * @since 6/9/26
 */
@CommandManifest(aliases = {"friend", "fr", "fren"},
        description = "Adds or removes a friend",
        syntax = "(username)")
public final class FriendCommand extends Command
{
    @Override
    public String dispatch(List<String> args)
    {
        if (args.isEmpty())
        {
            final Collection<String> friendList = Nebula.INSTANCE.getFriendManager().getAll();
            if (friendList.isEmpty())
            {
                return "Your friends list is empty :(";
            }
            return String.format("Friends (%s): %s", friendList.size(), String.join(", ", friendList));
        }
        final String name = args.get(0);
        if (Nebula.INSTANCE.getFriendManager().isFriend(name))
        {
            Nebula.INSTANCE.getFriendManager().unregisterFriend(name);
            return String.format("%s is no longer your friend!", name);
        } else
        {
            Nebula.INSTANCE.getFriendManager().registerFriend(name);
            return String.format("%s is now your friend!", name);
        }
    }
}
