package com.shynieke.statues.recipe;

import com.shynieke.statues.Reference;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.util.UpgradeHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;
import java.util.Map;

public enum UpgradeType implements StringRepresentable {
	CRAFTING("crafting", false, false, 1), //Unused but packs can add crafting recipes using the S.T.A.T.U.E
	UPGRADE("upgrade", false, false, 1), //Upgrades a statue to one that can level (Uses a statue core)
	GLOWING("glowing", true, false, 15), //Makes the statue emit light
	UNGLOWING("unglowing", false, false, 1), //Reduces the light emitted
	SPAWNER("spawner", true, true, 10), //Spawns the mob
	DESPAWNER("despawner", true, true, 1), //Stops the mob from spawning nearby
	MOB_KILLER("mob_killer", true, true, 3), //0 = Regular drops,1 = Player drops, 2 = XP too
	LOOTING("looting", true, true, 5), //Allows dropping of Loot (every upgrade increases chance of every drop by 10%)
	AUTOMATION("automation", true, true, 1), //Allows exporting of the loot
	SPEED("speed", true, true, 10), //Speed up the interactions / mob spawn timer
	INTERACTION("interaction", true, true, 1), //The special interactions for certain statues (like Mooshroom, and Etho)
	SOUND("sound", true, true, 1); //Plays the mobs sound when redstone is applied or interacted


	public static final StringRepresentable.EnumCodec<UpgradeType> CODEC = StringRepresentable.fromEnum(UpgradeType::values);
	private final String name;
	private final boolean upgraded, subsequentUsesSlot;
	private final int cap;

	UpgradeType(String name, boolean upgraded, boolean subsequentUsesSlot, int cap) {
		this.name = name;
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
						if (isSubsequentUsesSlot() && !(upgradeSlots > 0)) {
							//No upgrade slots
							return false;
						}
						String ID = this.name().toLowerCase(Locale.ROOT);
						String glowingID = GLOWING.name().toLowerCase(Locale.ROOT);
						Map<String, Short> upgradeMap = UpgradeHelper.loadUpgradeMap(compoundtag);

						short currentLevel = upgradeMap.getOrDefault(ID, (short) 0);
						if ((currentLevel + 1) <= getCap()) {
							if (level != -1) {
								if (currentLevel == level) {
									if (this == GLOWING) {
										UpgradeHelper.upgrade(upgradeMap, glowingID);
									} else {
										UpgradeHelper.upgrade(upgradeMap, ID);
									}
									if (isSubsequentUsesSlot())
										compoundtag.putInt(Reference.UPGRADE_SLOTS, upgradeSlots - 1);
								} else {
									//Level doesn't match
									return false;
								}
							} else {
								if (this == GLOWING) {
									UpgradeHelper.upgrade(upgradeMap, glowingID);
								} else {
									UpgradeHelper.upgrade(upgradeMap, ID);
								}
								if (isSubsequentUsesSlot())
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
					if (this == UNGLOWING) {
						CompoundTag compoundtag = stack.getTagElement("BlockEntityTag");
						if (compoundtag == null && stack.getTag() == null) {
							//Not upgraded
							return false;
						}

						if (compoundtag != null) {
							int upgradeSlots = compoundtag.getInt(Reference.UPGRADE_SLOTS);
							if (isSubsequentUsesSlot() && !(upgradeSlots > 0)) {
								//No upgrade slots
								return false;
							}

							String glowingID = GLOWING.name().toLowerCase(Locale.ROOT);
							Map<String, Short> upgradeMap = UpgradeHelper.loadUpgradeMap(compoundtag);
							short glowLevel = upgradeMap.getOrDefault(glowingID, (short) 0);

							if (glowLevel > 0) {
								UpgradeHelper.downgrade(upgradeMap, glowingID);

								if (upgradeMap.getOrDefault(glowingID, (short) 0) == 0)
									upgradeMap.remove(glowingID);

								UpgradeHelper.saveUpgradeMap(compoundtag, upgradeMap);
							} else {
								//Can't downgrade a stack that isn't glowing
								return false;
							}
						}
					} else {
						CompoundTag compoundtag = stack.getOrCreateTag();
						compoundtag.putBoolean(Reference.UPGRADED, true);
						compoundtag.putInt(Reference.UPGRADED, 1);
					}

					return true;
				}
			}
		}

		return false;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
