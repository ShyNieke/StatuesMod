package com.svennieke.statues.init;

import com.svennieke.statues.blocks.tiers.decorative.BlockBlaze_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockChicken_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockCow_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockCreeper_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockKingCluck_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockMooshroom_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockPig_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockSlime_Statue_T1;
import com.svennieke.statues.blocks.tiers.decorative.BlockSnowGolem_Statue_T1;
import com.svennieke.statues.blocks.tiers.functional.BlockBlaze_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockChicken_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockCow_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockCreeper_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockKingCluck_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockMooshroom_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockPig_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockSlime_Statue_T3;
import com.svennieke.statues.blocks.tiers.functional.BlockSnowGolem_Statue_T3;
import com.svennieke.statues.blocks.tiers.muted.BlockBlaze_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockChicken_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockCow_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockCreeper_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockKingCluck_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockMooshroom_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockPig_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockSlime_Statue_T4;
import com.svennieke.statues.blocks.tiers.muted.BlockSnowGolem_Statue_T4;
import com.svennieke.statues.blocks.tiers.sounds.BlockBlaze_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockChicken_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockCow_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockCreeper_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockKingCluck_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockMooshroom_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockPig_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockSlime_Statue_T2;
import com.svennieke.statues.blocks.tiers.sounds.BlockSnowGolem_Statue_T2;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StatuesBlocks {
	
	public static Block blaze_statue, chicken_statue,cow_statue,creeper_statue, 
						kingcluck_statue, mooshroom_statue, pig_statue,
						slime_statue, snowgolem_statue;
	
	public static Block blaze_statuet2, chicken_statuet2, cow_statuet2,creeper_statuet2, 
						kingcluck_statuet2, mooshroom_statuet2, pig_statuet2, slime_statuet2, 
						snowgolem_statuet2;
	
	public static Block blaze_statuet3, chicken_statuet3,cow_statuet3,creeper_statuet3, 
						kingcluck_statuet3, mooshroom_statuet3, pig_statuet3, slime_statuet3, 
							snowgolem_statuet3;
	
	public static Block blaze_statuet4, chicken_statuet4,cow_statuet4,creeper_statuet4, 
						kingcluck_statuet4, mooshroom_statuet4, pig_statuet4, slime_statuet4, 
						snowgolem_statuet4;
	
	public static void init()
	{
		blaze_statue = new BlockBlaze_Statue_T1();
		chicken_statue = new BlockChicken_Statue_T1();
		cow_statue = new BlockCow_Statue_T1();
		creeper_statue = new BlockCreeper_Statue_T1();
		kingcluck_statue = new BlockKingCluck_Statue_T1();
		mooshroom_statue = new BlockMooshroom_Statue_T1();
		pig_statue = new BlockPig_Statue_T1();
		slime_statue = new BlockSlime_Statue_T1();
		snowgolem_statue = new BlockSnowGolem_Statue_T1();
		
		blaze_statuet2 = new BlockBlaze_Statue_T2();
		chicken_statuet2 = new BlockChicken_Statue_T2();
		cow_statuet2 = new BlockCow_Statue_T2();
		creeper_statuet2 = new BlockCreeper_Statue_T2();
		kingcluck_statuet2 = new BlockKingCluck_Statue_T2();
		mooshroom_statuet2 = new BlockMooshroom_Statue_T2();
		pig_statuet2 = new BlockPig_Statue_T2();
		slime_statuet2 = new BlockSlime_Statue_T2();
		snowgolem_statuet2 = new BlockSnowGolem_Statue_T2();
		
		blaze_statuet3 = new BlockBlaze_Statue_T3();
		chicken_statuet3 = new BlockChicken_Statue_T3();
		cow_statuet3 = new BlockCow_Statue_T3();
		creeper_statuet3 = new BlockCreeper_Statue_T3();
		kingcluck_statuet3 = new BlockKingCluck_Statue_T3();
		mooshroom_statuet3 = new BlockMooshroom_Statue_T3();
		pig_statuet3 = new BlockPig_Statue_T3();
		slime_statuet3 = new BlockSlime_Statue_T3();
		snowgolem_statuet3 = new BlockSnowGolem_Statue_T3();
		
		blaze_statuet4 = new BlockBlaze_Statue_T4();
		chicken_statuet4 = new BlockChicken_Statue_T4();
		cow_statuet4 = new BlockCow_Statue_T4();
		creeper_statuet4 = new BlockCreeper_Statue_T4();
		kingcluck_statuet4 = new BlockKingCluck_Statue_T4();
		mooshroom_statuet4 = new BlockMooshroom_Statue_T4();
		pig_statuet4 = new BlockPig_Statue_T4();
		slime_statuet4 = new BlockSlime_Statue_T4();
		snowgolem_statuet4 = new BlockSnowGolem_Statue_T4();
	}
	
	public static void register()
	{
		registerBlock(blaze_statue);
		registerBlock(chicken_statue);
		registerBlock(cow_statue);
		registerBlock(creeper_statue);
		registerBlock(kingcluck_statue);
		registerBlock(mooshroom_statue);
		registerBlock(pig_statue);
		registerBlock(slime_statue);
		registerBlock(snowgolem_statue);
		
		registerBlock(blaze_statuet2);
		registerBlock(chicken_statuet2);
		registerBlock(cow_statuet2);
		registerBlock(creeper_statuet2);
		registerBlock(kingcluck_statuet2);
		registerBlock(mooshroom_statuet2);
		registerBlock(pig_statuet2);
		registerBlock(slime_statuet2);
		registerBlock(snowgolem_statuet2);
		
		registerBlock(blaze_statuet3);
		registerBlock(chicken_statuet3);
		registerBlock(cow_statuet3);
		registerBlock(creeper_statuet3);
		registerBlock(kingcluck_statuet3);
		registerBlock(mooshroom_statuet3);
		registerBlock(pig_statuet3);
		registerBlock(slime_statuet3);
		registerBlock(snowgolem_statuet3);
		
		registerBlock(blaze_statuet4);
		registerBlock(chicken_statuet4);
		registerBlock(cow_statuet4);
		registerBlock(creeper_statuet4);
		registerBlock(kingcluck_statuet4);
		registerBlock(mooshroom_statuet4);
		registerBlock(pig_statuet4);
		registerBlock(slime_statuet4);
		registerBlock(snowgolem_statuet4);
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
		registerRender(blaze_statue);
		registerRender(chicken_statue);
		registerRender(cow_statue);
		registerRender(creeper_statue);
		registerRender(kingcluck_statue);
		registerRender(mooshroom_statue);
		registerRender(pig_statue);
		registerRender(slime_statue);
		registerRender(snowgolem_statue);
		
		registerRender(blaze_statuet2);
		registerRender(chicken_statuet2);
		registerRender(cow_statuet2);
		registerRender(creeper_statuet2);
		registerRender(kingcluck_statuet2);
		registerRender(mooshroom_statuet2);
		registerRender(pig_statuet2);
		registerRender(slime_statuet2);
		registerRender(snowgolem_statuet2);
		
		registerRender(blaze_statuet3);
		registerRender(chicken_statuet3);
		registerRender(cow_statuet3);
		registerRender(creeper_statuet3);
		registerRender(kingcluck_statuet3);
		registerRender(mooshroom_statuet3);
		registerRender(pig_statuet3);
		registerRender(slime_statuet3);
		registerRender(snowgolem_statuet3);
		
		registerRender(blaze_statuet4);
		registerRender(chicken_statuet4);
		registerRender(cow_statuet4);
		registerRender(creeper_statuet4);
		registerRender(kingcluck_statuet4);
		registerRender(mooshroom_statuet4);
		registerRender(pig_statuet4);
		registerRender(slime_statuet4);
		registerRender(snowgolem_statuet4);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
