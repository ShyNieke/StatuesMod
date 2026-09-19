package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.lootmodifiers.CityStatuesLootModifier;
import com.shynieke.statues.lootmodifiers.SherdLootModifier;
import com.shynieke.statues.lootmodifiers.SnifferLootModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class StatueGLMProvider extends GlobalLootModifierProvider {
	public StatueGLMProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, Reference.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("statues_loot", new CityStatuesLootModifier(
				Optional.of(Holder.direct(
						LootTableIdCondition.builder(BuiltInLootTables.ANCIENT_CITY.identifier()).build()
				)), 1000));
		this.add("statues_sherd", new SherdLootModifier(
				Optional.of(Holder.direct(
						LootTableIdCondition.builder(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.identifier()).build()
				)), 1000));
		this.add("statues_core_flower", new SnifferLootModifier(
				Optional.of(Holder.direct(
						LootTableIdCondition.builder(BuiltInLootTables.SNIFFER_DIGGING.identifier()).build()
				)), 1000));
	}
}
