package ez.nebula.client.util.render;

import net.minecraft.client.Minecraft;

import static net.minecraft.src.Tessellator.instance;
import static org.lwjgl.opengl.GL11.*;

public final class RenderUtil
{
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static void renderRectangle(double x, double y, double width, double height, int color)
    {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(770, 771);
        setGLColor(color);

        instance.startDrawingQuads();
        instance.addVertex(x, y, 0.0);
        instance.addVertex(x, y + height, 0.0);
        instance.addVertex(x + width, y + height, 0.0);
        instance.addVertex(x + width, y, 0.0);
        instance.draw();

        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public static void renderOutline(double x, double y, double width, double height, float lineWidth, int color)
    {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(770, 771);
        setGLColor(color);
        glLineWidth(lineWidth);

        instance.startDrawing(GL_LINE_LOOP);
        instance.addVertex(x, y, 0.0);
        instance.addVertex(x, y + height, 0.0);
        instance.addVertex(x + width, y + height, 0.0);
        instance.addVertex(x + width, y, 0.0);
        instance.draw();

        glLineWidth(1.0f);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public static void setTessellatorColor(final int color)
    {
        final float red = (float) (color >> 16 & 255) / 255.0F;
        final float green = (float) (color >> 8 & 255) / 255.0F;
        final float blue = (float) (color & 255) / 255.0F;
        final float alpha = (float) (color >> 24 & 255) / 255.0F;
        instance.setColorRGBA_F(red, green, blue, alpha);
    }

    public static void setGLColor(final int color)
    {
        final float red = (float) (color >> 16 & 255) / 255.0F;
        final float green = (float) (color >> 8 & 255) / 255.0F;
        final float blue = (float) (color & 255) / 255.0F;
        final float alpha = (float) (color >> 24 & 255) / 255.0F;
        glColor4f(red, green, blue, alpha);
    }

    public static void setGLColorOpaque(final int color)
    {
        final float red = (float) (color >> 16 & 255) / 255.0F;
        final float blue = (float) (color & 255) / 255.0F;
        final float green = (float) (color >> 8 & 255) / 255.0F;
        glColor4f(red, green, blue, 1.0f);
    }
}
