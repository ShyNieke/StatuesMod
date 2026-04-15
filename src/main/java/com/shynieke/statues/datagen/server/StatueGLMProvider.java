package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.lootmodifiers.CityStatuesLootModifier;
import com.shynieke.statues.lootmodifiers.SherdLootModifier;
import com.shynieke.statues.lootmodifiers.SnifferLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class StatueGLMProvider extends GlobalLootModifierProvider {
	public StatueGLMProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, Reference.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("statues_loot", new CityStatuesLootModifier(
				new LootItemCondition[]{
						LootTableIdCondition.builder(BuiltInLootTables.ANCIENT_CITY.identifier()).build()
				}));
		this.add("statues_sherd", new SherdLootModifier(
				new LootItemCondition[]{
						LootTableIdCondition.builder(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.identifier()).build()
				}));
		this.add("statues_core_flower", new SnifferLootModifier(
				new LootItemCondition[]{
						LootTableIdCondition.builder(BuiltInLootTables.SNIFFER_DIGGING.identifier()).build()
				}));
	}
}
