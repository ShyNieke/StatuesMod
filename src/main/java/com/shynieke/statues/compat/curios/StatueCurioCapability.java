package com.shynieke.statues.compat.curios;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class StatueCurioCapability implements ICurio {
	private final ItemStack displayedItem;

	public StatueCurioCapability(ItemStack item) {
		this.displayedItem = item;
	}

	@Override
	public ItemStack getStack() {
		return displayedItem;
	}

	@Override
	public boolean canRightClickEquip() {
		return true;
	}
}
