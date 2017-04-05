package com.svennieke.statues.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StatuesCrafting {

	public static void register() {
		//GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.kingcluck_statue), "NGN", "GCG", "NGN", 'N', Items.GOLD_NUGGET, 'G', Items.GOLD_INGOT, 'C', StatuesBlocks.chicken_statue);
		
		// Tier one statues
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.blaze_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.BLAZE_ROD, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.chicken_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.EGG, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.cow_statue),"GMG","LCL", "GBG", 'G', Items.GOLD_INGOT, 'M', Items.MILK_BUCKET, 'L', Items.LEATHER, 'B', Items.BEEF, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.creeper_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.GUNPOWDER, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.mooshroom_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.MUSHROOM_STEW, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.pig_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.PORKCHOP, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.slime_statue),"GMG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Items.SLIME_BALL, 'C', StatuesItems.core);
		GameRegistry.addShapedRecipe(new ItemStack(StatuesBlocks.snowgolem_statue),"GPG","MCM", "GMG", 'G', Items.GOLD_INGOT, 'M', Blocks.SNOW, 'P', Blocks.PUMPKIN, 'C', StatuesItems.core);
	}
}

