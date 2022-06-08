package com.shynieke.statues.recipes;

import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class StatueLootList {
	public static ArrayList<StatueLootInfo> lootList = new ArrayList<>();
	public static StatueLootInfo loot_info;

	public static void initializeStatueLoot() {
		addLoot("baby_zombie", new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.IRON_NUGGET), ItemStack.EMPTY);
		addLoot("bee", ItemStack.EMPTY, new ItemStack(Items.HONEYCOMB), ItemStack.EMPTY);
		addLoot("blaze", new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_ROD), ItemStack.EMPTY);
		addLoot("chicken", new ItemStack(Items.FEATHER), new ItemStack(Items.CHICKEN), ItemStack.EMPTY);
		addLoot("chicken_jockey", new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.FEATHER), ItemStack.EMPTY);
		addLoot("king_cluck", new ItemStack(StatueRegistry.NUGGET.get()), ItemStack.EMPTY, new ItemStack(Items.GOLD_NUGGET));
		addLoot("cow", new ItemStack(Items.BEEF), ItemStack.EMPTY, new ItemStack(Items.LEATHER));
		addLoot("creeper", new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("enderman", new ItemStack(StatueRegistry.PEBBLE.get(), 16), new ItemStack(Items.ENDER_PEARL), ItemStack.EMPTY);
		addLoot("flood", ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("ghast", new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY, new ItemStack(Items.GHAST_TEAR));
		addLoot("guardian", new ItemStack(Items.COD), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_CRYSTALS));
		addLoot("husk", new ItemStack(Items.ROTTEN_FLESH), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT));
		addLoot("drowned", new ItemStack(Items.ROTTEN_FLESH), ItemStack.EMPTY, new ItemStack(Items.GOLD_INGOT));
		addLoot("magma_slime", new ItemStack(Items.MAGMA_CREAM), ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("mooshroom", ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("pig", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.PORKCHOP));
		addLoot("rabbit", new ItemStack(Items.RABBIT_HIDE), new ItemStack(Items.RABBIT), new ItemStack(Items.RABBIT_FOOT));

		addLoot("sheep_white", new ItemStack(Blocks.WHITE_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_orange", new ItemStack(Blocks.ORANGE_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_magenta", new ItemStack(Blocks.MAGENTA_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_light_blue", new ItemStack(Blocks.LIGHT_BLUE_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_yellow", new ItemStack(Blocks.YELLOW_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_lime", new ItemStack(Blocks.LIME_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_pink", new ItemStack(Blocks.PINK_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_gray", new ItemStack(Blocks.GRAY_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_light_gray", new ItemStack(Blocks.LIGHT_GRAY_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_cyan", new ItemStack(Blocks.CYAN_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_purple", new ItemStack(Blocks.PURPLE_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_blue", new ItemStack(Blocks.BLUE_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_brown", new ItemStack(Blocks.BROWN_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_green", new ItemStack(Blocks.GREEN_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_red", new ItemStack(Blocks.RED_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_black", new ItemStack(Blocks.BLACK_WOOL), new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_shaven", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.MUTTON));

		addLoot("slime", ItemStack.EMPTY, new ItemStack(Items.SLIME_BALL), ItemStack.EMPTY);
		addLoot("snow_golem", new ItemStack(Items.SNOWBALL), ItemStack.EMPTY, new ItemStack(Blocks.PUMPKIN));
		addLoot("squid", ItemStack.EMPTY, new ItemStack(Items.INK_SAC), ItemStack.EMPTY);
		addLoot("villager", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.EMERALD));
		addLoot("witch", new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLASS_BOTTLE));
		addLoot("wasteland_pig", new ItemStack(StatueRegistry.TEA.get()), getWastelandBlock(), ItemStack.EMPTY);
		addLoot("zombie", new ItemStack(Items.ROTTEN_FLESH), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT));
		addLoot("pufferfish", ItemStack.EMPTY, new ItemStack(Items.PUFFERFISH), ItemStack.EMPTY);
		addLoot("spider", new ItemStack(Items.STRING), new ItemStack(Items.SPIDER_EYE), ItemStack.EMPTY);
		addLoot("evoker", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.TOTEM_OF_UNDYING));
		addLoot("etho", new ItemStack(StatueRegistry.MARSHMALLOW.get()), ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("turtle", new ItemStack(Items.SEAGRASS), ItemStack.EMPTY, new ItemStack(Items.BOWL));
	}

	public static ItemStack getWastelandBlock() {
		ItemStack wasteland = new ItemStack(Blocks.SAND).setHoverName(Component.literal("Wasteland Block").withStyle(ChatFormatting.LIGHT_PURPLE));
		wasteland.enchant(Enchantments.VANISHING_CURSE, 1);
		CompoundTag nbt = wasteland.hasTag() ? wasteland.getTag() : new CompoundTag();
		if (nbt != null) {
			nbt.putInt("HideFlags", 1);
			wasteland.setTag(nbt);
		}
		return wasteland;
	}

	public static ItemStack getFloodBucket() {
		ItemStack floodBucket = new ItemStack(Items.WATER_BUCKET);
		floodBucket.setHoverName(Component.literal("The Flood").withStyle(ChatFormatting.BLUE));

		return floodBucket;
	}

	public static void addLoot(String statue, LootInfo loot) {
		loot_info = new StatueLootInfo(statue, loot);
		if (!lootList.contains(loot_info)) {
			lootList.add(loot_info);
		}
	}

	public static void addLoot(String statue, ItemStack stack1, ItemStack stack2, ItemStack stack3) {
		LootInfo info = new LootInfo(stack1, stack2, stack3);
		addLoot(statue, info);
	}

	public static void changeLoot(String statue, LootInfo loot) {
		if (!statue.isEmpty()) {
			for (int i = 0; i < lootList.size(); i++) {
				StatueLootInfo oldLoot = lootList.get(i);
				if (oldLoot.getStatue().equalsIgnoreCase(statue)) {
					lootList.set(i, new StatueLootInfo(oldLoot.getStatue(), loot));
				}
			}
		}
	}

	/**
	 * Returns the loot of a specified statue
	 * Returns an empty LootInfo when specified statue isn't found
	 *
	 * @param statueName
	 * @return
	 */
	public static StatueLootInfo getLootInfo(String statueName) {
		StatueLootInfo loot = new StatueLootInfo(statueName, LootInfo.EMPTY);
		for (StatueLootInfo info : lootList) {
			if (info.getStatue().equalsIgnoreCase(statueName)) {
				loot = info;
			}
		}

		return loot;
	}

	public static ArrayList<ItemStack> getStacksForStatue(String statue) {
		ArrayList<ItemStack> stacks = new ArrayList<>();

		for (StatueLootInfo info : lootList) {
			if (info.getStatue().equals(statue)) {
				stacks.add(0, info.getStack1());
				stacks.add(1, info.getStack2());
				stacks.add(2, info.getStack2());

				if (stacks.get(0) != info.getStack1())
					stacks.set(0, info.getStack1());
				if (stacks.get(1) != info.getStack2())
					stacks.set(1, info.getStack2());
				if (stacks.get(2) != info.getStack3())
					stacks.set(2, info.getStack3());
			}
		}

		return stacks;
	}

	public static ArrayList<Block> getBlockForStatue(String statue) {
		ArrayList<Block> blockList = new ArrayList<>();

		for (RegistryObject<Block> block : StatueRegistry.BLOCKS.getEntries()) {
			String blockName = block.get().getDescriptionId();
			if (!blockName.isEmpty() && blockName.contains(statue.replace("_", "")) && (blockName.contains("t3") || blockName.contains("t4"))) {
				if (!blockList.contains(block.get())) {
					blockList.add(block.get());
				}
			}
		}

		return blockList;
	}
}
