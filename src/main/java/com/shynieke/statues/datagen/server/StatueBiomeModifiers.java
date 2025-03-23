package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class StatueBiomeModifiers {

	protected static final ResourceKey<BiomeModifier> ADD_SPAWN_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			Reference.modLoc("add_statue_bat_spawn"));

	protected static final ResourceKey<BiomeModifier> ADD_FEWER_SPAWN_MODIFIER = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
			Reference.modLoc("add_fewer_statue_bat_spawn"));

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);

		context.register(ADD_SPAWN_MODIFIER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				biomeGetter.getOrThrow(StatueTags.CAN_SPAWN_STATUE_BAT),
				new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2)));

		context.register(ADD_FEWER_SPAWN_MODIFIER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				biomeGetter.getOrThrow(StatueTags.CAN_SPAWN_FEWER_STATUE_BAT),
				new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1)));
	}
}
