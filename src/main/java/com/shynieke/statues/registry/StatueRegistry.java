package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.CoreFlowerCropBlock;
import com.shynieke.statues.blocks.decorative.AzzaroStatueBlock;
import com.shynieke.statues.blocks.decorative.BumboStatueBlock;
import com.shynieke.statues.blocks.decorative.DisplayStandBlock;
import com.shynieke.statues.blocks.decorative.EndermiteStatueBlock;
import com.shynieke.statues.blocks.decorative.PebbleBlock;
import com.shynieke.statues.blocks.decorative.SombreroBlock;
import com.shynieke.statues.blocks.decorative.TadpoleStatueBlock;
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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
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
					.sized(0.6F, 1.8F).clientTrackingRange(10).build("player_statue"));
	public static final Supplier<EntityType<StatueBatEntity>> STATUE_BAT = ENTITIES.register("statue_bat",
			() -> EntityType.Builder.<StatueBatEntity>of(StatueBatEntity::new, MobCategory.AMBIENT)
					.sized(0.5F, 0.9F).clientTrackingRange(5).build("statue_bat"));

	public static final DeferredBlock<StatueTableBlock> STATUE_TABLE = registerStatue("statue_table", () -> new StatueTableBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AngryBeeStatueBlock> ANGRY_BEE_STATUE = registerStatue("angry_bee_statue", () -> new AngryBeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<BabyZombieStatueBlock> BABY_ZOMBIE_STATUE = registerStatue("baby_zombie_statue", () -> new BabyZombieStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<BeeStatueBlock> BEE_STATUE = registerBeeStatue("bee_statue", () -> new BeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<BeeStatueBlock> TRANS_BEE_STATUE = registerStatue("trans_bee_statue", () -> new BeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<BlazeStatueBlock> BLAZE_STATUE = registerStatue("blaze_statue", () -> new BlazeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<BrownMooshroomStatueBlock> BROWN_MOOSHROOM_STATUE = registerStatue("brown_mooshroom_statue", () -> new BrownMooshroomStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CampfireStatueBlock> CAMPFIRE_STATUE = registerStatue("campfire_statue", () -> new CampfireStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_BLACK_STATUE = registerStatue("cat_black_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_BRITISH_SHORTHAIR_STATUE = registerStatue("cat_british_shorthair_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_CALICO_STATUE = registerStatue("cat_calico_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_JELLIE_STATUE = registerStatue("cat_jellie_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_PERSIAN_STATUE = registerStatue("cat_persian_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_RAGDOLL_STATUE = registerStatue("cat_ragdoll_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_RED_STATUE = registerStatue("cat_red_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_SIAMESE_STATUE = registerStatue("cat_siamese_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_TABBY_STATUE = registerStatue("cat_tabby_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_TUXEDO_STATUE = registerStatue("cat_tuxedo_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CatStatueBlock> CAT_WHITE_STATUE = registerStatue("cat_white_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<ChickenJockeyStatueBlock> CHICKEN_JOCKEY_STATUE = registerStatue("chicken_jockey_statue", () -> new ChickenJockeyStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<ChickenStatueBlock> CHICKEN_STATUE = registerStatue("chicken_statue", () -> new ChickenStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CodStatueBlock> COD_STATUE = registerStatue("cod_statue", () -> new CodStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CowStatueBlock> COW_STATUE = registerStatue("cow_statue", () -> new CowStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<CreeperStatueBlock> CREEPER_STATUE = registerStatue("creeper_statue", () -> new CreeperStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<DetectivePlatypusStatueBlock> DETECTIVE_PLATYPUS = registerStatue("detective_platypus_statue", () -> new DetectivePlatypusStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<DolphinStatueBlock> DOLPHIN_STATUE = registerStatue("dolphin_statue", () -> new DolphinStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<DrownedStatueBlock> DROWNED_STATUE = registerStatue("drowned_statue", () -> new DrownedStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<ElderGuardianStatueBlock> ELDER_GUARDIAN_STATUE = registerStatue("elder_guardian_statue", () -> new ElderGuardianStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<EndermanStatueBlock> ENDERMAN_STATUE = registerStatue("enderman_statue", () -> new EndermanStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<EndermiteStatueBlock> ENDERMITE_STATUE = registerStatue("endermite_statue", () -> new EndermiteStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<EvokerStatueBlock> EVOKER_STATUE = registerStatue("evoker_statue", () -> new EvokerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FloodStatueBlock> FLOOD_STATUE = registerStatue("flood_statue", () -> new FloodStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FoxStatueBlock> FOX_SNOW_STATUE = registerStatue("fox_snow_statue", () -> new FoxStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FoxStatueBlock> FOX_STATUE = registerStatue("fox_statue", () -> new FoxStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<GhastStatueBlock> GHAST_STATUE = registerStatue("ghast_statue", () -> new GhastStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<GuardianStatueBlock> GUARDIAN_STATUE = registerStatue("guardian_statue", () -> new GuardianStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<HuskStatueBlock> HUSK_STATUE = registerStatue("husk_statue", () -> new HuskStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<InfoStatueBlock> INFO_STATUE = registerStatue("info_statue", () -> new InfoStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<KingCluckStatueBlock> KING_CLUCK_STATUE = registerStatue("king_cluck_statue", () -> new KingCluckStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<MagmaStatueBlock> MAGMA_STATUE = registerStatue("magma_statue", () -> new MagmaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<MooshroomStatueBlock> MOOSHROOM_STATUE = registerStatue("mooshroom_statue", () -> new MooshroomStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PandaStatueBlock> PANDA_ANGRY_STATUE = registerStatue("panda_angry_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PandaStatueBlock> PANDA_BROWN_STATUE = registerStatue("panda_brown_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PandaStatueBlock> PANDA_LAZY_STATUE = registerStatue("panda_lazy_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PandaStatueBlock> PANDA_NORMAL_STATUE = registerStatue("panda_normal_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PandaStatueBlock> PANDA_PLAYFUL_STATUE = registerStatue("panda_playful_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PandaStatueBlock> PANDA_WEAK_STATUE = registerStatue("panda_weak_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PandaStatueBlock> PANDA_WORRIED_STATUE = registerStatue("panda_worried_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PigStatueBlock> PIG_STATUE = registerStatue("pig_statue", () -> new PigStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PillagerStatueBlock> PILLAGER_STATUE = registerStatue("pillager_statue", () -> new PillagerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PlayerStatueBlock> PLAYER_STATUE = registerPlayerStatue("player_statue", () -> new PlayerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PufferfishStatueBlock> PUFFERFISH_MEDIUM_STATUE = registerStatue("pufferfish_medium_statue", () -> new PufferfishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final DeferredBlock<PufferfishStatueBlock> PUFFERFISH_SMALL_STATUE = registerStatue("pufferfish_small_statue", () -> new PufferfishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final DeferredBlock<PufferfishStatueBlock> PUFFERFISH_STATUE = registerStatue("pufferfish_statue", () -> new PufferfishStatueBlock(blockBuilder(), 2), blockItemBuilder());
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_BR_STATUE = registerStatue("rabbit_br_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_BS_STATUE = registerStatue("rabbit_bs_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_BW_STATUE = registerStatue("rabbit_bw_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_GO_STATUE = registerStatue("rabbit_go_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_WH_STATUE = registerStatue("rabbit_wh_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<RabbitStatueBlock> RABBIT_WS_STATUE = registerStatue("rabbit_ws_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<RavagerStatueBlock> RAVAGER_STATUE = registerStatue("ravager_statue", () -> new RavagerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SalmonStatueBlock> SALMON_STATUE = registerStatue("salmon_statue", () -> new SalmonStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SheepShavenStatueBlock> SHEEP_SHAVEN_STATUE = registerStatue("sheep_shaven_statue", () -> new SheepShavenStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_BLACK = registerStatue("sheep_statue_black", () -> new SheepStatueBlock(blockBuilder(), DyeColor.BLACK), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_BLUE = registerStatue("sheep_statue_blue", () -> new SheepStatueBlock(blockBuilder(), DyeColor.BLUE), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_BROWN = registerStatue("sheep_statue_brown", () -> new SheepStatueBlock(blockBuilder(), DyeColor.BROWN), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_CYAN = registerStatue("sheep_statue_cyan", () -> new SheepStatueBlock(blockBuilder(), DyeColor.CYAN), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_GRAY = registerStatue("sheep_statue_gray", () -> new SheepStatueBlock(blockBuilder(), DyeColor.GRAY), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_GREEN = registerStatue("sheep_statue_green", () -> new SheepStatueBlock(blockBuilder(), DyeColor.GREEN), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_LIGHT_BLUE = registerStatue("sheep_statue_light_blue", () -> new SheepStatueBlock(blockBuilder(), DyeColor.LIGHT_BLUE), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_LIGHT_GRAY = registerStatue("sheep_statue_light_gray", () -> new SheepStatueBlock(blockBuilder(), DyeColor.LIGHT_GRAY), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_LIME = registerStatue("sheep_statue_lime", () -> new SheepStatueBlock(blockBuilder(), DyeColor.LIME), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_MAGENTA = registerStatue("sheep_statue_magenta", () -> new SheepStatueBlock(blockBuilder(), DyeColor.MAGENTA), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_ORANGE = registerStatue("sheep_statue_orange", () -> new SheepStatueBlock(blockBuilder(), DyeColor.ORANGE), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_PINK = registerStatue("sheep_statue_pink", () -> new SheepStatueBlock(blockBuilder(), DyeColor.PINK), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_PURPLE = registerStatue("sheep_statue_purple", () -> new SheepStatueBlock(blockBuilder(), DyeColor.PURPLE), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_RED = registerStatue("sheep_statue_red", () -> new SheepStatueBlock(blockBuilder(), DyeColor.RED), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_WHITE = registerStatue("sheep_statue_white", () -> new SheepStatueBlock(blockBuilder(), DyeColor.WHITE), blockItemBuilder());
	public static final DeferredBlock<SheepStatueBlock> SHEEP_STATUE_YELLOW = registerStatue("sheep_statue_yellow", () -> new SheepStatueBlock(blockBuilder(), DyeColor.YELLOW), blockItemBuilder());
	public static final DeferredBlock<ShulkerStatueBlock> SHULKER_STATUE = registerStatue("shulker_statue", () -> new ShulkerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SlimeStatueBlock> SLIME_STATUE = registerStatue("slime_statue", () -> new SlimeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SnowGolemStatueBlock> SNOW_GOLEM_STATUE = registerStatue("snow_golem_statue", () -> new SnowGolemStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SpiderStatueBlock> SPIDER_STATUE = registerStatue("spider_statue", () -> new SpiderStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SquidStatueBlock> SQUID_STATUE = registerStatue("squid_statue", () -> new SquidStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<TotemOfUndyingStatueBlock> TOTEM_OF_UNDYING_STATUE = registerStatue("totem_of_undying_statue", () -> new TotemOfUndyingStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_B = registerStatue("tropical_fish_b", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BB = registerStatue("tropical_fish_bb", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BE = registerStatue("tropical_fish_be", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BM = registerStatue("tropical_fish_bm", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BMB = registerStatue("tropical_fish_bmb", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_BMS = registerStatue("tropical_fish_bms", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_E = registerStatue("tropical_fish_e", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_ES = registerStatue("tropical_fish_es", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_HB = registerStatue("tropical_fish_hb", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_SB = registerStatue("tropical_fish_sb", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_SD = registerStatue("tropical_fish_sd", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final DeferredBlock<FishStatueBlock> TROPICAL_FISH_SS = registerStatue("tropical_fish_ss", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final DeferredBlock<TurtleStatueBlock> TURTLE_STATUE = registerStatue("turtle_statue", () -> new TurtleStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<VillagerStatue> VILLAGER_BR_STATUE = registerStatue("villager_br_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<VillagerStatue> VILLAGER_GR_STATUE = registerStatue("villager_gr_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<VillagerStatue> VILLAGER_PU_STATUE = registerStatue("villager_pu_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<VillagerStatue> VILLAGER_WH_STATUE = registerStatue("villager_wh_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<VindicatorStatueBlock> VINDICATOR_STATUE = registerStatue("vindicator_statue", () -> new VindicatorStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<WastelandStatueBlock> WASTELAND_STATUE = registerStatue("wasteland_statue", () -> new WastelandStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<WitchStatueBlock> WITCH_STATUE = registerStatue("witch_statue", () -> new WitchStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<ZombieStatueBlock> ZOMBIE_STATUE = registerStatue("zombie_statue", () -> new ZombieStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AllayStatueBlock> ALLAY_STATUE = registerStatue("allay_statue", () -> new AllayStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_LUCY_STATUE = registerStatue("axolotl_lucy_statue", () -> new AxolotlStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_WILD_STATUE = registerStatue("axolotl_wild_statue", () -> new AxolotlStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_GOLD_STATUE = registerStatue("axolotl_gold_statue", () -> new AxolotlStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_CYAN_STATUE = registerStatue("axolotl_cyan_statue", () -> new AxolotlStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AxolotlStatueBlock> AXOLOTL_BLUE_STATUE = registerStatue("axolotl_blue_statue", () -> new AxolotlStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FrogStatueBlock> FROG_TEMPERATE_STATUE = registerStatue("frog_temperate_statue", () -> new FrogStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FrogStatueBlock> FROG_WARM_STATUE = registerStatue("frog_warm_statue", () -> new FrogStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FrogStatueBlock> FROG_COLD_STATUE = registerStatue("frog_cold_statue", () -> new FrogStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<TadpoleStatueBlock> TADPOLE_STATUE = registerStatue("tadpole_statue", () -> new TadpoleStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<WardenStatueBlock> WARDEN_STATUE = registerStatue("warden_statue", () -> new WardenStatueBlock(blockBuilder()), blockItemBuilder());


	public static final DeferredBlock<DisplayStandBlock> DISPLAY_STAND = registerBlock("display_stand", () -> new DisplayStandBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<BumboStatueBlock> BUMBO_STATUE = registerBlock("bumbo_statue", () -> new BumboStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<PebbleBlock> PEBBLE = registerBlock("pebble", () -> new PebbleBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SombreroBlock> SOMBRERO = registerBlock("sombrero", () -> new SombreroBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<TropiBeeStatueBlock> TROPIBEE = registerBlock("tropibee", () -> new TropiBeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<EagleRayStatueBlock> EAGLE_RAY = registerBlock("eagle_ray", () -> new EagleRayStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<SlabFishStatueBlock> SLABFISH = registerBlock("slabfish", () -> new SlabFishStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<AzzaroStatueBlock> AZZARO = registerBlock("azzaro", () -> new AzzaroStatueBlock(blockBuilder()), blockItemBuilder());
	public static final DeferredBlock<FlowerBlock> CORE_FLOWER = BLOCKS.register("core_flower", () -> new FlowerBlock(
			MobEffects.MOVEMENT_SLOWDOWN,
			5,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.PLANT)
					.noCollission()
					.instabreak()
					.sound(SoundType.GRASS)
					.offsetType(BlockBehaviour.OffsetType.XZ)
					.pushReaction(PushReaction.DESTROY)));
	public static final DeferredBlock<CoreFlowerCropBlock> CORE_FLOWER_CROP = BLOCKS.registerBlock("core_flower_crop", CoreFlowerCropBlock::new,
			BlockBehaviour.Properties.of()
					.mapColor(MapColor.PLANT)
					.noCollission()
					.randomTicks()
					.instabreak()
					.sound(SoundType.CROP)
					.pushReaction(PushReaction.DESTROY));

	public static final DeferredItem<Item> CUP = ITEMS.register("cup", () -> new Item(itemBuilder().food(StatueFoods.CUP)));
	public static final DeferredItem<Item> MARSHMALLOW = ITEMS.register("marshmallow", () -> new Item(itemBuilder().food(StatueFoods.MARSHMALLOW)));
	public static final DeferredItem<Item> MARSHMALLOW_CHARRED = ITEMS.register("marshmallow_charred", () -> new StatueCharredMarshmallow(itemBuilder()));
	public static final DeferredItem<Item> MARSHMALLOW_COOKED = ITEMS.register("marshmallow_cooked", () -> new Item(itemBuilder().food(StatueFoods.COOKED_MARSHMALLOW)));
	public static final DeferredItem<Item> MARSHMALLOW_GOLDEN = ITEMS.register("marshmallow_golden", () -> new StatueGoldenMarshmallow(itemBuilder()));
	public static final DeferredItem<Item> NUGGET = ITEMS.register("royal_nugget", () -> new Item(itemBuilder().food(StatueFoods.ROYAL_NUGGET)));
	public static final DeferredItem<Item> PLAYER_COMPASS = ITEMS.register("player_compass", () -> new PlayerCompassItem(itemBuilder()));
	public static final DeferredItem<StatueMooshroomSoup> SOUP = ITEMS.register("mooshroom_soup", () -> new StatueMooshroomSoup(itemBuilder()));
	public static final DeferredItem<StatueCoreItem> STATUE_CORE = ITEMS.register("statue_core", () -> new StatueCoreItem(itemBuilder()));
	public static final DeferredItem<StatueTeaItem> TEA = ITEMS.register("tea", () -> new StatueTeaItem(itemBuilder(), StatueFoods.TEA));
	public static final DeferredItem<Item> CORE_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("core_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(StatueTrims.CORE));
	public static final DeferredItem<Item> CORE_FLOWER_SEED = ITEMS.register("core_flower_seed", () -> new ItemNameBlockItem(CORE_FLOWER_CROP.get(), itemBuilder()));
	public static final DeferredItem<Item> CORE_FLOWER_ITEM = ITEMS.register("core_flower", () -> new BlockItem(CORE_FLOWER.get(), itemBuilder()));
	public static final DeferredItem<Item> STATUE_CORE_POTTERY_SHERD = ITEMS.register("statue_core_pottery_sherd", () -> SmithingTemplateItem.createArmorTrimTemplate(StatueTrims.CORE));

	public static final DeferredItem<PlayerStatueSpawnItem> PLAYER_STATUE_SPAWN_EGG = ITEMS.register("player_statue_spawn_egg", () -> new PlayerStatueSpawnItem(itemBuilder()));
	public static final DeferredItem<DeferredSpawnEggItem> STATUE_BAT_SPANW_EGG = ITEMS.register("statue_bat_spawn_egg", () -> new DeferredSpawnEggItem(StatueRegistry.STATUE_BAT::get, 3421236, 3556687, itemBuilder()));

	public static <B extends Block> DeferredBlock<B> registerStatue(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new StatueBlockItem(block.get(), properties));
		return block;
	}

	public static <B extends Block> DeferredBlock<B> registerBeeStatue(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new StatueBeeItem(block.get(), properties));
		return block;
	}

	public static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new StatueBlockItem(block.get(), properties));
		return block;
	}

	public static <B extends Block> DeferredBlock<B> registerPlayerStatue(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		DeferredBlock<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new PlayerStatueBlockItem(block.get(), properties));
		return block;
	}

	private static Item.Properties blockItemBuilder() {
		return new Item.Properties();
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties();
	}

	private static Block.Properties blockBuilder() {
		return Block.Properties.of().mapColor(MapColor.COLOR_PURPLE);
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
