package com.shynieke.statues.datagen.server;

import com.shynieke.statues.registry.StatueJukeboxSongs;
import com.shynieke.statues.registry.StatueTrims;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class StatueDatapackProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, $ -> {})
			.add(Registries.PLACED_FEATURE, $ -> {})
			.add(Registries.TRIM_PATTERN, StatueTrims::bootstrap)
			.add(Registries.JUKEBOX_SONG, StatueJukeboxSongs::bootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, StatueBiomeModifiers::bootstrap);

	public StatueDatapackProvider(PackOutput output, CompletableFuture<Provider> registries, Set<String> modIds) {
		super(output, registries, BUILDER, modIds);
	}
}
