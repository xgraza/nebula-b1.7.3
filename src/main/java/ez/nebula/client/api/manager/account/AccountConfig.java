package ez.nebula.client.api.manager.account;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import ez.nebula.client.api.config.IConfig;
import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.util.io.FileUtil;

import java.io.File;

/**
 *
 */
public final class AccountConfig implements IConfig
{
    private final AccountManager manager;

    public AccountConfig(AccountManager manager)
    {
        this.manager = manager;
    }

    @Override
    public String save()
    {
        final JsonArray array = new JsonArray();
        if (manager.getLastLoggedIntoAccount() != null)
        {
            array.add(manager.getLastLoggedIntoAccount().getUsername());
        }
        for (final Account account : manager.getAll())
        {
            array.add(account.toJSON());
        }
        return FileUtil.GSON.toJson(array);
    }

    @Override
    public void load(final String data)
    {
        if (data == null || data.isEmpty())
        {
            return;
        }
        final JsonElement element = JsonParser.parseString(data);
        if (element == null || !element.isJsonArray())
        {
            return;
        }
        String lastLoggedInto = null;
        manager.clearAccounts();
        final JsonArray array = element.getAsJsonArray();
        for (final JsonElement accountElement : array)
        {
            if (accountElement == null)
            {
                continue;
            }
            if (accountElement.isJsonObject())
            {
                final Account account = new Account();
                account.fromJSON(accountElement);
                manager.registerAccount(account);
            } else if (accountElement.isJsonPrimitive() && ((JsonPrimitive) accountElement).isString())
            {
                lastLoggedInto = accountElement.getAsString();
            }
        }
        if (lastLoggedInto != null)
        {
            Account lastLoggedIntoAccount = null;
            for (final Account account : manager.getAll())
            {
                if (account.getUsername().equals(lastLoggedInto))
                {
                    lastLoggedIntoAccount = account;
                    break;
                }
            }
            if (lastLoggedIntoAccount == null)
            {
                return;
            }
            Logger.info("Got last logged into account, logging in now!");
            manager.login(lastLoggedIntoAccount);
        }
    }

    @Override
    public File getFile()
    {
        return manager.getSaveFile();
    }
}
