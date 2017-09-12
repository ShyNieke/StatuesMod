package com.svennieke.statues.init;

import com.svennieke.statues.blocks.Statues.BlockBabyZombie_Statue;
import com.svennieke.statues.blocks.Statues.BlockBlaze_Statue;
import com.svennieke.statues.blocks.Statues.BlockChicken_Statue;
import com.svennieke.statues.blocks.Statues.BlockCow_Statue;
import com.svennieke.statues.blocks.Statues.BlockCreeper_Statue;
import com.svennieke.statues.blocks.Statues.BlockFlood_Statue;
import com.svennieke.statues.blocks.Statues.BlockInfo_Statue;
import com.svennieke.statues.blocks.Statues.BlockKingCluck_Statue;
import com.svennieke.statues.blocks.Statues.BlockMooshroom_Statue;
import com.svennieke.statues.blocks.Statues.BlockPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StatuesBlocks {

	public static Block baby_zombie_statue, blaze_statue, chicken_statue,cow_statue, creeper_statue, flood_statue,
						kingcluck_statue, mooshroom_statue, pig_statue, rabbit_statue,
						sheepshaven_statue, slime_statue, snowgolem_statue, villager_statue, squid_statue;
	
	public static Block baby_zombie_statuet2, blaze_statuet2, chicken_statuet2, cow_statuet2, creeper_statuet2, flood_statuet2,
						kingcluck_statuet2, mooshroom_statuet2, pig_statuet2, rabbit_statuet2, 
						sheepshaven_statuet2, slime_statuet2, snowgolem_statuet2, villager_statuet2, squid_statuet2;

	public static Block baby_zombie_statuet3, blaze_statuet3, chicken_statuet3,cow_statuet3, creeper_statuet3, flood_statuet3,
						kingcluck_statuet3, mooshroom_statuet3, pig_statuet3, rabbit_statuet3, 
						sheepshaven_statuet3, slime_statuet3, snowgolem_statuet3, villager_statuet3, squid_statuet3;

	public static Block baby_zombie_statuet4, blaze_statuet4, chicken_statuet4,cow_statuet4, creeper_statuet4, flood_statuet4,
						kingcluck_statuet4, mooshroom_statuet4, pig_statuet4, rabbit_statuet4, 
						sheepshaven_statuet4, slime_statuet4, snowgolem_statuet4, villager_statuet4, squid_statuet4;
	
	public static Block sheep_statue_white, sheep_statue_orange, sheep_statue_magenta, sheep_statue_lightblue, sheep_statue_yellow, 
						sheep_statue_lime, sheep_statue_pink, sheep_statue_gray, sheep_statue_lightgray, sheep_statue_cyan, 
						sheep_statue_purple, sheep_statue_blue, sheep_statue_brown, sheep_statue_green, sheep_statue_red, sheep_statue_black;
	
	public static Block sheep_statue_whitet2, sheep_statue_oranget2, sheep_statue_magentat2, sheep_statue_lightbluet2, sheep_statue_yellowt2, 
						sheep_statue_limet2, sheep_statue_pinkt2, sheep_statue_grayt2, sheep_statue_lightgrayt2, sheep_statue_cyant2, 
						sheep_statue_purplet2, sheep_statue_bluet2, sheep_statue_brownt2, sheep_statue_greent2, sheep_statue_redt2, sheep_statue_blackt2;
	
	public static Block sheep_statue_whitet3, sheep_statue_oranget3, sheep_statue_magentat3, sheep_statue_lightbluet3, sheep_statue_yellowt3, 
						sheep_statue_limet3, sheep_statue_pinkt3, sheep_statue_grayt3, sheep_statue_lightgrayt3, sheep_statue_cyant3, 
						sheep_statue_purplet3, sheep_statue_bluet3, sheep_statue_brownt3, sheep_statue_greent3, sheep_statue_redt3, sheep_statue_blackt3;
	
	public static Block sheep_statue_whitet4, sheep_statue_oranget4, sheep_statue_magentat4, sheep_statue_lightbluet4, sheep_statue_yellowt4, 
						sheep_statue_limet4, sheep_statue_pinkt4, sheep_statue_grayt4, sheep_statue_lightgrayt4, sheep_statue_cyant4, 
						sheep_statue_purplet4, sheep_statue_bluet4, sheep_statue_brownt4, sheep_statue_greent4, sheep_statue_redt4, sheep_statue_blackt4;
	
	public static Block info_statue, player_statue;
	
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
		
		sheep_statue_white = new BlockSheep_Statue("sheepstatue", "blocksheepstatue", 1, EnumDyeColor.WHITE);
		sheep_statue_whitet2 = new BlockSheep_Statue("sheepstatuet2", "blocksheepstatuet2", 2, EnumDyeColor.WHITE);
		sheep_statue_whitet3 = new BlockSheep_Statue("sheepstatuet3", "blocksheepstatuet3", 3, EnumDyeColor.WHITE);
		sheep_statue_whitet4 = new BlockSheep_Statue("sheepstatuet4", "blocksheepstatuet4", 4, EnumDyeColor.WHITE);
		
		sheep_statue_orange = new BlockSheep_Statue("sheepstatueorange", "blocksheepstatueorange", 1, EnumDyeColor.ORANGE);
		sheep_statue_oranget2 = new BlockSheep_Statue("sheepstatueoranget2", "blocksheepstatueoranget2", 2, EnumDyeColor.ORANGE);
		sheep_statue_oranget3 = new BlockSheep_Statue("sheepstatueoranget3", "blocksheepstatueoranget3", 3, EnumDyeColor.ORANGE);
		sheep_statue_oranget4 = new BlockSheep_Statue("sheepstatueoranget4", "blocksheepstatueoranget4", 4, EnumDyeColor.ORANGE);
		
		sheep_statue_magenta = new BlockSheep_Statue("sheepstatuemagenta", "blocksheepstatuemagenta", 1, EnumDyeColor.MAGENTA);
		sheep_statue_magentat2 = new BlockSheep_Statue("sheepstatuemagentat2", "blocksheepstatuemagentat2", 2, EnumDyeColor.MAGENTA);
		sheep_statue_magentat3 = new BlockSheep_Statue("sheepstatuemagentat3", "blocksheepstatuemagentat3", 3, EnumDyeColor.MAGENTA);
		sheep_statue_magentat4 = new BlockSheep_Statue("sheepstatuemagentat4", "blocksheepstatuemagentat4", 4, EnumDyeColor.MAGENTA);
		
		sheep_statue_lightblue = new BlockSheep_Statue("sheepstatuelightblue", "blocksheepstatuelightblue", 1, EnumDyeColor.LIGHT_BLUE);
		sheep_statue_lightbluet2 = new BlockSheep_Statue("sheepstatuelightbluet2", "blocksheepstatuelightbluet2", 2, EnumDyeColor.LIGHT_BLUE);
		sheep_statue_lightbluet3 = new BlockSheep_Statue("sheepstatuelightbluet3", "blocksheepstatuelightbluet3", 3, EnumDyeColor.LIGHT_BLUE);
		sheep_statue_lightbluet4 = new BlockSheep_Statue("sheepstatuelightbluet4", "blocksheepstatuelightbluet4", 4, EnumDyeColor.LIGHT_BLUE);
		
		sheep_statue_yellow = new BlockSheep_Statue("sheepstatueyellow", "blocksheepstatueyellow", 1, EnumDyeColor.YELLOW);
		sheep_statue_yellowt2 = new BlockSheep_Statue("sheepstatueyellowt2", "blocksheepstatueyellowt2", 2, EnumDyeColor.YELLOW);
		sheep_statue_yellowt3 = new BlockSheep_Statue("sheepstatueyellowt3", "blocksheepstatueyellowt3", 3, EnumDyeColor.YELLOW);
		sheep_statue_yellowt4 = new BlockSheep_Statue("sheepstatueyellowt4", "blocksheepstatueyellowt4", 4, EnumDyeColor.YELLOW);
		
		sheep_statue_lime = new BlockSheep_Statue("sheepstatuelime", "blocksheepstatuelime", 1, EnumDyeColor.LIME);
		sheep_statue_limet2 = new BlockSheep_Statue("sheepstatuelimet2", "blocksheepstatuelimet2", 2, EnumDyeColor.LIME);
		sheep_statue_limet3 = new BlockSheep_Statue("sheepstatuelimet3", "blocksheepstatuelimet3", 3, EnumDyeColor.LIME);
		sheep_statue_limet4 = new BlockSheep_Statue("sheepstatuelimet4", "blocksheepstatuelimet4", 4, EnumDyeColor.LIME);
		
		sheep_statue_pink = new BlockSheep_Statue("sheepstatuepink", "blocksheepstatuepink", 1, EnumDyeColor.PINK);
		sheep_statue_pinkt2 = new BlockSheep_Statue("sheepstatuepinkt2", "blocksheepstatuepinkt2", 2, EnumDyeColor.PINK);
		sheep_statue_pinkt3 = new BlockSheep_Statue("sheepstatuepinkt3", "blocksheepstatuepinkt3", 3, EnumDyeColor.PINK);
		sheep_statue_pinkt4 = new BlockSheep_Statue("sheepstatuepinkt4", "blocksheepstatuepinkt4", 4, EnumDyeColor.PINK);
		
		sheep_statue_gray = new BlockSheep_Statue("sheepstatuegray", "blocksheepstatuegray", 1, EnumDyeColor.GRAY);
		sheep_statue_grayt2 = new BlockSheep_Statue("sheepstatuegrayt2", "blocksheepstatuegrayt2", 2, EnumDyeColor.GRAY);
		sheep_statue_grayt3 = new BlockSheep_Statue("sheepstatuegrayt3", "blocksheepstatuegrayt3", 3, EnumDyeColor.GRAY);
		sheep_statue_grayt4 = new BlockSheep_Statue("sheepstatuegrayt4", "blocksheepstatuegrayt4", 4, EnumDyeColor.GRAY);

		sheep_statue_lightgray = new BlockSheep_Statue("sheepstatuelightgray", "blocksheepstatuelightgray", 1, EnumDyeColor.SILVER);
		sheep_statue_lightgrayt2 = new BlockSheep_Statue("sheepstatuelightgrayt2", "blocksheepstatuelightgrayt2", 2, EnumDyeColor.SILVER);
		sheep_statue_lightgrayt3 = new BlockSheep_Statue("sheepstatuelightgrayt3", "blocksheepstatuelightgrayt3", 3, EnumDyeColor.SILVER);
		sheep_statue_lightgrayt4 = new BlockSheep_Statue("sheepstatuelightgrayt4", "blocksheepstatuelightgrayt4", 4, EnumDyeColor.SILVER);

		sheep_statue_cyan = new BlockSheep_Statue("sheepstatuecyan", "blocksheepstatuecyan", 1, EnumDyeColor.CYAN);
		sheep_statue_cyant2 = new BlockSheep_Statue("sheepstatuecyant2", "blocksheepstatuecyant2", 2, EnumDyeColor.CYAN);
		sheep_statue_cyant3 = new BlockSheep_Statue("sheepstatuecyant3", "blocksheepstatuecyant3", 3, EnumDyeColor.CYAN);
		sheep_statue_cyant4 = new BlockSheep_Statue("sheepstatuecyant4", "blocksheepstatuecyant4", 4, EnumDyeColor.CYAN);

		sheep_statue_purple = new BlockSheep_Statue("sheepstatuepurple", "blocksheepstatuepurple", 1, EnumDyeColor.PURPLE);
		sheep_statue_purplet2 = new BlockSheep_Statue("sheepstatuepurplet2", "blocksheepstatuepurplet2", 2, EnumDyeColor.PURPLE);
		sheep_statue_purplet3 = new BlockSheep_Statue("sheepstatuepurplet3", "blocksheepstatuepurplet3", 3, EnumDyeColor.PURPLE);
		sheep_statue_purplet4 = new BlockSheep_Statue("sheepstatuepurplet4", "blocksheepstatuepurplet4", 4, EnumDyeColor.PURPLE);

		sheep_statue_blue = new BlockSheep_Statue("sheepstatueblue", "blocksheepstatueblue", 1, EnumDyeColor.BLUE);
		sheep_statue_bluet2 = new BlockSheep_Statue("sheepstatuebluet2", "blocksheepstatuebluet2", 2, EnumDyeColor.BLUE);
		sheep_statue_bluet3 = new BlockSheep_Statue("sheepstatuebluet3", "blocksheepstatuebluet3", 3, EnumDyeColor.BLUE);
		sheep_statue_bluet4 = new BlockSheep_Statue("sheepstatuebluet4", "blocksheepstatuebluet4", 4, EnumDyeColor.BLUE);

		sheep_statue_brown = new BlockSheep_Statue("sheepstatuebrown", "blocksheepstatuebrown", 1, EnumDyeColor.BROWN);
		sheep_statue_brownt2 = new BlockSheep_Statue("sheepstatuebrownt2", "blocksheepstatuebrownt2", 2, EnumDyeColor.BROWN);
		sheep_statue_brownt3 = new BlockSheep_Statue("sheepstatuebrownt3", "blocksheepstatuebrownt3", 3, EnumDyeColor.BROWN);
		sheep_statue_brownt4 = new BlockSheep_Statue("sheepstatuebrownt4", "blocksheepstatuebrownt4", 4, EnumDyeColor.BROWN);

		sheep_statue_green = new BlockSheep_Statue("sheepstatuegreen", "blocksheepstatuegreen", 1, EnumDyeColor.GREEN);
		sheep_statue_greent2 = new BlockSheep_Statue("sheepstatuegreent2", "blocksheepstatuegreent2", 2, EnumDyeColor.GREEN);
		sheep_statue_greent3 = new BlockSheep_Statue("sheepstatuegreent3", "blocksheepstatuegreent3", 3, EnumDyeColor.GREEN);
		sheep_statue_greent4 = new BlockSheep_Statue("sheepstatuegreent4", "blocksheepstatuegreent4", 4, EnumDyeColor.GREEN);
		
		sheep_statue_red = new BlockSheep_Statue("sheepstatuered", "blocksheepstatuered", 1, EnumDyeColor.RED);
		sheep_statue_redt2 = new BlockSheep_Statue("sheepstatueredt2", "blocksheepstatueredt2", 2, EnumDyeColor.RED);
		sheep_statue_redt3 = new BlockSheep_Statue("sheepstatueredt3", "blocksheepstatueredt3", 3, EnumDyeColor.RED);
		sheep_statue_redt4 = new BlockSheep_Statue("sheepstatueredt4", "blocksheepstatueredt4", 4, EnumDyeColor.RED);

		sheep_statue_black = new BlockSheep_Statue("sheepstatueblack", "blocksheepstatueblack", 1, EnumDyeColor.BLACK);
		sheep_statue_blackt2 = new BlockSheep_Statue("sheepstatueblackt2", "blocksheepstatueblackt2", 2, EnumDyeColor.BLACK);
		sheep_statue_blackt3 = new BlockSheep_Statue("sheepstatueblackt3", "blocksheepstatueblackt3", 3, EnumDyeColor.BLACK);
		sheep_statue_blackt4 = new BlockSheep_Statue("sheepstatueblackt4", "blocksheepstatueblackt4", 4, EnumDyeColor.BLACK);
		
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
		
		info_statue = new BlockInfo_Statue("infostatue", "blockinfostatue", 1);
		player_statue = new BlockPlayer_Statue("playerstatue", "blockplayerstatue", "");
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
		
		registerBlock(sheep_statue_white);
		registerBlock(sheep_statue_whitet2);
		registerBlock(sheep_statue_whitet3);
		registerBlock(sheep_statue_whitet4);
		
		registerBlock(sheep_statue_orange);
		registerBlock(sheep_statue_oranget2);
		registerBlock(sheep_statue_oranget3);
		registerBlock(sheep_statue_oranget4);
		
		registerBlock(sheep_statue_magenta);
		registerBlock(sheep_statue_magentat2);
		registerBlock(sheep_statue_magentat3);
		registerBlock(sheep_statue_magentat4);
		
		registerBlock(sheep_statue_lightblue);
		registerBlock(sheep_statue_lightbluet2);
		registerBlock(sheep_statue_lightbluet3);
		registerBlock(sheep_statue_lightbluet4);
		
		registerBlock(sheep_statue_yellow);
		registerBlock(sheep_statue_yellowt2);
		registerBlock(sheep_statue_yellowt3);
		registerBlock(sheep_statue_yellowt4);
		
		registerBlock(sheep_statue_lime);
		registerBlock(sheep_statue_limet2);
		registerBlock(sheep_statue_limet3);
		registerBlock(sheep_statue_limet4);
		
		registerBlock(sheep_statue_pink);
		registerBlock(sheep_statue_pinkt2);
		registerBlock(sheep_statue_pinkt3);
		registerBlock(sheep_statue_pinkt4);
		
		registerBlock(sheep_statue_gray);
		registerBlock(sheep_statue_grayt2);
		registerBlock(sheep_statue_grayt3);
		registerBlock(sheep_statue_grayt4);
		
		registerBlock(sheep_statue_lightgray);
		registerBlock(sheep_statue_lightgrayt2);
		registerBlock(sheep_statue_lightgrayt3);
		registerBlock(sheep_statue_lightgrayt4);
		
		registerBlock(sheep_statue_cyan);
		registerBlock(sheep_statue_cyant2);
		registerBlock(sheep_statue_cyant3);
		registerBlock(sheep_statue_cyant4);
		
		registerBlock(sheep_statue_purple);
		registerBlock(sheep_statue_purplet2);
		registerBlock(sheep_statue_purplet3);
		registerBlock(sheep_statue_purplet4);
		
		registerBlock(sheep_statue_blue);
		registerBlock(sheep_statue_bluet2);
		registerBlock(sheep_statue_bluet3);
		registerBlock(sheep_statue_bluet4);
		
		registerBlock(sheep_statue_brown);
		registerBlock(sheep_statue_brownt2);
		registerBlock(sheep_statue_brownt3);
		registerBlock(sheep_statue_brownt4);
		
		registerBlock(sheep_statue_green);
		registerBlock(sheep_statue_greent2);
		registerBlock(sheep_statue_greent3);
		registerBlock(sheep_statue_greent4);
		
		registerBlock(sheep_statue_red);
		registerBlock(sheep_statue_redt2);
		registerBlock(sheep_statue_redt3);
		registerBlock(sheep_statue_redt4);
		
		registerBlock(sheep_statue_black);
		registerBlock(sheep_statue_blackt2);
		registerBlock(sheep_statue_blackt3);
		registerBlock(sheep_statue_blackt4);
		
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
		
		registerBlock(info_statue);
		registerBlock(player_statue);
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
		
		registerRender(sheep_statue_white);
		registerRender(sheep_statue_whitet2);
		registerRender(sheep_statue_whitet3);
		registerRender(sheep_statue_whitet4);
		
		registerRender(sheep_statue_orange);
		registerRender(sheep_statue_oranget2);
		registerRender(sheep_statue_oranget3);
		registerRender(sheep_statue_oranget4);
		
		registerRender(sheep_statue_magenta);
		registerRender(sheep_statue_magentat2);
		registerRender(sheep_statue_magentat3);
		registerRender(sheep_statue_magentat4);
		
		registerRender(sheep_statue_lightblue);
		registerRender(sheep_statue_lightbluet2);
		registerRender(sheep_statue_lightbluet3);
		registerRender(sheep_statue_lightbluet4);
		
		registerRender(sheep_statue_yellow);
		registerRender(sheep_statue_yellowt2);
		registerRender(sheep_statue_yellowt3);
		registerRender(sheep_statue_yellowt4);
		
		registerRender(sheep_statue_lime);
		registerRender(sheep_statue_limet2);
		registerRender(sheep_statue_limet3);
		registerRender(sheep_statue_limet4);
		
		registerRender(sheep_statue_pink);
		registerRender(sheep_statue_pinkt2);
		registerRender(sheep_statue_pinkt3);
		registerRender(sheep_statue_pinkt4);
		
		registerRender(sheep_statue_gray);
		registerRender(sheep_statue_grayt2);
		registerRender(sheep_statue_grayt3);
		registerRender(sheep_statue_grayt4);
		
		registerRender(sheep_statue_lightgray);
		registerRender(sheep_statue_lightgrayt2);
		registerRender(sheep_statue_lightgrayt3);
		registerRender(sheep_statue_lightgrayt4);
		
		registerRender(sheep_statue_cyan);
		registerRender(sheep_statue_cyant2);
		registerRender(sheep_statue_cyant3);
		registerRender(sheep_statue_cyant4);
		
		registerRender(sheep_statue_purple);
		registerRender(sheep_statue_purplet2);
		registerRender(sheep_statue_purplet3);
		registerRender(sheep_statue_purplet4);
		
		registerRender(sheep_statue_blue);
		registerRender(sheep_statue_bluet2);
		registerRender(sheep_statue_bluet3);
		registerRender(sheep_statue_bluet4);
		
		registerRender(sheep_statue_brown);
		registerRender(sheep_statue_brownt2);
		registerRender(sheep_statue_brownt3);
		registerRender(sheep_statue_brownt4);
		
		registerRender(sheep_statue_green);
		registerRender(sheep_statue_greent2);
		registerRender(sheep_statue_greent3);
		registerRender(sheep_statue_greent4);
		
		registerRender(sheep_statue_red);
		registerRender(sheep_statue_redt2);
		registerRender(sheep_statue_redt3);
		registerRender(sheep_statue_redt4);
		
		registerRender(sheep_statue_black);
		registerRender(sheep_statue_blackt2);
		registerRender(sheep_statue_blackt3);
		registerRender(sheep_statue_blackt4);
		
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
		
		registerRender(info_statue);
		registerRender(player_statue);
	}
	
	public static void registerRender(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
