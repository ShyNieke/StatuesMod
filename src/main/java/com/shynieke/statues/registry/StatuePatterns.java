package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public class StatuePatterns {
	public static final ResourceKey<DecoratedPotPattern> STATUE_CORE = create("statue_core_pottery_pattern");

	private static ResourceKey<DecoratedPotPattern> create(String path) {
		return ResourceKey.create(Registries.DECORATED_POT_PATTERN, Reference.modLoc(path));
	}

	public static void bootstrap(BootstrapContext<DecoratedPotPattern> registry) {
		registerWithDefaultAsset(registry, STATUE_CORE);
	}

	private static void registerWithDefaultAsset(BootstrapContext<DecoratedPotPattern> registry, ResourceKey<DecoratedPotPattern> key) {
		registry.register(key, new DecoratedPotPattern(key.identifier().withSuffix("_pottery_pattern")));
	}
}
