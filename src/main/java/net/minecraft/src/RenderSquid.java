package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderSquid extends RenderLiving<EntitySquid> {
	public RenderSquid(ModelBase var1, float var2) {
		super(var1, var2);
	}

	public void doRender(EntitySquid var1, double var2, double var4, double var6, float var8, float var9) {
		super.doRender(var1, var2, var4, var6, var8, var9);
	}

	protected void rotateCorpse(EntitySquid var1, float var2, float var3, float var4) {
		float var5 = var1.field_21088_b + (var1.field_21089_a - var1.field_21088_b) * var4;
		float var6 = var1.field_21086_f + (var1.field_21087_c - var1.field_21086_f) * var4;
		GL11.glTranslatef(0.0F, 0.5F, 0.0F);
		GL11.glRotatef(180.0F - var3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(var5, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.2F, 0.0F);
	}

	protected void preRenderCallback(EntitySquid var1, float var2) {
	}

	protected float func_170_d(EntitySquid var1, float var2) {
		float var3 = var1.field_21082_j + (var1.field_21083_i - var1.field_21082_j) * var2;
		return var3;
	}
}
