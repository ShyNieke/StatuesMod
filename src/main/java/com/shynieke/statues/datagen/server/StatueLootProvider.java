package com.shynieke.statues.datagen.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StatueLootProvider extends LootTableProvider {
	public StatueLootProvider(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
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
			this.dropSelf(StatueRegistry.STATUE_TABLE.get());
			this.add(StatueRegistry.PLAYER_STATUE.get(), createNameableBlockEntityTable(StatueRegistry.PLAYER_STATUE.get()));
			for (RegistryObject<Block> blockObject : StatueRegistry.BLOCKS.getEntries()) {
				if (blockObject.get() instanceof AbstractStatueBase) {
					this.dropSelf(blockObject.get());
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
