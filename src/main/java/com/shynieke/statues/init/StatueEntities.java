package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.entity.PlayerStatueEntity;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class StatueEntities {
    public static void setupEntities() {
        GlobalEntityTypeAttributes.put(StatueRegistry.PLAYER_STATUE_ENTITY.get(), PlayerStatueEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(StatueRegistry.STATUE_BAT.get(), StatueBatEntity.registerAttributes().create());

        EntitySpawnPlacementRegistry.register(StatueRegistry.STATUE_BAT.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StatueBatEntity::canSpawnHere);
    }

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        RegistryKey<Biome> biomeKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        if(StatuesConfig.COMMON.statueBatSpawning.get() && !BiomeDictionary.hasType(biomeKey, Type.END)) {
            event.getSpawns().getSpawner(EntityClassification.AMBIENT).add(new MobSpawnInfo.Spawners(StatueRegistry.STATUE_BAT.get(), 4, 1, 2));
        }
    }
}
