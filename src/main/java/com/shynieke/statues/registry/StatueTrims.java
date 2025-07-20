package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.trim.TrimPattern;

public class StatueTrims {
	public static final ResourceKey<TrimPattern> CORE = registryKey("core");

	private static ResourceKey<TrimPattern> registryKey(String name) {
		return ResourceKey.create(Registries.TRIM_PATTERN, Reference.modLoc(name));
	}

	public static void bootstrap(BootstrapContext<TrimPattern> context) {
		register(context, CORE);
	}

	public static void register(BootstrapContext<TrimPattern> context, ResourceKey<TrimPattern> key) {
		TrimPattern trimpattern = new TrimPattern(
				defaultAssetId(key), Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false
		);
		context.register(key, trimpattern);
	}

	public static ResourceLocation defaultAssetId(ResourceKey<TrimPattern> key) {
		return key.location();
	}
}
