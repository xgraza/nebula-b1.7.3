package net.minecraft.src;

import java.util.Comparator;

public class RenderSorter implements Comparator {
	private EntityLiving baseEntity;

	public RenderSorter(EntityLiving var1) {
		this.baseEntity = var1;
	}

    @Override public int compare(Object o1, Object o2)
    {
        if (o1 instanceof WorldRenderer && o2 instanceof WorldRenderer)
        {
            return compare((WorldRenderer) o1, (WorldRenderer) o2);
        }
        return 0;
    }

    public int compare(WorldRenderer var1, WorldRenderer var2) {
		boolean var3 = var1.isInFrustum;
		boolean var4 = var2.isInFrustum;
		if(var3 && !var4) {
			return 1;
		} else if(var4 && !var3) {
			return -1;
		} else {
			double var5 = (double)var1.distanceToEntitySquared(this.baseEntity);
			double var7 = (double)var2.distanceToEntitySquared(this.baseEntity);
			return var5 < var7 ? 1 : (var5 > var7 ? -1 : (var1.chunkIndex < var2.chunkIndex ? 1 : -1));
		}
	}
}
