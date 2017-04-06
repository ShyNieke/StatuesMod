package com.svennieke.statues.init;

import com.svennieke.statues.Statues;
import com.svennieke.statues.entity.EntityStatueBat;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class StatueEntity {
	
	public static void register() {
		EntityRegistry.registerModEntity(EntityStatueBat.class, "StatueBat", 0, Statues.instance, 80, 3, true);
		EntityRegistry.addSpawn(EntityStatueBat.class, 1, 1, 2, EnumCreatureType.AMBIENT);
		EntityRegistry.registerEgg(EntityStatueBat.class, 3421236, 3556687);
	}
}
