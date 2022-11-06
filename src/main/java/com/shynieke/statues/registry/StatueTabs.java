package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class StatueTabs {
	public static final CreativeModeTab STATUES_BLOCKS = new CreativeModeTab(Reference.MOD_ID + ".blocks") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(StatueRegistry.SLIME_STATUE.get());
		}
	}.setRecipeFolderName("statues_blocks");

	public static final CreativeModeTab STATUES_ITEMS = new CreativeModeTab(Reference.MOD_ID + ".items") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(StatueRegistry.STATUE_CORE.get());
		}
	}.setRecipeFolderName("statues_items");
}
