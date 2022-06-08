package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.entity.StatueBatEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
}
