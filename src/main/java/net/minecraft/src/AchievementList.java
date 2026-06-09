package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AchievementList {
	public static int minDisplayColumn;
	public static int minDisplayRow;
	public static int maxDisplayColumn;
	public static int maxDisplayRow;
	public static List achievementList = new ArrayList();
	public static Achievement openInventory = (new Achievement(0, "openInventory", 0, 0, Item.book, (Achievement)null)).func_27082_h().registerStat();
	public static Achievement mineWood = (new Achievement(1, "mineWood", 2, 1, Block.wood, openInventory)).registerStat();
	public static Achievement buildWorkBench = (new Achievement(2, "buildWorkBench", 4, -1, Block.workbench, mineWood)).registerStat();
	public static Achievement buildPickaxe = (new Achievement(3, "buildPickaxe", 4, 2, Item.pickaxeWood, buildWorkBench)).registerStat();
	public static Achievement buildFurnace = (new Achievement(4, "buildFurnace", 3, 4, Block.stoneOvenActive, buildPickaxe)).registerStat();
	public static Achievement acquireIron = (new Achievement(5, "acquireIron", 1, 4, Item.ingotIron, buildFurnace)).registerStat();
	public static Achievement buildHoe = (new Achievement(6, "buildHoe", 2, -3, Item.hoeWood, buildWorkBench)).registerStat();
	public static Achievement makeBread = (new Achievement(7, "makeBread", -1, -3, Item.bread, buildHoe)).registerStat();
	public static Achievement bakeCake = (new Achievement(8, "bakeCake", 0, -5, Item.cake, buildHoe)).registerStat();
	public static Achievement buildBetterPickaxe = (new Achievement(9, "buildBetterPickaxe", 6, 2, Item.pickaxeStone, buildPickaxe)).registerStat();
	public static Achievement cookFish = (new Achievement(10, "cookFish", 2, 6, Item.fishCooked, buildFurnace)).registerStat();
	public static Achievement onARail = (new Achievement(11, "onARail", 2, 3, Block.rail, acquireIron)).setSpecial().registerStat();
	public static Achievement buildSword = (new Achievement(12, "buildSword", 6, -1, Item.swordWood, buildWorkBench)).registerStat();
	public static Achievement killEnemy = (new Achievement(13, "killEnemy", 8, -1, Item.bone, buildSword)).registerStat();
	public static Achievement killCow = (new Achievement(14, "killCow", 7, -3, Item.leather, buildSword)).registerStat();
	public static Achievement flyPig = (new Achievement(15, "flyPig", 8, -4, Item.saddle, killCow)).setSpecial().registerStat();

	public static void func_27374_a() {
	}

	static {
		System.out.println(achievementList.size() + " achievements");
	}
}
