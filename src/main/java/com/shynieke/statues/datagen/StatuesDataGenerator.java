package com.shynieke.statues.datagen;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueTags;
import com.shynieke.statues.lootmodifiers.StatuesLootModifier;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new StatueLoot(generator));
			StatueBlockTags blockTags = new StatueBlockTags(generator, event.getExistingFileHelper());
			generator.addProvider(event.includeServer(), blockTags);
			generator.addProvider(event.includeServer(), new StatueItemTags(generator, blockTags, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new StatueBiomeTags(generator, event.getExistingFileHelper()));
			generator.addProvider(event.includeServer(), new StatueModifiers(generator));

			final HolderSet.Named<Biome> spawnBiomesTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), StatueTags.CAN_SPAWN_STATUE_BAT);
			final BiomeModifier addSpawn = AddSpawnsBiomeModifier.singleSpawn(
					spawnBiomesTag,
					new SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));

			final HolderSet.Named<Biome> spawnLessBiomesTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), StatueTags.CAN_SPAWN_FEWER_STATUE_BAT);
			final BiomeModifier addFewerSpawn = AddSpawnsBiomeModifier.singleSpawn(
					spawnLessBiomesTag,
					new SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, helper, Reference.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
					Map.of(new ResourceLocation(Reference.MOD_ID, "add_statue_bat_spawn"), addSpawn,
							new ResourceLocation(Reference.MOD_ID, "add_fewer_statue_bat_spawn"), addFewerSpawn
					)
			));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new StatueItemModels(generator, helper));
		}
	}

	private static class StatueBlockTags extends BlockTagsProvider {
		public StatueBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(StatueTags.STATUE_BLOCKS).add(StatueRegistry.ANGRY_BEE_STATUE.get(), StatueRegistry.BABY_ZOMBIE_STATUE.get(), StatueRegistry.BEE_STATUE.get(), StatueRegistry.TRANS_BEE.get(), StatueRegistry.BLAZE_STATUE.get(),
					StatueRegistry.BROWN_MOOSHROOM_STATUE.get(), StatueRegistry.CAMPFIRE_STATUE.get(), StatueRegistry.CAT_BLACK_STATUE.get(), StatueRegistry.CAT_BRITISH_SHORTHAIR_STATUE.get(), StatueRegistry.CAT_CALICO_STATUE.get(),
					StatueRegistry.CAT_JELLIE_STATUE.get(), StatueRegistry.CAT_PERSIAN_STATUE.get(), StatueRegistry.CAT_RAGDOLL_STATUE.get(), StatueRegistry.CAT_RED_STATUE.get(), StatueRegistry.CAT_SIAMESE_STATUE.get(), StatueRegistry.CAT_TABBY_STATUE.get(),
					StatueRegistry.CAT_TUXEDO_STATUE.get(), StatueRegistry.CAT_WHITE_STATUE.get(), StatueRegistry.CHICKEN_JOCKEY_STATUE.get(), StatueRegistry.CHICKEN_STATUE.get(), StatueRegistry.COD_STATUE.get(), StatueRegistry.COW_STATUE.get(),
					StatueRegistry.CREEPER_STATUE.get(), StatueRegistry.DETECTIVE_PLATYPUS.get(), StatueRegistry.DOLPHIN_STATUE.get(), StatueRegistry.DROWNED_STATUE.get(), StatueRegistry.ELDER_GUARDIAN_STATUE.get(), StatueRegistry.ENDERMAN_STATUE.get(),
					StatueRegistry.ENDERMITE_STATUE.get(), StatueRegistry.EVOKER_STATUE.get(), StatueRegistry.FLOOD_STATUE.get(), StatueRegistry.FOX_SNOW_STATUE.get(), StatueRegistry.FOX_STATUE.get(), StatueRegistry.GHAST_STATUE.get(), StatueRegistry.GUARDIAN_STATUE.get(),
					StatueRegistry.HUSK_STATUE.get(), StatueRegistry.INFO_STATUE.get(), StatueRegistry.KING_CLUCK_STATUE.get(), StatueRegistry.MAGMA_STATUE.get(), StatueRegistry.MOOSHROOM_STATUE.get(), StatueRegistry.PANDA_ANGRY_STATUE.get(),
					StatueRegistry.PANDA_BROWN_STATUE.get(), StatueRegistry.PANDA_LAZY_STATUE.get(), StatueRegistry.PANDA_NORMAL_STATUE.get(), StatueRegistry.PANDA_PLAYFUL_STATUE.get(), StatueRegistry.PANDA_WEAK_STATUE.get(),
					StatueRegistry.PANDA_WORRIED_STATUE.get(), StatueRegistry.PIG_STATUE.get(), StatueRegistry.PILLAGER_STATUE.get(), StatueRegistry.PLAYER_STATUE.get(), StatueRegistry.PUFFERFISH_MEDIUM_STATUE.get(),
					StatueRegistry.PUFFERFISH_SMALL_STATUE.get(), StatueRegistry.PUFFERFISH_STATUE.get(), StatueRegistry.RABBIT_BR_STATUE.get(), StatueRegistry.RABBIT_BS_STATUE.get(), StatueRegistry.RABBIT_BW_STATUE.get(),
					StatueRegistry.RABBIT_GO_STATUE.get(), StatueRegistry.RABBIT_WH_STATUE.get(), StatueRegistry.RABBIT_WS_STATUE.get(), StatueRegistry.RAVAGER_STATUE.get(), StatueRegistry.SALMON_STATUE.get(), StatueRegistry.SHEEP_SHAVEN_STATUE.get(),
					StatueRegistry.SHEEP_STATUE_BLACK.get(), StatueRegistry.SHEEP_STATUE_BLUE.get(), StatueRegistry.SHEEP_STATUE_BROWN.get(), StatueRegistry.SHEEP_STATUE_CYAN.get(), StatueRegistry.SHEEP_STATUE_GRAY.get(), StatueRegistry.SHEEP_STATUE_GREEN.get(),
					StatueRegistry.SHEEP_STATUE_LIGHT_BLUE.get(), StatueRegistry.SHEEP_STATUE_LIGHT_GRAY.get(), StatueRegistry.SHEEP_STATUE_LIME.get(), StatueRegistry.SHEEP_STATUE_MAGENTA.get(), StatueRegistry.SHEEP_STATUE_ORANGE.get(),
					StatueRegistry.SHEEP_STATUE_PINK.get(), StatueRegistry.SHEEP_STATUE_PURPLE.get(), StatueRegistry.SHEEP_STATUE_RED.get(), StatueRegistry.SHEEP_STATUE_WHITE.get(), StatueRegistry.SHEEP_STATUE_YELLOW.get(), StatueRegistry.SHULKER_STATUE.get(),
					StatueRegistry.SLIME_STATUE.get(), StatueRegistry.SNOW_GOLEM_STATUE.get(), StatueRegistry.SPIDER_STATUE.get(), StatueRegistry.SQUID_STATUE.get(), StatueRegistry.TOTEM_OF_UNDYING_STATUE.get(), StatueRegistry.TROPICAL_FISH_B.get(),
					StatueRegistry.TROPICAL_FISH_BB.get(), StatueRegistry.TROPICAL_FISH_BE.get(), StatueRegistry.TROPICAL_FISH_BM.get(), StatueRegistry.TROPICAL_FISH_BMB.get(), StatueRegistry.TROPICAL_FISH_BMS.get(), StatueRegistry.TROPICAL_FISH_E.get(),
					StatueRegistry.TROPICAL_FISH_ES.get(), StatueRegistry.TROPICAL_FISH_HB.get(), StatueRegistry.TROPICAL_FISH_SB.get(), StatueRegistry.TROPICAL_FISH_SD.get(), StatueRegistry.TROPICAL_FISH_SS.get(), StatueRegistry.TURTLE_STATUE.get(),
					StatueRegistry.VILLAGER_BR_STATUE.get(), StatueRegistry.VILLAGER_GR_STATUE.get(), StatueRegistry.VILLAGER_PU_STATUE.get(), StatueRegistry.VILLAGER_WH_STATUE.get(), StatueRegistry.VINDICATOR_STATUE.get(), StatueRegistry.WASTELAND_STATUE.get(),
					StatueRegistry.WITCH_STATUE.get(), StatueRegistry.ZOMBIE_STATUE.get(), StatueRegistry.BUMBO_STATUE.get(), StatueRegistry.TROPIBEE.get(), StatueRegistry.EAGLE_RAY.get(), StatueRegistry.SLABFISH.get());
		}
	}

	private static class StatueItemTags extends ItemTagsProvider {
		public StatueItemTags(DataGenerator generator, BlockTagsProvider tagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, tagsProvider, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.copy(StatueTags.STATUE_BLOCKS, StatueTags.STATUES_ITEMS);
			this.tag(StatueTags.CURIOS_STATUE).addTag(StatueTags.STATUES_ITEMS).add(StatueRegistry.DISPLAY_STAND.get().asItem(), StatueRegistry.SOMBRERO.get().asItem());
			this.tag(StatueTags.PLAYER_UPGRADE_ITEM).add(StatueRegistry.STATUE_CORE.get());
		}
	}

	private static class StatueBiomeTags extends BiomeTagsProvider {

		public StatueBiomeTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			TagsProvider.TagAppender<Biome> tagappender1 = this.tag(StatueTags.CAN_SPAWN_STATUE_BAT);
			MultiNoiseBiomeSource.Preset.OVERWORLD.possibleBiomes().forEach((resourceKey) -> {
				if (!resourceKey.equals(Biomes.DEEP_DARK) && !resourceKey.equals(Biomes.MUSHROOM_FIELDS))
					tagappender1.add(resourceKey);
			});
			this.tag(StatueTags.CAN_SPAWN_FEWER_STATUE_BAT).add(Biomes.BASALT_DELTAS);
		}
	}

	private static class StatueLoot extends LootTableProvider {
		public StatueLoot(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(Pair.of(StatueBlocks::new, LootContextParamSets.BLOCK), Pair.of(StatueEntities::new, LootContextParamSets.ENTITY));
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
			map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
		}

		private class StatueBlocks extends BlockLoot {
			@Override
			protected void addTables() {
				this.add(StatueRegistry.PEBBLE.get(), (block) -> createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(block)))));
				this.dropSelf(StatueRegistry.DISPLAY_STAND.get());
				this.dropSelf(StatueRegistry.SOMBRERO.get());
				this.dropSelf(StatueRegistry.INFO_STATUE.get());
				this.add(StatueRegistry.PLAYER_STATUE.get(), createNameableBlockEntityTable(StatueRegistry.PLAYER_STATUE.get()));
				for (RegistryObject<Block> blockObject : StatueRegistry.BLOCKS.getEntries()) {
					if (blockObject.get() instanceof AbstractStatueBase) {
						AbstractStatueBase base = (AbstractStatueBase) blockObject.get();
						if (base.canBeUpgraded()) {
							this.add(blockObject.get(), createNameableBlockEntityTable(blockObject.get()));
						} else {
							this.dropSelf(blockObject.get());
						}
					}
				}
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) StatueRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}


		private class StatueEntities extends EntityLoot {
			@Override
			protected void addTables() {
				this.add(StatueRegistry.PLAYER_STATUE_ENTITY.get(), LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
								.add(LootItem.lootTableItem(StatueRegistry.STATUE_CORE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F))))
								.when(LootItemKilledByPlayerCondition.killedByPlayer())));
				this.add(StatueRegistry.STATUE_BAT.get(), LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
								.add(LootItem.lootTableItem(StatueRegistry.STATUE_CORE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
								.when(LootItemKilledByPlayerCondition.killedByPlayer())));
			}

			@Override
			protected boolean isNonLiving(EntityType<?> entitytype) {
				return false;
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				Stream<EntityType<?>> entityTypeStream = StatueRegistry.ENTITIES.getEntries().stream().map(RegistryObject::get);
				return entityTypeStream::iterator;
			}
		}
	}

	private static class StatueItemModels extends ItemModelProvider {
		public StatueItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Reference.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {

		}

		@Override
		public String getName() {
			return "Item Models";
		}
	}

	public static class StatueModifiers extends GlobalLootModifierProvider {
		public StatueModifiers(DataGenerator generator) {
			super(generator, Reference.MOD_ID);
		}

		@Override
		protected void start() {
			this.add("statues_loot", new StatuesLootModifier(
					new LootItemCondition[]{
							LootTableIdCondition.builder(BuiltInLootTables.ANCIENT_CITY).build()
					}));
		}
	}
}
