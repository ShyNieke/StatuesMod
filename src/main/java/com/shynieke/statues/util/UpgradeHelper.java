package com.shynieke.statues.util;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UpgradeHelper {

	public static void saveUpgradeMap(@NotNull CompoundTag tag, @NotNull Map<String, Short> upgradeMap) {
		ListTag upgradeList = new ListTag();
		for (Map.Entry<String, Short> entry : upgradeMap.entrySet()) {
			CompoundTag compoundtag = new CompoundTag();
			compoundtag.putString("id", entry.getKey());
			compoundtag.putShort("lvl", entry.getValue());
			upgradeList.add(compoundtag);
		}

		tag.put("Upgrades", upgradeList);
	}

	public static Map<String, Short> loadUpgradeMap(@NotNull CompoundTag tag) {
		Map<String, Short> upgradeMap = new HashMap<>();
		ListTag upgradeList = tag.contains("Upgrades") ? tag.getList("Upgrades", 10) : new ListTag();
		for (int i = 0; i < upgradeList.size(); i++) {
			CompoundTag compoundtag = upgradeList.getCompound(i);
			String id = compoundtag.getString("id");
			Short lvl = compoundtag.getShort("lvl");
			upgradeMap.put(id, lvl);
		}

		return upgradeMap;
	}

	public static void upgrade(Map<String, Short> upgradeMap, String id) {
		short level = upgradeMap.getOrDefault(id, (short) 0);
		upgradeMap.put(id, (short)(level + 1));
	}

	public static Component getUpgradeName(String id, int level) {
		String descriptionID = "statues.upgrade." + id + ".name";
		MutableComponent mutablecomponent = Component.translatable(descriptionID).withStyle(ChatFormatting.GRAY);

		if (level > 0) {
			mutablecomponent.append(" ").append(Component.translatable("statues.upgrade.level." + level));
		}

		return mutablecomponent;
	}
}
