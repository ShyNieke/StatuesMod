package com.shynieke.statues.registry;

import com.google.common.collect.ImmutableMap;
import com.shynieke.statues.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StatuePatterns {
	public static final DeferredRegister<String> POT_PATTERNS = DeferredRegister.create(Registries.DECORATED_POT_PATTERNS, Reference.MOD_ID);

	public static final DeferredHolder<String, String> STATUE_CORE = POT_PATTERNS.register("statue_core_pottery_pattern", () -> Reference.MOD_ID + ":statue_core_pottery_pattern");

	public static void expandVanillaDefinitions() {
		ImmutableMap.Builder<Item, ResourceKey<String>> itemsToPot = new ImmutableMap.Builder<>();
		itemsToPot.putAll(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
		itemsToPot.put(StatueRegistry.STATUE_CORE_POTTERY_SHERD.get(), STATUE_CORE.getKey());
		DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = itemsToPot.build();
	}
}
