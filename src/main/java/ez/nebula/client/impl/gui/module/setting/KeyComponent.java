package ez.nebula.client.impl.gui.module.setting;

import ez.nebula.client.api.manager.key.Key;
import ez.nebula.client.api.manager.key.trait.Device;
import ez.nebula.client.api.render.component.IComponent;
import ez.nebula.client.util.render.RenderUtil;
import ez.nebula.client.util.text.FormattingUtil;
import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.util.Collection;

public final class KeyComponent implements IComponent
{
    private static final int HEADER_COLOR = new Color(35, 35, 35).getRGB();

    private final String name;
    private final Key key;
    private double x, y, width, height;
    private boolean listening;

    public KeyComponent(final String name, final Key key)
    {
        this.name = name;
        this.key = key;
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        RenderUtil.renderRectangle(x, y, width, height, HEADER_COLOR);
        final int textY = (int) (y + getPadding() + (height / 2.0) - 9 / 2.0);
        getFont().drawStringWithShadow(listening ? "Waiting for input..." : name, (int) (x + (getPadding() * 2)), textY, -1);
        if (!listening)
        {
            final String name = FormattingUtil.formatKey(key.getKeyCode(), key.getDevice().equals(Device.KEYBOARD));
            final int textWidth = getFont().getStringWidth(name);
            getFont().drawStringWithShadow(name, (int) (x + width - getPadding() - textWidth), textY, 0xAAAAAA);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (listening)
        {
            if (!isMouseIn(mouseX, mouseY))
            {
                key.setKeyCode(mouseButton);
                key.setDevice(Device.MOUSE);
                listening = false;
                return;
            }
        }

        if (mouseButton == 0)
        {
            listening = !listening;
        } else if (mouseButton == 1)
        {
            listening = false;
            key.setKeyCode(Keyboard.KEY_NONE);
            key.setDevice(Device.KEYBOARD);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        if (!listening)
        {
            return;
        }
        listening = false;
        key.setDevice(Device.KEYBOARD);
        key.setKeyCode(Math.max(keyCode, Keyboard.KEY_NONE));
    }

    @Override public double getX()
    {
        return x;
    }

    @Override public void setX(double x)
    {
        this.x = x;
    }

    @Override public double getY()
    {
        return y;
    }

    @Override public void setY(double y)
    {
        this.y = y;
    }

    @Override public double getWidth()
    {
        return width;
    }

    @Override public void setWidth(double width)
    {
        this.width = width;
    }

    @Override public double getHeight()
    {
        return height;
    }

    @Override public void setHeight(double height)
    {
        this.height = height;
    }

    @Override
    public Collection<IComponent> getChildren()
    {
        return null;
    }
}
