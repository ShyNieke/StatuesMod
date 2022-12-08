package com.shynieke.statues.datagen.server;

import com.shynieke.statues.Reference;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class StatueBiomeTagProvider extends BiomeTagsProvider {

	public StatueBiomeTagProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
		super(generator, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		TagsProvider.TagAppender<Biome> tagappender1 = this.tag(StatueTags.CAN_SPAWN_STATUE_BAT);
		MultiNoiseBiomeSource.Preset.OVERWORLD.possibleBiomes().forEach((resourceKey) -> {
			if (!resourceKey.equals(Biomes.DEEP_DARK) && !resourceKey.equals(Biomes.MUSHROOM_FIELDS))
				tagappender1.add(resourceKey);
		});
		this.tag(StatueTags.CAN_SPAWN_FEWER_STATUE_BAT).add(Biomes.BASALT_DELTAS);
	}
}