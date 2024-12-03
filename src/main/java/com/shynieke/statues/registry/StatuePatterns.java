package com.shynieke.statues.registry;

import com.google.common.collect.ImmutableMap;
import com.shynieke.statues.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StatuePatterns {
	public static final DeferredRegister<DecoratedPotPattern> POT_PATTERNS = DeferredRegister.create(Registries.DECORATED_POT_PATTERN, Reference.MOD_ID);

	public static final ResourceKey<DecoratedPotPattern> STATUE_CORE = create("statue_core_pottery_pattern");

	private static ResourceKey<DecoratedPotPattern> create(String path) {
		return ResourceKey.create(Registries.DECORATED_POT_PATTERN, Reference.modLoc(path));
	}

	public static void expandVanillaDefinitions() {
		ImmutableMap.Builder<Item, ResourceKey<DecoratedPotPattern>> itemsToPot = new ImmutableMap.Builder<>();
		itemsToPot.putAll(DecoratedPotPatterns.ITEM_TO_POT_TEXTURE);
		itemsToPot.put(StatueRegistry.STATUE_CORE_POTTERY_SHERD.get(), STATUE_CORE);
		DecoratedPotPatterns.ITEM_TO_POT_TEXTURE = itemsToPot.build();
	}
}
