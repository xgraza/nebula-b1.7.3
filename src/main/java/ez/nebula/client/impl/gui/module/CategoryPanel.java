package ez.nebula.client.impl.gui.module;

import ez.nebula.client.api.render.component.IComponent;
import ez.nebula.client.util.render.RenderUtil;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public final class CategoryPanel implements IComponent
{
    private static final int TOGGLED_COLOR = new Color(122, 90, 159).getRGB();
    private static final int BACKGROUND_COLOR = new Color(40, 40, 40).getRGB();
    private static final int HEADER_COLOR = new Color(40, 40, 40).getRGB();

    private final List<IComponent> childrenList = new LinkedList<>();
    private final String name;

    private double x, y, width, height;

    public CategoryPanel(final String name, final List<IComponent> components)
    {
        this.name = name;
        childrenList.addAll(components);
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        RenderUtil.renderRectangle(x, y, width, getHeight(), BACKGROUND_COLOR);
        double componentWidth = width - (getPadding() * 2);
        RenderUtil.renderRectangle(x + getPadding(), y + getPadding(), componentWidth, height, BACKGROUND_COLOR);
        RenderUtil.renderOutline(x, y, width, getHeight(), 1.5f, TOGGLED_COLOR);

        final int textY = (int) (y + (getPadding() * 2) + (height / 2.0) - 9 / 2.0);
        getFont().drawStringWithShadow(name, (int) (x + (getPadding() * 2)), textY, -1);

        componentWidth -= (getPadding() * 2);
        double posY = y + height + (getPadding() * 2);
        for (final IComponent component : getChildren())
        {
            component.setX(x + (getPadding() * 2));
            component.setY(posY);
            component.setWidth(componentWidth);
            component.setHeight(height - getPadding());

            component.render(mouseX, mouseY);

            posY += component.getHeight() + getPadding();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        for (final IComponent component : getChildren())
        {
            if (component.isMouseIn(mouseX, mouseY))
            {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        for (final IComponent component : getChildren())
        {
            component.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    @Override
    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    @Override
    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    @Override
    public double getHeight()
    {
        double h = getPadding() + height + getPadding();
        for (final IComponent component : getChildren())
        {
            h += component.getHeight() + getPadding();
        }
        return h + getPadding();
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
