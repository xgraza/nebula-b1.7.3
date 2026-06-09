package ez.nebula.client.api.manager.account;

import ez.nebula.client.api.manager.ITypedManager;
import ez.nebula.client.core.Nebula;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xgraza
 * @since 6/9/26
 */
public final class AccountManager implements ITypedManager<Account>
{
    private static final Minecraft MC = Minecraft.getMinecraft();

    private final List<Account> accountList = new ArrayList<>();

    @Override
    public void init()
    {
        Nebula.INSTANCE.getConfigManager()
                .addConfiguration(new AccountConfig(this));
    }

    @Override
    public Collection<Account> getAll()
    {
        return accountList;
    }

    public void registerAccount(final Account account)
    {
        accountList.add(account);
    }

    public void removeAccount(final Account account)
    {
        accountList.remove(account);
    }

    public void clearAccounts()
    {
        accountList.clear();
    }

    public boolean isLoggedInAs(final String username)
    {
        return MC.session.username.equals(username);
    }

    public void login(final Account account)
    {
        if (account.isCracked())
        {
            MC.session = new Session(account.getDisplayName(), "");
        } else
        {

        }
    }

    public Account getLastLoggedIntoAccount()
    {
        return null;
    }

    public File getSaveFile()
    {
        return new File(Nebula.INSTANCE.getNebulaDirectory(), "accounts.json");
    }
}
