package com.shynieke.statues.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.shynieke.statues.init.StatueRegistry.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class StatuesDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new StatueLoot(generator));
			StatueBlockTags blockTags = new StatueBlockTags(generator, event.getExistingFileHelper());
			generator.addProvider(blockTags);
			generator.addProvider(new StatueItemTags(generator, blockTags, event.getExistingFileHelper()));
		}
		if (event.includeClient()) {
			generator.addProvider(new StatueItemModels(generator, helper));
		}
	}

	private static class StatueBlockTags extends BlockTagsProvider {
		public StatueBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(StatueTags.STATUE_BLOCKS).add(ANGRY_BEE_STATUE.get(), BABY_ZOMBIE_STATUE.get(), BEE_STATUE.get(), TRANS_BEE.get(), BLAZE_STATUE.get(),
					BROWN_MOOSHROOM_STATUE.get(), CAMPFIRE_STATUE.get(), CAT_BLACK_STATUE.get(), CAT_BRITISH_SHORTHAIR_STATUE.get(), CAT_CALICO_STATUE.get(),
					CAT_JELLIE_STATUE.get(), CAT_PERSIAN_STATUE.get(), CAT_RAGDOLL_STATUE.get(), CAT_RED_STATUE.get(), CAT_SIAMESE_STATUE.get(), CAT_TABBY_STATUE.get(),
					CAT_TUXEDO_STATUE.get(), CAT_WHITE_STATUE.get(), CHICKEN_JOCKEY_STATUE.get(), CHICKEN_STATUE.get(), COD_STATUE.get(), COW_STATUE.get(),
					CREEPER_STATUE.get(), DETECTIVE_PLATYPUS.get(), DOLPHIN_STATUE.get(), DROWNED_STATUE.get(), ELDER_GUARDIAN_STATUE.get(), ENDERMAN_STATUE.get(),
					ENDERMITE_STATUE.get(), EVOKER_STATUE.get(), FLOOD_STATUE.get(), FOX_SNOW_STATUE.get(), FOX_STATUE.get(), GHAST_STATUE.get(), GUARDIAN_STATUE.get(),
					HUSK_STATUE.get(), INFO_STATUE.get(), KING_CLUCK_STATUE.get(), MAGMA_STATUE.get(), MOOSHROOM_STATUE.get(), PANDA_ANGRY_STATUE.get(),
					PANDA_BROWN_STATUE.get(), PANDA_LAZY_STATUE.get(), PANDA_NORMAL_STATUE.get(), PANDA_PLAYFUL_STATUE.get(), PANDA_WEAK_STATUE.get(),
					PANDA_WORRIED_STATUE.get(), PIG_STATUE.get(), PILLAGER_STATUE.get(), PLAYER_STATUE.get(), PUFFERFISH_MEDIUM_STATUE.get(),
					PUFFERFISH_SMALL_STATUE.get(), PUFFERFISH_STATUE.get(), RABBIT_BR_STATUE.get(), RABBIT_BS_STATUE.get(), RABBIT_BW_STATUE.get(),
					RABBIT_GO_STATUE.get(), RABBIT_WH_STATUE.get(), RABBIT_WS_STATUE.get(), RAVAGER_STATUE.get(), SALMON_STATUE.get(), SHEEP_SHAVEN_STATUE.get(),
					SHEEP_STATUE_BLACK.get(), SHEEP_STATUE_BLUE.get(), SHEEP_STATUE_BROWN.get(), SHEEP_STATUE_CYAN.get(), SHEEP_STATUE_GRAY.get(), SHEEP_STATUE_GREEN.get(),
					SHEEP_STATUE_LIGHT_BLUE.get(), SHEEP_STATUE_LIGHT_GRAY.get(), SHEEP_STATUE_LIME.get(), SHEEP_STATUE_MAGENTA.get(), SHEEP_STATUE_ORANGE.get(),
					SHEEP_STATUE_PINK.get(), SHEEP_STATUE_PURPLE.get(), SHEEP_STATUE_RED.get(), SHEEP_STATUE_WHITE.get(), SHEEP_STATUE_YELLOW.get(), SHULKER_STATUE.get(),
					SLIME_STATUE.get(), SNOW_GOLEM_STATUE.get(), SPIDER_STATUE.get(), SQUID_STATUE.get(), TOTEM_OF_UNDYING_STATUE.get(), TROPICAL_FISH_B.get(),
					TROPICAL_FISH_BB.get(), TROPICAL_FISH_BE.get(), TROPICAL_FISH_BM.get(), TROPICAL_FISH_BMB.get(), TROPICAL_FISH_BMS.get(), TROPICAL_FISH_E.get(),
					TROPICAL_FISH_ES.get(), TROPICAL_FISH_HB.get(), TROPICAL_FISH_SB.get(), TROPICAL_FISH_SD.get(), TROPICAL_FISH_SS.get(), TURTLE_STATUE.get(),
					VILLAGER_BR_STATUE.get(), VILLAGER_GR_STATUE.get(), VILLAGER_PU_STATUE.get(), VILLAGER_WH_STATUE.get(), VINDICATOR_STATUE.get(), WASTELAND_STATUE.get(),
					WITCH_STATUE.get(), ZOMBIE_STATUE.get(), BUMBO_STATUE.get(), DISPLAY_STAND.get(), TROPIBEE.get(), EAGLE_RAY.get(), SLABFISH.get());
		}
	}

	private static class StatueItemTags extends ItemTagsProvider {
		public StatueItemTags(DataGenerator generator, BlockTagsProvider tagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, tagsProvider, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.copy(StatueTags.STATUE_BLOCKS, StatueTags.STATUES_ITEMS);
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
				this.add(PEBBLE.get(), (block) -> createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(block)))));
				this.dropSelf(DISPLAY_STAND.get());
				this.dropSelf(SOMBRERO.get());
				this.dropSelf(INFO_STATUE.get());
				this.add(PLAYER_STATUE.get(), createNameableBlockEntityTable(PLAYER_STATUE.get()));
				for(RegistryObject<Block> blockObject : BLOCKS.getEntries()) {
					if(blockObject.get() instanceof AbstractStatueBase) {
						AbstractStatueBase base = (AbstractStatueBase)blockObject.get();
						if(base.canBeUpgraded()) {
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
				this.add(PLAYER_STATUE_ENTITY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(STATUE_CORE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))).when(LootItemKilledByPlayerCondition.killedByPlayer())));
				this.add(STATUE_BAT.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(STATUE_CORE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))).when(LootItemKilledByPlayerCondition.killedByPlayer())));
			}

			@Override
			protected boolean isNonLiving(EntityType<?> entitytype) {
				return false;
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				return (Iterable<EntityType<?>>) StatueRegistry.ENTITIES.getEntries().stream().map(RegistryObject::get)::iterator;
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
}
