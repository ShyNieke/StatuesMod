package com.svennieke.statues.init;

import akka.util.BoundedBlockingQueue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StatuesCrafting {

	public static void register() {
		//GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.kingcluck_statue), "NGN", "GCG", "NGN", 'N', Items.GOLD_NUGGET, 'G', Items.GOLD_INGOT, 'C', StatuesBlocks.chicken_statue);
		
		// Tier one statues (decoratives only)
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.BLAZE_ROD, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.EGG, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statue),"GMG","LCL", "GBG", 'G', Items.GOLD_INGOT, 'M', Items.MILK_BUCKET, 'L', Items.LEATHER, 'B', Items.BEEF, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.GUNPOWDER, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.MUSHROOM_STEW, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.PORKCHOP, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.SLIME_BALL, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statue),"GPG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Blocks.SNOW, 'P', Blocks.PUMPKIN, 'C', StatuesItems.core);
		
		//tier two statues (sounds only)
		
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.blaze_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.chicken_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.cow_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.creeper_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.kingcluck_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.kingcluck_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.mooshroom_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.pig_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.slime_statue);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statuet2),"NRN","RSR", "NRN", 'N', Blocks.NOTEBLOCK, 'R', Blocks.REDSTONE_BLOCK, 'S', StatuesBlocks.snowgolem_statue);
		
		//tier three statues (sounds and functions)
		
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.blaze_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.chicken_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.cow_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.creeper_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.kingcluck_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.kingcluck_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.mooshroom_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.pig_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.slime_statuet2);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statuet3),"NCN","CSC", "CRC", 'N', Blocks.QUARTZ_ORE, 'C', Blocks.CHORUS_FLOWER, 'S', StatuesBlocks.snowgolem_statuet2);
	}
}

