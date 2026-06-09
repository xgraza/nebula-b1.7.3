package ez.nebula.client.api.manager.macro;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ez.nebula.client.api.Toggleable;
import ez.nebula.client.api.config.IJSONSerializable;
import ez.nebula.client.api.manager.key.Key;
import ez.nebula.client.api.manager.key.trait.Device;
import net.minecraft.client.Minecraft;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class Macro implements Toggleable, IJSONSerializable
{
    private static final Minecraft MC = Minecraft.getMinecraft();

    private final Key key;
    private String text;

    public Macro(final int keyCode, final Device device)
    {
        key = new Key(keyCode, device)
        {
            @Override
            public void onToggled()
            {
                toggle();
            }
        };
    }

    @Override
    public void toggle()
    {
        if (text != null && MC.thePlayer != null)
        {
            MC.thePlayer.sendChatMessage(text);
        }
    }

    @Override
    public void setToggled(boolean state)
    {

    }

    @Override
    public boolean isToggled()
    {
        return false;
    }

    public Key getKey()
    {
        return key;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    @Override
    public void fromJSON(JsonElement element)
    {
        if (!element.isJsonObject())
        {
            return;
        }
        final JsonObject object = element.getAsJsonObject();
        if (object.has("key"))
        {
            key.fromJSON(object.get("key").getAsJsonObject());
        }
        if (object.has("text"))
        {
            setText(object.get("text").getAsString());
        }
    }

    @Override
    public JsonElement toJSON()
    {
        final JsonObject object = new JsonObject();
        object.add("key", getKey().toJSON());
        if (text != null)
        {
            object.addProperty("text", text);
        }
        return object;
    }
}
