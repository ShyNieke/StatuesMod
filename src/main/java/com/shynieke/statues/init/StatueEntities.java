package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class StatueEntities {
	public static void setupEntities() {
		SpawnPlacements.register(StatueRegistry.STATUE_BAT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StatueBatEntity::canSpawnHere);
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(StatueRegistry.PLAYER_STATUE_ENTITY.get(), PlayerStatue.createLivingAttributes().build());
		event.put(StatueRegistry.STATUE_BAT.get(), StatueBatEntity.createAttributes().build());
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void addSpawn(BiomeLoadingEvent event) {
		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
		if (StatuesConfig.COMMON.statueBatSpawning.get()) {
			if (!BiomeDictionary.hasType(biomeKey, Type.END) && BiomeDictionary.hasType(biomeKey, Type.OVERWORLD)) {
				if (event.getCategory() != BiomeCategory.MUSHROOM && event.getCategory() != BiomeCategory.NONE) {
					event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));
				}
			} else if (BiomeDictionary.hasType(biomeKey, Type.NETHER) && biomeKey == Biomes.BASALT_DELTAS) {
				event.getSpawns().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(StatueRegistry.STATUE_BAT.get(), 1, 1, 1));
			}
		}
	}
}
