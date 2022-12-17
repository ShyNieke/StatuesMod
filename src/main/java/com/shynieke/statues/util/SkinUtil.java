package com.shynieke.statues.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class SkinUtil {
	public static boolean isSlimSkin(UUID playerUUID) {
		return (playerUUID.hashCode() & 1) == 1;
	}

	public static boolean isStatueNamed(ItemStack stack) {
		CompoundTag compoundtag = stack.getTagElement("display");
		return compoundtag != null && compoundtag.contains("Name", 8);
	}
}
