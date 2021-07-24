package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class StatueTags {
    public static final Tag.Named<Item> STATUES_ITEMS = ItemTags.bind(Reference.MOD_ID + ":statues");
    public static final Tag.Named<Block> STATUE_BLOCKS = BlockTags.bind(Reference.MOD_ID + ":statues");

    public static final Tag.Named<Item> PLAYER_UPGRADE_ITEM = ItemTags.bind(Reference.MOD_ID + ":player_upgrade_item");
}
