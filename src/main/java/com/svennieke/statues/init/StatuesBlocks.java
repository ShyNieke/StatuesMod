package com.svennieke.statues.init;

import com.svennieke.statues.blocks.BlockBlaze_Statue;
import com.svennieke.statues.blocks.BlockChicken_Statue;
import com.svennieke.statues.blocks.BlockCow_Statue;
import com.svennieke.statues.blocks.BlockCreeper_Statue;
import com.svennieke.statues.blocks.BlockKingCluck_Statue;
import com.svennieke.statues.blocks.BlockMooshroom_Statue;
import com.svennieke.statues.blocks.BlockPig_Statue;
import com.svennieke.statues.blocks.BlockSlime_Statue;
import com.svennieke.statues.blocks.BlockSnowGolem_Statue;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StatuesBlocks {
	
	public static Block slime_statue;
	public static Block blaze_statue;
	public static Block chicken_statue;
	public static Block kingcluck_statue;
	public static Block creeper_statue;
	public static Block snowgolem_statue;
	public static Block cow_statue;
	public static Block mooshroom_statue;
	public static Block pig_statue;
	
	public static void init()
	{
		slime_statue = new BlockSlime_Statue();
		blaze_statue = new BlockBlaze_Statue();
		snowgolem_statue = new BlockSnowGolem_Statue();
		chicken_statue = new BlockChicken_Statue();
		kingcluck_statue = new BlockKingCluck_Statue();
		creeper_statue = new BlockCreeper_Statue();
		cow_statue = new BlockCow_Statue();
		mooshroom_statue = new BlockMooshroom_Statue();
		mooshroom_statue = new BlockPig_Statue();
	}
	
	public static void register()
	{
		registerBlock(slime_statue);
		registerBlock(blaze_statue);
		registerBlock(snowgolem_statue);
		registerBlock(cow_statue);
		registerBlock(chicken_statue);
		registerBlock(kingcluck_statue);
		registerBlock(creeper_statue);
		registerBlock(mooshroom_statue);
		registerBlock(pig_statue);
	}
	
	public static void registerBlock(Block block) 
	{
		registerBlock(block, new ItemBlock(block));
	}
	
	public static void registerBlock(Block block, ItemBlock item) 
	{
		GameRegistry.register(block);
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}
	
	public static void registerRenders()
	{
		registerRender(slime_statue);
		registerRender(blaze_statue);
		registerRender(snowgolem_statue);
		registerRender(cow_statue);
		registerRender(chicken_statue);
		registerRender(kingcluck_statue);
		registerRender(creeper_statue);
		registerRender(mooshroom_statue);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
