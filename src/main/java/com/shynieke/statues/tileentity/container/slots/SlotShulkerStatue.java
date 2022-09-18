package com.shynieke.statues.tileentity.container.slots;

import com.shynieke.statues.blocks.Statues.BlockShulker_Statue;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotShulkerStatue extends Slot {

	public SlotShulkerStatue(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return !(Block.getBlockFromItem(stack.getItem()) instanceof BlockShulker_Statue);
	}

}
