package com.shynieke.statues.menu;

import com.shynieke.statues.blockentities.StatueTableBlockEntity;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StatueTableMenu extends AbstractContainerMenu {
	private final StatueTableBlockEntity statueBE;
	private final Player player;

	public final int[] validRecipe = new int[1];

	public StatueTableMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
		this(windowId, playerInventory, getBlockEntity(playerInventory, data));
	}

	private static StatueTableBlockEntity getBlockEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(data.readBlockPos());

		if (blockEntity instanceof StatueTableBlockEntity statueTableBlock) {
			return statueTableBlock;
		}

		throw new IllegalStateException("Block entity is not correct! " + blockEntity);
	}

	public StatueTableMenu(int id, Inventory playerInventoryIn, StatueTableBlockEntity statueTableBlockEntity) {
		super(StatueRegistry.STATUE_TABLE_MENU.get(), id);
		this.player = playerInventoryIn.player;
		this.statueBE = statueTableBlockEntity;

		final Level level = this.player.level();
		final BlockPos pos = this.statueBE.getBlockPos();
		IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
		if (handler == null)
			throw new IllegalStateException("Item handler is null!");

		//Statue Block slot
		this.addSlot(new TableSlot(handler, 0, 80, 30));
		//Statue Core Slot
		this.addSlot(new TableSlot(handler, 1, 8, 48));

		//Catalyst slots [2, 5]
		this.addSlot(new TableSlot(handler, 2, 62, 12));
		this.addSlot(new TableSlot(handler, 3, 98, 12));
		this.addSlot(new TableSlot(handler, 4, 62, 48));
		this.addSlot(new TableSlot(handler, 5, 98, 48));

		//player inventory here
		int xPos = 8;
		int yPos = 75;
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(playerInventoryIn, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlot(new Slot(playerInventoryIn, x, xPos + x * 18, yPos + 58));
		}

		this.validRecipe[0] = statueBE.hasValidRecipe() ? 1 : 0;
		this.addDataSlot(DataSlot.shared(this.validRecipe, 0));
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return this.statueBE.stillValid(playerIn) && !playerIn.isSpectator();
	}

	@Override
	@NotNull
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			final int containerSize = 6;

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

	public StatueTableBlockEntity getStatueBE() {
		return statueBE;
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
	}

	@Override
	public void slotsChanged(Container inventoryIn) {
		if (inventoryIn != null) {
			super.slotsChanged(inventoryIn);
		}
		getStatueBE().setChanged();
		if (!player.level().isClientSide) {
			this.validRecipe[0] = statueBE.hasValidRecipe() ? 1 : 0;
		}
	}

	public class TableSlot extends SlotItemHandler {
		private final int index;

		public TableSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
			this.index = index;
		}

		@Override
		public int getMaxStackSize() {
			if (index < 2)
				return 1;
			return super.getMaxStackSize();
		}

		@Override
		public void setChanged() {
			super.setChanged();
			slotsChanged(null);
		}
	}
}