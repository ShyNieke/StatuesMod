package com.shynieke.statues.datagen.server;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class StatueLootProvider {

	public static SingleRegistryBootstrap<LootTable> create() {
		return new LootTableProvider(
				BuiltInLootTables.all(),
				List.of(
						new LootTableProvider.SubProviderEntry(StatueBlocks::new, LootContextParamSets.BLOCK),
						new LootTableProvider.SubProviderEntry(StatueEntities::new, LootContextParamSets.ENTITY)
				)
		);
	}

	private static class StatueBlocks extends BlockLootSubProvider {

		private final HolderGetter<Enchantment> enchantments;

		protected StatueBlocks(LootTableSubProvider.Context context) {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
			this.enchantments = context.lookup(Registries.ENCHANTMENT);
		}

		@Override
		protected void generate() {
			this.add(StatueRegistry.PEBBLE.get(), (block) -> createSilkTouchDispatchTable(block,
					applyExplosionCondition(block, LootItem.lootTableItem(Items.FLINT)
							.when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.1F, 0.14285715F, 0.25F, 1.0F))
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
													.setRolls(ContextIntProviders.exactly(1))
													.add(
															LootItem.lootTableItem(block)
																	.apply(
																			CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
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
		protected StatueEntities(LootTableSubProvider.Context context) {
			super(FeatureFlags.REGISTRY.allFlags(), context);
		}

		@Override
		public void generate() {
			this.add(StatueRegistry.PLAYER_STATUE_ENTITY.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
							.add(LootItem.lootTableItem(StatueRegistry.STATUE_CORE.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 1))))
							.when(LootItemKilledByPlayerCondition.killedByPlayer())));
			this.add(StatueRegistry.STATUE_BAT.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
							.add(LootItem.lootTableItem(StatueRegistry.STATUE_CORE.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
							.when(LootItemKilledByPlayerCondition.killedByPlayer())));
		}

		@Override
		protected Stream<EntityType<?>> getKnownEntityTypes() {
			return StatueRegistry.ENTITIES.getEntries().stream().map(holder -> holder.get());
		}
	}
}
