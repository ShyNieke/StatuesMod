package com.shynieke.statues.world.biome_modifier;

import com.mojang.serialization.Codec;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueBiomeModifierSerializers;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public class StatueBatBiomeModifier implements BiomeModifier {

	private static final StatueBatBiomeModifier INSTANCE = new StatueBatBiomeModifier();

	public StatueBatBiomeModifier() {
	}

	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD) {
			if (StatuesConfig.COMMON.statueBatSpawning.get()) {
				MobSpawnSettingsBuilder mobSpawnSettings = builder.getMobSpawnSettings();
				if (!biome.is(BiomeTags.IS_END) && !biome.is(Biomes.DEEP_DARK)) {
					if (biome.is(BiomeTags.IS_NETHER)) {
						if (biome.is(Biomes.BASALT_DELTAS)) {
							mobSpawnSettings.getSpawner(MobCategory.AMBIENT).add(new SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1));
						}
					} else {
						if (!biome.is(Tags.Biomes.IS_MUSHROOM)) {
							mobSpawnSettings.getSpawner(MobCategory.AMBIENT).add(new SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));
						}
					}
				}
			}
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return StatueBiomeModifierSerializers.STATUE_BAT_SERIALIZER.get();
	}


	public static Codec<StatueBatBiomeModifier> makeCodec() {
		return Codec.unit(INSTANCE);
	}
}
