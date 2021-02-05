package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class StatueTags {
    public static final ITag.INamedTag<Item> PLAYER_UPGRADE_ITEM = ItemTags.makeWrapperTag(Reference.MOD_ID + ":player_upgrade_item");
}
