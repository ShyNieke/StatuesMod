package com.svennieke.statues.compat.list;

import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;

public class StatueLootList {
	public static ArrayList<LootInfo> lootList = new ArrayList<>();
	public static LootInfo loot_info;
	
	public static void initializeStatueLoot() {
		lootList.add(new LootInfo("baby_zombie", new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.IRON_NUGGET), ItemStack.EMPTY));
		lootList.add(new LootInfo("blaze", new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_ROD), ItemStack.EMPTY));
		lootList.add(new LootInfo("chicken", new ItemStack(Items.FEATHER), new ItemStack(Items.CHICKEN), ItemStack.EMPTY));
		lootList.add(new LootInfo("chicken_jockey", new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.FEATHER), ItemStack.EMPTY));
		lootList.add(new LootInfo("king_cluck", new ItemStack(StatuesItems.nugget), ItemStack.EMPTY, new ItemStack(Items.GOLD_NUGGET)));
		lootList.add(new LootInfo("cow", new ItemStack(Items.BEEF), ItemStack.EMPTY, new ItemStack(Items.LEATHER)));
		lootList.add(new LootInfo("creeper", new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY, ItemStack.EMPTY));
		lootList.add(new LootInfo("enderman", new ItemStack(StatuesBlocks.pebble, 16), new ItemStack(Items.ENDER_PEARL), ItemStack.EMPTY));
		lootList.add(new LootInfo("flood", ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY));
		lootList.add(new LootInfo("ghast", new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY, new ItemStack(Items.GHAST_TEAR)));
		lootList.add(new LootInfo("guardian", new ItemStack(Items.COD), new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.PRISMARINE_CRYSTALS)));
		lootList.add(new LootInfo("husk", new ItemStack(Items.ROTTEN_FLESH), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT)));
		lootList.add(new LootInfo("magma_slime", new ItemStack(Items.MAGMA_CREAM), ItemStack.EMPTY, ItemStack.EMPTY));
		lootList.add(new LootInfo("mooshroom", ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY));
		lootList.add(new LootInfo("pig", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.PORKCHOP)));
		lootList.add(new LootInfo("rabbit", new ItemStack(Items.RABBIT_HIDE), new ItemStack(Items.RABBIT), new ItemStack(Items.RABBIT_FOOT)));
		lootList.add(new LootInfo("sheep", ItemStack.EMPTY, new ItemStack(Items.MUTTON), ItemStack.EMPTY));
		lootList.add(new LootInfo("sheep_shaven", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.MUTTON)));
		lootList.add(new LootInfo("slime", ItemStack.EMPTY, new ItemStack(Items.SLIME_BALL), ItemStack.EMPTY));
		lootList.add(new LootInfo("snowgolem", new ItemStack(Items.SNOWBALL), ItemStack.EMPTY, new ItemStack(Blocks.PUMPKIN)));
		lootList.add(new LootInfo("squid", ItemStack.EMPTY, new ItemStack(Items.INK_SAC), ItemStack.EMPTY));
		lootList.add(new LootInfo("villager", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.EMERALD)));
		lootList.add(new LootInfo("witch", new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLASS_BOTTLE)));
		lootList.add(new LootInfo("wasteland_pig", new ItemStack(StatuesItems.tea), getWastelandBlock(), ItemStack.EMPTY));
		lootList.add(new LootInfo("zombie", new ItemStack(Items.ROTTEN_FLESH), ItemStack.EMPTY, new ItemStack(Items.IRON_INGOT)));
		lootList.add(new LootInfo("pufferfish", ItemStack.EMPTY, new ItemStack(Items.PUFFERFISH), ItemStack.EMPTY));
		lootList.add(new LootInfo("spider", new ItemStack(Items.STRING), new ItemStack(Items.SPIDER_EYE), ItemStack.EMPTY));
		lootList.add(new LootInfo("evoker", ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.TOTEM_OF_UNDYING)));
		//lootList.add(new LootInfo("etho", new ItemStack(StatuesItems.marshmallow), ItemStack.EMPTY, ItemStack.EMPTY));
	}
	
	public static ItemStack getWastelandBlock()
	{
		ItemStack wasteland = new ItemStack(Blocks.SAND).setDisplayName(new TextComponentString("§dWasteland Block§r"));
		wasteland.addEnchantment(Enchantments.VANISHING_CURSE, 1);
		NBTTagCompound nbt = wasteland.getTag();
		nbt.setInt("HideFlags", 1);
		wasteland.setTag(nbt);
		return wasteland;
	}
	
	public static ItemStack getFloodBucket()
	{
		ItemStack floodbucket = new ItemStack(Items.WATER_BUCKET); 
        floodbucket.setDisplayName(new TextComponentString("§9The Flood§r"));
		
		return floodbucket;
	}
	
	public static void addLoot(String statue, ItemStack stack1, ItemStack stack2, ItemStack stack3)
	{
		loot_info = new LootInfo(statue, stack1, stack2, stack3);
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
			String blockName = blocks.getTranslationKey();
			if(blockName.contains(statue.replace("_", "")) && (blockName.contains("t3") || blockName.contains("t4")))
			{
				if (!blockList.contains(blocks))
				{
					blockList.add(blocks);
				}
			}
		}

		return blockList;
	}
}
