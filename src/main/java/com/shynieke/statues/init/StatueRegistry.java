package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.decorative.BumboStatueBlock;
import com.shynieke.statues.blocks.decorative.DisplayStandBlock;
import com.shynieke.statues.blocks.decorative.EndermiteStatueBlock;
import com.shynieke.statues.blocks.decorative.PebbleBlock;
import com.shynieke.statues.blocks.decorative.SombreroBlock;
import com.shynieke.statues.blocks.decorative.TotemOfUndyingStatueBlock;
import com.shynieke.statues.blocks.decorative.charity.EagleRayStatueBlock;
import com.shynieke.statues.blocks.decorative.charity.SlabFishStatueBlock;
import com.shynieke.statues.blocks.decorative.charity.TropiBeeStatueBlock;
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
import com.shynieke.statues.blocks.statues.WastelandStatueBlock;
import com.shynieke.statues.blocks.statues.WitchStatueBlock;
import com.shynieke.statues.blocks.statues.ZombieStatueBlock;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class StatueRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MOD_ID);

	public static final RegistryObject<EntityType<PlayerStatue>> PLAYER_STATUE_ENTITY = ENTITIES.register("player_statue",
			() -> register("player_statue", EntityType.Builder.<PlayerStatue>of(PlayerStatue::new, MobCategory.MISC)
					.sized(0.6F, 1.8F).clientTrackingRange(10)));
	public static final RegistryObject<EntityType<StatueBatEntity>> STATUE_BAT = ENTITIES.register("statue_bat",
			() -> register("statue_bat", EntityType.Builder.<StatueBatEntity>of(StatueBatEntity::new, MobCategory.AMBIENT)
					.sized(0.5F, 0.9F).clientTrackingRange(5)));

	public static final RegistryObject<Block> ANGRY_BEE_STATUE = registerStatue("angry_bee_statue", () -> new AngryBeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> BABY_ZOMBIE_STATUE = registerStatue("baby_zombie_statue", () -> new BabyZombieStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> BEE_STATUE = registerBeeStatue("bee_statue", () -> new BeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> TRANS_BEE = registerStatue("trans_bee_statue", () -> new BeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> BLAZE_STATUE = registerStatue("blaze_statue", () -> new BlazeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> BROWN_MOOSHROOM_STATUE = registerStatue("brown_mooshroom_statue", () -> new BrownMooshroomStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAMPFIRE_STATUE = registerStatue("campfire_statue", () -> new CampfireStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_BLACK_STATUE = registerStatue("cat_black_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_BRITISH_SHORTHAIR_STATUE = registerStatue("cat_british_shorthair_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_CALICO_STATUE = registerStatue("cat_calico_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_JELLIE_STATUE = registerStatue("cat_jellie_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_PERSIAN_STATUE = registerStatue("cat_persian_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_RAGDOLL_STATUE = registerStatue("cat_ragdoll_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_RED_STATUE = registerStatue("cat_red_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_SIAMESE_STATUE = registerStatue("cat_siamese_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_TABBY_STATUE = registerStatue("cat_tabby_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_TUXEDO_STATUE = registerStatue("cat_tuxedo_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CAT_WHITE_STATUE = registerStatue("cat_white_statue", () -> new CatStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CHICKEN_JOCKEY_STATUE = registerStatue("chicken_jockey_statue", () -> new ChickenJockeyStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CHICKEN_STATUE = registerStatue("chicken_statue", () -> new ChickenStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> COD_STATUE = registerStatue("cod_statue", () -> new CodStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> COW_STATUE = registerStatue("cow_statue", () -> new CowStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> CREEPER_STATUE = registerStatue("creeper_statue", () -> new CreeperStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> DETECTIVE_PLATYPUS = registerStatue("detective_platypus_statue", () -> new DetectivePlatypusStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> DOLPHIN_STATUE = registerStatue("dolphin_statue", () -> new DolphinStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> DROWNED_STATUE = registerStatue("drowned_statue", () -> new DrownedStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> ELDER_GUARDIAN_STATUE = registerStatue("elder_guardian_statue", () -> new ElderGuardianStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> ENDERMAN_STATUE = registerStatue("enderman_statue", () -> new EndermanStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> ENDERMITE_STATUE = registerStatue("endermite_statue", () -> new EndermiteStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> EVOKER_STATUE = registerStatue("evoker_statue", () -> new EvokerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> FLOOD_STATUE = registerStatue("flood_statue", () -> new FloodStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> FOX_SNOW_STATUE = registerStatue("fox_snow_statue", () -> new FoxStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> FOX_STATUE = registerStatue("fox_statue", () -> new FoxStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> GHAST_STATUE = registerStatue("ghast_statue", () -> new GhastStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> GUARDIAN_STATUE = registerStatue("guardian_statue", () -> new GuardianStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> HUSK_STATUE = registerStatue("husk_statue", () -> new HuskStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> INFO_STATUE = registerStatue("info_statue", () -> new InfoStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> KING_CLUCK_STATUE = registerStatue("king_cluck_statue", () -> new KingCluckStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> MAGMA_STATUE = registerStatue("magma_statue", () -> new MagmaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> MOOSHROOM_STATUE = registerStatue("mooshroom_statue", () -> new MooshroomStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PANDA_ANGRY_STATUE = registerStatue("panda_angry_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PANDA_BROWN_STATUE = registerStatue("panda_brown_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PANDA_LAZY_STATUE = registerStatue("panda_lazy_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PANDA_NORMAL_STATUE = registerStatue("panda_normal_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PANDA_PLAYFUL_STATUE = registerStatue("panda_playful_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PANDA_WEAK_STATUE = registerStatue("panda_weak_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PANDA_WORRIED_STATUE = registerStatue("panda_worried_statue", () -> new PandaStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PIG_STATUE = registerStatue("pig_statue", () -> new PigStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PILLAGER_STATUE = registerStatue("pillager_statue", () -> new PillagerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PLAYER_STATUE = registerPlayerStatue("player_statue", () -> new PlayerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PUFFERFISH_MEDIUM_STATUE = registerStatue("pufferfish_medium_statue", () -> new PufferfishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final RegistryObject<Block> PUFFERFISH_SMALL_STATUE = registerStatue("pufferfish_small_statue", () -> new PufferfishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final RegistryObject<Block> PUFFERFISH_STATUE = registerStatue("pufferfish_statue", () -> new PufferfishStatueBlock(blockBuilder(), 2), blockItemBuilder());
	public static final RegistryObject<Block> RABBIT_BR_STATUE = registerStatue("rabbit_br_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> RABBIT_BS_STATUE = registerStatue("rabbit_bs_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> RABBIT_BW_STATUE = registerStatue("rabbit_bw_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> RABBIT_GO_STATUE = registerStatue("rabbit_go_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> RABBIT_WH_STATUE = registerStatue("rabbit_wh_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> RABBIT_WS_STATUE = registerStatue("rabbit_ws_statue", () -> new RabbitStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> RAVAGER_STATUE = registerStatue("ravager_statue", () -> new RavagerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SALMON_STATUE = registerStatue("salmon_statue", () -> new SalmonStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_SHAVEN_STATUE = registerStatue("sheep_shaven_statue", () -> new SheepShavenStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_BLACK = registerStatue("sheep_statue_black", () -> new SheepStatueBlock(blockBuilder(), DyeColor.BLACK), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_BLUE = registerStatue("sheep_statue_blue", () -> new SheepStatueBlock(blockBuilder(), DyeColor.BLUE), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_BROWN = registerStatue("sheep_statue_brown", () -> new SheepStatueBlock(blockBuilder(), DyeColor.BROWN), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_CYAN = registerStatue("sheep_statue_cyan", () -> new SheepStatueBlock(blockBuilder(), DyeColor.CYAN), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_GRAY = registerStatue("sheep_statue_gray", () -> new SheepStatueBlock(blockBuilder(), DyeColor.GRAY), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_GREEN = registerStatue("sheep_statue_green", () -> new SheepStatueBlock(blockBuilder(), DyeColor.GREEN), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_LIGHT_BLUE = registerStatue("sheep_statue_light_blue", () -> new SheepStatueBlock(blockBuilder(), DyeColor.LIGHT_BLUE), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_LIGHT_GRAY = registerStatue("sheep_statue_light_gray", () -> new SheepStatueBlock(blockBuilder(), DyeColor.LIGHT_GRAY), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_LIME = registerStatue("sheep_statue_lime", () -> new SheepStatueBlock(blockBuilder(), DyeColor.LIME), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_MAGENTA = registerStatue("sheep_statue_magenta", () -> new SheepStatueBlock(blockBuilder(), DyeColor.MAGENTA), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_ORANGE = registerStatue("sheep_statue_orange", () -> new SheepStatueBlock(blockBuilder(), DyeColor.ORANGE), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_PINK = registerStatue("sheep_statue_pink", () -> new SheepStatueBlock(blockBuilder(), DyeColor.PINK), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_PURPLE = registerStatue("sheep_statue_purple", () -> new SheepStatueBlock(blockBuilder(), DyeColor.PURPLE), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_RED = registerStatue("sheep_statue_red", () -> new SheepStatueBlock(blockBuilder(), DyeColor.RED), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_WHITE = registerStatue("sheep_statue_white", () -> new SheepStatueBlock(blockBuilder(), DyeColor.WHITE), blockItemBuilder());
	public static final RegistryObject<Block> SHEEP_STATUE_YELLOW = registerStatue("sheep_statue_yellow", () -> new SheepStatueBlock(blockBuilder(), DyeColor.YELLOW), blockItemBuilder());
	public static final RegistryObject<Block> SHULKER_STATUE = registerStatue("shulker_statue", () -> new ShulkerStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SLIME_STATUE = registerStatue("slime_statue", () -> new SlimeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SNOW_GOLEM_STATUE = registerStatue("snow_golem_statue", () -> new SnowGolemStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SPIDER_STATUE = registerStatue("spider_statue", () -> new SpiderStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SQUID_STATUE = registerStatue("squid_statue", () -> new SquidStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> TOTEM_OF_UNDYING_STATUE = registerStatue("totem_of_undying_statue", () -> new TotemOfUndyingStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_B = registerStatue("tropical_fish_b", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_BB = registerStatue("tropical_fish_bb", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_BE = registerStatue("tropical_fish_be", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_BM = registerStatue("tropical_fish_bm", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_BMB = registerStatue("tropical_fish_bmb", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_BMS = registerStatue("tropical_fish_bms", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_E = registerStatue("tropical_fish_e", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_ES = registerStatue("tropical_fish_es", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_HB = registerStatue("tropical_fish_hb", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_SB = registerStatue("tropical_fish_sb", () -> new FishStatueBlock(blockBuilder(), 1), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_SD = registerStatue("tropical_fish_sd", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final RegistryObject<Block> TROPICAL_FISH_SS = registerStatue("tropical_fish_ss", () -> new FishStatueBlock(blockBuilder(), 0), blockItemBuilder());
	public static final RegistryObject<Block> TURTLE_STATUE = registerStatue("turtle_statue", () -> new TurtleStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> VILLAGER_BR_STATUE = registerStatue("villager_br_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> VILLAGER_GR_STATUE = registerStatue("villager_gr_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> VILLAGER_PU_STATUE = registerStatue("villager_pu_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> VILLAGER_WH_STATUE = registerStatue("villager_wh_statue", () -> new VillagerStatue(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> VINDICATOR_STATUE = registerStatue("vindicator_statue", () -> new VindicatorStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> WASTELAND_STATUE = registerStatue("wasteland_statue", () -> new WastelandStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> WITCH_STATUE = registerStatue("witch_statue", () -> new WitchStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> ZOMBIE_STATUE = registerStatue("zombie_statue", () -> new ZombieStatueBlock(blockBuilder()), blockItemBuilder());

	public static final RegistryObject<Block> DISPLAY_STAND = registerBlock("display_stand", () -> new DisplayStandBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> BUMBO_STATUE = registerBlock("bumbo_statue", () -> new BumboStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> PEBBLE = registerBlock("pebble", () -> new PebbleBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SOMBRERO = registerBlock("sombrero", () -> new SombreroBlock(blockBuilder()), blockItemBuilder());

	public static final RegistryObject<Block> TROPIBEE = registerBlock("tropibee", () -> new TropiBeeStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> EAGLE_RAY = registerBlock("eagle_ray", () -> new EagleRayStatueBlock(blockBuilder()), blockItemBuilder());
	public static final RegistryObject<Block> SLABFISH = registerBlock("slabfish", () -> new SlabFishStatueBlock(blockBuilder()), blockItemBuilder());

	public static final RegistryObject<Item> CUP = ITEMS.register("cup", () -> new Item(itemBuilder().food(StatueFoods.CUP)));
	public static final RegistryObject<Item> MARSHMALLOW = ITEMS.register("marshmallow", () -> new Item(itemBuilder().food(StatueFoods.MARSHMALLOW)));
	public static final RegistryObject<Item> MARSHMALLOW_CHARRED = ITEMS.register("marshmallow_charred", () -> new StatueCharredMarshmallow(itemBuilder()));
	public static final RegistryObject<Item> MARSHMALLOW_COOKED = ITEMS.register("marshmallow_cooked", () -> new Item(itemBuilder().food(StatueFoods.COOKED_MARSHMALLOW)));
	public static final RegistryObject<Item> MARSHMALLOW_GOLDEN = ITEMS.register("marshmallow_golden", () -> new StatueGoldenMarshmallow(itemBuilder()));
	public static final RegistryObject<Item> NUGGET = ITEMS.register("royal_nugget", () -> new Item(itemBuilder().food(StatueFoods.ROYAL_NUGGET)));
	public static final RegistryObject<Item> PLAYER_COMPASS = ITEMS.register("player_compass", () -> new PlayerCompassItem(itemBuilder()));
	public static final RegistryObject<Item> SOUP = ITEMS.register("mooshroom_soup", () -> new StatueMooshroomSoup(itemBuilder()));
	public static final RegistryObject<Item> STATUE_CORE = ITEMS.register("statue_core", () -> new StatueCoreItem(itemBuilder()));
	public static final RegistryObject<Item> TEA = ITEMS.register("tea", () -> new StatueTeaItem(itemBuilder(), StatueFoods.TEA));

	public static final RegistryObject<Item> PLAYER_STATUE_SPAWN_EGG = ITEMS.register("player_statue_spawn_egg", () -> new PlayerStatueSpawnItem(itemBuilder()));
	public static final RegistryObject<Item> STATUE_BAT_SPANW_EGG = ITEMS.register("statue_bat_spawn_egg", () -> new ForgeSpawnEggItem(StatueRegistry.STATUE_BAT::get, 3421236, 3556687, itemBuilder()));

	public static <B extends Block> RegistryObject<B> registerStatue(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		RegistryObject<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new StatueBlockItem(block.get(), properties));
		return block;
	}

	public static <B extends Block> RegistryObject<B> registerBeeStatue(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		RegistryObject<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new StatueBeeItem(block.get(), properties));
		return block;
	}

	public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		RegistryObject<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new StatueBlockItem(block.get(), properties));
		return block;
	}

	public static <B extends Block> RegistryObject<B> registerPlayerStatue(String name, Supplier<? extends B> supplier, Item.Properties properties) {
		RegistryObject<B> block = StatueRegistry.BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new PlayerStatueBlockItem(block.get(), properties));
		return block;
	}

	private static Item.Properties blockItemBuilder() {
		return new Item.Properties().tab(StatueTabs.STATUES_BLOCKS);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().tab(StatueTabs.STATUES_ITEMS);
	}

	private static Block.Properties blockBuilder() {
		return Block.Properties.of(Material.SHULKER_SHELL);
	}

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}
}
