package com.svennieke.statues.init;

import net.minecraftforge.common.config.Configuration;

public class StatuesConfigGen {
	public static void configOptions(Configuration config) {
		config.load();
		// Drop chance
		StatuesConfig.DropChance = config.get("Statues Drop Chance", "DropChance", 0.01).getDouble(0.01);
		
		config.save();
		config.load();
}
}
