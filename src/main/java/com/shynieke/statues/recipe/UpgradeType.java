package com.shynieke.statues.recipe;

import com.shynieke.statues.Reference;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.util.UpgradeHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;
import java.util.Map;

public enum UpgradeType {
	CRAFTING(false, false, 1), //Unused but packs can add crafting recipes using the S.T.A.T.U.E
	UPGRADE(false, false, 1), //Upgrades a statue to one that can level (Uses a statue core)
	GLOWING(true, false, 15), //Glow Ink
	UNGLOWING(false, false, 14), //Ink Sac
	SPAWNER(true, true, 10), //Spawns the mob
	DESPAWNER(true, true, 1), //Stops the mob from spawning nearby
	MOB_KILLER(true, true, 3), //0 = Regular drops,1 = Player drops, 2 = XP too
	LOOTING(true, true, 5), //Drops the Loot Recipes (every upgrade increases chance of every drop by 10%
	AUTOMATION(true, true, 1), //Allows exporting of the loot (Currently not implemented)
	SPEED(true, true, 10), //Speed up the interactions / mob spawn timer (Not implemented)
	INTERACTION(true, true, 1), //The special interactions for certain statues (like Mooshroom, and Etho)
	SOUND(true, true, 1); //Plays the mobs sound when redstone is applied or interacted

	private final boolean upgraded, subsequentUsesSlot;
	private final int cap;

	UpgradeType(boolean upgraded, boolean subsequentUsesSlot, int cap) {
		this.upgraded = upgraded;
		this.subsequentUsesSlot = subsequentUsesSlot;
		this.cap = cap;
	}

	public boolean isSubsequentUsesSlot() {
		return subsequentUsesSlot;
	}

	public boolean requiresUpgrade() {
		return upgraded;
	}

	public int getCap() {
		return cap;
	}

	public boolean apply(ItemStack stack, int level) {
		if (this == CRAFTING) {
			//Replace center stack
			return true;
		} else if (this == UPGRADE) {
			if (stack.getTagElement("BlockEntityTag") == null) {
				CompoundTag entityTag = new CompoundTag();
				entityTag.putInt(Reference.LEVEL, 0);
				entityTag.putInt(Reference.KILL_COUNT, 0);
				entityTag.putInt(Reference.UPGRADE_SLOTS, 0);
				entityTag.putBoolean(Reference.UPGRADED, true);

				stack.addTagElement("BlockEntityTag", entityTag);
				return true;
			} else {
				//Already upgraded
				return false;
			}
		} else {
			if (stack.getItem() instanceof StatueBlockItem) {
				if (requiresUpgrade()) {
					CompoundTag compoundtag = stack.getTagElement("BlockEntityTag");
					if (compoundtag == null && (stack.getTag() == null || !stack.getTag().getBoolean(Reference.UPGRADED))) {
						//Not upgraded
						return false;
					}

					if (compoundtag != null) {
						int upgradeSlots = compoundtag.getInt(Reference.UPGRADE_SLOTS);
						if (subsequentUsesSlot && !(upgradeSlots > 0)) {
							//No upgrade slots
							return false;
						}
						String ID = this.name().toLowerCase(Locale.ROOT);
						String glowingID = GLOWING.name().toLowerCase(Locale.ROOT);
						String unglowingID = UNGLOWING.name().toLowerCase(Locale.ROOT);
						Map<String, Short> upgradeMap = UpgradeHelper.loadUpgradeMap(compoundtag);

						if (this == UNGLOWING) {
							if (upgradeMap.getOrDefault(glowingID, (short) 0) == 0)
								return false;
						}

						short currentLevel = upgradeMap.getOrDefault(ID, (short) 0);
						if ((currentLevel + 1) <= cap) {
							if (level != -1) {
								if (currentLevel == level) {
									if (this == UNGLOWING) {
										if (upgradeMap.getOrDefault(glowingID, (short) 0) > 0) {
											UpgradeHelper.downgrade(upgradeMap, glowingID);
										}
									} else if (this == GLOWING) {
										if (upgradeMap.getOrDefault(unglowingID, (short) 0) > 0) {
											UpgradeHelper.downgrade(upgradeMap, unglowingID);
										} else {
											UpgradeHelper.upgrade(upgradeMap, glowingID);
										}
									} else {
										UpgradeHelper.upgrade(upgradeMap, ID);
									}
									if (subsequentUsesSlot)
										compoundtag.putInt(Reference.UPGRADE_SLOTS, upgradeSlots - 1);
								} else {
									//Level doesn't match
									return false;
								}
							} else {
								if (this == UNGLOWING) {
									if (upgradeMap.getOrDefault(glowingID, (short) 0) > 0) {
										UpgradeHelper.downgrade(upgradeMap, glowingID);
									}
								} else if (this == GLOWING) {
									if (upgradeMap.getOrDefault(unglowingID, (short) 0) > 0) {
										UpgradeHelper.downgrade(upgradeMap, unglowingID);
									} else {
										UpgradeHelper.upgrade(upgradeMap, glowingID);
									}
								} else {
									UpgradeHelper.upgrade(upgradeMap, ID);
								}
								if (subsequentUsesSlot)
									compoundtag.putInt(Reference.UPGRADE_SLOTS, upgradeSlots - 1);
							}
						} else {
							//Next update would be over the cap
							return false;
						}
						UpgradeHelper.saveUpgradeMap(compoundtag, upgradeMap);
						stack.addTagElement("BlockEntityTag", compoundtag);
						return true;
					}
				} else {
					CompoundTag compoundtag = stack.getOrCreateTag();
					compoundtag.putBoolean(Reference.UPGRADED, true);
					compoundtag.putInt(Reference.UPGRADED, 1);
					return true;
				}
			}
		}

		return false;
	}
}
