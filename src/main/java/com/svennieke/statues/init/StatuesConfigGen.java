package com.svennieke.statues.init;

import net.minecraftforge.common.config.Configuration;

public class StatuesConfigGen {
	public static void configOptions(Configuration config) {
		config.load();
		// Mature Sounds
		StatuesConfig.DropChance = config.get("Statues Drop Chance", "DropChance", 0.001).getDouble(0.001);
		
		config.save();
		config.load();
}
}
