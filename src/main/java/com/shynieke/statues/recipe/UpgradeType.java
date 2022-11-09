package com.shynieke.statues.recipe;

import com.shynieke.statues.Reference;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.util.UpgradeHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;
import java.util.Map;

public enum UpgradeType {
	CRAFTING(false),
	UPGRADE(false),
	GLOWING(15), //Glow Ink
	UNGLOWING(14), //Ink Sac
	SPAWNER(10),
	DESPAWNER,
	MOB_KILLER(3), //0 = Regular drops,1 = Player drops, 2 = XP too
	LOOTING(5), //Drops the Loot Recipes (every upgrade increases chance of every drop by 10%
	AUTOMATION,
	SPEED(10),
	INTERACTION, //The special interactions for certain statues (like Mooshroom, and Etho)
	SOUND;

	private final boolean upgraded;
	private final int cap;

	UpgradeType(boolean upgraded, int cap) {
		this.upgraded = upgraded;
		this.cap = cap;
	}

	UpgradeType(boolean upgraded) {
		this(upgraded, 1);
	}

	UpgradeType(int cap) {
		this(true, cap);
	}

	UpgradeType() {
		this(true, 1);
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
					CompoundTag compoundtag = BlockItem.getBlockEntityData(stack);
					if (compoundtag == null && (stack.getTag() == null || !stack.getTag().getBoolean(Reference.UPGRADED))) {
						//Not upgraded
						return false;
					}

					if (compoundtag != null) {
						int upgradeSlots = compoundtag.getInt(Reference.UPGRADE_SLOTS);
						if (!(upgradeSlots > 0)) {
							//No upgrade slots
							return false;
						}
						String id = this.name().toLowerCase(Locale.ROOT);
						Map<String, Short> upgradeMap = UpgradeHelper.loadUpgradeMap(compoundtag);
						short currentLevel = upgradeMap.getOrDefault(id, (short) 0);
						if ((currentLevel + 1) < cap) {
							if (level != -1) {
								if (currentLevel == level) {
									UpgradeHelper.upgrade(upgradeMap, id);
									compoundtag.putInt(Reference.UPGRADE_SLOTS, upgradeSlots - 1);
								} else {
									//Level doesn't match
									return false;
								}
							} else {
								UpgradeHelper.upgrade(upgradeMap, id);
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
