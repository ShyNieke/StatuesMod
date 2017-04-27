package com.svennieke.statues.init;

import com.svennieke.statues.Statues;
import com.svennieke.statues.entity.EntityStatueBat;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class StatuesEntity {
	
	public static void register() {		
		EntityRegistry.registerModEntity(StatuesEntityName.STATUE_BAT_REGISTRY, EntityStatueBat.class, StatuesEntityName.STATUE_BAT, 0, Statues.instance, 80, 3, true, 3421236, 3556687);
		System.out.println("Registered Statues Bat");
		//EntityRegistry.addSpawn(EntityStatueBat.class, 5, 1, 2, EnumCreatureType.AMBIENT, Biomes.BEACH);
		//System.out.println("Registered Statues Bat Spawn");
	}
}
