package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class StatueTags {
	public static final TagKey<Item> CURIOS_STATUE = ItemTags.create(new ResourceLocation("curios", "statue"));

	public static final TagKey<Item> STATUES_ITEMS = ItemTags.create(new ResourceLocation(Reference.MOD_ID, "statues"));
	public static final TagKey<Block> STATUE_BLOCKS = BlockTags.create(new ResourceLocation(Reference.MOD_ID, "statues"));

	public static final TagKey<Item> PLAYER_UPGRADE_ITEM = ItemTags.create(new ResourceLocation(Reference.MOD_ID, "player_upgrade_item"));
}
