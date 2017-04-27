package com.svennieke.statues.init;

import com.svennieke.statues.Reference;

import net.minecraft.util.ResourceLocation;

public final class StatuesEntityName {
	public static final String STATUE_BAT = Reference.MOD_PREFIX + "StatueBat";
	
	public static final ResourceLocation STATUE_BAT_REGISTRY = Name("statue_bat");
	
	private static ResourceLocation Name(String s) {
		return new ResourceLocation(Reference.MOD_ID, s);
	}
}
