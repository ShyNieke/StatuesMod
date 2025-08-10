package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StatueBiomeTagProvider extends BiomeTagsProvider {

	public StatueBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Reference.MOD_ID);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider) {
		List<ResourceKey<Biome>> list = MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD.usedBiomes().toList();
		list = list.stream().filter(resourceKey ->
				!resourceKey.equals(Biomes.DEEP_DARK) && !resourceKey.equals(Biomes.MUSHROOM_FIELDS)
		).toList();
		this.tag(StatueTags.CAN_SPAWN_STATUE_BAT).addAll(list);
		this.tag(StatueTags.CAN_SPAWN_FEWER_STATUE_BAT).addAll(list);
	}
}