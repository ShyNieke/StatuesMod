package com.svennieke.statues.init;

import com.svennieke.statues.blocks.Statues.BlockBabyZombie_Statue;
import com.svennieke.statues.blocks.Statues.BlockBlaze_Statue;
import com.svennieke.statues.blocks.Statues.BlockChicken_Statue;
import com.svennieke.statues.blocks.Statues.BlockCow_Statue;
import com.svennieke.statues.blocks.Statues.BlockCreeper_Statue;
import com.svennieke.statues.blocks.Statues.BlockFlood_Statue;
import com.svennieke.statues.blocks.Statues.BlockKingCluck_Statue;
import com.svennieke.statues.blocks.Statues.BlockMooshroom_Statue;
import com.svennieke.statues.blocks.Statues.BlockPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockRabbit_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheepShaven_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheep_Statue;
import com.svennieke.statues.blocks.Statues.BlockSlime_Statue;
import com.svennieke.statues.blocks.Statues.BlockSnowGolem_Statue;
import com.svennieke.statues.blocks.Statues.BlockSquid_Statue;
import com.svennieke.statues.blocks.Statues.BlockVillager_Statue;
import com.svennieke.statues.items.ItemBlockStatue;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StatuesBlocks {

	public static Block baby_zombie_statue, blaze_statue, chicken_statue,cow_statue, creeper_statue, flood_statue,
						kingcluck_statue, mooshroom_statue, pig_statue, rabbit_statue,
						sheep_statue, sheepshaven_statue, slime_statue, snowgolem_statue, villager_statue, squid_statue;
	
	public static Block baby_zombie_statuet2, blaze_statuet2, chicken_statuet2, cow_statuet2, creeper_statuet2, flood_statuet2,
						kingcluck_statuet2, mooshroom_statuet2, pig_statuet2, rabbit_statuet2, 
						sheep_statuet2, sheepshaven_statuet2, slime_statuet2, snowgolem_statuet2, villager_statuet2, squid_statuet2;
	
	public static Block baby_zombie_statuet3, blaze_statuet3, chicken_statuet3,cow_statuet3, creeper_statuet3, flood_statuet3,
						kingcluck_statuet3, mooshroom_statuet3, pig_statuet3, rabbit_statuet3, 
						sheep_statuet3, sheepshaven_statuet3, slime_statuet3, snowgolem_statuet3, villager_statuet3, squid_statuet3;
	
	public static Block baby_zombie_statuet4, blaze_statuet4, chicken_statuet4,cow_statuet4, creeper_statuet4, flood_statuet4,
						kingcluck_statuet4, mooshroom_statuet4, pig_statuet4, rabbit_statuet4, 
						sheep_statuet4, sheepshaven_statuet4, slime_statuet4, snowgolem_statuet4, villager_statuet4, squid_statuet4;
	
	public static void init()
	{
		baby_zombie_statue = new BlockBabyZombie_Statue("babyzombiestatue", "blockbabyzombiestatue", 1);
		baby_zombie_statuet2 = new BlockBabyZombie_Statue("babyzombiestatuet2", "blockbabyzombiestatuet2", 2);
		baby_zombie_statuet3 = new BlockBabyZombie_Statue("babyzombiestatuet3", "blockbabyzombiestatuet3", 3);
		baby_zombie_statuet4 = new BlockBabyZombie_Statue("babyzombiestatuet4", "blockbabyzombiestatuet4", 4);
		
		blaze_statue = new BlockBlaze_Statue("blazestatue", "blockblazestatue", 1);
		blaze_statuet2 = new BlockBlaze_Statue("blazestatuet2", "blockblazestatuet2", 2);
		blaze_statuet3 = new BlockBlaze_Statue("blazestatuet3", "blockblazestatuet3", 3);
		blaze_statuet4 = new BlockBlaze_Statue("blazestatuet4", "blockblazestatuet4", 4);
		
		chicken_statue = new BlockChicken_Statue("chickenstatue", "blockchickenstatue", 1);
		chicken_statuet2 = new BlockChicken_Statue("chickenstatuet2", "blockchickenstatuet2", 2);
		chicken_statuet3 = new BlockChicken_Statue("chickenstatuet3", "blockchickenstatuet3", 3);
		chicken_statuet4 = new BlockChicken_Statue("chickenstatuet4", "blockchickenstatuet4", 4);
		
		cow_statue = new BlockCow_Statue("cowstatue", "blockcowstatue", 1);
		cow_statuet2 = new BlockCow_Statue("cowstatuet2", "blockcowstatuet2", 2);
		cow_statuet3 = new BlockCow_Statue("cowstatuet3", "blockcowstatuet3", 3);
		cow_statuet4 = new BlockCow_Statue("cowstatuet4", "blockcowstatuet4", 4);
		
		creeper_statue = new BlockCreeper_Statue("creeperstatue", "blockcreeperstatue", 1);
		creeper_statuet2 = new BlockCreeper_Statue("creeperstatuet2", "blockcreeperstatuet2", 2);
		creeper_statuet3 = new BlockCreeper_Statue("creeperstatuet3", "blockcreeperstatuet3", 3);
		creeper_statuet4 = new BlockCreeper_Statue("creeperstatuet4", "blockcreeperstatuet4", 4);
		
		flood_statue = new BlockFlood_Statue("floodstatue", "blockfloodstatue", 1);
		flood_statuet2 = new BlockFlood_Statue("floodstatuet2", "blockfloodstatuet2", 2);
		flood_statuet3 = new BlockFlood_Statue("floodstatuet3", "blockfloodstatuet3", 3);
		flood_statuet4 = new BlockFlood_Statue("floodstatuet4", "blockfloodstatuet4", 4);
		
		kingcluck_statue = new BlockKingCluck_Statue("kingcluckstatue", "blockkingcluckstatue", 1);
		kingcluck_statuet2 = new BlockKingCluck_Statue("kingcluckstatuet2", "blockkingcluckstatuet2", 2);
		kingcluck_statuet3 = new BlockKingCluck_Statue("kingcluckstatuet3", "blockkingcluckstatuet3", 3);
		kingcluck_statuet4 = new BlockKingCluck_Statue("kingcluckstatuet4", "blockkingcluckstatuet4", 4);
		
		mooshroom_statue = new BlockMooshroom_Statue("mooshroomstatue", "blockmooshroomstatue", 1);
		mooshroom_statuet2 = new BlockMooshroom_Statue("mooshroomstatuet2", "blockmooshroomstatuet2", 2);
		mooshroom_statuet3 = new BlockMooshroom_Statue("mooshroomstatuet3", "blockmooshroomstatuet3", 3);
		mooshroom_statuet4 = new BlockMooshroom_Statue("mooshroomstatuet4", "blockmooshroomstatuet4", 4);
		
		pig_statue = new BlockPig_Statue("pigstatue", "blockpigstatue", 1);
		pig_statuet2 = new BlockPig_Statue("pigstatuet2", "blockpigstatuet2", 2);
		pig_statuet3 = new BlockPig_Statue("pigstatuet3", "blockpigstatuet3", 3);
		pig_statuet4 = new BlockPig_Statue("pigstatuet4", "blockpigstatuet4", 4);
		
		rabbit_statue = new BlockRabbit_Statue("rabbitstatue", "blockrabbitstatue", 1);
		rabbit_statuet2 = new BlockRabbit_Statue("rabbitstatuet2", "blockrabbitstatuet2", 2);
		rabbit_statuet3 = new BlockRabbit_Statue("rabbitstatuet3", "blockrabbitstatuet3", 3);
		rabbit_statuet4 = new BlockRabbit_Statue("rabbitstatuet4", "blockrabbitstatuet4", 4);
		
		sheepshaven_statue = new BlockSheepShaven_Statue("sheepshavenstatue", "blocksheepshavenstatue", 1);
		sheepshaven_statuet2 = new BlockSheepShaven_Statue("sheepshavenstatuet2", "blocksheepshavenstatuet2", 2);
		sheepshaven_statuet3 = new BlockSheepShaven_Statue("sheepshavenstatuet3", "blocksheepshavenstatuet3", 3);
		sheepshaven_statuet4 = new BlockSheepShaven_Statue("sheepshavenstatuet4", "blocksheepshavenstatuet4", 4);
		
		sheep_statue = new BlockSheep_Statue("sheepstatue", "blocksheepstatue", 1);
		sheep_statuet2 = new BlockSheep_Statue("sheepstatuet2", "blocksheepstatuet2", 2);
		sheep_statuet3 = new BlockSheep_Statue("sheepstatuet3", "blocksheepstatuet3", 3);
		sheep_statuet4 = new BlockSheep_Statue("sheepstatuet4", "blocksheepstatuet4", 4);
		
		slime_statue = new BlockSlime_Statue("slimestatue", "blockslimestatue", 1);
		slime_statuet2 = new BlockSlime_Statue("slimestatuet2", "blockslimestatuet2", 2);
		slime_statuet3 = new BlockSlime_Statue("slimestatuet3", "blockslimestatuet3", 3);
		slime_statuet4 = new BlockSlime_Statue("slimestatuet4", "blockslimestatuet4", 4);
		
		snowgolem_statue = new BlockSnowGolem_Statue("snowgolemstatue", "blocksnowgolemstatue", 1);
		snowgolem_statuet2 = new BlockSnowGolem_Statue("snowgolemstatuet2", "blocksnowgolemstatuet2", 2);
		snowgolem_statuet3 = new BlockSnowGolem_Statue("snowgolemstatuet3", "blocksnowgolemstatuet3", 3);
		snowgolem_statuet4 = new BlockSnowGolem_Statue("snowgolemstatuet4", "blocksnowgolemstatuet4", 4);
		
		squid_statue = new BlockSquid_Statue("squidstatue", "blocksquidstatue", 1);
		squid_statuet2 = new BlockSquid_Statue("squidstatuet2", "blocksquidstatuet2", 2);
		squid_statuet3 = new BlockSquid_Statue("squidstatuet3", "blocksquidstatuet3", 3);
		squid_statuet4 = new BlockSquid_Statue("squidstatuet4", "blocksquidstatuet4", 4);
		
		villager_statue = new BlockVillager_Statue("villagerstatue", "blockvillagerstatue", 1);
		villager_statuet2 = new BlockVillager_Statue("villagerstatuet2", "blockvillagerstatuet2", 2);
		villager_statuet3 = new BlockVillager_Statue("villagerstatuet3", "blockvillagerstatuet3", 3);
		villager_statuet4 = new BlockVillager_Statue("villagerstatuet4", "blockvillagerstatuet4", 4);
	}
	
	public static void register()
	{
		registerBlock(baby_zombie_statue);
		registerBlock(baby_zombie_statuet2);
		registerBlock(baby_zombie_statuet3);
		registerBlock(baby_zombie_statuet4);
		
		registerBlock(blaze_statue);
		registerBlock(blaze_statuet2);
		registerBlock(blaze_statuet3);
		registerBlock(blaze_statuet4);
		
		registerBlock(chicken_statue);
		registerBlock(chicken_statuet2);
		registerBlock(chicken_statuet3);
		registerBlock(chicken_statuet4);
		
		registerBlock(cow_statue);
		registerBlock(cow_statuet2);
		registerBlock(cow_statuet3);
		registerBlock(cow_statuet4);
		
		registerBlock(creeper_statue);
		registerBlock(creeper_statuet2);
		registerBlock(creeper_statuet3);
		registerBlock(creeper_statuet4);
		
		registerBlock(flood_statue);
		registerBlock(flood_statuet2);
		registerBlock(flood_statuet3);
		registerBlock(flood_statuet4);
		
		registerBlock(kingcluck_statue);
		registerBlock(kingcluck_statuet2);
		registerBlock(kingcluck_statuet3);
		registerBlock(kingcluck_statuet4);
		
		registerBlock(mooshroom_statue);
		registerBlock(mooshroom_statuet2);
		registerBlock(mooshroom_statuet3);
		registerBlock(mooshroom_statuet4);
		
		registerBlock(pig_statue);
		registerBlock(pig_statuet2);
		registerBlock(pig_statuet3);
		registerBlock(pig_statuet4);
		
		registerBlock(rabbit_statue);
		registerBlock(rabbit_statuet2);
		registerBlock(rabbit_statuet3);
		registerBlock(rabbit_statuet4);
		
		registerBlock(sheepshaven_statue);
		registerBlock(sheepshaven_statuet2);
		registerBlock(sheepshaven_statuet3);
		registerBlock(sheepshaven_statuet4);
		
		registerBlock(sheep_statue);
		registerBlock(sheep_statuet2);
		registerBlock(sheep_statuet3);
		registerBlock(sheep_statuet4);
		
		registerBlock(slime_statue);
		registerBlock(slime_statuet2);
		registerBlock(slime_statuet3);
		registerBlock(slime_statuet4);
		
		registerBlock(snowgolem_statue);
		registerBlock(snowgolem_statuet2);
		registerBlock(snowgolem_statuet3);
		registerBlock(snowgolem_statuet4);
		
		registerBlock(squid_statue);
		registerBlock(squid_statuet2);
		registerBlock(squid_statuet3);
		registerBlock(squid_statuet4);
		
		registerBlock(villager_statue);
		registerBlock(villager_statuet2);
		registerBlock(villager_statuet3);
		registerBlock(villager_statuet4);
	}
	
	public static void registerBlock(Block block) 
	{
		registerBlock(block, new ItemBlockStatue(block));
	}
	
	public static void registerBlock(Block block, ItemBlockStatue item) 
	{
		ForgeRegistries.BLOCKS.register(block);
		item.setRegistryName(block.getRegistryName());
		ForgeRegistries.ITEMS.register(item);
	}
	
	public static void registerRenders()
	{
		registerRender(baby_zombie_statue);
		registerRender(baby_zombie_statuet2);
		registerRender(baby_zombie_statuet3);
		registerRender(baby_zombie_statuet4);
		
		registerRender(blaze_statue);
		registerRender(blaze_statuet2);
		registerRender(blaze_statuet3);
		registerRender(blaze_statuet4);
		
		registerRender(chicken_statue);
		registerRender(chicken_statuet2);
		registerRender(chicken_statuet3);
		registerRender(chicken_statuet4);
		
		registerRender(cow_statue);
		registerRender(cow_statuet2);
		registerRender(cow_statuet3);
		registerRender(cow_statuet4);
		
		registerRender(creeper_statue);
		registerRender(creeper_statuet2);
		registerRender(creeper_statuet3);
		registerRender(creeper_statuet4);
		
		registerRender(flood_statue);
		registerRender(flood_statuet2);
		registerRender(flood_statuet3);
		registerRender(flood_statuet4);
		
		registerRender(kingcluck_statue);
		registerRender(kingcluck_statuet2);
		registerRender(kingcluck_statuet3);
		registerRender(kingcluck_statuet4);
		
		registerRender(mooshroom_statue);
		registerRender(mooshroom_statuet2);
		registerRender(mooshroom_statuet3);
		registerRender(mooshroom_statuet4);
		
		registerRender(pig_statue);
		registerRender(pig_statuet2);
		registerRender(pig_statuet3);
		registerRender(pig_statuet4);
		
		registerRender(rabbit_statue);
		registerRender(rabbit_statuet2);
		registerRender(rabbit_statuet3);
		registerRender(rabbit_statuet4);
		
		registerRender(sheepshaven_statue);
		registerRender(sheepshaven_statuet2);
		registerRender(sheepshaven_statuet3);
		registerRender(sheepshaven_statuet4);
		
		registerRender(sheep_statue);
		registerRender(sheep_statuet2);
		registerRender(sheep_statuet3);
		registerRender(sheep_statuet4);
		
		registerRender(slime_statue);
		registerRender(slime_statuet2);
		registerRender(slime_statuet3);
		registerRender(slime_statuet4);
		
		registerRender(snowgolem_statue);
		registerRender(snowgolem_statuet2);
		registerRender(snowgolem_statuet3);
		registerRender(snowgolem_statuet4);
		
		registerRender(squid_statue);
		registerRender(squid_statuet2);
		registerRender(squid_statuet3);
		registerRender(squid_statuet4);
		
		registerRender(villager_statue);
		registerRender(villager_statuet2);
		registerRender(villager_statuet3);
		registerRender(villager_statuet4);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
