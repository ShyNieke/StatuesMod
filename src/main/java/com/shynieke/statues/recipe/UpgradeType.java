package com.shynieke.statues.recipe;

import com.shynieke.statues.datacomponent.StatueStats;
import com.shynieke.statues.datacomponent.StatueUpgrades;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.registry.StatueDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.IntFunction;

public enum UpgradeType implements StringRepresentable {
	CRAFTING(0, "crafting", false, false, 1), //Unused but packs can add crafting recipes using the S.T.A.T.U.E
	UPGRADE(1, "upgrade", false, false, 1), //Upgrades a statue to one that can level (Uses a statue core)
	GLOWING(2, "glowing", true, false, 15), //Makes the statue emit light
	UNGLOWING(3, "unglowing", false, false, 1), //Reduces the light emitted
	SPAWNER(4, "spawner", true, true, 10), //Spawns the mob
	DESPAWNER(5, "despawner", true, true, 1), //Stops the mob from spawning nearby
	MOB_KILLER(6, "mob_killer", true, true, 3), //0 = Regular drops,1 = Player drops, 2 = XP too
	LOOTING(7, "looting", true, true, 5), //Allows dropping of Loot (every upgrade increases chance of every drop by 10%)
	AUTOMATION(8, "automation", true, true, 1), //Allows exporting of the loot
	SPEED(9, "speed", true, true, 10), //Speed up the interactions / mob spawn timer
	INTERACTION(10, "interaction", true, true, 1), //The special interactions for certain statues (like Mooshroom, and Etho)
	SOUND(11, "sound", true, true, 1); //Plays the mobs sound when redstone is applied or interacted

	public static final IntFunction<UpgradeType> BY_ID = ByIdMap.continuous(type -> type.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StringRepresentable.EnumCodec<UpgradeType> CODEC = StringRepresentable.fromEnum(UpgradeType::values);
	public static final StreamCodec<ByteBuf, UpgradeType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, type -> type.id);
	private final int id;
	private final String name;
	private final boolean upgraded, subsequentUsesSlot;
	private final int cap;

	UpgradeType(int id, String name, boolean upgraded, boolean subsequentUsesSlot, int cap) {
		this.id = id;
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
			if (!stack.has(StatueDataComponents.UPGRADED)) {
				StatueStats stats = stack.getOrDefault(StatueDataComponents.STATS, StatueStats.empty());
				stack.set(StatueDataComponents.STATS, stats);
				stack.set(StatueDataComponents.UPGRADED, true);

				return true;
			} else {
				//Already upgraded
				return false;
			}
		} else {
			if (stack.getItem() instanceof StatueBlockItem) {
				if (requiresUpgrade()) {
					if (!stack.has(StatueDataComponents.UPGRADED)) {
						//Not upgraded
						return false;
					}

					if (stack.has(StatueDataComponents.UPGRADED)) {
						StatueStats stats = stack.getOrDefault(StatueDataComponents.STATS, StatueStats.empty());
						int upgradeSlots = stats.upgradeSlots();
						if (isSubsequentUsesSlot() && !(upgradeSlots > 0)) {
							//No upgrade slots
							return false;
						}
						String ID = this.name().toLowerCase(Locale.ROOT);
						String glowingID = GLOWING.name().toLowerCase(Locale.ROOT);
						StatueUpgrades statueUpgrades = stack.getOrDefault(StatueDataComponents.UPGRADES, StatueUpgrades.empty());
						Map<String, Short> upgradeMap = new HashMap<>();
						short currentLevel = statueUpgrades.upgradeMap().getOrDefault(ID, (short) 0);
						if ((currentLevel + 1) <= getCap()) {
							if (level != -1) {
								if (currentLevel == level) {
									if (this == GLOWING) {
										upgradeMap.putAll(statueUpgrades.withUpgrade(glowingID));
									} else {
										upgradeMap.putAll(statueUpgrades.withUpgrade(ID));
									}
									if (isSubsequentUsesSlot()) {
										upgradeSlots -= 1;
										stats.setUpgradeSlots(upgradeSlots);
										stack.set(StatueDataComponents.STATS, stats);
									}
								} else {
									//Level doesn't match
									return false;
								}
							} else {
								if (this == GLOWING) {
									upgradeMap.putAll(statueUpgrades.withUpgrade(glowingID));
								} else {
									upgradeMap.putAll(statueUpgrades.withUpgrade(ID));
								}
								if (isSubsequentUsesSlot()) {
									upgradeSlots -= 1;
									stats.setUpgradeSlots(upgradeSlots);
									stack.set(StatueDataComponents.STATS, stats);
								}
							}
						} else {
							//Next update would be over the cap
							return false;
						}
						stack.set(StatueDataComponents.UPGRADES, new StatueUpgrades(upgradeMap));
						return true;
					}
				} else {
					if (this == UNGLOWING) {
						if (!stack.has(StatueDataComponents.UPGRADED)) {
							//Not upgraded
							return false;
						}

						if (stack.has(StatueDataComponents.UPGRADED)) {
							StatueStats stats = stack.getOrDefault(StatueDataComponents.STATS, StatueStats.empty());
							int upgradeSlots = stats.upgradeSlots();
							if (isSubsequentUsesSlot() && !(upgradeSlots > 0)) {
								//No upgrade slots
								return false;
							}

							String glowingID = GLOWING.name().toLowerCase(Locale.ROOT);
							StatueUpgrades statueUpgrades = stack.getOrDefault(StatueDataComponents.UPGRADES, StatueUpgrades.empty());
							short glowLevel = statueUpgrades.upgradeMap().getOrDefault(glowingID, (short) 0);

							if (glowLevel > 0) {
								Map<String, Short> upgradeMap = new HashMap<>(statueUpgrades.withDowngrade(glowingID));

								if (upgradeMap.getOrDefault(glowingID, (short) 0) == 0)
									upgradeMap.remove(glowingID);

								stack.set(StatueDataComponents.UPGRADES, new StatueUpgrades(upgradeMap));
							} else {
								//Can't downgrade a stack that isn't glowing
								return false;
							}
						}
					} else {
						stack.set(StatueDataComponents.UPGRADED, true);
//						stack.set(StatueDataComponents.UPGRADE_SLOTS, 1); What did this even do?
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
