package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.decorative.BumboStatueBlock;
import com.shynieke.statues.blocks.decorative.DisplayStandBlock;
import com.shynieke.statues.blocks.decorative.EndermiteStatueBlock;
import com.shynieke.statues.blocks.decorative.PebbleBlock;
import com.shynieke.statues.blocks.decorative.SombreroBlock;
import com.shynieke.statues.blocks.decorative.TotemOfUndyingStatueBlock;
import com.shynieke.statues.blocks.statues.BabyZombieStatueBlock;
import com.shynieke.statues.blocks.statues.BlazeStatueBlock;
import com.shynieke.statues.blocks.statues.CampfireStatueBlock;
import com.shynieke.statues.blocks.statues.ChickenJockeyStatueBlock;
import com.shynieke.statues.blocks.statues.ChickenStatueBlock;
import com.shynieke.statues.blocks.statues.CowStatueBlock;
import com.shynieke.statues.blocks.statues.CreeperStatueBlock;
import com.shynieke.statues.blocks.statues.EndermanStatueBlock;
import com.shynieke.statues.blocks.statues.EvokerStatueBlock;
import com.shynieke.statues.blocks.statues.FloodStatueBlock;
import com.shynieke.statues.blocks.statues.GhastStatueBlock;
import com.shynieke.statues.blocks.statues.GuardianStatueBlock;
import com.shynieke.statues.blocks.statues.HuskStatueBlock;
import com.shynieke.statues.blocks.statues.InfoStatueBlock;
import com.shynieke.statues.blocks.statues.KingCluckStatueBlock;
import com.shynieke.statues.blocks.statues.MagmaStatueBlock;
import com.shynieke.statues.blocks.statues.MooshroomStatueBlock;
import com.shynieke.statues.blocks.statues.PigStatueBlock;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.blocks.statues.RabbitStatueBlock;
import com.shynieke.statues.blocks.statues.SheepShavenStatueBlock;
import com.shynieke.statues.blocks.statues.SheepStatueBlock;
import com.shynieke.statues.blocks.statues.ShulkerStatueBlock;
import com.shynieke.statues.blocks.statues.SlimeStatueBlock;
import com.shynieke.statues.blocks.statues.SnowGolemStatueBlock;
import com.shynieke.statues.blocks.statues.SpiderStatueBlock;
import com.shynieke.statues.blocks.statues.VillagerStatue;
import com.shynieke.statues.blocks.statues.WastelandStatueBlock;
import com.shynieke.statues.blocks.statues.WitchStatueBlock;
import com.shynieke.statues.blocks.statues.ZombieStatueBlock;
import com.shynieke.statues.blocks.statues.fish.CodStatueBlock;
import com.shynieke.statues.blocks.statues.fish.DolphinStatueBlock;
import com.shynieke.statues.blocks.statues.fish.DrownedStatueBlock;
import com.shynieke.statues.blocks.statues.fish.FishStatueBlock;
import com.shynieke.statues.blocks.statues.fish.PufferfishStatueBlock;
import com.shynieke.statues.blocks.statues.fish.SalmonStatueBlock;
import com.shynieke.statues.blocks.statues.fish.SquidStatueBlock;
import com.shynieke.statues.blocks.statues.fish.TurtleStatueBlock;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.tiles.render.PlayerTileInventoryRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class StatueBlocks {
	public static Block baby_zombie_statue;
	public static Block blaze_statue;
	public static Block campfire_statue;
	public static Block chicken_jockey_statue;
	public static Block chicken_statue;
	public static Block cow_statue;
	public static Block creeper_statue;
	public static Block enderman_statue;
	public static Block evoker_statue;
	public static Block flood_statue;
	public static Block ghast_statue;
	public static Block guardian_statue;
	public static Block husk_statue;
	public static Block king_cluck_statue;
	public static Block magma_statue;
	public static Block mooshroom_statue;
	public static Block pig_statue;
	public static Block rabbit_br_statue;
	public static Block rabbit_bs_statue;
	public static Block rabbit_bw_statue;
	public static Block rabbit_go_statue;
	public static Block rabbit_wh_statue;
	public static Block rabbit_ws_statue;
	public static Block shulker_statue;
	public static Block slime_statue;
	public static Block snow_golem_statue;
	public static Block spider_statue;
	public static Block villager_br_statue;
	public static Block villager_gr_statue;
	public static Block villager_pu_statue;
	public static Block villager_wh_statue;
	public static Block wasteland_statue;
	public static Block witch_statue;
	public static Block zombie_statue;

	//Fish statues
	public static Block cod_statue;
	public static Block dolphin_statue;
	public static Block drowned_statue;
	public static Block pufferfish_medium_statue;
	public static Block pufferfish_small_statue;
	public static Block pufferfish_statue;
	public static Block salmon_statue;
	public static Block squid_statue;
	public static Block tropical_fish_b;
	public static Block tropical_fish_bb;
	public static Block tropical_fish_be;
	public static Block tropical_fish_bm;
	public static Block tropical_fish_bmb;
	public static Block tropical_fish_bms;
	public static Block tropical_fish_e;
	public static Block tropical_fish_es;
	public static Block tropical_fish_hb;
	public static Block tropical_fish_sb;
	public static Block tropical_fish_sd;
	public static Block tropical_fish_ss;
	public static Block turtle_statue;

	//Sheep Statues
	public static Block sheep_shaven_statue;
	public static Block sheep_statue_white;
	public static Block sheep_statue_orange;
	public static Block sheep_statue_magenta;
	public static Block sheep_statue_light_blue;
	public static Block sheep_statue_yellow;
	public static Block sheep_statue_lime;
	public static Block sheep_statue_pink;
	public static Block sheep_statue_gray;
	public static Block sheep_statue_light_gray;
	public static Block sheep_statue_cyan;
	public static Block sheep_statue_purple;
	public static Block sheep_statue_blue;
	public static Block sheep_statue_brown;
	public static Block sheep_statue_green;
	public static Block sheep_statue_red;
	public static Block sheep_statue_black;

	//Other Statues
	public static Block info_statue;
	public static Block player_statue;
	public static Block endermite_statue;
	public static Block totem_of_undying_statue;
	public static Block pebble;
	public static Block display_stand;
	public static Block sombrero;
	public static Block bumbo_statue;

	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	public static ArrayList<Block> STATUES = new ArrayList<>();

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();

		baby_zombie_statue = registerStatue(new BabyZombieStatueBlock(blockBuilder()), "baby_zombie_statue");
		blaze_statue = registerStatue(new BlazeStatueBlock(blockBuilder()), "blaze_statue");
		bumbo_statue = registerBlock(new BumboStatueBlock(blockBuilder()), "bumbo_statue");
		campfire_statue = registerStatue(new CampfireStatueBlock(blockBuilder()), "campfire_statue");
		chicken_jockey_statue = registerStatue(new ChickenJockeyStatueBlock(blockBuilder()), "chicken_jockey_statue");
		chicken_statue = registerStatue(new ChickenStatueBlock(blockBuilder()), "chicken_statue");
		cod_statue = registerStatue(new CodStatueBlock(blockBuilder()), "cod_statue");
		cow_statue = registerStatue(new CowStatueBlock(blockBuilder()), "cow_statue");
		creeper_statue = registerStatue(new CreeperStatueBlock(blockBuilder()), "creeper_statue");
		display_stand = registerBlock(new DisplayStandBlock(blockBuilder()), "display_stand");
		dolphin_statue = registerStatue(new DolphinStatueBlock(blockBuilder()), "dolphin_statue");
		drowned_statue = registerStatue(new DrownedStatueBlock(blockBuilder()), "drowned_statue");
		enderman_statue = registerStatue(new EndermanStatueBlock(blockBuilder()), "enderman_statue");
		endermite_statue = registerStatue(new EndermiteStatueBlock(blockBuilder()), "endermite_statue");
		evoker_statue = registerStatue(new EvokerStatueBlock(blockBuilder()), "evoker_statue");
		flood_statue = registerStatue(new FloodStatueBlock(blockBuilder()), "flood_statue");
		ghast_statue = registerStatue(new GhastStatueBlock(blockBuilder()), "ghast_statue");
		guardian_statue = registerStatue(new GuardianStatueBlock(blockBuilder()), "guardian_statue");
		husk_statue = registerStatue(new HuskStatueBlock(blockBuilder()), "husk_statue");
		info_statue = registerStatue(new InfoStatueBlock(blockBuilder()), "info_statue");
		king_cluck_statue = registerStatue(new KingCluckStatueBlock(blockBuilder()), "king_cluck_statue");
		magma_statue = registerStatue(new MagmaStatueBlock(blockBuilder()), "magma_statue");
		mooshroom_statue = registerStatue(new MooshroomStatueBlock(blockBuilder()), "mooshroom_statue");
		pebble = registerBlock(new PebbleBlock(blockBuilder()), "pebble");
		pig_statue = registerStatue(new PigStatueBlock(blockBuilder()), "pig_statue");
		player_statue = registerPlayerStatue(new PlayerStatueBlock(blockBuilder()), "player_statue");
		pufferfish_medium_statue = registerStatue(new PufferfishStatueBlock(blockBuilder(),1), "pufferfish_medium_statue");
		pufferfish_small_statue = registerStatue(new PufferfishStatueBlock(blockBuilder(),0), "pufferfish_small_statue");
		pufferfish_statue = registerStatue(new PufferfishStatueBlock(blockBuilder(),2), "pufferfish_statue");
		rabbit_br_statue = registerStatue(new RabbitStatueBlock(blockBuilder()), "rabbit_br_statue");
		rabbit_bs_statue = registerStatue(new RabbitStatueBlock(blockBuilder()), "rabbit_bs_statue");
		rabbit_bw_statue = registerStatue(new RabbitStatueBlock(blockBuilder()), "rabbit_bw_statue");
		rabbit_go_statue = registerStatue(new RabbitStatueBlock(blockBuilder()), "rabbit_go_statue");
		rabbit_wh_statue = registerStatue(new RabbitStatueBlock(blockBuilder()), "rabbit_wh_statue");
		rabbit_ws_statue = registerStatue(new RabbitStatueBlock(blockBuilder()), "rabbit_ws_statue");
		salmon_statue = registerStatue(new SalmonStatueBlock(blockBuilder()), "salmon_statue");
		sheep_shaven_statue = registerStatue(new SheepShavenStatueBlock(blockBuilder()), "sheep_shaven_statue");
		sheep_statue_black = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.BLACK), "sheep_statue_black");
		sheep_statue_blue = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.BLUE), "sheep_statue_blue");
		sheep_statue_brown = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.BROWN), "sheep_statue_brown");
		sheep_statue_cyan = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.CYAN), "sheep_statue_cyan");
		sheep_statue_gray = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.GRAY), "sheep_statue_gray");
		sheep_statue_green = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.GREEN), "sheep_statue_green");
		sheep_statue_light_blue = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.LIGHT_BLUE), "sheep_statue_light_blue");
		sheep_statue_light_gray = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.LIGHT_GRAY), "sheep_statue_light_gray");
		sheep_statue_lime = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.LIME), "sheep_statue_lime");
		sheep_statue_magenta = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.MAGENTA), "sheep_statue_magenta");
		sheep_statue_orange = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.ORANGE), "sheep_statue_orange");
		sheep_statue_pink = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.PINK), "sheep_statue_pink");
		sheep_statue_purple = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.PURPLE), "sheep_statue_purple");
		sheep_statue_red = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.RED), "sheep_statue_red");
		sheep_statue_white = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.WHITE), "sheep_statue_white");
		sheep_statue_yellow = registerStatue(new SheepStatueBlock(blockBuilder(), DyeColor.YELLOW), "sheep_statue_yellow");
		shulker_statue = registerStatue(new ShulkerStatueBlock(blockBuilder()), "shulker_statue");
		slime_statue = registerStatue(new SlimeStatueBlock(blockBuilder()), "slime_statue");
		snow_golem_statue = registerStatue(new SnowGolemStatueBlock(blockBuilder()), "snow_golem_statue");
		sombrero = registerBlock(new SombreroBlock(blockBuilder()), "sombrero");
		spider_statue = registerStatue(new SpiderStatueBlock(blockBuilder()), "spider_statue");
		squid_statue = registerStatue(new SquidStatueBlock(blockBuilder()), "squid_statue");
		totem_of_undying_statue = registerStatue(new TotemOfUndyingStatueBlock(blockBuilder()), "totem_of_undying_statue");
		tropical_fish_b = registerStatue(new FishStatueBlock(blockBuilder(), 1), "tropical_fish_b");
		tropical_fish_bb = registerStatue(new FishStatueBlock(blockBuilder(), 0), "tropical_fish_bb");
		tropical_fish_be = registerStatue(new FishStatueBlock(blockBuilder(), 0), "tropical_fish_be");
		tropical_fish_bm = registerStatue(new FishStatueBlock(blockBuilder(), 1), "tropical_fish_bm");
		tropical_fish_bmb = registerStatue(new FishStatueBlock(blockBuilder(), 1), "tropical_fish_bmb");
		tropical_fish_bms = registerStatue(new FishStatueBlock(blockBuilder(), 0), "tropical_fish_bms");
		tropical_fish_e = registerStatue(new FishStatueBlock(blockBuilder(), 1), "tropical_fish_e");
		tropical_fish_es = registerStatue(new FishStatueBlock(blockBuilder(), 0), "tropical_fish_es");
		tropical_fish_hb = registerStatue(new FishStatueBlock(blockBuilder(), 1), "tropical_fish_hb");
		tropical_fish_sb = registerStatue(new FishStatueBlock(blockBuilder(), 1), "tropical_fish_sb");
		tropical_fish_sd = registerStatue(new FishStatueBlock(blockBuilder(), 0), "tropical_fish_sd");
		tropical_fish_ss = registerStatue(new FishStatueBlock(blockBuilder(), 0), "tropical_fish_ss");
		turtle_statue = registerStatue(new TurtleStatueBlock(blockBuilder()), "turtle_statue");
		villager_br_statue = registerStatue(new VillagerStatue(blockBuilder()), "villager_br_statue");
		villager_gr_statue = registerStatue(new VillagerStatue(blockBuilder()), "villager_gr_statue");
		villager_pu_statue = registerStatue(new VillagerStatue(blockBuilder()), "villager_pu_statue");
		villager_wh_statue = registerStatue(new VillagerStatue(blockBuilder()), "villager_wh_statue");
		wasteland_statue = registerStatue(new WastelandStatueBlock(blockBuilder()), "wasteland_statue");
		witch_statue = registerStatue(new WitchStatueBlock(blockBuilder()), "witch_statue");
		zombie_statue = registerStatue(new ZombieStatueBlock(blockBuilder()), "zombie_statue");

		registry.registerAll(BLOCKS.toArray(new Block[0]));
	}

	public static <T extends Block> Block registerStatue(T block, String registry)
	{
		block.setRegistryName(new ResourceLocation(Reference.MOD_ID, registry));
		STATUES.add(block);
		return registerBlock( block, new StatueBlockItem(block, itemBuilder()));
	}

	public static <T extends Block> Block registerBlock(T block, String registry)
	{
		block.setRegistryName(new ResourceLocation(Reference.MOD_ID, registry));
		return registerBlock( block, new StatueBlockItem(block, itemBuilder()));
	}

	public static <T extends Block> Block registerPlayerStatue(T block, String registry)
	{
		block.setRegistryName(new ResourceLocation(Reference.MOD_ID, registry));
		return registerBlock( block, new StatueBlockItem(block, itemBuilder().setTEISR(() -> PlayerTileInventoryRenderer::new)));
	}

	private static Item.Properties itemBuilder()
	{
		return new Item.Properties().group(StatueTabs.STATUES_BLOCKS);
	}
	
	private static Block.Properties blockBuilder() { return Block.Properties.create(Material.SHULKER); }


	public static <T extends Block> T registerBlock(T block, BlockItem item)
	{
		item.setRegistryName((item).getBlock().getRegistryName());
		StatueItems.ITEMS.add(item);
		BLOCKS.add(block);
		return block;
	}
}
