package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.lootmodifiers.StatuesLootModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class StatueGLMProvider extends GlobalLootModifierProvider {
	public StatueGLMProvider(DataGenerator generator) {
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
