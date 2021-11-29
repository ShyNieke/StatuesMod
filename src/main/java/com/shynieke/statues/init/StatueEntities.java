package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.entity.PlayerStatueEntity;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
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
        EntitySpawnPlacementRegistry.register(StatueRegistry.STATUE_BAT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StatueBatEntity::canSpawnHere);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(StatueRegistry.PLAYER_STATUE_ENTITY.get(), PlayerStatueEntity.createLivingAttributes().build());
        event.put(StatueRegistry.STATUE_BAT.get(), StatueBatEntity.registerAttributes().build());
    }

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        if(StatuesConfig.COMMON.statueBatSpawning.get() && !BiomeDictionary.hasType(biomeKey, Type.END)) {
            if(event.getCategory() == Category.NETHER) {
                if(event.getName().getPath().equals("basalt_deltas")) {
                    event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(StatueRegistry.STATUE_BAT.get(), 1, 1, 1));
                }
            } else {
                if(event.getCategory() != Category.MUSHROOM && event.getCategory() != Category.NONE) {
                    event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));
                }
            }
        }
    }
}
