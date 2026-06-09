package ez.nebula.client.api.manager.key;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ez.nebula.client.api.config.IJSONSerializable;
import ez.nebula.client.api.manager.key.trait.Device;

/**
 * @author xgraza
 * @since 6/8/26
 */
public abstract class Key implements IJSONSerializable
{
    private int keyCode;
    private Device device;

    public Key(int keyCode, Device device)
    {
        this.keyCode = keyCode;
        this.device = device;
    }

    public abstract void onToggled();

    public int getKeyCode()
    {
        return keyCode;
    }

    public void setKeyCode(int keyCode)
    {
        this.keyCode = keyCode;
    }

    public Device getDevice()
    {
        return device;
    }

    public void setDevice(Device device)
    {
        this.device = device;
    }

    @Override
    public void fromJSON(JsonElement element)
    {
        if (!element.isJsonObject())
        {
            return;
        }
        final JsonObject object = element.getAsJsonObject();
        if (object.has("keyCode"))
        {
            setKeyCode(object.get("keyCode").getAsInt());
        }
        if (object.has("device"))
        {
            setDevice(Device.values()[object.get("device").getAsInt()]);
        }
    }

    @Override
    public JsonElement toJSON()
    {
        final JsonObject object = new JsonObject();
        object.addProperty("keyCode", keyCode);
        object.addProperty("device", device.ordinal());
        return object;
    }
}
