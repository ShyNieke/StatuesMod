package com.svennieke.statues.compat.list;

import java.util.ArrayList;

import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StatueLootList {
	public static ArrayList<LootInfo> lootList = new ArrayList<>();
	public static LootInfo loot_info;
	
	public static void initializeStatueLoot() {
		addLoot("baby_zombie", new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.IRON_NUGGET), ItemStack.EMPTY);
		addLoot("blaze", new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_ROD), ItemStack.EMPTY);
		addLoot("chicken", new ItemStack(Items.FEATHER), new ItemStack(Items.CHICKEN), ItemStack.EMPTY);
		addLoot("chicken_jockey", new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.FEATHER), ItemStack.EMPTY);
		addLoot("king_cluck", new ItemStack(StatuesItems.nugget), ItemStack.EMPTY, new ItemStack(Items.GOLD_NUGGET));
		addLoot("cow", new ItemStack(Items.BEEF), ItemStack.EMPTY, new ItemStack(Items.LEATHER));
		addLoot("creeper", new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("enderman", new ItemStack(StatuesBlocks.pebble, 16), new ItemStack(Items.ENDER_PEARL), ItemStack.EMPTY);
		addLoot("flood", ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("ghast", new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY, new ItemStack(Items.GHAST_TEAR));
		addLoot("guardian", new ItemStack(Items.FISH, 1, 0), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_CRYSTALS));
		addLoot("husk", new ItemStack(Items.ROTTEN_FLESH), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT));
		addLoot("magma_slime", new ItemStack(Items.MAGMA_CREAM), ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("mooshroom", ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
		addLoot("pig", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.PORKCHOP));
		addLoot("rabbit", new ItemStack(Items.RABBIT_HIDE), new ItemStack(Items.RABBIT), new ItemStack(Items.RABBIT_FOOT));
		addLoot("sheep", ItemStack.EMPTY, new ItemStack(Items.MUTTON), ItemStack.EMPTY);
		addLoot("sheep_shaven", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.MUTTON));
		addLoot("slime", ItemStack.EMPTY, new ItemStack(Items.SLIME_BALL), ItemStack.EMPTY);
		addLoot("snowgolem", new ItemStack(Items.SNOWBALL), ItemStack.EMPTY, new ItemStack(Blocks.PUMPKIN));
		addLoot("squid", ItemStack.EMPTY, new ItemStack(Items.DYE, 1, 0), ItemStack.EMPTY);
		addLoot("villager", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.EMERALD));
		addLoot("witch", new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLASS_BOTTLE));
		addLoot("wasteland_pig", new ItemStack(StatuesItems.tea), getWastelandBlock(), ItemStack.EMPTY);
		addLoot("zombie", new ItemStack(Items.ROTTEN_FLESH), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT));
		addLoot("pufferfish", ItemStack.EMPTY, new ItemStack(Items.FISH, 1, 3), ItemStack.EMPTY);
		addLoot("spider", new ItemStack(Items.STRING), new ItemStack(Items.SPIDER_EYE), ItemStack.EMPTY);
		addLoot("evoker", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.TOTEM_OF_UNDYING));
	}
	
	public static ItemStack getWastelandBlock()
	{
		ItemStack wasteland = new ItemStack(Blocks.SAND).setStackDisplayName("§dWasteland Block§r");
		wasteland.addEnchantment(Enchantments.VANISHING_CURSE, 1);
		NBTTagCompound nbt = wasteland.getTagCompound();
		nbt.setInteger("HideFlags", 1);
		wasteland.setTagCompound(nbt);
		return wasteland;
	}
	
	public static ItemStack getFloodBucket()
	{
		ItemStack floodbucket = new ItemStack(Items.WATER_BUCKET); 
        floodbucket.setStackDisplayName("§9The Flood§r");
		
		return floodbucket;
	}
	
	public static void addLoot(String statue, ItemStack stack1, ItemStack stack2, ItemStack stack3)
	{
		loot_info = new LootInfo(stack1, stack2, stack3, statue);
		if(lootList.contains(loot_info))
			return;
		else
			lootList.add(loot_info);
	}
	
		
	public static void changeLoot(String statue, ItemStack stack1, ItemStack stack2, ItemStack stack3)
	{
		for(LootInfo info : lootList)
		{
			if(info.getStatue().equals(statue))
			{
				info.setStack1(stack1);
				info.setStack2(stack2);
				info.setStack3(stack3);
			}
		}
	}
	
	public static ArrayList<ItemStack> getStacksForStatue(String statue)
	{
		ArrayList<ItemStack> stacks = new ArrayList<>();
		
		for(LootInfo info : lootList)
		{
			if(info.getStatue() == statue)
			{
				stacks.add(0, info.getStack1());
				stacks.add(1, info.getStack2());
				stacks.add(2, info.getStack2());
				
				if(stacks.get(0) != info.getStack1())
					stacks.set(0, info.getStack1());
				if(stacks.get(1) != info.getStack2())
					stacks.set(1, info.getStack2());
				if(stacks.get(2) != info.getStack3())
					stacks.set(2, info.getStack3());
			}
		}
		
		return stacks;
	}
	
	public static ArrayList<Block> getBlockForStatue(String statue)
	{
		ArrayList<Block> blockList = new ArrayList<>();
		
		for(Block blocks : StatuesBlocks.BLOCKS)
		{
			String blockName = blocks.getUnlocalizedName();
			if(blockName.contains(statue.replace("_", "")) && (blockName.contains("t3") || blockName.contains("t4")))
			{
				if (!blockList.contains(blocks))
				{
					System.out.println(blocks);
					blockList.add(blocks);
				}
			}
		}

		return blockList;
	}
}
