package com.shynieke.statues.init;

import com.shynieke.statues.Reference;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class StatueTabs {
	public static final ItemGroup STATUES_BLOCKS = new ItemGroup(Reference.MOD_ID + ".blocks") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(StatueRegistry.SLIME_STATUE.get());
		}
	}.setRecipeFolderName("statues_blocks");

	public static final ItemGroup STATUES_ITEMS = new ItemGroup(Reference.MOD_ID + ".items") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(StatueRegistry.STATUE_CORE.get());
		}
	}.setRecipeFolderName("statues_items");
}
