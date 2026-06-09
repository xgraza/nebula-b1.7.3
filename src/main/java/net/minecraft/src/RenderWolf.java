package net.minecraft.src;

public class RenderWolf extends RenderLiving<EntityWolf> {
	public RenderWolf(ModelBase var1, float var2) {
		super(var1, var2);
	}

	public void doRender(EntityWolf var1, double var2, double var4, double var6, float var8, float var9) {
		super.doRender(var1, var2, var4, var6, var8, var9);
	}

	protected float func_170_d(EntityWolf var1, float var2) {
		return var1.setTailRotation();
	}

	protected void preRenderCallback(EntityWolf var1, float var2) {
	}
}
