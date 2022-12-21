package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class StatueBiomeModifiers {

	protected static final ResourceKey<BiomeModifier> ADD_SPAWN_MODIFIER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "add_statue_bat_spawn"));

	protected static final ResourceKey<BiomeModifier> ADD_FEWER_SPAWN_MODIFIER = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(Reference.MOD_ID, "add_fewer_statue_bat_spawn"));

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);

		context.register(ADD_SPAWN_MODIFIER, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				biomeGetter.getOrThrow(StatueTags.CAN_SPAWN_STATUE_BAT),
				new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2)));

		context.register(ADD_FEWER_SPAWN_MODIFIER, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				biomeGetter.getOrThrow(StatueTags.CAN_SPAWN_FEWER_STATUE_BAT),
				new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1)));
	}
}
