package ez.nebula.client.api.manager.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ez.nebula.client.api.config.IJSONSerializable;

public final class Account implements IJSONSerializable
{
    private String username, password, displayName;

    public Account()
    {

    }

    public Account(String username, String password)
    {
        this.username = username;
        this.password = password;
        displayName = username;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public boolean isCracked()
    {
        return password == null || password.isEmpty();
    }

    @Override
    public void fromJSON(final JsonElement element)
    {
        if (!element.isJsonObject())
        {
            return;
        }
        final JsonObject object = element.getAsJsonObject();
        if (!object.has("username"))
        {
            throw new RuntimeException("Empty account!");
        }
        username = object.get("username").getAsString();
        displayName = username;
        if (object.has("displayName"))
        {
            displayName = object.get("displayName").getAsString();
        }
        if (object.has("password"))
        {
            password = object.get("password").getAsString();
        }
    }

    @Override
    public JsonElement toJSON()
    {
        final JsonObject object = new JsonObject();
        object.addProperty("username", username);
        if (password != null && !password.isEmpty())
        {
            object.addProperty("password", password);
        }
        if (displayName != null && !displayName.equals(username))
        {
            object.addProperty("displayName", displayName);
        }
        return object;
    }
}
