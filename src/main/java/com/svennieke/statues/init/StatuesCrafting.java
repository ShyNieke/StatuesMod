package com.svennieke.statues.init;

import akka.util.BoundedBlockingQueue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class StatuesCrafting {

	public static void register() {
		
		// Tier one statues (decorative only)
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.BLAZE_ROD, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.baby_zombie_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.ROTTEN_FLESH, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.EGG, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statue),"GMG","LCL", "GBG", 'G', Items.GOLD_INGOT, 'M', Items.MILK_BUCKET, 'L', Items.LEATHER, 'B', Items.BEEF, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.GUNPOWDER, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.MUSHROOM_STEW, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.PORKCHOP, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.rabbit_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.RABBIT_HIDE, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheep_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Blocks.WOOL, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheepshaven_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.MUTTON, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.SLIME_BALL, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statue),"GPG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Blocks.SNOW, 'P', Blocks.PUMPKIN, 'C', StatuesItems.core);
		
		//tier two statues (sounds only)
		
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.blaze_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.baby_zombie_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.baby_zombie_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.chicken_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.cow_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.creeper_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.flood_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.flood_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.kingcluck_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.kingcluck_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.mooshroom_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.pig_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.rabbit_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.rabbit_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheep_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.sheep_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheepshaven_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.sheepshaven_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.slime_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.snowgolem_statue);
		
		//tier three statues (sounds and functions)
		
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.blaze_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.baby_zombie_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.baby_zombie_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.chicken_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.cow_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.creeper_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.flood_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.flood_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.kingcluck_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.kingcluck_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.mooshroom_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.pig_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.rabbit_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.rabbit_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheep_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.sheep_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheepshaven_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.sheepshaven_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.slime_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statuet3),"NCN","CSC", "NCN", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.snowgolem_statuet2);
		
		//tier four statues (only functions)
		
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.blaze_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.baby_zombie_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.baby_zombie_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.chicken_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.cow_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.creeper_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.flood_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.flood_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.kingcluck_statuet4),"WWW","WSW", "WWw", 'W', Blocks.WOOL, 'S', StatuesBlocks.kingcluck_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.mooshroom_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.pig_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.rabbit_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.rabbit_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheep_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.sheep_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.sheepshaven_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.sheepshaven_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.slime_statuet3);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statuet4),"WWW","WSW", "WWW", 'W', Blocks.WOOL, 'S', StatuesBlocks.snowgolem_statuet3);
	}
}

