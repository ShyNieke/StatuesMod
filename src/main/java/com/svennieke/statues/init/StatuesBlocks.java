package com.svennieke.statues.init;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.Statues.BlockBabyZombie_Statue;
import com.svennieke.statues.blocks.Statues.BlockBlaze_Statue;
import com.svennieke.statues.blocks.Statues.BlockBumbo_Statue;
import com.svennieke.statues.blocks.Statues.BlockChickenJockey_Statue;
import com.svennieke.statues.blocks.Statues.BlockChicken_Statue;
import com.svennieke.statues.blocks.Statues.BlockCow_Statue;
import com.svennieke.statues.blocks.Statues.BlockCreeper_Statue;
import com.svennieke.statues.blocks.Statues.BlockEnderman_Statue;
import com.svennieke.statues.blocks.Statues.BlockEndermite_Statue;
import com.svennieke.statues.blocks.Statues.BlockEtho_Statue;
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
import com.svennieke.statues.blocks.Statues.BlockSpider_Statue;
import com.svennieke.statues.blocks.Statues.BlockSquid_Statue;
import com.svennieke.statues.blocks.Statues.BlockTotemOfUndying_Statue;
import com.svennieke.statues.blocks.Statues.BlockVillager_Statue;
import com.svennieke.statues.blocks.Statues.BlockWastelandPig_Statue;
import com.svennieke.statues.blocks.Statues.BlockWitch_Statue;
import com.svennieke.statues.blocks.Statues.BlockZombie_Statue;
import com.svennieke.statues.blocks.decorative.BlockDisplayStand;
import com.svennieke.statues.blocks.decorative.BlockPebble;
import com.svennieke.statues.items.ItemBlockStatue;
import com.svennieke.statues.renderer.PlayerInventoryRender;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class StatuesBlocks {

	//Regular Statues
	public static Block baby_zombie_statue_t1;
	public static Block baby_zombie_statue_t2;
	public static Block baby_zombie_statue_t3;
	public static Block baby_zombie_statue_t4;
	public static Block baby_zombie_statue_t5;

	public static Block blaze_statue_t1;
	public static Block blaze_statue_t2;
	public static Block blaze_statue_t3;
	public static Block blaze_statue_t4;
	public static Block blaze_statue_t5;

	public static Block chicken_statue_t1;
	public static Block chicken_statue_t2;
	public static Block chicken_statue_t3;
	public static Block chicken_statue_t4;
	public static Block chicken_statue_t5;

	public static Block cow_statue_t1;
	public static Block cow_statue_t2;
	public static Block cow_statue_t3;
	public static Block cow_statue_t4;
	public static Block cow_statue_t5;

	public static Block creeper_statue_t1;
	public static Block creeper_statue_t2;
	public static Block creeper_statue_t3;
	public static Block creeper_statue_t4;
	public static Block creeper_statue_t5;

	public static Block flood_statue_t1;
	public static Block flood_statue_t2;
	public static Block flood_statue_t3;
	public static Block flood_statue_t4;
	public static Block flood_statue_t5;

	public static Block kingcluck_statue_t1;
	public static Block kingcluck_statue_t2;
	public static Block kingcluck_statue_t3;
	public static Block kingcluck_statue_t4;
	public static Block kingcluck_statue_t5;

	public static Block mooshroom_statue_t1;
	public static Block mooshroom_statue_t2;
	public static Block mooshroom_statue_t3;
	public static Block mooshroom_statue_t4;
	public static Block mooshroom_statue_t5;

	public static Block pig_statue_t1;
	public static Block pig_statue_t2;
	public static Block pig_statue_t3;
	public static Block pig_statue_t4;
	public static Block pig_statue_t5;

	public static Block rabbit_bs_statue_t1;
	public static Block rabbit_bs_statue_t2;
	public static Block rabbit_bs_statue_t3;
	public static Block rabbit_bs_statue_t4;
	public static Block rabbit_bs_statue_t5;

	public static Block rabbit_br_statue_t1;
	public static Block rabbit_br_statue_t2;
	public static Block rabbit_br_statue_t3;
	public static Block rabbit_br_statue_t4;
	public static Block rabbit_br_statue_t5;

	public static Block rabbit_bw_statue_t1;
	public static Block rabbit_bw_statue_t2;
	public static Block rabbit_bw_statue_t3;
	public static Block rabbit_bw_statue_t4;
	public static Block rabbit_bw_statue_t5;

	public static Block rabbit_ws_statue_t1;
	public static Block rabbit_ws_statue_t2;
	public static Block rabbit_ws_statue_t3;
	public static Block rabbit_ws_statue_t4;
	public static Block rabbit_ws_statue_t5;

	public static Block rabbit_wh_statue_t1;
	public static Block rabbit_wh_statue_t2;
	public static Block rabbit_wh_statue_t3;
	public static Block rabbit_wh_statue_t4;
	public static Block rabbit_wh_statue_t5;

	public static Block rabbit_go_statue_t1;
	public static Block rabbit_go_statue_t2;
	public static Block rabbit_go_statue_t3;
	public static Block rabbit_go_statue_t4;
	public static Block rabbit_go_statue_t5;

	public static Block sheep_shaven_statue_t1;
	public static Block sheep_shaven_statue_t2;
	public static Block sheep_shaven_statue_t3;
	public static Block sheep_shaven_statue_t4;
	public static Block sheep_shaven_statue_t5;

	public static Block slime_statue_t1;
	public static Block slime_statue_t2;
	public static Block slime_statue_t3;
	public static Block slime_statue_t4;
	public static Block slime_statue_t5;

	public static Block snowgolem_statue_t1;
	public static Block snowgolem_statue_t2;
	public static Block snowgolem_statue_t3;
	public static Block snowgolem_statue_t4;
	public static Block snowgolem_statue_t5;

	public static Block villager_br_statue_t1;
	public static Block villager_br_statue_t2;
	public static Block villager_br_statue_t3;
	public static Block villager_br_statue_t4;
	public static Block villager_br_statue_t5;

	public static Block villager_gr_statue_t1;
	public static Block villager_gr_statue_t2;
	public static Block villager_gr_statue_t3;
	public static Block villager_gr_statue_t4;
	public static Block villager_gr_statue_t5;

	public static Block villager_pu_statue_t1;
	public static Block villager_pu_statue_t2;
	public static Block villager_pu_statue_t3;
	public static Block villager_pu_statue_t4;
	public static Block villager_pu_statue_t5;

	public static Block villager_wh_statue_t1;
	public static Block villager_wh_statue_t2;
	public static Block villager_wh_statue_t3;
	public static Block villager_wh_statue_t4;
	public static Block villager_wh_statue_t5;

	public static Block squid_statue_t1;
	public static Block squid_statue_t2;
	public static Block squid_statue_t3;
	public static Block squid_statue_t4;
	public static Block squid_statue_t5;

	public static Block witch_statue_t1;
	public static Block witch_statue_t2;
	public static Block witch_statue_t3;
	public static Block witch_statue_t4;
	public static Block witch_statue_t5;

	public static Block zombie_statue_t1;
	public static Block zombie_statue_t2;
	public static Block zombie_statue_t3;
	public static Block zombie_statue_t4;
	public static Block zombie_statue_t5;

	public static Block husk_statue_t1;
	public static Block husk_statue_t2;
	public static Block husk_statue_t3;
	public static Block husk_statue_t4;
	public static Block husk_statue_t5;

	public static Block shulker_statue_t1;
	public static Block shulker_statue_t2;
	public static Block shulker_statue_t3;
	public static Block shulker_statue_t4;
	public static Block shulker_statue_t5;

	public static Block chicken_jockey_statue_t1;
	public static Block chicken_jockey_statue_t2;
	public static Block chicken_jockey_statue_t3;
	public static Block chicken_jockey_statue_t4;
	public static Block chicken_jockey_statue_t5;

	public static Block magma_statue_t1;
	public static Block magma_statue_t2;
	public static Block magma_statue_t3;
	public static Block magma_statue_t4;
	public static Block magma_statue_t5;

	public static Block ghast_statue_t1;
	public static Block ghast_statue_t2;
	public static Block ghast_statue_t3;
	public static Block ghast_statue_t4;
	public static Block ghast_statue_t5;

	public static Block guardian_statue_t1;
	public static Block guardian_statue_t2;
	public static Block guardian_statue_t3;
	public static Block guardian_statue_t4;
	public static Block guardian_statue_t5;

	public static Block wasteland_statue_t1;
	public static Block wasteland_statue_t2;
	public static Block wasteland_statue_t3;
	public static Block wasteland_statue_t4;
	public static Block wasteland_statue_t5;

	public static Block enderman_statue_t1;
	public static Block enderman_statue_t2;
	public static Block enderman_statue_t3;
	public static Block enderman_statue_t4;
	public static Block enderman_statue_t5;

	public static Block pufferfish_statue_t1;
	public static Block pufferfish_statue_t2;
	public static Block pufferfish_statue_t3;
	public static Block pufferfish_statue_t4;
	public static Block pufferfish_statue_t5;

	public static Block evoker_statue_t1;
	public static Block evoker_statue_t2;
	public static Block evoker_statue_t3;
	public static Block evoker_statue_t4;
	public static Block evoker_statue_t5;

	public static Block spider_statue_t1;
	public static Block spider_statue_t2;
	public static Block spider_statue_t3;
	public static Block spider_statue_t4;
	public static Block spider_statue_t5;

	public static Block campfire_statue_t1;
	//public static Block campfire_statue_t2;
	//public static Block campfire_statue_t3;
	//public static Block campfire_statue_t4;
	//public static Block campfire_statue_t5;
	
	//Sheep Statues
	public static Block sheep_statue_white_t1;
	public static Block sheep_statue_white_t2;
	public static Block sheep_statue_white_t3;
	public static Block sheep_statue_white_t4;
	public static Block sheep_statue_white_t5;

	public static Block sheep_statue_orange_t1;
	public static Block sheep_statue_orange_t2;
	public static Block sheep_statue_orange_t3;
	public static Block sheep_statue_orange_t4;
	public static Block sheep_statue_orange_t5;

	public static Block sheep_statue_magenta_t1;
	public static Block sheep_statue_magenta_t2;
	public static Block sheep_statue_magenta_t3;
	public static Block sheep_statue_magenta_t4;
	public static Block sheep_statue_magenta_t5;

	public static Block sheep_statue_lightblue_t1;
	public static Block sheep_statue_lightblue_t2;
	public static Block sheep_statue_lightblue_t3;
	public static Block sheep_statue_lightblue_t4;
	public static Block sheep_statue_lightblue_t5;

	public static Block sheep_statue_yellow_t1;
	public static Block sheep_statue_yellow_t2;
	public static Block sheep_statue_yellow_t3;
	public static Block sheep_statue_yellow_t4;
	public static Block sheep_statue_yellow_t5;

	public static Block sheep_statue_lime_t1;
	public static Block sheep_statue_lime_t2;
	public static Block sheep_statue_lime_t3;
	public static Block sheep_statue_lime_t4;
	public static Block sheep_statue_lime_t5;

	public static Block sheep_statue_pink_t1;
	public static Block sheep_statue_pink_t2;
	public static Block sheep_statue_pink_t3;
	public static Block sheep_statue_pink_t4;
	public static Block sheep_statue_pink_t5;

	public static Block sheep_statue_gray_t1;
	public static Block sheep_statue_gray_t2;
	public static Block sheep_statue_gray_t3;
	public static Block sheep_statue_gray_t4;
	public static Block sheep_statue_gray_t5;

	public static Block sheep_statue_lightgray_t1;
	public static Block sheep_statue_lightgray_t2;
	public static Block sheep_statue_lightgray_t3;
	public static Block sheep_statue_lightgray_t4;
	public static Block sheep_statue_lightgray_t5;

	public static Block sheep_statue_cyan_t1;
	public static Block sheep_statue_cyan_t2;
	public static Block sheep_statue_cyan_t3;
	public static Block sheep_statue_cyan_t4;
	public static Block sheep_statue_cyan_t5;

	public static Block sheep_statue_purple_t1;
	public static Block sheep_statue_purple_t2;
	public static Block sheep_statue_purple_t3;
	public static Block sheep_statue_purple_t4;
	public static Block sheep_statue_purple_t5;

	public static Block sheep_statue_blue_t1;
	public static Block sheep_statue_blue_t2;
	public static Block sheep_statue_blue_t3;
	public static Block sheep_statue_blue_t4;
	public static Block sheep_statue_blue_t5;

	public static Block sheep_statue_brown_t1;
	public static Block sheep_statue_brown_t2;
	public static Block sheep_statue_brown_t3;
	public static Block sheep_statue_brown_t4;
	public static Block sheep_statue_brown_t5;

	public static Block sheep_statue_green_t1;
	public static Block sheep_statue_green_t2;
	public static Block sheep_statue_green_t3;
	public static Block sheep_statue_green_t4;
	public static Block sheep_statue_green_t5;

	public static Block sheep_statue_red_t1;
	public static Block sheep_statue_red_t2;
	public static Block sheep_statue_red_t3;
	public static Block sheep_statue_red_t4;
	public static Block sheep_statue_red_t5;

	public static Block sheep_statue_black_t1;
	public static Block sheep_statue_black_t2;
	public static Block sheep_statue_black_t3;
	public static Block sheep_statue_black_t4;
	public static Block sheep_statue_black_t5;

	//Other Statues
	public static Block info_statue;
	public static Block player_statue;
	public static Block endermite_statue;
	public static Block totemofundying_statue;
	public static Block pebble;
	public static Block display_stand;
	public static Block sombrero;
	public static Block bumbo_statue;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();

		baby_zombie_statue_t1 = registerStatue(new BlockBabyZombie_Statue(Block.Properties.create(Material.TNT)), "baby_zombie_statue_t1", 1);
		baby_zombie_statue_t2 = registerStatue(new BlockBabyZombie_Statue(Block.Properties.create(Material.TNT)), "baby_zombie_statue_t2", 2);
		baby_zombie_statue_t3 = registerStatue(new BlockBabyZombie_Statue(Block.Properties.create(Material.TNT)), "baby_zombie_statue_t3", 3);
		baby_zombie_statue_t4 = registerStatue(new BlockBabyZombie_Statue(Block.Properties.create(Material.TNT)), "baby_zombie_statue_t4", 4);
		baby_zombie_statue_t5 = registerStatue(new BlockBabyZombie_Statue(Block.Properties.create(Material.TNT)), "baby_zombie_statue_t5", 5);

		blaze_statue_t1 = registerStatue(new BlockBlaze_Statue(Block.Properties.create(Material.TNT)), "blaze_statue_t1", 1);
		blaze_statue_t2 = registerStatue(new BlockBlaze_Statue(Block.Properties.create(Material.TNT)), "blaze_statue_t2", 2);
		blaze_statue_t3 = registerStatue(new BlockBlaze_Statue(Block.Properties.create(Material.TNT)), "blaze_statue_t3", 3);
		blaze_statue_t4 = registerStatue(new BlockBlaze_Statue(Block.Properties.create(Material.TNT)), "blaze_statue_t4", 4);
		blaze_statue_t5 = registerStatue(new BlockBlaze_Statue(Block.Properties.create(Material.TNT)), "blaze_statue_t5", 5);

		chicken_statue_t1 = registerStatue(new BlockChicken_Statue(Block.Properties.create(Material.TNT)), "chicken_statue_t1", 1);
		chicken_statue_t2 = registerStatue(new BlockChicken_Statue(Block.Properties.create(Material.TNT)), "chicken_statue_t2", 2);
		chicken_statue_t3 = registerStatue(new BlockChicken_Statue(Block.Properties.create(Material.TNT)), "chicken_statue_t3", 3);
		chicken_statue_t4 = registerStatue(new BlockChicken_Statue(Block.Properties.create(Material.TNT)), "chicken_statue_t4", 4);
		chicken_statue_t5 = registerStatue(new BlockChicken_Statue(Block.Properties.create(Material.TNT)), "chicken_statue_t5", 5);

		cow_statue_t1 = registerStatue(new BlockCow_Statue(Block.Properties.create(Material.TNT)), "cow_statue_t1", 1);
		cow_statue_t2 = registerStatue(new BlockCow_Statue(Block.Properties.create(Material.TNT)), "cow_statue_t2", 2);
		cow_statue_t3 = registerStatue(new BlockCow_Statue(Block.Properties.create(Material.TNT)), "cow_statue_t3", 3);
		cow_statue_t4 = registerStatue(new BlockCow_Statue(Block.Properties.create(Material.TNT)), "cow_statue_t4", 4);
		cow_statue_t5 = registerStatue(new BlockCow_Statue(Block.Properties.create(Material.TNT)), "cow_statue_t5", 5);

		creeper_statue_t1 = registerStatue(new BlockCreeper_Statue(Block.Properties.create(Material.TNT)), "creeper_statue_t1", 1);
		creeper_statue_t2 = registerStatue(new BlockCreeper_Statue(Block.Properties.create(Material.TNT)), "creeper_statue_t2", 2);
		creeper_statue_t3 = registerStatue(new BlockCreeper_Statue(Block.Properties.create(Material.TNT)), "creeper_statue_t3", 3);
		creeper_statue_t4 = registerStatue(new BlockCreeper_Statue(Block.Properties.create(Material.TNT)), "creeper_statue_t4", 4);
		creeper_statue_t5 = registerStatue(new BlockCreeper_Statue(Block.Properties.create(Material.TNT)), "creeper_statue_t5", 5);

		flood_statue_t1 = registerStatue(new BlockFlood_Statue(Block.Properties.create(Material.TNT)), "flood_statue_t1", 1);
		flood_statue_t2 = registerStatue(new BlockFlood_Statue(Block.Properties.create(Material.TNT)), "flood_statue_t2", 2);
		flood_statue_t3 = registerStatue(new BlockFlood_Statue(Block.Properties.create(Material.TNT)), "flood_statue_t3", 3);
		flood_statue_t4 = registerStatue(new BlockFlood_Statue(Block.Properties.create(Material.TNT)), "flood_statue_t4", 4);
		flood_statue_t5 = registerStatue(new BlockFlood_Statue(Block.Properties.create(Material.TNT)), "flood_statue_t5", 5);

		kingcluck_statue_t1 = registerStatue(new BlockKingCluck_Statue(Block.Properties.create(Material.TNT)), "kingcluck_statue_t1", 1);
		kingcluck_statue_t2 = registerStatue(new BlockKingCluck_Statue(Block.Properties.create(Material.TNT)), "kingcluck_statue_t2", 2);
		kingcluck_statue_t3 = registerStatue(new BlockKingCluck_Statue(Block.Properties.create(Material.TNT)), "kingcluck_statue_t3", 3);
		kingcluck_statue_t4 = registerStatue(new BlockKingCluck_Statue(Block.Properties.create(Material.TNT)), "kingcluck_statue_t4", 4);
		kingcluck_statue_t5 = registerStatue(new BlockKingCluck_Statue(Block.Properties.create(Material.TNT)), "kingcluck_statue_t5", 5);

		mooshroom_statue_t1 = registerStatue(new BlockMooshroom_Statue(Block.Properties.create(Material.TNT)), "mooshroom_statue_t1", 1);
		mooshroom_statue_t2 = registerStatue(new BlockMooshroom_Statue(Block.Properties.create(Material.TNT)), "mooshroom_statue_t2", 2);
		mooshroom_statue_t3 = registerStatue(new BlockMooshroom_Statue(Block.Properties.create(Material.TNT)), "mooshroom_statue_t3", 3);
		mooshroom_statue_t4 = registerStatue(new BlockMooshroom_Statue(Block.Properties.create(Material.TNT)), "mooshroom_statue_t4", 4);
		mooshroom_statue_t5 = registerStatue(new BlockMooshroom_Statue(Block.Properties.create(Material.TNT)), "mooshroom_statue_t5", 5);

		pig_statue_t1 = registerStatue(new BlockPig_Statue(Block.Properties.create(Material.TNT)), "pig_statue_t1", 1);
		pig_statue_t2 = registerStatue(new BlockPig_Statue(Block.Properties.create(Material.TNT)), "pig_statue_t2", 2);
		pig_statue_t3 = registerStatue(new BlockPig_Statue(Block.Properties.create(Material.TNT)), "pig_statue_t3", 3);
		pig_statue_t4 = registerStatue(new BlockPig_Statue(Block.Properties.create(Material.TNT)), "pig_statue_t4", 4);
		pig_statue_t5 = registerStatue(new BlockPig_Statue(Block.Properties.create(Material.TNT)), "pig_statue_t5", 5);

		rabbit_bs_statue_t1 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bs_statue_t1", 1);
		rabbit_bs_statue_t2 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bs_statue_t2", 2);
		rabbit_bs_statue_t3 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bs_statue_t3", 3);
		rabbit_bs_statue_t4 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bs_statue_t4", 4);
		rabbit_bs_statue_t5 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bs_statue_t5", 5);

		rabbit_br_statue_t1 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_br_statue_t1", 1);
		rabbit_br_statue_t2 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_br_statue_t2", 2);
		rabbit_br_statue_t3 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_br_statue_t3", 3);
		rabbit_br_statue_t4 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_br_statue_t4", 4);
		rabbit_br_statue_t5 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_br_statue_t5", 5);

		rabbit_bw_statue_t1 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bw_statue_t1", 1);
		rabbit_bw_statue_t2 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bw_statue_t2", 2);
		rabbit_bw_statue_t3 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bw_statue_t3", 3);
		rabbit_bw_statue_t4 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bw_statue_t4", 4);
		rabbit_bw_statue_t5 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_bw_statue_t5", 5);

		rabbit_ws_statue_t1 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_ws_statue_t1", 1);
		rabbit_ws_statue_t2 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_ws_statue_t2", 2);
		rabbit_ws_statue_t3 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_ws_statue_t3", 3);
		rabbit_ws_statue_t4 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_ws_statue_t4", 4);
		rabbit_ws_statue_t5 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_ws_statue_t5", 5);

		rabbit_wh_statue_t1 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_wh_statue_t1", 1);
		rabbit_wh_statue_t2 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_wh_statue_t2", 2);
		rabbit_wh_statue_t3 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_wh_statue_t3", 3);
		rabbit_wh_statue_t4 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_wh_statue_t4", 4);
		rabbit_wh_statue_t5 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_wh_statue_t5", 5);

		rabbit_go_statue_t1 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_go_statue_t1", 1);
		rabbit_go_statue_t2 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_go_statue_t2", 2);
		rabbit_go_statue_t3 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_go_statue_t3", 3);
		rabbit_go_statue_t4 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_go_statue_t4", 4);
		rabbit_go_statue_t5 = registerStatue(new BlockRabbit_Statue(Block.Properties.create(Material.TNT)), "rabbit_go_statue_t5", 5);

		sheep_shaven_statue_t1 = registerStatue(new BlockSheepShaven_Statue(Block.Properties.create(Material.TNT)), "sheep_shaven_statue_t1", 1);
		sheep_shaven_statue_t2 = registerStatue(new BlockSheepShaven_Statue(Block.Properties.create(Material.TNT)), "sheep_shaven_statue_t2", 2);
		sheep_shaven_statue_t3 = registerStatue(new BlockSheepShaven_Statue(Block.Properties.create(Material.TNT)), "sheep_shaven_statue_t3", 3);
		sheep_shaven_statue_t4 = registerStatue(new BlockSheepShaven_Statue(Block.Properties.create(Material.TNT)), "sheep_shaven_statue_t4", 4);
		sheep_shaven_statue_t5 = registerStatue(new BlockSheepShaven_Statue(Block.Properties.create(Material.TNT)), "sheep_shaven_statue_t5", 5);

		slime_statue_t1 = registerStatue(new BlockSlime_Statue(Block.Properties.create(Material.TNT)), "slime_statue_t1", 1);
		slime_statue_t2 = registerStatue(new BlockSlime_Statue(Block.Properties.create(Material.TNT)), "slime_statue_t2", 2);
		slime_statue_t3 = registerStatue(new BlockSlime_Statue(Block.Properties.create(Material.TNT)), "slime_statue_t3", 3);
		slime_statue_t4 = registerStatue(new BlockSlime_Statue(Block.Properties.create(Material.TNT)), "slime_statue_t4", 4);
		slime_statue_t5 = registerStatue(new BlockSlime_Statue(Block.Properties.create(Material.TNT)), "slime_statue_t5", 5);

		snowgolem_statue_t1 = registerStatue(new BlockSnowGolem_Statue(Block.Properties.create(Material.TNT)), "snowgolem_statue_t1", 1);
		snowgolem_statue_t2 = registerStatue(new BlockSnowGolem_Statue(Block.Properties.create(Material.TNT)), "snowgolem_statue_t2", 2);
		snowgolem_statue_t3 = registerStatue(new BlockSnowGolem_Statue(Block.Properties.create(Material.TNT)), "snowgolem_statue_t3", 3);
		snowgolem_statue_t4 = registerStatue(new BlockSnowGolem_Statue(Block.Properties.create(Material.TNT)), "snowgolem_statue_t4", 4);
		snowgolem_statue_t5 = registerStatue(new BlockSnowGolem_Statue(Block.Properties.create(Material.TNT)), "snowgolem_statue_t5", 5);

		villager_br_statue_t1 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_br_statue_t1", 1);
		villager_br_statue_t2 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_br_statue_t2", 2);
		villager_br_statue_t3 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_br_statue_t3", 3);
		villager_br_statue_t4 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_br_statue_t4", 4);
		villager_br_statue_t5 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_br_statue_t5", 5);

		villager_gr_statue_t1 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_gr_statue_t1", 1);
		villager_gr_statue_t2 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_gr_statue_t2", 2);
		villager_gr_statue_t3 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_gr_statue_t3", 3);
		villager_gr_statue_t4 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_gr_statue_t4", 4);
		villager_gr_statue_t5 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_gr_statue_t5", 5);

		villager_pu_statue_t1 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_pu_statue_t1", 1);
		villager_pu_statue_t2 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_pu_statue_t2", 2);
		villager_pu_statue_t3 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_pu_statue_t3", 3);
		villager_pu_statue_t4 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_pu_statue_t4", 4);
		villager_pu_statue_t5 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_pu_statue_t5", 5);

		villager_wh_statue_t1 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_wh_statue_t1", 1);
		villager_wh_statue_t2 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_wh_statue_t2", 2);
		villager_wh_statue_t3 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_wh_statue_t3", 3);
		villager_wh_statue_t4 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_wh_statue_t4", 4);
		villager_wh_statue_t5 = registerStatue(new BlockVillager_Statue(Block.Properties.create(Material.TNT)), "villager_wh_statue_t5", 5);

		squid_statue_t1 = registerStatue(new BlockSquid_Statue(Block.Properties.create(Material.TNT)), "squid_statue_t1", 1);
		squid_statue_t2 = registerStatue(new BlockSquid_Statue(Block.Properties.create(Material.TNT)), "squid_statue_t2", 2);
		squid_statue_t3 = registerStatue(new BlockSquid_Statue(Block.Properties.create(Material.TNT)), "squid_statue_t3", 3);
		squid_statue_t4 = registerStatue(new BlockSquid_Statue(Block.Properties.create(Material.TNT)), "squid_statue_t4", 4);
		squid_statue_t5 = registerStatue(new BlockSquid_Statue(Block.Properties.create(Material.TNT)), "squid_statue_t5", 5);

		witch_statue_t1 = registerStatue(new BlockWitch_Statue(Block.Properties.create(Material.TNT)), "witch_statue_t1", 1);
		witch_statue_t2 = registerStatue(new BlockWitch_Statue(Block.Properties.create(Material.TNT)), "witch_statue_t2", 2);
		witch_statue_t3 = registerStatue(new BlockWitch_Statue(Block.Properties.create(Material.TNT)), "witch_statue_t3", 3);
		witch_statue_t4 = registerStatue(new BlockWitch_Statue(Block.Properties.create(Material.TNT)), "witch_statue_t4", 4);
		witch_statue_t5 = registerStatue(new BlockWitch_Statue(Block.Properties.create(Material.TNT)), "witch_statue_t5", 5);

		zombie_statue_t1 = registerStatue(new BlockZombie_Statue(Block.Properties.create(Material.TNT)), "zombie_statue_t1", 1);
		zombie_statue_t2 = registerStatue(new BlockZombie_Statue(Block.Properties.create(Material.TNT)), "zombie_statue_t2", 2);
		zombie_statue_t3 = registerStatue(new BlockZombie_Statue(Block.Properties.create(Material.TNT)), "zombie_statue_t3", 3);
		zombie_statue_t4 = registerStatue(new BlockZombie_Statue(Block.Properties.create(Material.TNT)), "zombie_statue_t4", 4);
		zombie_statue_t5 = registerStatue(new BlockZombie_Statue(Block.Properties.create(Material.TNT)), "zombie_statue_t5", 5);

		husk_statue_t1 = registerStatue(new BlockHusk_Statue(Block.Properties.create(Material.TNT)), "husk_statue_t1", 1);
		husk_statue_t2 = registerStatue(new BlockHusk_Statue(Block.Properties.create(Material.TNT)), "husk_statue_t2", 2);
		husk_statue_t3 = registerStatue(new BlockHusk_Statue(Block.Properties.create(Material.TNT)), "husk_statue_t3", 3);
		husk_statue_t4 = registerStatue(new BlockHusk_Statue(Block.Properties.create(Material.TNT)), "husk_statue_t4", 4);
		husk_statue_t5 = registerStatue(new BlockHusk_Statue(Block.Properties.create(Material.TNT)), "husk_statue_t5", 5);

		shulker_statue_t1 = registerStatue(new BlockShulker_Statue(Block.Properties.create(Material.TNT)), "shulker_statue_t1", 1);
		shulker_statue_t2 = registerStatue(new BlockShulker_Statue(Block.Properties.create(Material.TNT)), "shulker_statue_t2", 2);
		shulker_statue_t3 = registerStatue(new BlockShulker_Statue(Block.Properties.create(Material.TNT)), "shulker_statue_t3", 3);
		shulker_statue_t4 = registerStatue(new BlockShulker_Statue(Block.Properties.create(Material.TNT)), "shulker_statue_t4", 4);
		shulker_statue_t5 = registerStatue(new BlockShulker_Statue(Block.Properties.create(Material.TNT)), "shulker_statue_t5", 5);

		chicken_jockey_statue_t1 = registerStatue(new BlockChickenJockey_Statue(Block.Properties.create(Material.TNT)), "chicken_jockey_statue_t1", 1);
		chicken_jockey_statue_t2 = registerStatue(new BlockChickenJockey_Statue(Block.Properties.create(Material.TNT)), "chicken_jockey_statue_t2", 2);
		chicken_jockey_statue_t3 = registerStatue(new BlockChickenJockey_Statue(Block.Properties.create(Material.TNT)), "chicken_jockey_statue_t3", 3);
		chicken_jockey_statue_t4 = registerStatue(new BlockChickenJockey_Statue(Block.Properties.create(Material.TNT)), "chicken_jockey_statue_t4", 4);
		chicken_jockey_statue_t5 = registerStatue(new BlockChickenJockey_Statue(Block.Properties.create(Material.TNT)), "chicken_jockey_statue_t5", 5);

		magma_statue_t1 = registerStatue(new BlockMagmaSlime_Statue(Block.Properties.create(Material.TNT)), "magma_statue_t1", 1);
		magma_statue_t2 = registerStatue(new BlockMagmaSlime_Statue(Block.Properties.create(Material.TNT)), "magma_statue_t2", 2);
		magma_statue_t3 = registerStatue(new BlockMagmaSlime_Statue(Block.Properties.create(Material.TNT)), "magma_statue_t3", 3);
		magma_statue_t4 = registerStatue(new BlockMagmaSlime_Statue(Block.Properties.create(Material.TNT)), "magma_statue_t4", 4);
		magma_statue_t5 = registerStatue(new BlockMagmaSlime_Statue(Block.Properties.create(Material.TNT)), "magma_statue_t5", 5);

		ghast_statue_t1 = registerStatue(new BlockGhast_Statue(Block.Properties.create(Material.TNT)), "ghast_statue_t1", 1);
		ghast_statue_t2 = registerStatue(new BlockGhast_Statue(Block.Properties.create(Material.TNT)), "ghast_statue_t2", 2);
		ghast_statue_t3 = registerStatue(new BlockGhast_Statue(Block.Properties.create(Material.TNT)), "ghast_statue_t3", 3);
		ghast_statue_t4 = registerStatue(new BlockGhast_Statue(Block.Properties.create(Material.TNT)), "ghast_statue_t4", 4);
		ghast_statue_t5 = registerStatue(new BlockGhast_Statue(Block.Properties.create(Material.TNT)), "ghast_statue_t5", 5);

		guardian_statue_t1 = registerStatue(new BlockGuardian_Statue(Block.Properties.create(Material.TNT)), "guardian_statue_t1", 1);
		guardian_statue_t2 = registerStatue(new BlockGuardian_Statue(Block.Properties.create(Material.TNT)), "guardian_statue_t2", 2);
		guardian_statue_t3 = registerStatue(new BlockGuardian_Statue(Block.Properties.create(Material.TNT)), "guardian_statue_t3", 3);
		guardian_statue_t4 = registerStatue(new BlockGuardian_Statue(Block.Properties.create(Material.TNT)), "guardian_statue_t4", 4);
		guardian_statue_t5 = registerStatue(new BlockGuardian_Statue(Block.Properties.create(Material.TNT)), "guardian_statue_t5", 5);

		wasteland_statue_t1 = registerStatue(new BlockWastelandPig_Statue(Block.Properties.create(Material.TNT)), "wasteland_statue_t1", 1);
		wasteland_statue_t2 = registerStatue(new BlockWastelandPig_Statue(Block.Properties.create(Material.TNT)), "wasteland_statue_t2", 2);
		wasteland_statue_t3 = registerStatue(new BlockWastelandPig_Statue(Block.Properties.create(Material.TNT)), "wasteland_statue_t3", 3);
		wasteland_statue_t4 = registerStatue(new BlockWastelandPig_Statue(Block.Properties.create(Material.TNT)), "wasteland_statue_t4", 4);
		wasteland_statue_t5 = registerStatue(new BlockWastelandPig_Statue(Block.Properties.create(Material.TNT)), "wasteland_statue_t5", 5);

		enderman_statue_t1 = registerStatue(new BlockEnderman_Statue(Block.Properties.create(Material.TNT)), "enderman_statue_t1", 1);
		enderman_statue_t2 = registerStatue(new BlockEnderman_Statue(Block.Properties.create(Material.TNT)), "enderman_statue_t2", 2);
		enderman_statue_t3 = registerStatue(new BlockEnderman_Statue(Block.Properties.create(Material.TNT)), "enderman_statue_t3", 3);
		enderman_statue_t4 = registerStatue(new BlockEnderman_Statue(Block.Properties.create(Material.TNT)), "enderman_statue_t4", 4);
		enderman_statue_t5 = registerStatue(new BlockEnderman_Statue(Block.Properties.create(Material.TNT)), "enderman_statue_t5", 5);

		pufferfish_statue_t1 = registerStatue(new BlockPufferfish_Statue(Block.Properties.create(Material.TNT)), "pufferfish_statue_t1", 1);
		pufferfish_statue_t2 = registerStatue(new BlockPufferfish_Statue(Block.Properties.create(Material.TNT)), "pufferfish_statue_t2", 2);
		pufferfish_statue_t3 = registerStatue(new BlockPufferfish_Statue(Block.Properties.create(Material.TNT)), "pufferfish_statue_t3", 3);
		pufferfish_statue_t4 = registerStatue(new BlockPufferfish_Statue(Block.Properties.create(Material.TNT)), "pufferfish_statue_t4", 4);
		pufferfish_statue_t5 = registerStatue(new BlockPufferfish_Statue(Block.Properties.create(Material.TNT)), "pufferfish_statue_t5", 5);

		evoker_statue_t1 = registerStatue(new BlockEvoker_Statue(Block.Properties.create(Material.TNT)), "evoker_statue_t1", 1);
		evoker_statue_t2 = registerStatue(new BlockEvoker_Statue(Block.Properties.create(Material.TNT)), "evoker_statue_t2", 2);
		evoker_statue_t3 = registerStatue(new BlockEvoker_Statue(Block.Properties.create(Material.TNT)), "evoker_statue_t3", 3);
		evoker_statue_t4 = registerStatue(new BlockEvoker_Statue(Block.Properties.create(Material.TNT)), "evoker_statue_t4", 4);
		evoker_statue_t5 = registerStatue(new BlockEvoker_Statue(Block.Properties.create(Material.TNT)), "evoker_statue_t5", 5);

		spider_statue_t1 = registerStatue(new BlockSpider_Statue(Block.Properties.create(Material.TNT)), "spider_statue_t1", 1);
		spider_statue_t2 = registerStatue(new BlockSpider_Statue(Block.Properties.create(Material.TNT)), "spider_statue_t2", 2);
		spider_statue_t3 = registerStatue(new BlockSpider_Statue(Block.Properties.create(Material.TNT)), "spider_statue_t3", 3);
		spider_statue_t4 = registerStatue(new BlockSpider_Statue(Block.Properties.create(Material.TNT)), "spider_statue_t4", 4);
		spider_statue_t5 = registerStatue(new BlockSpider_Statue(Block.Properties.create(Material.TNT)), "spider_statue_t5", 5);

		campfire_statue_t1 = registerStatue(new BlockEtho_Statue(Block.Properties.create(Material.TNT)), "campfire_statue_t1", 1);
		//campfire_statue_t2 = registerStatue(new BlockEtho_Statue(Block.Properties.create(Material.TNT)), "campfire_statue_t2", 2);
		//campfire_statue_t3 = registerStatue(new BlockEtho_Statue(Block.Properties.create(Material.TNT)), "campfire_statue_t3", 3);
		//campfire_statue_t4 = registerStatue(new BlockEtho_Statue(Block.Properties.create(Material.TNT)), "campfire_statue_t4", 4);
		//campfire_statue_t5 = registerStatue(new BlockEtho_Statue(Block.Properties.create(Material.TNT)), "campfire_statue_t5", 5);

		sheep_statue_white_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_white_t1", 1);
		sheep_statue_white_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_white_t2", 2);
		sheep_statue_white_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_white_t3", 3);
		sheep_statue_white_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_white_t4", 4);
		sheep_statue_white_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_white_t5", 5);

		sheep_statue_orange_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_orange_t1", 1);
		sheep_statue_orange_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_orange_t2", 2);
		sheep_statue_orange_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_orange_t3", 3);
		sheep_statue_orange_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_orange_t4", 4);
		sheep_statue_orange_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_orange_t5", 5);

		sheep_statue_magenta_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_magenta_t1", 1);
		sheep_statue_magenta_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_magenta_t2", 2);
		sheep_statue_magenta_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_magenta_t3", 3);
		sheep_statue_magenta_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_magenta_t4", 4);
		sheep_statue_magenta_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_magenta_t5", 5);

		sheep_statue_lightblue_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightblue_t1", 1);
		sheep_statue_lightblue_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightblue_t2", 2);
		sheep_statue_lightblue_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightblue_t3", 3);
		sheep_statue_lightblue_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightblue_t4", 4);
		sheep_statue_lightblue_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightblue_t5", 5);

		sheep_statue_yellow_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_yellow_t1", 1);
		sheep_statue_yellow_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_yellow_t2", 2);
		sheep_statue_yellow_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_yellow_t3", 3);
		sheep_statue_yellow_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_yellow_t4", 4);
		sheep_statue_yellow_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_yellow_t5", 5);

		sheep_statue_lime_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lime_t1", 1);
		sheep_statue_lime_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lime_t2", 2);
		sheep_statue_lime_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lime_t3", 3);
		sheep_statue_lime_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lime_t4", 4);
		sheep_statue_lime_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lime_t5", 5);

		sheep_statue_pink_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_pink_t1", 1);
		sheep_statue_pink_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_pink_t2", 2);
		sheep_statue_pink_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_pink_t3", 3);
		sheep_statue_pink_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_pink_t4", 4);
		sheep_statue_pink_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_pink_t5", 5);

		sheep_statue_gray_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_gray_t1", 1);
		sheep_statue_gray_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_gray_t2", 2);
		sheep_statue_gray_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_gray_t3", 3);
		sheep_statue_gray_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_gray_t4", 4);
		sheep_statue_gray_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_gray_t5", 5);

		sheep_statue_lightgray_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightgray_t1", 1);
		sheep_statue_lightgray_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightgray_t2", 2);
		sheep_statue_lightgray_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightgray_t3", 3);
		sheep_statue_lightgray_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightgray_t4", 4);
		sheep_statue_lightgray_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_lightgray_t5", 5);

		sheep_statue_cyan_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_cyan_t1", 1);
		sheep_statue_cyan_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_cyan_t2", 2);
		sheep_statue_cyan_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_cyan_t3", 3);
		sheep_statue_cyan_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_cyan_t4", 4);
		sheep_statue_cyan_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_cyan_t5", 5);

		sheep_statue_purple_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_purple_t1", 1);
		sheep_statue_purple_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_purple_t2", 2);
		sheep_statue_purple_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_purple_t3", 3);
		sheep_statue_purple_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_purple_t4", 4);
		sheep_statue_purple_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_purple_t5", 5);

		sheep_statue_blue_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_blue_t1", 1);
		sheep_statue_blue_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_blue_t2", 2);
		sheep_statue_blue_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_blue_t3", 3);
		sheep_statue_blue_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_blue_t4", 4);
		sheep_statue_blue_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_blue_t5", 5);

		sheep_statue_brown_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_brown_t1", 1);
		sheep_statue_brown_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_brown_t2", 2);
		sheep_statue_brown_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_brown_t3", 3);
		sheep_statue_brown_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_brown_t4", 4);
		sheep_statue_brown_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_brown_t5", 5);

		sheep_statue_green_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_green_t1", 1);
		sheep_statue_green_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_green_t2", 2);
		sheep_statue_green_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_green_t3", 3);
		sheep_statue_green_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_green_t4", 4);
		sheep_statue_green_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_green_t5", 5);

		sheep_statue_red_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_red_t1", 1);
		sheep_statue_red_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_red_t2", 2);
		sheep_statue_red_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_red_t3", 3);
		sheep_statue_red_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_red_t4", 4);
		sheep_statue_red_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_red_t5", 5);

		sheep_statue_black_t1 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_black_t1", 1);
		sheep_statue_black_t2 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_black_t2", 2);
		sheep_statue_black_t3 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_black_t3", 3);
		sheep_statue_black_t4 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_black_t4", 4);
		sheep_statue_black_t5 = registerStatue(new BlockSheep_Statue(Block.Properties.create(Material.TNT)), "sheep_statue_black_t5", 5);

		info_statue = registerBlock(new BlockInfo_Statue(Block.Properties.create(Material.TNT)), "info_statue");
		player_statue = registerPlayer(new BlockPlayer_Statue(Block.Properties.create(Material.TNT)), "player_statue");
		
		endermite_statue = registerBlock(new BlockEndermite_Statue(Block.Properties.create(Material.TNT)), "endermite_statue");

		pebble = registerBlock(new BlockPebble(Block.Properties.create(Material.SAND)), "pebble");

		display_stand = registerBlock(new BlockDisplayStand(Block.Properties.create(Material.ROCK)), "display_stand");
		sombrero = registerBlock(new BlockSombrero_Statue(Block.Properties.create(Material.TNT)), "sombrero");
		bumbo_statue = registerBlock(new BlockBumbo_Statue(Block.Properties.create(Material.TNT)), "bumbo_statue");
		totemofundying_statue = registerBlock(new BlockTotemOfUndying_Statue(Block.Properties.create(Material.TNT)), "totem_of_undying_statue");
		
		registry.registerAll(BLOCKS.toArray(new Block[0]));
	}
    
	public static <T extends Block> Block registerStatue(T block, String registry, int tier)
	{
        IStatue statue;
        block.setRegistryName(new ResourceLocation(Reference.MOD_ID, registry));
        if(block instanceof IStatue)
        {
            statue = (IStatue)block;
            statue.setTier(tier);
            
            statue.setColor(statue.getColor());
    	    return registerBlock((Block)statue, new ItemBlockStatue((Block)statue, itemBuilder()));
        }
        else
        return registerBlock(block, new ItemBlockStatue(block, itemBuilder()));
	}
	
	public static <T extends Block> T registerBlock(T block, String registry)
    {
    	block.setRegistryName(new ResourceLocation(Reference.MOD_ID, registry));
        return registerBlock(block, new ItemBlockStatue(block, itemBuilder()));
    }
	
	public static <T extends Block> T registerPlayer(T block, String registry)
    {
    	block.setRegistryName(new ResourceLocation(Reference.MOD_ID, registry));
        return registerBlock(block, new ItemBlockStatue(block, itemBuilder().setTEISR(() -> PlayerInventoryRender::new)));
    }
	
	private static Item.Properties itemBuilder()
	{
		return new Item.Properties();
	}
    
    public static <T extends Block> T registerBlock(T block, ItemBlock item)
    {
        item.setRegistryName(((ItemBlock) item).getBlock().getRegistryName());
    	StatuesItems.ITEMS.add(item);
        BLOCKS.add(block);
        return block;
    }
}