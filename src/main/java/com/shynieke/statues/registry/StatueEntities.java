package com.shynieke.statues.registry;

import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class StatueEntities {
	public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(StatueRegistry.STATUE_BAT.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				StatueBatEntity::canSpawnHere, RegisterSpawnPlacementsEvent.Operation.AND);
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(StatueRegistry.PLAYER_STATUE_ENTITY.get(), PlayerStatue.createLivingAttributes().build());
		event.put(StatueRegistry.STATUE_BAT.get(), StatueBatEntity.createAttributes().build());
	}
}
