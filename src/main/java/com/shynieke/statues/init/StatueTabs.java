package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class StatueTabs {
	public static final ItemGroup STATUES_BLOCKS = new ItemGroup(Reference.MOD_ID + ".blocks") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(StatueRegistry.SLIME_STATUE.get());
		}
	}.setGroupPath("statues_blocks");

	public static final ItemGroup STATUES_ITEMS = new ItemGroup(Reference.MOD_ID + ".items") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(StatueRegistry.STATUE_CORE.get());
		}
	}.setGroupPath("statues_items");
}
