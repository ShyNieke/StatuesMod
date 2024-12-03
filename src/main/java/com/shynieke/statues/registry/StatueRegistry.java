package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.CoreFlowerCropBlock;
import com.shynieke.statues.blocks.decorative.AzzaroStatueBlock;
import com.shynieke.statues.blocks.decorative.BumboStatueBlock;
import com.shynieke.statues.blocks.decorative.DisplayStandBlock;
import com.shynieke.statues.blocks.decorative.PebbleBlock;
import com.shynieke.statues.blocks.decorative.SombreroBlock;
import com.shynieke.statues.blocks.decorative.TotemOfUndyingStatueBlock;
import com.shynieke.statues.blocks.decorative.charity.EagleRayStatueBlock;
import com.shynieke.statues.blocks.decorative.charity.SlabFishStatueBlock;
import com.shynieke.statues.blocks.decorative.charity.TropiBeeStatueBlock;
import com.shynieke.statues.blocks.statues.AllayStatueBlock;
import com.shynieke.statues.blocks.statues.AngryBeeStatueBlock;
import com.shynieke.statues.blocks.statues.BabyZombieStatueBlock;
import com.shynieke.statues.blocks.statues.BeeStatueBlock;
import com.shynieke.statues.blocks.statues.BlazeStatueBlock;
import com.shynieke.statues.blocks.statues.BrownMooshroomStatueBlock;
import com.shynieke.statues.blocks.statues.CampfireStatueBlock;
import com.shynieke.statues.blocks.statues.CatStatueBlock;
import com.shynieke.statues.blocks.statues.ChickenJockeyStatueBlock;
import com.shynieke.statues.blocks.statues.ChickenStatueBlock;
import com.shynieke.statues.blocks.statues.CowStatueBlock;
import com.shynieke.statues.blocks.statues.CreeperStatueBlock;
import com.shynieke.statues.blocks.statues.DetectivePlatypusStatueBlock;
import com.shynieke.statues.blocks.statues.EndermanStatueBlock;
import com.shynieke.statues.blocks.statues.EndermiteStatueBlock;
import com.shynieke.statues.blocks.statues.EvokerStatueBlock;
import com.shynieke.statues.blocks.statues.FloodStatueBlock;
import com.shynieke.statues.blocks.statues.FoxStatueBlock;
import com.shynieke.statues.blocks.statues.FrogStatueBlock;
import com.shynieke.statues.blocks.statues.GhastStatueBlock;
import com.shynieke.statues.blocks.statues.HuskStatueBlock;
import com.shynieke.statues.blocks.statues.InfoStatueBlock;
import com.shynieke.statues.blocks.statues.KingCluckStatueBlock;
import com.shynieke.statues.blocks.statues.MagmaStatueBlock;
import com.shynieke.statues.blocks.statues.MooshroomStatueBlock;
import com.shynieke.statues.blocks.statues.PandaStatueBlock;
import com.shynieke.statues.blocks.statues.PigStatueBlock;
import com.shynieke.statues.blocks.statues.PillagerStatueBlock;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.blocks.statues.RabbitStatueBlock;
import com.shynieke.statues.blocks.statues.RavagerStatueBlock;
import com.shynieke.statues.blocks.statues.SheepShavenStatueBlock;
import com.shynieke.statues.blocks.statues.SheepStatueBlock;
import com.shynieke.statues.blocks.statues.ShulkerStatueBlock;
import com.shynieke.statues.blocks.statues.SlimeStatueBlock;
import com.shynieke.statues.blocks.statues.SnowGolemStatueBlock;
import com.shynieke.statues.blocks.statues.SpiderStatueBlock;
import com.shynieke.statues.blocks.statues.TadpoleStatueBlock;
import com.shynieke.statues.blocks.statues.VillagerStatue;
import com.shynieke.statues.blocks.statues.VindicatorStatueBlock;
import com.shynieke.statues.blocks.statues.WardenStatueBlock;
import com.shynieke.statues.blocks.statues.WastelandStatueBlock;
import com.shynieke.statues.blocks.statues.WitchStatueBlock;
import com.shynieke.statues.blocks.statues.ZombieStatueBlock;
import com.shynieke.statues.blocks.statues.fish.AxolotlStatueBlock;
import com.shynieke.statues.blocks.statues.fish.CodStatueBlock;
import com.shynieke.statues.blocks.statues.fish.DolphinStatueBlock;
import com.shynieke.statues.blocks.statues.fish.DrownedStatueBlock;
import com.shynieke.statues.blocks.statues.fish.ElderGuardianStatueBlock;
import com.shynieke.statues.blocks.statues.fish.FishStatueBlock;
import com.shynieke.statues.blocks.statues.fish.GuardianStatueBlock;
import com.shynieke.statues.blocks.statues.fish.PufferfishStatueBlock;
import com.shynieke.statues.blocks.statues.fish.SalmonStatueBlock;
import com.shynieke.statues.blocks.statues.fish.SquidStatueBlock;
import com.shynieke.statues.blocks.statues.fish.TurtleStatueBlock;
import com.shynieke.statues.blocks.table.StatueTableBlock;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.entity.StatueBatEntity;
import com.shynieke.statues.items.PlayerCompassItem;
import com.shynieke.statues.items.PlayerStatueBlockItem;
import com.shynieke.statues.items.PlayerStatueSpawnItem;
import com.shynieke.statues.items.StatueBeeItem;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.items.StatueCharredMarshmallow;
import com.shynieke.statues.items.StatueCoreItem;
import com.shynieke.statues.items.StatueGoldenMarshmallow;
import com.shynieke.statues.items.StatueMooshroomSoup;
import com.shynieke.statues.items.StatueTeaItem;
import com.shynieke.statues.menu.ShulkerStatueMenu;
import com.shynieke.statues.menu.StatueTableMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class StatueRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Reference.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Reference.MOD_ID);
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Reference.MOD_ID);

	public static final Supplier<MenuType<StatueTableMenu>> STATUE_TABLE_MENU = MENU_TYPES.register("statue_table", () ->
			IMenuTypeExtension.create(StatueTableMenu::new));
	public static final Supplier<MenuType<ShulkerStatueMenu>> SHULKER_STATUE_MENU = MENU_TYPES.register("shulker_statue", () ->
			IMenuTypeExtension.create(ShulkerStatueMenu::new));

	public static final Supplier<EntityType<PlayerStatue>> PLAYER_STATUE_ENTITY = ENTITIES.register("player_statue",
			() -> EntityType.Builder.<PlayerStatue>of(PlayerStatue::new, MobCategory.MISC)
					.sized(0.6F, 1.8F)
					.eyeHeight(1.62F)
					.vehicleAttachment(Player.DEFAULT_VEHICLE_ATTACHMENT)
					.clientTrackingRange(32)
					.updateInterval(2)
					.build(statuesEntityId("player_statue"))
	);
	public static final Supplier<EntityType<StatueBatEntity>> STATUE_BAT = ENTITIES.register("statue_bat",
			() -> EntityType.Builder.<StatueBatEntity>of(StatueBatEntity::new, MobCategory.AMBIENT)
					.sized(0.5F, 0.9F)
					.eyeHeight(0.45F)
					.clientTrackingRange(5)
					.build(statuesEntityId("statue_bat"))
	);

	private static ResourceKey<EntityType<?>> statuesEntityId(String p_368595_) {
		return ResourceKey.create(Registries.ENTITY_TYPE, Reference.modLoc(p_368595_));
	}

	public static final DeferredBlock<StatueTableBlock> STATUE_TABLE = registerStatue("statue_table", () -> new StatueTableBlock(blockBuilder("statue_table")));
	public static final DeferredBlock<AngryBeeStatueBlock> ANGRY_BEE_STATUE = registerStatue("angry_bee_statue", () -> new AngryBeeStatueBlock(blockBuilder("angry_bee_statue")));
	public static final DeferredBlock<BabyZombieStatueBlock> BABY_ZOMBIE_STATUE = registerStatue("baby_zombie_statue", () -> new BabyZombieStatueBlock(blockBuilder("baby_zombie_statue")));
	public static final DeferredBlock<BeeStatueBlock> BEE_STATUE = registerBeeStatue("bee_statue", () -> new BeeStatueBlock(blockBuilder("bee_statue")));
	public static final DeferredBlock<BeeStatueBlock> TRANS_BEE_STATUE = registerStatue("trans_bee_statue", () -> new BeeStatueBlock(blockBuilder("trans_bee_statue")));
	public static final DeferredBlock<BlazeStatueBlock> BLAZE_STATUE = registerStatue("blaze_statue", () -> new BlazeStatueBlock(blockBuilder("blaze_statue")));
	public static final DeferredBlock<BrownMooshroomStatueBlock> BROWN_MOOSHROOM_STATUE = registerStatue("brown_mooshroom_statue", () -> new BrownMooshroomStatueBlock(blockBuilder("brown_mooshroom_statue")));
	public static final DeferredBlock<CampfireStatueBlock> CAMPFIRE_STATUE = registerStatue("campfire_statue", () -> new CampfireStatueBlock(blockBuilder("campfire_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_BLACK_STATUE = registerStatue("cat_black_statue", () -> new CatStatueBlock(blockBuilder("cat_black_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_BRITISH_SHORTHAIR_STATUE = registerStatue("cat_british_shorthair_statue", () -> new CatStatueBlock(blockBuilder("cat_british_shorthair_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_CALICO_STATUE = registerStatue("cat_calico_statue", () -> new CatStatueBlock(blockBuilder("cat_calico_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_JELLIE_STATUE = registerStatue("cat_jellie_statue", () -> new CatStatueBlock(blockBuilder("cat_jellie_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_PERSIAN_STATUE = registerStatue("cat_persian_statue", () -> new CatStatueBlock(blockBuilder("cat_persian_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_RAGDOLL_STATUE = registerStatue("cat_ragdoll_statue", () -> new CatStatueBlock(blockBuilder("cat_ragdoll_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_RED_STATUE = registerStatue("cat_red_statue", () -> new CatStatueBlock(blockBuilder("cat_red_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_SIAMESE_STATUE = registerStatue("cat_siamese_statue", () -> new CatStatueBlock(blockBuilder("cat_siamese_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_TABBY_STATUE = registerStatue("cat_tabby_statue", () -> new CatStatueBlock(blockBuilder("cat_tabby_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_TUXEDO_STATUE = registerStatue("cat_tuxedo_statue", () -> new CatStatueBlock(blockBuilder("cat_tuxedo_statue")));
	public static final DeferredBlock<CatStatueBlock> CAT_WHITE_STATUE = registerStatue("cat_white_statue", () -> new CatStatueBlock(blockBuilder("cat_white_statue")));
	public static final DeferredBlock<ChickenJockeyStatueBlock> CHICKEN_JOCKEY_STATUE = registerStatue("chicken_jockey_statue", () -> new ChickenJockeyStatueBlock(blockBuilder("chicken_jockey_statue")));
	public static final DeferredBlock<ChickenStatueBlock> CHICKEN_STATUE = registerStatue("chicken_statue", () -> new ChickenStatueBlock(blockBuilder("chicken_statue")));
	public static final DeferredBlock<CodStatueBlock> COD_STATUE = registerStatue("cod_statue", () -> new CodStatueBlock(blockBuilder("cod_statue")));
	public static final DeferredBlock<CowStatueBlock> COW_STATUE = registerStatue("cow_statue", () -> new CowStatueBlock(blockBuilder("cow_statue")));
	public static final DeferredBlock<CreeperStatueBlock> CREEPER_STATUE = registerStatue("creeper_statue", () -> new CreeperStatueBlock(blockBuilder("creeper_statue")));
	public static final DeferredBlock<DetectivePlatypusStatueBlock> DETECTIVE_PLATYPUS = registerStatue("detective_platypus_statue", () -> new DetectivePlatypusStatueBlock(blockBuilder("detective_platypus_statue")));
	public static final DeferredBlock<DolphinStatueBlock> DOLPHIN_STATUE = registerStatue("dolphin_statue", () -> new DolphinStatueBlock(blockBuilder("dolphin_statue")));
	public static final DeferredBlock<DrownedStatueBlock> DROWNED_STATUE = registerStatue("drowned_statue", () -> new DrownedStatueBlock(blockBuilder("drowned_statue")));
	public static final DeferredBlock<ElderGuardianStatueBlock> ELDER_GUARDIAN_STATUE = registerStatue("elder_guardian_statue", () -> new ElderGuardianStatueBlock(blockBuilder("elder_guardian_statue")));
	public static final DeferredBlock<EndermanStatueBlock> ENDERMAN_STATUE = registerStatue("enderman_statue", () -> new EndermanStatueBlock(blockBuilder("enderman_statue")));
	public static final DeferredBlock<EndermiteStatueBlock> ENDERMITE_STATUE = registerStatue("endermite_statue", () -> new EndermiteStatueBlock(blockBuilder("endermite_statue")));
	public static final DeferredBlock<EvokerStatueBlock> EVOKER_STATUE = registerStatue("evoker_statue", () -> new EvokerStatueBlock(blockBuilder("evoker_statue")));
	public static final DeferredBlock<FloodStatueBlock> FLOOD_STATUE = registerStatue("flood_statue", () -> new FloodStatueBlock(blockBuilder("flood_statue")));
	public static final DeferredBlock<FoxStatueBlock> FOX_SNOW_STATUE = registerStatue("fox_snow_statue", () -> new FoxStatueBlock(blockBuilder("fox_snow_statue")));
	public static final DeferredBlock<FoxStatueBlock> FOX_STATUE = registerStatue("fox_statue", () -> new FoxStatueBlock(blockBuilder("fox_statue")));
	public static final DeferredBlock<GhastStatueBlock> GHAST_STATUE = registerStatue("ghast_statue", () -> new GhastStatueBlock(blockBuilder("ghast_statue")));
	public static final DeferredBlock<GuardianStatueBlock> GUARDIAN_STATUE = registerStatue("guardian_statue", () -> new GuardianStatueBlock(blockBuilder("guardian_statue")));
	public static final DeferredBlock<HuskStatueBlock> HUSK_STATUE = registerStatue("husk_statue", () -> new HuskStatueBlock(blockBuilder("husk_statue")));
	public static final DeferredBlock<InfoStatueBlock> INFO_STATUE = registerStatue("info_statue", () -> new InfoStatueBlock(blockBuilder("info_statue")));
	public static final DeferredBlock<KingCluckStatueBlock> KING_CLUCK_STATUE = registerStatue("king_cluck_statue", () -> new KingCluckStatueBlock(blockBuilder("king_cluck_statue")));
	public static final DeferredBlock<MagmaStatueBlock> MAGMA_STATUE = registerStatue("magma_statue", () -> new MagmaStatueBlock(blockBuilder("magma_statue")));
	public static final DeferredBlock<MooshroomStatueBlock> MOOSHROOM_STATUE = registerStatue("mooshroom_statue", () -> new MooshroomStatueBlock(blockBuilder("mooshroom_statue")));
	public static final DeferredBlock<PandaStatueBlock> PANDA_ANGRY_STATUE = registerStatue("panda_angry_statue", () -> new PandaStatueBlock(blockBuilder("panda_angry_statue")));
	public static final DeferredBlock<PandaStatueBlock> PANDA_BROWN_STATUE = registerStatue("panda_brown_statue", () -> new PandaStatueBlock(blockBuilder("panda_brown_statue")));
	public static final DeferredBlock<PandaStatueBlock> PANDA_LAZY_STATUE = registerStatue("panda_lazy_statue", () -> new PandaStatueBlock(blockBuilder("panda_lazy_statue")));
	public static final DeferredBlock<PandaStatueBlock> PANDA_NORMAL_STATUE = registerStatue("panda_normal_statue", () -> new PandaStatueBlock(blockBuilder("panda_normal_statue")));
	public static final DeferredBlock<PandaStatueBlock> PANDA_PLAYFUL_STATUE = registerStatue("panda_playful_statue", () -> new PandaStatueBlock(blockBuilder("panda_playful_statue")));
	public static final DeferredBlock<PandaStatueBlock> PANDA_WEAK_STATUE = registerStatue("panda_weak_statue", () -> new PandaStatueBlock(blockBuilder("panda_weak_statue")));
	public static final DeferredBlock<PandaStatueBlock> PANDA_WORRIED_STATUE = registerStatue("panda_worried_statue", () -> new PandaStatueBlock(blockBuilder("panda_worried_statue")));
	public static final DeferredBlock<PigStatueBlock> PIG_STATUE = registerStatue("pig_statue", () -> new PigStatueBlock(blockBuilder("pig_statue")));
	public static final DeferredBlock<PillagerStatueBlock> PILLAGER_STATUE = registerStatue("pillager_statue", () -> new PillagerStatueBlock(blockBuilder("pillager_statue")));
	public static final DeferredBlock<PlayerStatueBlock> PLAYER_STATUE = registerPlayerStatue("player_statue", () -> new PlayerStatueBlock(blockBuilder("player_statue")));
	public static final DeferredBlock<PufferfishStatueBlock> PUFFERFISH_MEDIUM_STATUE = registerStatue("pufferfish_medium_statue", () -> new PufferfishStatueBlock(blockBuilder("pufferfish_medium_statue"), 1));
	public static final DeferredBlock<PufferfishStatueBlock> PUFFERFISH_SMALL_STATUE = registerStatue("pufferfish_small_statue", () -> new PufferfishStatueBlock(blockBuilder("pufferfish_small_statue"), 0));
	public static final DeferredBlock<PufferfishStatueBlock> PUFFERFISH_STATUE = registerStatue("pufferfish_statue", () -> new PufferfishStatueBlock(blockBuilder("pufferfish_statue"), 2));
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_BR_STATUE = registerStatue("rabbit_br_statue", () -> new RabbitStatueBlock(blockBuilder("rabbit_br_statue")));
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_BS_STATUE = registerStatue("rabbit_bs_statue", () -> new RabbitStatueBlock(blockBuilder("rabbit_bs_statue")));
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_BW_STATUE = registerStatue("rabbit_bw_statue", () -> new RabbitStatueBlock(blockBuilder("rabbit_bw_statue")));
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_GO_STATUE = registerStatue("rabbit_go_statue", () -> new RabbitStatueBlock(blockBuilder("rabbit_go_statue")));
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_WH_STATUE = registerStatue("rabbit_wh_statue", () -> new RabbitStatueBlock(blockBuilder("rabbit_wh_statue")));
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_WS_STATUE = registerStatue("rabbit_ws_statue", () -> new RabbitStatueBlock(blockBuilder("rabbit_ws_statue")));
	public static final DeferredBlock<RavagerStatueBlock> RAVAGER_STATUE = registerStatue("ravager_statue", () -> new RavagerStatueBlock(blockBuilder("ravager_statue")));
	public static final DeferredBlock<SalmonStatueBlock> SALMON_STATUE = registerStatue("salmon_statue", () -> new SalmonStatueBlock(blockBuilder("salmon_statue")));
	public static final DeferredBlock<SheepShavenStatueBlock> SHEEP_SHAVEN_STATUE = registerStatue("sheep_shaven_statue", () -> new SheepShavenStatueBlock(blockBuilder("sheep_shaven_statue")));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_BLACK = registerStatue("sheep_statue_black", () -> new SheepStatueBlock(blockBuilder("sheep_statue_black"), DyeColor.BLACK));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_BLUE = registerStatue("sheep_statue_blue", () -> new SheepStatueBlock(blockBuilder("sheep_statue_blue"), DyeColor.BLUE));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_BROWN = registerStatue("sheep_statue_brown", () -> new SheepStatueBlock(blockBuilder("sheep_statue_brown"), DyeColor.BROWN));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_CYAN = registerStatue("sheep_statue_cyan", () -> new SheepStatueBlock(blockBuilder("sheep_statue_cyan"), DyeColor.CYAN));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_GRAY = registerStatue("sheep_statue_gray", () -> new SheepStatueBlock(blockBuilder("sheep_statue_gray"), DyeColor.GRAY));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_GREEN = registerStatue("sheep_statue_green", () -> new SheepStatueBlock(blockBuilder("sheep_statue_green"), DyeColor.GREEN));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_LIGHT_BLUE = registerStatue("sheep_statue_light_blue", () -> new SheepStatueBlock(blockBuilder("sheep_statue_light_blue"), DyeColor.LIGHT_BLUE));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_LIGHT_GRAY = registerStatue("sheep_statue_light_gray", () -> new SheepStatueBlock(blockBuilder("sheep_statue_light_gray"), DyeColor.LIGHT_GRAY));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_LIME = registerStatue("sheep_statue_lime", () -> new SheepStatueBlock(blockBuilder("sheep_statue_lime"), DyeColor.LIME));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_MAGENTA = registerStatue("sheep_statue_magenta", () -> new SheepStatueBlock(blockBuilder("sheep_statue_magenta"), DyeColor.MAGENTA));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_ORANGE = registerStatue("sheep_statue_orange", () -> new SheepStatueBlock(blockBuilder("sheep_statue_orange"), DyeColor.ORANGE));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_PINK = registerStatue("sheep_statue_pink", () -> new SheepStatueBlock(blockBuilder("sheep_statue_pink"), DyeColor.PINK));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_PURPLE = registerStatue("sheep_statue_purple", () -> new SheepStatueBlock(blockBuilder("sheep_statue_purple"), DyeColor.PURPLE));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_RED = registerStatue("sheep_statue_red", () -> new SheepStatueBlock(blockBuilder("sheep_statue_red"), DyeColor.RED));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_WHITE = registerStatue("sheep_statue_white", () -> new SheepStatueBlock(blockBuilder("sheep_statue_white"), DyeColor.WHITE));
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_YELLOW = registerStatue("sheep_statue_yellow", () -> new SheepStatueBlock(blockBuilder("sheep_statue_yellow"), DyeColor.YELLOW));
	public static final DeferredBlock<ShulkerStatueBlock> SHULKER_STATUE = registerStatue("shulker_statue", () -> new ShulkerStatueBlock(blockBuilder("shulker_statue")));
	public static final DeferredBlock<SlimeStatueBlock> SLIME_STATUE = registerStatue("slime_statue", () -> new SlimeStatueBlock(blockBuilder("slime_statue")));
	public static final DeferredBlock<SnowGolemStatueBlock> SNOW_GOLEM_STATUE = registerStatue("snow_golem_statue", () -> new SnowGolemStatueBlock(blockBuilder("snow_golem_statue")));
	public static final DeferredBlock<SpiderStatueBlock> SPIDER_STATUE = registerStatue("spider_statue", () -> new SpiderStatueBlock(blockBuilder("spider_statue")));
	public static final DeferredBlock<SquidStatueBlock> SQUID_STATUE = registerStatue("squid_statue", () -> new SquidStatueBlock(blockBuilder("squid_statue")));
	public static final DeferredBlock<TotemOfUndyingStatueBlock> TOTEM_OF_UNDYING_STATUE = registerStatue("totem_of_undying_statue", () -> new TotemOfUndyingStatueBlock(blockBuilder("totem_of_undying_statue")));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_B = registerStatue("tropical_fish_b", () -> new FishStatueBlock(blockBuilder("tropical_fish_b"), 1));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BB = registerStatue("tropical_fish_bb", () -> new FishStatueBlock(blockBuilder("tropical_fish_bb"), 0));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BE = registerStatue("tropical_fish_be", () -> new FishStatueBlock(blockBuilder("tropical_fish_be"), 0));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BM = registerStatue("tropical_fish_bm", () -> new FishStatueBlock(blockBuilder("tropical_fish_bm"), 1));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BMB = registerStatue("tropical_fish_bmb", () -> new FishStatueBlock(blockBuilder("tropical_fish_bmb"), 1));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BMS = registerStatue("tropical_fish_bms", () -> new FishStatueBlock(blockBuilder("tropical_fish_bms"), 0));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_E = registerStatue("tropical_fish_e", () -> new FishStatueBlock(blockBuilder("tropical_fish_e"), 1));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_ES = registerStatue("tropical_fish_es", () -> new FishStatueBlock(blockBuilder("tropical_fish_es"), 0));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_HB = registerStatue("tropical_fish_hb", () -> new FishStatueBlock(blockBuilder("tropical_fish_hb"), 1));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_SB = registerStatue("tropical_fish_sb", () -> new FishStatueBlock(blockBuilder("tropical_fish_sb"), 1));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_SD = registerStatue("tropical_fish_sd", () -> new FishStatueBlock(blockBuilder("tropical_fish_sd"), 0));
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_SS = registerStatue("tropical_fish_ss", () -> new FishStatueBlock(blockBuilder("tropical_fish_ss"), 0));
	public static final DeferredBlock<TurtleStatueBlock> TURTLE_STATUE = registerStatue("turtle_statue", () -> new TurtleStatueBlock(blockBuilder("turtle_statue")));
	public static final DeferredBlock<VillagerStatue> VILLAGER_BR_STATUE = registerStatue("villager_br_statue", () -> new VillagerStatue(blockBuilder("villager_br_statue")));
	public static final DeferredBlock<VillagerStatue> VILLAGER_GR_STATUE = registerStatue("villager_gr_statue", () -> new VillagerStatue(blockBuilder("villager_gr_statue")));
	public static final DeferredBlock<VillagerStatue> VILLAGER_PU_STATUE = registerStatue("villager_pu_statue", () -> new VillagerStatue(blockBuilder("villager_pu_statue")));
	public static final DeferredBlock<VillagerStatue> VILLAGER_WH_STATUE = registerStatue("villager_wh_statue", () -> new VillagerStatue(blockBuilder("villager_wh_statue")));
	public static final DeferredBlock<VindicatorStatueBlock> VINDICATOR_STATUE = registerStatue("vindicator_statue", () -> new VindicatorStatueBlock(blockBuilder("vindicator_statue")));
	public static final DeferredBlock<WastelandStatueBlock> WASTELAND_STATUE = registerStatue("wasteland_statue", () -> new WastelandStatueBlock(blockBuilder("wasteland_statue")));
	public static final DeferredBlock<WitchStatueBlock> WITCH_STATUE = registerStatue("witch_statue", () -> new WitchStatueBlock(blockBuilder("witch_statue")));
	public static final DeferredBlock<ZombieStatueBlock> ZOMBIE_STATUE = registerStatue("zombie_statue", () -> new ZombieStatueBlock(blockBuilder("zombie_statue")));
	public static final DeferredBlock<AllayStatueBlock> ALLAY_STATUE = registerStatue("allay_statue", () -> new AllayStatueBlock(blockBuilder("allay_statue")));
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_LUCY_STATUE = registerStatue("axolotl_lucy_statue", () -> new AxolotlStatueBlock(blockBuilder("axolotl_lucy_statue")));
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_WILD_STATUE = registerStatue("axolotl_wild_statue", () -> new AxolotlStatueBlock(blockBuilder("axolotl_wild_statue")));
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_GOLD_STATUE = registerStatue("axolotl_gold_statue", () -> new AxolotlStatueBlock(blockBuilder("axolotl_gold_statue")));
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_CYAN_STATUE = registerStatue("axolotl_cyan_statue", () -> new AxolotlStatueBlock(blockBuilder("axolotl_cyan_statue")));
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_BLUE_STATUE = registerStatue("axolotl_blue_statue", () -> new AxolotlStatueBlock(blockBuilder("axolotl_blue_statue")));
	public static final DeferredBlock<FrogStatueBlock> FROG_TEMPERATE_STATUE = registerStatue("frog_temperate_statue", () -> new FrogStatueBlock(blockBuilder("frog_temperate_statue")));
	public static final DeferredBlock<FrogStatueBlock> FROG_WARM_STATUE = registerStatue("frog_warm_statue", () -> new FrogStatueBlock(blockBuilder("frog_warm_statue")));
	public static final DeferredBlock<FrogStatueBlock> FROG_COLD_STATUE = registerStatue("frog_cold_statue", () -> new FrogStatueBlock(blockBuilder("frog_cold_statue")));
	public static final DeferredBlock<TadpoleStatueBlock> TADPOLE_STATUE = registerStatue("tadpole_statue", () -> new TadpoleStatueBlock(blockBuilder("tadpole_statue")));
	public static final DeferredBlock<WardenStatueBlock> WARDEN_STATUE = registerStatue("warden_statue", () -> new WardenStatueBlock(blockBuilder("warden_statue")));


	public static final DeferredBlock<DisplayStandBlock> DISPLAY_STAND = registerBlock("display_stand", () -> new DisplayStandBlock(blockBuilder("display_stand")));
	public static final DeferredBlock<BumboStatueBlock> BUMBO_STATUE = registerBlock("bumbo_statue", () -> new BumboStatueBlock(blockBuilder("bumbo_statue")));
	public static final DeferredBlock<PebbleBlock> PEBBLE = registerBlock("pebble", () -> new PebbleBlock(blockBuilder("pebble")));
	public static final DeferredBlock<SombreroBlock> SOMBRERO = registerBlock("sombrero", () -> new SombreroBlock(blockBuilder("sombrero")));
	public static final DeferredBlock<TropiBeeStatueBlock> TROPIBEE = registerBlock("tropibee", () -> new TropiBeeStatueBlock(blockBuilder("tropibee")));
	public static final DeferredBlock<EagleRayStatueBlock> EAGLE_RAY = registerBlock("eagle_ray", () -> new EagleRayStatueBlock(blockBuilder("eagle_ray")));
	public static final DeferredBlock<SlabFishStatueBlock> SLABFISH = registerBlock("slabfish", () -> new SlabFishStatueBlock(blockBuilder("slabfish")));
	public static final DeferredBlock<AzzaroStatueBlock> AZZARO = registerBlock("azzaro", () -> new AzzaroStatueBlock(blockBuilder("azzaro")));
	public static final DeferredBlock<FlowerBlock> CORE_FLOWER = BLOCKS.registerBlock("core_flower", (properties) -> new FlowerBlock(
			MobEffects.MOVEMENT_SLOWDOWN, 5, properties),
			BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY));
	public static final DeferredBlock<CoreFlowerCropBlock> CORE_FLOWER_CROP = BLOCKS.registerBlock("core_flower_crop", CoreFlowerCropBlock::new,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.PLANT)
					.noCollission()
					.randomTicks()
					.instabreak()
					.sound(SoundType.CROP)
					.pushReaction(PushReaction.DESTROY));

	public static final DeferredItem<Item> CUP = ITEMS.registerItem("cup", (properties) -> new Item(properties.food(StatueFoods.CUP)), itemBuilder());
	public static final DeferredItem<Item> MARSHMALLOW = ITEMS.registerItem("marshmallow", (properties) -> new Item(properties.food(StatueFoods.MARSHMALLOW)), itemBuilder());
	public static final DeferredItem<StatueCharredMarshmallow> MARSHMALLOW_CHARRED = ITEMS.registerItem("marshmallow_charred", StatueCharredMarshmallow::new, itemBuilder());
	public static final DeferredItem<Item> MARSHMALLOW_COOKED = ITEMS.registerItem("marshmallow_cooked", (properties) -> new Item(properties.food(StatueFoods.COOKED_MARSHMALLOW)), itemBuilder());
	public static final DeferredItem<StatueGoldenMarshmallow> MARSHMALLOW_GOLDEN = ITEMS.registerItem("marshmallow_golden", StatueGoldenMarshmallow::new, itemBuilder());
	public static final DeferredItem<Item> NUGGET = ITEMS.registerItem("royal_nugget", (properties) -> new Item(properties.food(StatueFoods.ROYAL_NUGGET)), itemBuilder());
	public static final DeferredItem<PlayerCompassItem> PLAYER_COMPASS = ITEMS.registerItem("player_compass", PlayerCompassItem::new, itemBuilder());
	public static final DeferredItem<StatueMooshroomSoup> SOUP = ITEMS.registerItem("mooshroom_soup", StatueMooshroomSoup::new, itemBuilder());
	public static final DeferredItem<StatueCoreItem> STATUE_CORE = ITEMS.registerItem("statue_core", (properties) -> new StatueCoreItem(properties.jukeboxPlayable(StatueJukeboxSongs.CREDITS)), itemBuilder());
	public static final DeferredItem<StatueTeaItem> TEA = ITEMS.registerItem("tea", (properties) -> new StatueTeaItem(properties, StatueFoods.TEA), itemBuilder());
	public static final DeferredItem<Item> CORE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.registerItem("core_armor_trim_smithing_template", (properties) -> SmithingTemplateItem.createArmorTrimTemplate(properties.rarity(Rarity.RARE)), itemBuilder());
	public static final DeferredItem<BlockItem> CORE_FLOWER_SEED = ITEMS.registerSimpleBlockItem("core_flower_seed", CORE_FLOWER_CROP, itemBuilder());
	public static final DeferredItem<Item> CORE_FLOWER_ITEM = ITEMS.registerItem("core_flower", (properties) -> new BlockItem(CORE_FLOWER.get(), properties), itemBuilder());
	public static final DeferredItem<Item> STATUE_CORE_POTTERY_SHERD = ITEMS.registerItem("statue_core_pottery_sherd", (properties) -> SmithingTemplateItem.createArmorTrimTemplate(properties.rarity(Rarity.RARE)), itemBuilder());

	public static final DeferredItem<PlayerStatueSpawnItem> PLAYER_STATUE_SPAWN_EGG = ITEMS.registerItem("player_statue_spawn_egg", PlayerStatueSpawnItem::new, itemBuilder());
	public static final DeferredItem<SpawnEggItem> STATUE_BAT_SPANW_EGG = ITEMS.registerItem("statue_bat_spawn_egg", (properties) -> new SpawnEggItem(StatueRegistry.STATUE_BAT.get(), properties), itemBuilder()); //3421236, 3556687

	public static <B extends Block> DeferredBlock<B> registerStatue(String name, Supplier<? extends B> supplier) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.registerItem(name, props -> new StatueBlockItem(block.get(), props));
		return block;
	}

	public static <B extends Block> DeferredBlock<B> registerBeeStatue(String name, Supplier<? extends B> supplier) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.registerItem(name, props -> new StatueBeeItem(block.get(), props));
		return block;
	}

	public static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> supplier) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.registerItem(name, props -> new StatueBlockItem(block.get(), props));
		return block;
	}

	public static <B extends Block> DeferredBlock<B> registerPlayerStatue(String name, Supplier<? extends B> supplier) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.registerItem(name, props -> new PlayerStatueBlockItem(block.get(), props));
		return block;
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties();
	}

	private static Block.Properties blockBuilder(String path) {
		return Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).setId(blockKey(path));
	}

	private static ResourceKey<Block> blockKey(String path) {
		return ResourceKey.create(Registries.BLOCK, Reference.modLoc(path));
	}


	public static final Supplier<CreativeModeTab> STATUES_BLOCKS = CREATIVE_MODE_TABS.register("blocks", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(StatueRegistry.SLIME_STATUE.get()))
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.title(Component.translatable("itemGroup.statues.blocks"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = StatueRegistry.BLOCKS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
	public static final Supplier<CreativeModeTab> STATUES_ITEMS = CREATIVE_MODE_TABS.register("items", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(StatueRegistry.STATUE_CORE.get()))
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.title(Component.translatable("itemGroup.statues.items"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = StatueRegistry.ITEMS.getEntries().stream()
						.filter(reg -> !(reg.get() instanceof BlockItem)).map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
