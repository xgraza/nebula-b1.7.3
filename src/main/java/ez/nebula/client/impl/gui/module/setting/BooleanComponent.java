package ez.nebula.client.impl.gui.module.setting;

import ez.nebula.client.api.render.component.IComponent;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.util.render.RenderUtil;

import java.awt.Color;
import java.util.Collection;

public final class BooleanComponent implements IComponent
{
    private static final int TOGGLED_COLOR = new Color(144, 80, 223).getRGB();
    private static final int HEADER_COLOR = new Color(35, 35, 35).getRGB();

    private final Setting<Boolean> setting;
    private double x, y, width, height;

    public BooleanComponent(final Setting<Boolean> setting)
    {
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        int color = setting.getValue() ? TOGGLED_COLOR : HEADER_COLOR;
        RenderUtil.renderRectangle(x, y, width, height, color);
        final int textY = (int) (y + getPadding() + (height / 2.0) - 9 / 2.0);
        getFont().drawStringWithShadow(setting.getName(), (int) (x + (getPadding() * 2)), textY, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            playButtonClick();
            setting.setValue(!setting.getValue());
        }
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
