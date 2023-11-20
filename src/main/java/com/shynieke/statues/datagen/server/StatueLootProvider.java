package com.shynieke.statues.datagen.server;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class StatueLootProvider extends LootTableProvider {
	public StatueLootProvider(PackOutput packOutput) {
		super(packOutput, Set.of(), List.of(new SubProviderEntry(StatueBlocks::new, LootContextParamSets.BLOCK),
				new SubProviderEntry(StatueEntities::new, LootContextParamSets.ENTITY)));
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
		map.forEach((name, table) -> table.validate(validationtracker));
	}

	private static class StatueBlocks extends BlockLootSubProvider {

		protected StatueBlocks() {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		protected void generate() {
			this.add(StatueRegistry.PEBBLE.get(), (block) -> createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(block)))));
			this.dropSelf(StatueRegistry.DISPLAY_STAND.get());
			this.dropSelf(StatueRegistry.SOMBRERO.get());
			this.dropSelf(StatueRegistry.INFO_STATUE.get());
			this.dropSelf(StatueRegistry.STATUE_TABLE.get());
			this.add(StatueRegistry.PLAYER_STATUE.get(), createNameableBlockEntityTable(StatueRegistry.PLAYER_STATUE.get()));
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
		protected StatueEntities() {
			super(FeatureFlags.REGISTRY.allFlags());
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
		protected boolean canHaveLootTable(EntityType<?> entitytype) {
			return true;
		}

		@Override
		protected Stream<EntityType<?>> getKnownEntityTypes() {
			return StatueRegistry.ENTITIES.getEntries().stream().map(holder -> holder.get());
		}
	}
}
