package ez.nebula.client.api.render.component;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;

import java.util.Collection;
import java.util.Collections;

public interface IComponent
{
    Minecraft MC = Minecraft.getMinecraft();

    double getX();
    void setX(double x);
    double getY();
    void setY(double y);
    double getWidth();
    void setWidth(double width);
    double getHeight();
    void setHeight(double height);

    default double getPadding()
    {
        return 1.5;
    }

    default boolean isVisible()
    {
        return true;
    }

    default IComponent getParent()
    {
        return null;
    }

    default Collection<IComponent> getChildren()
    {
        return Collections.emptyList();
    }

    default FontRenderer getFont()
    {
        return MC.fontRenderer;
    }

    default void playButtonClick()
    {
        MC.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
    }

    default void render(final int mouseX, final int mouseY)
    {

    }

    default void mouseClicked(final int mouseX, final int mouseY, final int mouseButton)
    {

    }

    default void keyTyped(final char typedChar, final int keyCode)
    {

    }

    default boolean isMouseIn(final int mouseX, final int mouseY)
    {
        return getX() <= mouseX && getX() + getWidth() >= mouseX
                && getY() <= mouseY && getY() + getHeight() >= mouseY;
    }

    default boolean isMouseIn(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height)
    {
        return x <= mouseX && x + width >= mouseX
                && y <= mouseY && y + height >= mouseY;
    }
}
