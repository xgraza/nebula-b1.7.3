package ez.nebula.client.impl.gui.module.setting;

import ez.nebula.client.api.render.component.IComponent;
import ez.nebula.client.api.setting.EnumSetting;
import ez.nebula.client.util.render.RenderUtil;
import ez.nebula.client.util.text.FormattingUtil;

import java.awt.Color;
import java.util.Collection;

public final class EnumComponent implements IComponent
{
    private static final int HEADER_COLOR = new Color(35, 35, 35).getRGB();

    private final EnumSetting<?> setting;
    private double x, y, width, height;

    public EnumComponent(final EnumSetting<?> setting)
    {
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        RenderUtil.renderRectangle(x, y, width, height, HEADER_COLOR);
        final int textY = (int) (y + getPadding() + (height / 2.0) - 9 / 2.0);
        getFont().drawStringWithShadow(setting.getName(), (int) (x + (getPadding() * 2)), textY, -1);
        final String name = FormattingUtil.formatEnum(setting.getValue());
        final int textWidth = getFont().getStringWidth(name);
        getFont().drawStringWithShadow(name, (int) (x + width - getPadding() - textWidth), textY, 0xAAAAAA);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            playButtonClick();
            setting.nextValue();
        } else if (mouseButton == 1)
        {
            setting.previousValue();
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
