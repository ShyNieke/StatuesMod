package com.shynieke.statues;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class Reference {
	public static final String MOD_ID = "statues";
	public static final String MOD_PREFIX = MOD_ID + ":";

	public static final String KILL_COUNT = "statueMobKilled";
	public static final String LEVEL = "statueLevel";
	public static final String UPGRADED = "statueUpgraded";
	public static final String UPGRADE_SLOTS = "upgradeSlots";
	public static final GameProfile GAME_PROFILE = new GameProfile(UUID.nameUUIDFromBytes("fakeplayer.statue".getBytes()), Component.translatable("fakeplayer.statue").getString());

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
