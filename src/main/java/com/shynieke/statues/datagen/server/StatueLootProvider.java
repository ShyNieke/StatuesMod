package com.shynieke.statues.datagen.server;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class StatueLootProvider extends LootTableProvider {
	public StatueLootProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(
				new SubProviderEntry(StatueBlocks::new, LootContextParamSets.BLOCK),
				new SubProviderEntry(StatueEntities::new, LootContextParamSets.ENTITY)
		), lookupProvider);
	}

	@Override
	protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
		super.validate(writableregistry, validationcontext, problemreporter$collector);
	}

	private static class StatueBlocks extends BlockLootSubProvider {

		protected StatueBlocks(HolderLookup.Provider registries) {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
		}

		@Override
		protected void generate() {
			HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

			this.add(StatueRegistry.PEBBLE.get(), (block) -> createSilkTouchDispatchTable(block,
					applyExplosionCondition(block, LootItem.lootTableItem(Items.FLINT)
							.when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.1F, 0.14285715F, 0.25F, 1.0F))
							.otherwise(LootItem.lootTableItem(block)))));
			this.dropSelf(StatueRegistry.DISPLAY_STAND.get());
			this.dropSelf(StatueRegistry.SOMBRERO.get());
			this.dropSelf(StatueRegistry.BUMBO_STATUE.get());
			this.dropSelf(StatueRegistry.EAGLE_RAY.get());
			this.dropSelf(StatueRegistry.SLABFISH.get());
			this.dropSelf(StatueRegistry.TROPIBEE.get());
			this.dropSelf(StatueRegistry.AZZARO.get());
			this.dropSelf(StatueRegistry.TOTEM_OF_UNDYING_STATUE.get());
			this.dropSelf(StatueRegistry.INFO_STATUE.get());
			this.dropSelf(StatueRegistry.STATUE_TABLE.get());
			this.dropSelf(StatueRegistry.CORE_FLOWER.get());
			this.add(
					StatueRegistry.CORE_FLOWER_CROP.get(),
					this.applyExplosionDecay(
							StatueRegistry.CORE_FLOWER_CROP, LootTable.lootTable().withPool(LootPool.lootPool()
									.add(LootItem.lootTableItem(StatueRegistry.CORE_FLOWER_SEED.get())))
					)
			);
			this.add(
					StatueRegistry.PLAYER_STATUE.get(),
					block -> LootTable.lootTable()
							.withPool(
									this.applyExplosionCondition(
											block,
											LootPool.lootPool()
													.setRolls(ConstantValue.exactly(1.0F))
													.add(
															LootItem.lootTableItem(block)
																	.apply(
																			CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
																					.include(DataComponents.PROFILE)
																					.include(DataComponents.CUSTOM_NAME)
																	)
													)
									)
							)
			);
			for (DeferredHolder<Block, ? extends Block> blockObject : StatueRegistry.BLOCKS.getEntries()) {
				if (blockObject.get() instanceof AbstractStatueBase) {
					this.dropSelf(blockObject.get());
				}
			}
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return (Iterable<Block>) StatueRegistry.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get())::iterator;
		}
	}

	private static class StatueEntities extends EntityLootSubProvider {
		protected StatueEntities(HolderLookup.Provider registries) {
			super(FeatureFlags.REGISTRY.allFlags(), registries);
		}

		@Override
		public void generate() {
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
		protected Stream<EntityType<?>> getKnownEntityTypes() {
			return StatueRegistry.ENTITIES.getEntries().stream().map(holder -> holder.get());
		}
	}
}
