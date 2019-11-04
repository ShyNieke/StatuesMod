package com.svennieke.statues.init;

import com.svennieke.statues.Reference;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class StatueTabs {
	public static final ItemGroup STATUES_BLOCKS = new ItemGroup(Reference.MOD_ID + ".blocks") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(StatueBlocks.slime_statue);
		}
	}.setTabPath("statues_blocks");

	public static final ItemGroup STATUES_ITEMS = new ItemGroup(Reference.MOD_ID + ".items") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(StatueItems.statue_core);
		}
	}.setTabPath("statues_items");
}
