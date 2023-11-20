package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.lootmodifiers.StatuesLootModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class StatueGLMProvider extends GlobalLootModifierProvider {
	public StatueGLMProvider(PackOutput packOutput) {
		super(packOutput, Reference.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("statues_loot", new StatuesLootModifier(
				new LootItemCondition[]{
						LootTableIdCondition.builder(BuiltInLootTables.ANCIENT_CITY).build()
				}));
	}
}
