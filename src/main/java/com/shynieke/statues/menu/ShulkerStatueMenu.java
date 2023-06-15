package com.shynieke.statues.menu;

import com.shynieke.statues.blockentities.ShulkerStatueBlockEntity;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ShulkerStatueMenu extends AbstractContainerMenu {
	private final ShulkerStatueBlockEntity shulkerBE;

	public ShulkerStatueMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
		this(windowId, playerInventory, getBlockEntity(playerInventory, data));
	}

	private static ShulkerStatueBlockEntity getBlockEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final BlockEntity BlockEntityAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());

		if (BlockEntityAtPos instanceof ShulkerStatueBlockEntity) {
			return (ShulkerStatueBlockEntity) BlockEntityAtPos;
		}

		throw new IllegalStateException("Block entity is not correct! " + BlockEntityAtPos);
	}

	public ShulkerStatueMenu(int id, Inventory playerInventoryIn, ShulkerStatueBlockEntity shulkerBlockEntity) {
		super(StatueRegistry.SHULKER_STATUE_MENU.get(), id);
		this.shulkerBE = shulkerBlockEntity;

		int xPos = 8;
		int yPos = 18;

		int index = 0;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				int middle = x - 4;
				if (middle >= -1 && middle <= 1)
					continue;

				this.addSlot(new SlotItemHandler(shulkerBlockEntity.handler, index++, xPos + x * 18, yPos + y * 18));
			}
		}

		//player inventory here
		yPos = 84;
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(playerInventoryIn, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlot(new Slot(playerInventoryIn, x, xPos + x * 18, yPos + 58));
		}
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return this.shulkerBE.stillValid(playerIn) && !playerIn.isSpectator();
	}

	@Override
	@Nonnull
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			final int containerSize = 18;

			if (index < containerSize) {
				if (!this.moveItemStackTo(itemstack1, containerSize, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else {
				if (!this.moveItemStackTo(itemstack1, 0, containerSize, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		this.slotsChanged(null);

		return itemstack;
	}

	public ShulkerStatueBlockEntity getShulkerBE() {
		return shulkerBE;
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
	}
}