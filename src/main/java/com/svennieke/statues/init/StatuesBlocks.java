package com.svennieke.statues.init;

import java.util.ArrayList;

import com.svennieke.statues.blocks.Statues.BlockBabyZombie_Statue;
import com.svennieke.statues.blocks.Statues.BlockBlaze_Statue;
import com.svennieke.statues.blocks.Statues.BlockBumbo_Statue;
import com.svennieke.statues.blocks.Statues.BlockChickenJockey_Statue;
import com.svennieke.statues.blocks.Statues.BlockChicken_Statue;
import com.svennieke.statues.blocks.Statues.BlockCow_Statue;
import com.svennieke.statues.blocks.Statues.BlockCreeper_Statue;
import com.svennieke.statues.blocks.Statues.BlockEnderman_Statue;
import com.svennieke.statues.blocks.Statues.BlockEndermite_Statue;
import com.svennieke.statues.blocks.Statues.BlockEvoker_Statue;
import com.svennieke.statues.blocks.Statues.BlockFlood_Statue;
import com.svennieke.statues.blocks.Statues.BlockGhast_Statue;
import com.svennieke.statues.blocks.Statues.BlockGuardian_Statue;
import com.svennieke.statues.blocks.Statues.BlockHusk_Statue;
import com.svennieke.statues.blocks.Statues.BlockInfo_Statue;
import com.svennieke.statues.blocks.Statues.BlockKingCluck_Statue;
import com.svennieke.statues.blocks.Statues.BlockMagmaSlime_Statue;
import com.svennieke.statues.blocks.Statues.BlockMooshroom_Statue;
import com.svennieke.statues.blocks.Statues.BlockPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.svennieke.statues.blocks.Statues.BlockPufferfish_Statue;
import com.svennieke.statues.blocks.Statues.BlockRabbit_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheepShaven_Statue;
import com.svennieke.statues.blocks.Statues.BlockSheep_Statue;
import com.svennieke.statues.blocks.Statues.BlockShulker_Statue;
import com.svennieke.statues.blocks.Statues.BlockSlime_Statue;
import com.svennieke.statues.blocks.Statues.BlockSnowGolem_Statue;
import com.svennieke.statues.blocks.Statues.BlockSombrero_Statue;
import com.svennieke.statues.blocks.Statues.BlockSquid_Statue;
import com.svennieke.statues.blocks.Statues.BlockTotemOfUndying_Statue;
import com.svennieke.statues.blocks.Statues.BlockVillager_Statue;
import com.svennieke.statues.blocks.Statues.BlockWastelandPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockWitch_Statue;
import com.svennieke.statues.blocks.Statues.BlockZombie_Statue;
import com.svennieke.statues.blocks.decorative.BlockDisplayStand;
import com.svennieke.statues.blocks.decorative.BlockPebble;
import com.svennieke.statues.items.ItemBlockStatue;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class StatuesBlocks {

	//Regular Statues
	public static Block baby_zombie_statue;
	public static Block blaze_statue;
	public static Block chicken_statue;
	public static Block cow_statue;
	public static Block creeper_statue;
	public static Block flood_statue;
	public static Block kingcluck_statue;
	public static Block mooshroom_statue;
	public static Block pig_statue;
	public static Block rabbit_bs_statue;
	public static Block rabbit_br_statue;
	public static Block rabbit_bw_statue;
	public static Block rabbit_ws_statue;
	public static Block rabbit_wh_statue;
	public static Block rabbit_go_statue;
	public static Block sheepshaven_statue;
	public static Block slime_statue;
	public static Block snowgolem_statue;
	public static Block villager_br_statue;
	public static Block villager_gr_statue;
	public static Block villager_pu_statue;
	public static Block villager_wh_statue;
	public static Block squid_statue;
	public static Block witch_statue;
	public static Block zombie_statue;
	public static Block husk_statue;
	public static Block shulker_statue;
	public static Block chicken_jockey_statue;
	public static Block magma_statue;
	public static Block ghast_statue;
	public static Block guardian_statue;
	public static Block wasteland_statue;
	public static Block enderman_statue;
	public static Block pufferfish_statue;
	
	public static Block baby_zombie_statuet2;
	public static Block blaze_statuet2;
	public static Block chicken_statuet2;
	public static Block cow_statuet2;
	public static Block creeper_statuet2;
	public static Block flood_statuet2;
	public static Block kingcluck_statuet2;
	public static Block mooshroom_statuet2;
	public static Block pig_statuet2;
	public static Block rabbit_bs_statuet2;
	public static Block rabbit_br_statuet2;
	public static Block rabbit_bw_statuet2;
	public static Block rabbit_ws_statuet2;
	public static Block rabbit_wh_statuet2;
	public static Block rabbit_go_statuet2;
	public static Block sheepshaven_statuet2;
	public static Block slime_statuet2;
	public static Block snowgolem_statuet2;
	public static Block villager_br_statuet2;
	public static Block villager_gr_statuet2;
	public static Block villager_pu_statuet2;
	public static Block villager_wh_statuet2;
	public static Block squid_statuet2;
	public static Block witch_statuet2;
	public static Block zombie_statuet2;
	public static Block husk_statuet2;
	public static Block shulker_statuet2;
	public static Block chicken_jockey_statuet2;
	public static Block magma_statuet2;
	public static Block ghast_statuet2;
	public static Block guardian_statuet2;
	public static Block wasteland_statuet2;
	public static Block enderman_statuet2;
	public static Block pufferfish_statuet2;

	public static Block baby_zombie_statuet3;
	public static Block blaze_statuet3;
	public static Block chicken_statuet3;
	public static Block cow_statuet3;
	public static Block creeper_statuet3;
	public static Block flood_statuet3;
	public static Block kingcluck_statuet3;
	public static Block mooshroom_statuet3;
	public static Block pig_statuet3;
	public static Block rabbit_bs_statuet3;
	public static Block rabbit_br_statuet3;
	public static Block rabbit_bw_statuet3;
	public static Block rabbit_ws_statuet3;
	public static Block rabbit_wh_statuet3;
	public static Block rabbit_go_statuet3;
	public static Block sheepshaven_statuet3;
	public static Block slime_statuet3;
	public static Block snowgolem_statuet3;
	public static Block villager_br_statuet3;
	public static Block villager_gr_statuet3;
	public static Block villager_pu_statuet3;
	public static Block villager_wh_statuet3;
	public static Block squid_statuet3;
	public static Block witch_statuet3;
	public static Block zombie_statuet3;
	public static Block husk_statuet3;
	public static Block shulker_statuet3;
	public static Block chicken_jockey_statuet3;
	public static Block magma_statuet3;
	public static Block ghast_statuet3;
	public static Block guardian_statuet3;
	public static Block wasteland_statuet3;
	public static Block enderman_statuet3;
	public static Block pufferfish_statuet3;

	public static Block baby_zombie_statuet4;
	public static Block blaze_statuet4;
	public static Block chicken_statuet4;
	public static Block cow_statuet4;
	public static Block creeper_statuet4;
	public static Block flood_statuet4;
	public static Block kingcluck_statuet4;
	public static Block mooshroom_statuet4;
	public static Block pig_statuet4;
	public static Block rabbit_bs_statuet4;
	public static Block rabbit_br_statuet4;
	public static Block rabbit_bw_statuet4;
	public static Block rabbit_ws_statuet4;
	public static Block rabbit_wh_statuet4;
	public static Block rabbit_go_statuet4;
	public static Block sheepshaven_statuet4;
	public static Block slime_statuet4;
	public static Block snowgolem_statuet4;
	public static Block villager_br_statuet4;
	public static Block villager_gr_statuet4;
	public static Block villager_pu_statuet4;
	public static Block villager_wh_statuet4;
	public static Block squid_statuet4;
	public static Block witch_statuet4;
	public static Block zombie_statuet4;
	public static Block husk_statuet4;
	public static Block shulker_statuet4;
	public static Block chicken_jockey_statuet4;
	public static Block magma_statuet4;
	public static Block ghast_statuet4;
	public static Block guardian_statuet4;
	public static Block wasteland_statuet4;
	public static Block enderman_statuet4;
	public static Block pufferfish_statuet4;

	//Sheep Statues
	public static Block sheep_statue_white;
	public static Block sheep_statue_orange;
	public static Block sheep_statue_magenta;
	public static Block sheep_statue_lightblue;
	public static Block sheep_statue_yellow;
	public static Block sheep_statue_lime;
	public static Block sheep_statue_pink;
	public static Block sheep_statue_gray;
	public static Block sheep_statue_lightgray;
	public static Block sheep_statue_cyan;
	public static Block sheep_statue_purple;
	public static Block sheep_statue_blue;
	public static Block sheep_statue_brown;
	public static Block sheep_statue_green;
	public static Block sheep_statue_red;
	public static Block sheep_statue_black;
	
	public static Block sheep_statue_whitet2;
	public static Block sheep_statue_oranget2;
	public static Block sheep_statue_magentat2;
	public static Block sheep_statue_lightbluet2;
	public static Block sheep_statue_yellowt2;
	public static Block sheep_statue_limet2;
	public static Block sheep_statue_pinkt2;
	public static Block sheep_statue_grayt2;
	public static Block sheep_statue_lightgrayt2;
	public static Block sheep_statue_cyant2;
	public static Block sheep_statue_purplet2;
	public static Block sheep_statue_bluet2;
	public static Block sheep_statue_brownt2;
	public static Block sheep_statue_greent2;
	public static Block sheep_statue_redt2;
	public static Block sheep_statue_blackt2;
	
	public static Block sheep_statue_whitet3;
	public static Block sheep_statue_oranget3;
	public static Block sheep_statue_magentat3;
	public static Block sheep_statue_lightbluet3;
	public static Block sheep_statue_yellowt3;
	public static Block sheep_statue_limet3;
	public static Block sheep_statue_pinkt3;
	public static Block sheep_statue_grayt3;
	public static Block sheep_statue_lightgrayt3;
	public static Block sheep_statue_cyant3;
	public static Block sheep_statue_purplet3;
	public static Block sheep_statue_bluet3;
	public static Block sheep_statue_brownt3;
	public static Block sheep_statue_greent3;
	public static Block sheep_statue_redt3;
	public static Block sheep_statue_blackt3;
	
	public static Block sheep_statue_whitet4;
	public static Block sheep_statue_oranget4;
	public static Block sheep_statue_magentat4;
	public static Block sheep_statue_lightbluet4;
	public static Block sheep_statue_yellowt4;
	public static Block sheep_statue_limet4;
	public static Block sheep_statue_pinkt4;
	public static Block sheep_statue_grayt4;
	public static Block sheep_statue_lightgrayt4;
	public static Block sheep_statue_cyant4;
	public static Block sheep_statue_purplet4;
	public static Block sheep_statue_bluet4;
	public static Block sheep_statue_brownt4;
	public static Block sheep_statue_greent4;
	public static Block sheep_statue_redt4;
	public static Block sheep_statue_blackt4;
	
	//Other Statues
	public static Block info_statue;
	public static Block player_statue;
	public static Block endermite_statue;
	public static Block evoker_statue;
	public static Block totemofundying_statue;
	public static Block pebble;
	public static Block display_stand;
	public static Block sombrero;
	public static Block bumbo_statue;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();
		
		baby_zombie_statue = registerBlock(new BlockBabyZombie_Statue("babyzombiestatue", "blockbabyzombiestatue", 1));
		baby_zombie_statuet2 = registerBlock(new BlockBabyZombie_Statue("babyzombiestatuet2", "blockbabyzombiestatuet2", 2));
		baby_zombie_statuet3 = registerBlock(new BlockBabyZombie_Statue("babyzombiestatuet3", "blockbabyzombiestatuet3", 3));
		baby_zombie_statuet4 = registerBlock(new BlockBabyZombie_Statue("babyzombiestatuet4", "blockbabyzombiestatuet4", 4));
		
		blaze_statue = registerBlock(new BlockBlaze_Statue("blazestatue", "blockblazestatue", 1));
		blaze_statuet2 = registerBlock(new BlockBlaze_Statue("blazestatuet2", "blockblazestatuet2", 2));
		blaze_statuet3 = registerBlock(new BlockBlaze_Statue("blazestatuet3", "blockblazestatuet3", 3));
		blaze_statuet4 = registerBlock(new BlockBlaze_Statue("blazestatuet4", "blockblazestatuet4", 4));
		
		chicken_statue = registerBlock(new BlockChicken_Statue("chickenstatue", "blockchickenstatue", 1));
		chicken_statuet2 = registerBlock(new BlockChicken_Statue("chickenstatuet2", "blockchickenstatuet2", 2));
		chicken_statuet3 = registerBlock(new BlockChicken_Statue("chickenstatuet3", "blockchickenstatuet3", 3));
		chicken_statuet4 = registerBlock(new BlockChicken_Statue("chickenstatuet4", "blockchickenstatuet4", 4));
		
		cow_statue = registerBlock(new BlockCow_Statue("cowstatue", "blockcowstatue", 1));
		cow_statuet2 = registerBlock(new BlockCow_Statue("cowstatuet2", "blockcowstatuet2", 2));
		cow_statuet3 = registerBlock(new BlockCow_Statue("cowstatuet3", "blockcowstatuet3", 3));
		cow_statuet4 = registerBlock(new BlockCow_Statue("cowstatuet4", "blockcowstatuet4", 4));
		
		creeper_statue = registerBlock(new BlockCreeper_Statue("creeperstatue", "blockcreeperstatue", 1));
		creeper_statuet2 = registerBlock(new BlockCreeper_Statue("creeperstatuet2", "blockcreeperstatuet2", 2));
		creeper_statuet3 = registerBlock(new BlockCreeper_Statue("creeperstatuet3", "blockcreeperstatuet3", 3));
		creeper_statuet4 = registerBlock(new BlockCreeper_Statue("creeperstatuet4", "blockcreeperstatuet4", 4));
		
		flood_statue = registerBlock(new BlockFlood_Statue("floodstatue", "blockfloodstatue", 1));
		flood_statuet2 = registerBlock(new BlockFlood_Statue("floodstatuet2", "blockfloodstatuet2", 2));
		flood_statuet3 = registerBlock(new BlockFlood_Statue("floodstatuet3", "blockfloodstatuet3", 3));
		flood_statuet4 = registerBlock(new BlockFlood_Statue("floodstatuet4", "blockfloodstatuet4", 4));
		
		kingcluck_statue = registerBlock(new BlockKingCluck_Statue("kingcluckstatue", "blockkingcluckstatue", 1));
		kingcluck_statuet2 = registerBlock(new BlockKingCluck_Statue("kingcluckstatuet2", "blockkingcluckstatuet2", 2));
		kingcluck_statuet3 = registerBlock(new BlockKingCluck_Statue("kingcluckstatuet3", "blockkingcluckstatuet3", 3));
		kingcluck_statuet4 = registerBlock(new BlockKingCluck_Statue("kingcluckstatuet4", "blockkingcluckstatuet4", 4));
		
		mooshroom_statue = registerBlock(new BlockMooshroom_Statue("mooshroomstatue", "blockmooshroomstatue", 1));
		mooshroom_statuet2 = registerBlock(new BlockMooshroom_Statue("mooshroomstatuet2", "blockmooshroomstatuet2", 2));
		mooshroom_statuet3 = registerBlock(new BlockMooshroom_Statue("mooshroomstatuet3", "blockmooshroomstatuet3", 3));
		mooshroom_statuet4 = registerBlock(new BlockMooshroom_Statue("mooshroomstatuet4", "blockmooshroomstatuet4", 4));
		
		pig_statue = registerBlock(new BlockPig_Statue("pigstatue", "blockpigstatue", 1));
		pig_statuet2 = registerBlock(new BlockPig_Statue("pigstatuet2", "blockpigstatuet2", 2));
		pig_statuet3 = registerBlock(new BlockPig_Statue("pigstatuet3", "blockpigstatuet3", 3));
		pig_statuet4 = registerBlock(new BlockPig_Statue("pigstatuet4", "blockpigstatuet4", 4));
		
		rabbit_bs_statue = registerBlock(new BlockRabbit_Statue("rabbitblackspotstatue", "blockrabbitblackspotstatue", 1));
		rabbit_bs_statuet2 = registerBlock(new BlockRabbit_Statue("rabbitblackspotstatuet2", "blockrabbitblackspotstatuet2", 2));
		rabbit_bs_statuet3 = registerBlock(new BlockRabbit_Statue("rabbitblackspotstatuet3", "blockrabbitblackspotstatuet3", 3));
		rabbit_bs_statuet4 = registerBlock(new BlockRabbit_Statue("rabbitblackspotstatuet4", "blockrabbitblackspotstatuet4", 4));
		
		rabbit_br_statue = registerBlock(new BlockRabbit_Statue("rabbitbrownstatue", "blockrabbitbrownstatue", 1));
		rabbit_br_statuet2 = registerBlock(new BlockRabbit_Statue("rabbitbrownstatuet2", "blockrabbitbrownstatuet2", 2));
		rabbit_br_statuet3 = registerBlock(new BlockRabbit_Statue("rabbitbrownstatuet3", "blockrabbitbrownstatuet3", 3));
		rabbit_br_statuet4 = registerBlock(new BlockRabbit_Statue("rabbitbrownstatuet4", "blockrabbitbrownstatuet4", 4));
		
		rabbit_bw_statue = registerBlock(new BlockRabbit_Statue("rabbitstatue", "blockrabbitstatue", 1));
		rabbit_bw_statuet2 = registerBlock(new BlockRabbit_Statue("rabbitstatuet2", "blockrabbitstatuet2", 2));
		rabbit_bw_statuet3 = registerBlock(new BlockRabbit_Statue("rabbitstatuet3", "blockrabbitstatuet3", 3));
		rabbit_bw_statuet4 = registerBlock(new BlockRabbit_Statue("rabbitstatuet4", "blockrabbitstatuet4", 4));
		
		rabbit_ws_statue = registerBlock(new BlockRabbit_Statue("rabbitwhitespotstatue", "blockrabbitwhitespotstatue", 1));
		rabbit_ws_statuet2 = registerBlock(new BlockRabbit_Statue("rabbitwhitespotstatuet2", "blockrabbitwhitespotstatuet2", 2));
		rabbit_ws_statuet3 = registerBlock(new BlockRabbit_Statue("rabbitwhitespotstatuet3", "blockrabbitwhitespotstatuet3", 3));
		rabbit_ws_statuet4 = registerBlock(new BlockRabbit_Statue("rabbitwhitespotstatuet4", "blockrabbitwhitespotstatuet4", 4));
		
		rabbit_wh_statue = registerBlock(new BlockRabbit_Statue("rabbitwhitestatue", "blockrabbitwhitestatue", 1));
		rabbit_wh_statuet2 = registerBlock(new BlockRabbit_Statue("rabbitwhitestatuet2", "blockrabbitwhitestatuet2", 2));
		rabbit_wh_statuet3 = registerBlock(new BlockRabbit_Statue("rabbitwhitestatuet3", "blockrabbitwhitestatuet3", 3));
		rabbit_wh_statuet4 = registerBlock(new BlockRabbit_Statue("rabbitwhitestatuet4", "blockrabbitwhitestatuet4", 4));
		
		rabbit_go_statue = registerBlock(new BlockRabbit_Statue("rabbityellowstatue", "blockrabbityellowstatue", 1));
		rabbit_go_statuet2 = registerBlock(new BlockRabbit_Statue("rabbityellowstatuet2", "blockrabbityellowstatuet2", 2));
		rabbit_go_statuet3 = registerBlock(new BlockRabbit_Statue("rabbityellowstatuet3", "blockrabbityellowstatuet3", 3));
		rabbit_go_statuet4 = registerBlock(new BlockRabbit_Statue("rabbityellowstatuet4", "blockrabbityellowstatuet4", 4));
		
		sheepshaven_statue = registerBlock(new BlockSheepShaven_Statue("sheepshavenstatue", "blocksheepshavenstatue", 1));
		sheepshaven_statuet2 = registerBlock(new BlockSheepShaven_Statue("sheepshavenstatuet2", "blocksheepshavenstatuet2", 2));
		sheepshaven_statuet3 = registerBlock(new BlockSheepShaven_Statue("sheepshavenstatuet3", "blocksheepshavenstatuet3", 3));
		sheepshaven_statuet4 = registerBlock(new BlockSheepShaven_Statue("sheepshavenstatuet4", "blocksheepshavenstatuet4", 4));
		
		sheep_statue_white = registerBlock(new BlockSheep_Statue("sheepstatue", "blocksheepstatue", 1, EnumDyeColor.WHITE));
		sheep_statue_whitet2 = registerBlock(new BlockSheep_Statue("sheepstatuet2", "blocksheepstatuet2", 2, EnumDyeColor.WHITE));
		sheep_statue_whitet3 = registerBlock(new BlockSheep_Statue("sheepstatuet3", "blocksheepstatuet3", 3, EnumDyeColor.WHITE));
		sheep_statue_whitet4 = registerBlock(new BlockSheep_Statue("sheepstatuet4", "blocksheepstatuet4", 4, EnumDyeColor.WHITE));
		
		sheep_statue_orange = registerBlock(new BlockSheep_Statue("sheepstatueorange", "blocksheepstatueorange", 1, EnumDyeColor.ORANGE));
		sheep_statue_oranget2 = registerBlock(new BlockSheep_Statue("sheepstatueoranget2", "blocksheepstatueoranget2", 2, EnumDyeColor.ORANGE));
		sheep_statue_oranget3 = registerBlock(new BlockSheep_Statue("sheepstatueoranget3", "blocksheepstatueoranget3", 3, EnumDyeColor.ORANGE));
		sheep_statue_oranget4 = registerBlock(new BlockSheep_Statue("sheepstatueoranget4", "blocksheepstatueoranget4", 4, EnumDyeColor.ORANGE));
		
		sheep_statue_magenta = registerBlock(new BlockSheep_Statue("sheepstatuemagenta", "blocksheepstatuemagenta", 1, EnumDyeColor.MAGENTA));
		sheep_statue_magentat2 = registerBlock(new BlockSheep_Statue("sheepstatuemagentat2", "blocksheepstatuemagentat2", 2, EnumDyeColor.MAGENTA));
		sheep_statue_magentat3 = registerBlock(new BlockSheep_Statue("sheepstatuemagentat3", "blocksheepstatuemagentat3", 3, EnumDyeColor.MAGENTA));
		sheep_statue_magentat4 = registerBlock(new BlockSheep_Statue("sheepstatuemagentat4", "blocksheepstatuemagentat4", 4, EnumDyeColor.MAGENTA));
		
		sheep_statue_lightblue = registerBlock(new BlockSheep_Statue("sheepstatuelightblue", "blocksheepstatuelightblue", 1, EnumDyeColor.LIGHT_BLUE));
		sheep_statue_lightbluet2 = registerBlock(new BlockSheep_Statue("sheepstatuelightbluet2", "blocksheepstatuelightbluet2", 2, EnumDyeColor.LIGHT_BLUE));
		sheep_statue_lightbluet3 = registerBlock(new BlockSheep_Statue("sheepstatuelightbluet3", "blocksheepstatuelightbluet3", 3, EnumDyeColor.LIGHT_BLUE));
		sheep_statue_lightbluet4 = registerBlock(new BlockSheep_Statue("sheepstatuelightbluet4", "blocksheepstatuelightbluet4", 4, EnumDyeColor.LIGHT_BLUE));
		
		sheep_statue_yellow = registerBlock(new BlockSheep_Statue("sheepstatueyellow", "blocksheepstatueyellow", 1, EnumDyeColor.YELLOW));
		sheep_statue_yellowt2 = registerBlock(new BlockSheep_Statue("sheepstatueyellowt2", "blocksheepstatueyellowt2", 2, EnumDyeColor.YELLOW));
		sheep_statue_yellowt3 = registerBlock(new BlockSheep_Statue("sheepstatueyellowt3", "blocksheepstatueyellowt3", 3, EnumDyeColor.YELLOW));
		sheep_statue_yellowt4 = registerBlock(new BlockSheep_Statue("sheepstatueyellowt4", "blocksheepstatueyellowt4", 4, EnumDyeColor.YELLOW));
		
		sheep_statue_lime = registerBlock(new BlockSheep_Statue("sheepstatuelime", "blocksheepstatuelime", 1, EnumDyeColor.LIME));
		sheep_statue_limet2 = registerBlock(new BlockSheep_Statue("sheepstatuelimet2", "blocksheepstatuelimet2", 2, EnumDyeColor.LIME));
		sheep_statue_limet3 = registerBlock(new BlockSheep_Statue("sheepstatuelimet3", "blocksheepstatuelimet3", 3, EnumDyeColor.LIME));
		sheep_statue_limet4 = registerBlock(new BlockSheep_Statue("sheepstatuelimet4", "blocksheepstatuelimet4", 4, EnumDyeColor.LIME));
		
		sheep_statue_pink = registerBlock(new BlockSheep_Statue("sheepstatuepink", "blocksheepstatuepink", 1, EnumDyeColor.PINK));
		sheep_statue_pinkt2 = registerBlock(new BlockSheep_Statue("sheepstatuepinkt2", "blocksheepstatuepinkt2", 2, EnumDyeColor.PINK));
		sheep_statue_pinkt3 = registerBlock(new BlockSheep_Statue("sheepstatuepinkt3", "blocksheepstatuepinkt3", 3, EnumDyeColor.PINK));
		sheep_statue_pinkt4 = registerBlock(new BlockSheep_Statue("sheepstatuepinkt4", "blocksheepstatuepinkt4", 4, EnumDyeColor.PINK));
		
		sheep_statue_gray = registerBlock(new BlockSheep_Statue("sheepstatuegray", "blocksheepstatuegray", 1, EnumDyeColor.GRAY));
		sheep_statue_grayt2 = registerBlock(new BlockSheep_Statue("sheepstatuegrayt2", "blocksheepstatuegrayt2", 2, EnumDyeColor.GRAY));
		sheep_statue_grayt3 = registerBlock(new BlockSheep_Statue("sheepstatuegrayt3", "blocksheepstatuegrayt3", 3, EnumDyeColor.GRAY));
		sheep_statue_grayt4 = registerBlock(new BlockSheep_Statue("sheepstatuegrayt4", "blocksheepstatuegrayt4", 4, EnumDyeColor.GRAY));

		sheep_statue_lightgray = registerBlock(new BlockSheep_Statue("sheepstatuelightgray", "blocksheepstatuelightgray", 1, EnumDyeColor.SILVER));
		sheep_statue_lightgrayt2 = registerBlock(new BlockSheep_Statue("sheepstatuelightgrayt2", "blocksheepstatuelightgrayt2", 2, EnumDyeColor.SILVER));
		sheep_statue_lightgrayt3 = registerBlock(new BlockSheep_Statue("sheepstatuelightgrayt3", "blocksheepstatuelightgrayt3", 3, EnumDyeColor.SILVER));
		sheep_statue_lightgrayt4 = registerBlock(new BlockSheep_Statue("sheepstatuelightgrayt4", "blocksheepstatuelightgrayt4", 4, EnumDyeColor.SILVER));

		sheep_statue_cyan = registerBlock(new BlockSheep_Statue("sheepstatuecyan", "blocksheepstatuecyan", 1, EnumDyeColor.CYAN));
		sheep_statue_cyant2 = registerBlock(new BlockSheep_Statue("sheepstatuecyant2", "blocksheepstatuecyant2", 2, EnumDyeColor.CYAN));
		sheep_statue_cyant3 = registerBlock(new BlockSheep_Statue("sheepstatuecyant3", "blocksheepstatuecyant3", 3, EnumDyeColor.CYAN));
		sheep_statue_cyant4 = registerBlock(new BlockSheep_Statue("sheepstatuecyant4", "blocksheepstatuecyant4", 4, EnumDyeColor.CYAN));

		sheep_statue_purple = registerBlock(new BlockSheep_Statue("sheepstatuepurple", "blocksheepstatuepurple", 1, EnumDyeColor.PURPLE));
		sheep_statue_purplet2 = registerBlock(new BlockSheep_Statue("sheepstatuepurplet2", "blocksheepstatuepurplet2", 2, EnumDyeColor.PURPLE));
		sheep_statue_purplet3 = registerBlock(new BlockSheep_Statue("sheepstatuepurplet3", "blocksheepstatuepurplet3", 3, EnumDyeColor.PURPLE));
		sheep_statue_purplet4 = registerBlock(new BlockSheep_Statue("sheepstatuepurplet4", "blocksheepstatuepurplet4", 4, EnumDyeColor.PURPLE));

		sheep_statue_blue = registerBlock(new BlockSheep_Statue("sheepstatueblue", "blocksheepstatueblue", 1, EnumDyeColor.BLUE));
		sheep_statue_bluet2 = registerBlock(new BlockSheep_Statue("sheepstatuebluet2", "blocksheepstatuebluet2", 2, EnumDyeColor.BLUE));
		sheep_statue_bluet3 = registerBlock(new BlockSheep_Statue("sheepstatuebluet3", "blocksheepstatuebluet3", 3, EnumDyeColor.BLUE));
		sheep_statue_bluet4 = registerBlock(new BlockSheep_Statue("sheepstatuebluet4", "blocksheepstatuebluet4", 4, EnumDyeColor.BLUE));

		sheep_statue_brown = registerBlock(new BlockSheep_Statue("sheepstatuebrown", "blocksheepstatuebrown", 1, EnumDyeColor.BROWN));
		sheep_statue_brownt2 = registerBlock(new BlockSheep_Statue("sheepstatuebrownt2", "blocksheepstatuebrownt2", 2, EnumDyeColor.BROWN));
		sheep_statue_brownt3 = registerBlock(new BlockSheep_Statue("sheepstatuebrownt3", "blocksheepstatuebrownt3", 3, EnumDyeColor.BROWN));
		sheep_statue_brownt4 = registerBlock(new BlockSheep_Statue("sheepstatuebrownt4", "blocksheepstatuebrownt4", 4, EnumDyeColor.BROWN));

		sheep_statue_green = registerBlock(new BlockSheep_Statue("sheepstatuegreen", "blocksheepstatuegreen", 1, EnumDyeColor.GREEN));
		sheep_statue_greent2 = registerBlock(new BlockSheep_Statue("sheepstatuegreent2", "blocksheepstatuegreent2", 2, EnumDyeColor.GREEN));
		sheep_statue_greent3 = registerBlock(new BlockSheep_Statue("sheepstatuegreent3", "blocksheepstatuegreent3", 3, EnumDyeColor.GREEN));
		sheep_statue_greent4 = registerBlock(new BlockSheep_Statue("sheepstatuegreent4", "blocksheepstatuegreent4", 4, EnumDyeColor.GREEN));
		
		sheep_statue_red = registerBlock(new BlockSheep_Statue("sheepstatuered", "blocksheepstatuered", 1, EnumDyeColor.RED));
		sheep_statue_redt2 = registerBlock(new BlockSheep_Statue("sheepstatueredt2", "blocksheepstatueredt2", 2, EnumDyeColor.RED));
		sheep_statue_redt3 = registerBlock(new BlockSheep_Statue("sheepstatueredt3", "blocksheepstatueredt3", 3, EnumDyeColor.RED));
		sheep_statue_redt4 = registerBlock(new BlockSheep_Statue("sheepstatueredt4", "blocksheepstatueredt4", 4, EnumDyeColor.RED));

		sheep_statue_black = registerBlock(new BlockSheep_Statue("sheepstatueblack", "blocksheepstatueblack", 1, EnumDyeColor.BLACK));
		sheep_statue_blackt2 = registerBlock(new BlockSheep_Statue("sheepstatueblackt2", "blocksheepstatueblackt2", 2, EnumDyeColor.BLACK));
		sheep_statue_blackt3 = registerBlock(new BlockSheep_Statue("sheepstatueblackt3", "blocksheepstatueblackt3", 3, EnumDyeColor.BLACK));
		sheep_statue_blackt4 = registerBlock(new BlockSheep_Statue("sheepstatueblackt4", "blocksheepstatueblackt4", 4, EnumDyeColor.BLACK));
		
		slime_statue = registerBlock(new BlockSlime_Statue("slimestatue", "blockslimestatue", 1));
		slime_statuet2 = registerBlock(new BlockSlime_Statue("slimestatuet2", "blockslimestatuet2", 2));
		slime_statuet3 = registerBlock(new BlockSlime_Statue("slimestatuet3", "blockslimestatuet3", 3));
		slime_statuet4 = registerBlock(new BlockSlime_Statue("slimestatuet4", "blockslimestatuet4", 4));
		
		snowgolem_statue = registerBlock(new BlockSnowGolem_Statue("snowgolemstatue", "blocksnowgolemstatue", 1));
		snowgolem_statuet2 = registerBlock(new BlockSnowGolem_Statue("snowgolemstatuet2", "blocksnowgolemstatuet2", 2));
		snowgolem_statuet3 = registerBlock(new BlockSnowGolem_Statue("snowgolemstatuet3", "blocksnowgolemstatuet3", 3));
		snowgolem_statuet4 = registerBlock(new BlockSnowGolem_Statue("snowgolemstatuet4", "blocksnowgolemstatuet4", 4));
		
		squid_statue = registerBlock(new BlockSquid_Statue("squidstatue", "blocksquidstatue", 1));
		squid_statuet2 = registerBlock(new BlockSquid_Statue("squidstatuet2", "blocksquidstatuet2", 2));
		squid_statuet3 = registerBlock(new BlockSquid_Statue("squidstatuet3", "blocksquidstatuet3", 3));
		squid_statuet4 = registerBlock(new BlockSquid_Statue("squidstatuet4", "blocksquidstatuet4", 4));
		
		villager_br_statue = registerBlock(new BlockVillager_Statue("villagerbrowncoatstatue", "blockvillagerbrowncoatstatue", 1));
		villager_br_statuet2 = registerBlock(new BlockVillager_Statue("villagerbrowncoatstatuet2", "blockvillagerbrowncoatstatuet2", 2));
		villager_br_statuet3 = registerBlock(new BlockVillager_Statue("villagerbrowncoatstatuet3", "blockvillagerbrowncoatstatuet3", 3));
		villager_br_statuet4 = registerBlock(new BlockVillager_Statue("villagerbrowncoatstatuet4", "blockvillagerbrowncoatstatuet4", 4));
		
		villager_pu_statue = registerBlock(new BlockVillager_Statue("villagerpurplecoatstatue", "blockvillagerpurplecoatstatue", 1));
		villager_pu_statuet2 = registerBlock(new BlockVillager_Statue("villagerpurplecoatstatuet2", "blockvillagerpurplecoatstatuet2", 2));
		villager_pu_statuet3 = registerBlock(new BlockVillager_Statue("villagerpurplecoatstatuet3", "blockvillagerpurplecoatstatuet3", 3));
		villager_pu_statuet4 = registerBlock(new BlockVillager_Statue("villagerpurplecoatstatuet4", "blockvillagerpurplecoatstatuet4", 4));
		
		villager_gr_statue = registerBlock(new BlockVillager_Statue("villagerstatue", "blockvillagerstatue", 1));
		villager_gr_statuet2 = registerBlock(new BlockVillager_Statue("villagerstatuet2", "blockvillagerstatuet2", 2));
		villager_gr_statuet3 = registerBlock(new BlockVillager_Statue("villagerstatuet3", "blockvillagerstatuet3", 3));
		villager_gr_statuet4 = registerBlock(new BlockVillager_Statue("villagerstatuet4", "blockvillagerstatuet4", 4));
		
		villager_wh_statue = registerBlock(new BlockVillager_Statue("villagerwhitecoatstatue", "blockvillagerwhitecoatstatue", 1));
		villager_wh_statuet2 = registerBlock(new BlockVillager_Statue("villagerwhitecoatstatuet2", "blockvillagerwhitecoatstatuet2", 2));
		villager_wh_statuet3 = registerBlock(new BlockVillager_Statue("villagerwhitecoatstatuet3", "blockvillagerwhitecoatstatuet3", 3));
		villager_wh_statuet4 = registerBlock(new BlockVillager_Statue("villagerwhitecoatstatuet4", "blockvillagerwhitecoatstatuet4", 4));
		
		info_statue = registerBlock(new BlockInfo_Statue("infostatue", "blockinfostatue", 1));
		player_statue = registerBlock(new BlockPlayer_Statue("playerstatue", "blockplayerstatue", ""));
		
		witch_statue = registerBlock(new BlockWitch_Statue("witchstatue", "blockwitchstatue", 1));
		witch_statuet2 = registerBlock(new BlockWitch_Statue("witchstatuet2", "blockwitchstatuet2", 2));
		witch_statuet3 = registerBlock(new BlockWitch_Statue("witchstatuet3", "blockwitchstatuet3", 3));
		witch_statuet4 = registerBlock(new BlockWitch_Statue("witchstatuet4", "blockwitchstatuet4", 4));
		
		zombie_statue = registerBlock(new BlockZombie_Statue("zombiestatue", "blockzombiestatue", 1));
		zombie_statuet2 = registerBlock(new BlockZombie_Statue("zombiestatuet2", "blockzombiestatuet2", 2));
		zombie_statuet3 = registerBlock(new BlockZombie_Statue("zombiestatuet3", "blockzombiestatuet3", 3));
		zombie_statuet4 = registerBlock(new BlockZombie_Statue("zombiestatuet4", "blockzombiestatuet4", 4));
		
		husk_statue = registerBlock(new BlockHusk_Statue("huskstatue", "blockhuskstatue", 1));
		husk_statuet2 = registerBlock(new BlockHusk_Statue("huskstatuet2", "blockhuskstatuet2", 2));
		husk_statuet3 = registerBlock(new BlockHusk_Statue("huskstatuet3", "blockhuskstatuet3", 3));
		husk_statuet4 = registerBlock(new BlockHusk_Statue("huskstatuet4", "blockhuskstatuet4", 4));
		
		shulker_statue = registerBlock(new BlockShulker_Statue("shulkerstatue", "blockshulkerstatue", 1));
		shulker_statuet2 = registerBlock(new BlockShulker_Statue("shulkerstatuet2", "blockshulkerstatuet2", 2));
		shulker_statuet3 = registerBlock(new BlockShulker_Statue("shulkerstatuet3", "blockshulkerstatuet3", 3));
		shulker_statuet4 = registerBlock(new BlockShulker_Statue("shulkerstatuet4", "blockshulkerstatuet4", 4));
		
		chicken_jockey_statue = registerBlock(new BlockChickenJockey_Statue("chickenjockeystatue", "blockchickenjockeystatue", 1));
		chicken_jockey_statuet2 = registerBlock(new BlockChickenJockey_Statue("chickenjockeystatuet2", "blockchickenjockeystatuet2", 2));
		chicken_jockey_statuet3 = registerBlock(new BlockChickenJockey_Statue("chickenjockeystatuet3", "blockchickenjockeystatuet3", 3));
		chicken_jockey_statuet4 = registerBlock(new BlockChickenJockey_Statue("chickenjockeystatuet4", "blockchickenjockeystatuet4", 4));
		
		endermite_statue = registerBlock(new BlockEndermite_Statue("endermitestatue", "blockendermitestatue", 1));
		
		magma_statue = registerBlock(new BlockMagmaSlime_Statue("magmastatue", "blockmagmastatue", 1));
		magma_statuet2 = registerBlock(new BlockMagmaSlime_Statue("magmastatuet2", "blockmagmastatuet2", 2));
		magma_statuet3 = registerBlock(new BlockMagmaSlime_Statue("magmastatuet3", "blockmagmastatuet3", 3));
		magma_statuet4 = registerBlock(new BlockMagmaSlime_Statue("magmastatuet4", "blockmagmastatuet4", 4));
		
		ghast_statue = registerBlock(new BlockGhast_Statue("ghaststatue", "blockghaststatue", 1));
		ghast_statuet2 = registerBlock(new BlockGhast_Statue("ghaststatuet2", "blockghaststatuet2", 2));
		ghast_statuet3 = registerBlock(new BlockGhast_Statue("ghaststatuet3", "blockghaststatuet3", 3));
		ghast_statuet4 = registerBlock(new BlockGhast_Statue("ghaststatuet4", "blockghaststatuet4", 4));
		
		guardian_statue = registerBlock(new BlockGuardian_Statue("guardianstatue", "blockguardianstatue", 1));
		guardian_statuet2 = registerBlock(new BlockGuardian_Statue("guardianstatuet2", "blockguardianstatuet2", 2));
		guardian_statuet3 = registerBlock(new BlockGuardian_Statue("guardianstatuet3", "blockguardianstatuet3", 3));
		guardian_statuet4 = registerBlock(new BlockGuardian_Statue("guardianstatuet4", "blockguardianstatuet4", 4));
		
		enderman_statue = registerBlock(new BlockEnderman_Statue("endermanstatue", "blockendermanstatue", 1));
		enderman_statuet2 = registerBlock(new BlockEnderman_Statue("endermanstatuet2", "blockendermanstatuet2", 2));
		enderman_statuet3 = registerBlock(new BlockEnderman_Statue("endermanstatuet3", "blockendermanstatuet3", 3));
		enderman_statuet4 = registerBlock(new BlockEnderman_Statue("endermanstatuet4", "blockendermanstatuet4", 4));
		
		pufferfish_statue = registerBlock(new BlockPufferfish_Statue("pufferfishstatue", "blockpufferfishstatue", 1));
		pufferfish_statuet2 = registerBlock(new BlockPufferfish_Statue("pufferfishstatuet2", "blockpufferfishstatuet2", 2));
		pufferfish_statuet3 = registerBlock(new BlockPufferfish_Statue("pufferfishstatuet3", "blockpufferfishstatuet3", 3));
		pufferfish_statuet4 = registerBlock(new BlockPufferfish_Statue("pufferfishstatuet4", "blockpufferfishstatuet4", 4));
		
		pebble = registerBlock(new BlockPebble("pebble", "blockpebble"));
		
		wasteland_statue = registerBlock(new BlockWastelandPig_Statue("wastelandpigstatue", "blockwastelandpigstatue", 1));
		wasteland_statuet2 = registerBlock(new BlockWastelandPig_Statue("wastelandpigstatuet2", "blockwastelandpigstatuet2", 2));
		wasteland_statuet3 = registerBlock(new BlockWastelandPig_Statue("wastelandpigstatuet3", "blockwastelandpigstatuet3", 3));
		wasteland_statuet4 = registerBlock(new BlockWastelandPig_Statue("wastelandpigstatuet4", "blockwastelandpigstatuet4", 4));
		
		display_stand = registerBlock(new BlockDisplayStand("displaystand", "blockdisplaystand"));
		sombrero = registerBlock(new BlockSombrero_Statue("sombrero", "blocksombrero"));
		bumbo_statue = registerBlock(new BlockBumbo_Statue("bumbostatue", "blockbumbostatue"));
		evoker_statue = registerBlock(new BlockEvoker_Statue("evokerstatue", "blockevokerstatue"));
		totemofundying_statue = registerBlock(new BlockTotemOfUndying_Statue("totemofundyingstatue", "blocktotemofundyingstatue"));
		
		registry.registerAll(BLOCKS.toArray(new Block[0]));
	}
	
	public static <T extends Block> T registerBlock(T block)
    {
        return registerBlock(block, new ItemBlockStatue(block));
    }
    
    public static <T extends Block> T registerBlock(T block, ItemBlock item)
    {
    	item.setRegistryName(block.getRegistryName());
    	StatuesItems.ITEMS.add(item);
        BLOCKS.add(block);
        return block;
    }
}
