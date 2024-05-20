package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StatueBiomeTagProvider extends BiomeTagsProvider {

	public StatueBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		TagsProvider.TagAppender<Biome> tagAppender = this.tag(StatueTags.CAN_SPAWN_STATUE_BAT);
		try {
			MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD.usedBiomes().forEach((resourceKey) -> {
				if (!resourceKey.equals(Biomes.DEEP_DARK) && !resourceKey.equals(Biomes.MUSHROOM_FIELDS))
					tagAppender.add(resourceKey);
			});
			this.tag(StatueTags.CAN_SPAWN_FEWER_STATUE_BAT).add(Biomes.BASALT_DELTAS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}