package ez.nebula.client.impl.gui.module;

import ez.nebula.client.api.manager.key.Key;
import ez.nebula.client.api.manager.key.trait.Device;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.render.component.IComponent;
import ez.nebula.client.api.setting.EnumSetting;
import ez.nebula.client.api.setting.NumberSetting;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.impl.gui.module.setting.BooleanComponent;
import ez.nebula.client.impl.gui.module.setting.EnumComponent;
import ez.nebula.client.impl.gui.module.setting.KeyComponent;
import ez.nebula.client.impl.gui.module.setting.NumberComponent;
import ez.nebula.client.util.render.RenderUtil;
import ez.nebula.client.util.text.FormattingUtil;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class ModuleComponent implements IComponent
{
    private static final int TOGGLED_COLOR = new Color(144, 80, 223).getRGB();
    private static final int HEADER_COLOR = new Color(35, 35, 35).getRGB();

    private final List<IComponent> childrenList = new LinkedList<>();
    private final Module module;

    private double x, y, width, height;
    private boolean opened;

    @SuppressWarnings("unchecked")
    public ModuleComponent(Module module)
    {
        this.module = module;

        getChildren().add(new KeyComponent("Bind", module.getKey()));

        for (final Setting<?> setting : module.getSettings())
        {
            if (Boolean.class.isAssignableFrom(setting.getType()))
            {
                getChildren().add(new BooleanComponent((Setting<Boolean>) setting));
            } else if (Enum.class.isAssignableFrom(setting.getType()))
            {
                getChildren().add(new EnumComponent((EnumSetting<?>) setting));
            } else if (Number.class.isAssignableFrom(setting.getType()))
            {
                getChildren().add(new NumberComponent((NumberSetting<?>) setting));
            } else if (Key.class.isAssignableFrom(setting.getType()))
            {
                getChildren().add(new KeyComponent(setting.getName(), (Key) setting.getValue()));
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        int color = module.isToggled() ? TOGGLED_COLOR : HEADER_COLOR;
        RenderUtil.renderRectangle(x, y, width, getHeight(), color);
        final int textY = (int) (y + getPadding() + (height / 2.0) - 9 / 2.0);
        getFont().drawStringWithShadow(module.getManifest().name(), (int) (x + (getPadding() * 2)), textY, -1);

        if (getChildren().size() > 2)
        {
            final String text = opened ? "-" : "+";
            final int textWidth = getFont().getStringWidth(text);
            getFont().drawStringWithShadow(text, (int) (x + width - getPadding() - textWidth), textY, 0xAAAAAA);
        }

        if (opened)
        {
            RenderUtil.renderRectangle(x + getPadding(), y + height, width - (getPadding() * 2), getHeight() - height - getPadding(), HEADER_COLOR);

            double posY = getY() + height + getPadding();
            for (final IComponent component : getChildren())
            {
                component.setX(x + (getPadding() * 2));
                component.setY(posY);
                component.setWidth(width - (getPadding() * 4));
                component.setHeight(height);

                component.render(mouseX, mouseY);

                posY += component.getHeight() + getPadding();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (isMouseIn(mouseX, mouseY, x, y, width, height))
        {
            if (mouseButton == 0)
            {
                module.toggle();
                playButtonClick();
            } else if (mouseButton == 1)
            {
                opened = !opened;
                playButtonClick();
            }
        } else
        {
            if (!opened)
            {
                return;
            }
            for (final IComponent component : getChildren())
            {
                if (component.isMouseIn(mouseX, mouseY))
                {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        if (!opened)
        {
            return;
        }
        for (final IComponent component : getChildren())
        {
            component.keyTyped(typedChar, keyCode);
        }
    }

    @Override public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    @Override public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    @Override public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    @Override public double getHeight()
    {
        if (opened)
        {
            double h = height + getPadding();
            for (final IComponent component : getChildren())
            {
                h += component.getHeight() + getPadding();
            }
            return h + getPadding();
        }
        return height;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public List<IComponent> getChildren()
    {
        return childrenList;
    }
}
