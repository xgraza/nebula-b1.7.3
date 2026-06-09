package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator {
	final CraftingManager craftingManager;

	RecipeSorter(CraftingManager var1) {
		this.craftingManager = var1;
	}

    @Override public int compare(Object o1, Object o2)
    {
        if (o1 instanceof IRecipe && o2 instanceof IRecipe)
        {
            return compare((IRecipe) o1, (IRecipe) o2);
        }
        return 0;
    }

    public int compare(IRecipe var1, IRecipe var2) {
		return var1 instanceof ShapelessRecipes && var2 instanceof ShapedRecipes ? 1 : (var2 instanceof ShapelessRecipes && var1 instanceof ShapedRecipes ? -1 : (var2.getRecipeSize() < var1.getRecipeSize() ? -1 : (var2.getRecipeSize() > var1.getRecipeSize() ? 1 : 0)));
	}
}
