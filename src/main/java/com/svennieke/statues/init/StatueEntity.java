package com.svennieke.statues.init;

import com.svennieke.statues.Statues;
import com.svennieke.statues.entity.EntityStatueBat;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class StatueEntity {
	
	public static void register() {
		EntityRegistry.registerModEntity(EntityStatueBat.class, "StatueBat", 0, Statues.instance, 80, 3, true);
	}
}
