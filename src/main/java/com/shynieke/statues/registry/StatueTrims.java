package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

public class StatueTrims {
	public static final ResourceKey<TrimPattern> CORE = registerKey("core");

	private static ResourceKey<TrimPattern> registerKey(String name) {
		return ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation(Reference.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<TrimPattern> context) {
		register(context, StatueRegistry.CORE_ARMOR_TRIM_SMITHING_TEMPLATE.asItem(), CORE);
	}

	private static void register(BootstapContext<TrimPattern> pContext, Item pTemplateItem, ResourceKey<TrimPattern> pTrimPatternKey) {
		TrimPattern trimpattern = new TrimPattern(pTrimPatternKey.location(), BuiltInRegistries.ITEM.wrapAsHolder(pTemplateItem), Component.translatable(Util.makeDescriptionId("trim_pattern", pTrimPatternKey.location())), false);
		pContext.register(pTrimPatternKey, trimpattern);
	}
}
