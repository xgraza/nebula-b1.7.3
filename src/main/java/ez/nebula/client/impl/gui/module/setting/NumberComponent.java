package ez.nebula.client.impl.gui.module.setting;

import ez.nebula.client.api.render.component.IComponent;
import ez.nebula.client.api.setting.NumberSetting;
import ez.nebula.client.util.math.MathUtil;
import ez.nebula.client.util.render.RenderUtil;
import org.lwjgl.input.Mouse;

import java.awt.Color;
import java.util.Collection;

public final class NumberComponent implements IComponent
{
    private static final int TOGGLED_COLOR = new Color(144, 80, 223).getRGB();

    private final NumberSetting<?> setting;
    private final double diff;

    private double x, y, width, height;
    private boolean draggingSlider;

    public NumberComponent(final NumberSetting<?> setting)
    {
        this.setting = setting;
        if (setting != null)
        {
            this.diff = setting.getMax().doubleValue() - setting.getMin().doubleValue();
        } else
        {
            this.diff = 0.1f;
        }
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        if (draggingSlider)
        {
            setValue(mouseX);
            if (!Mouse.isButtonDown(0))
            {
                draggingSlider = false;
                playButtonClick();
            }
        }

        final double min = setting.getMin().doubleValue();
        final double value = setting.getValue().doubleValue();
        final double barWidth = width * ((value - min) / diff);
        RenderUtil.renderRectangle(x, y, barWidth, height, TOGGLED_COLOR);

        final int textY = (int) (y + getPadding() + (height / 2.0) - 9 / 2.0);
        getFont().drawStringWithShadow(setting.getName(), (int) (x + (getPadding() * 2)), textY, -1);
        final String name = String.valueOf(setting.getValue());
        final int textWidth = getFont().getStringWidth(name);
        getFont().drawStringWithShadow(name, (int) (x + width - getPadding() - textWidth), textY, 0xAAAAAA);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            draggingSlider = !draggingSlider;
            playButtonClick();
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

    @SuppressWarnings("unchecked")
    private void setValue(final int mouseX)
    {
        if (mouseX > x + width || mouseX < x)
        {
            return;
        }
        double value = setting.getMin().doubleValue()
                + diff * (mouseX - getX()) / getWidth();

        final double precision = 1.0 / setting.getScale().doubleValue();
        value = Math.round(value * precision) / precision;
        value = MathUtil.round(value, 2);

        if (value > setting.getMax().doubleValue())
        {
            value = setting.getMax().doubleValue();
        }
        if (value < setting.getMin().doubleValue())
        {
            value = setting.getMin().doubleValue();
        }

        if (setting.getValue() instanceof Integer)
        {
            ((NumberSetting<Integer>) setting).setValue((int) value);
        } else if (setting.getValue() instanceof Long)
        {
            ((NumberSetting<Long>) setting).setValue((long) value);
        } else if (setting.getValue() instanceof Double)
        {
            ((NumberSetting<Double>) setting).setValue(value);
        } else if (setting.getValue() instanceof Float)
        {
            ((NumberSetting<Float>) setting).setValue((float) value);
        }
    }
}
