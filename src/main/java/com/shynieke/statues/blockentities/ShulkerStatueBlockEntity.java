package com.shynieke.statues.blockentities;

import com.shynieke.statues.Reference;
import com.shynieke.statues.menu.ShulkerStatueMenu;
import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShulkerStatueBlockEntity extends StatueBlockEntity implements MenuProvider {
	public final ItemStackHandler handler = new ItemStackHandler(18) {

		@Override
		public boolean isItemValid(int slot, @NotNull ItemStack stack) {
			return super.isItemValid(slot, stack) &&
					!(Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock) &&
					!(Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock) && stack.getItem().canFitInsideContainerItems();
		}

		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			refreshClient();
		}
	};
	private LazyOptional<IItemHandler> stackHolder = LazyOptional.of(() -> handler);

	public ShulkerStatueBlockEntity(BlockPos pos, BlockState state) {
		super(StatueBlockEntities.SHULKER_STATUE.get(), pos, state);
	}

	@Override
	public void onSpecialInteract(Level level, BlockPos pos, BlockState state, Player player, InteractionHand handIn, BlockHitResult result) {
		if (!level.isClientSide && !player.isCrouching()) {
			NetworkHooks.openScreen((ServerPlayer) player, this, pos);
		}
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		compound.put("ItemStackHandler", handler.serializeNBT());
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		handler.deserializeNBT(compound.getCompound("ItemStackHandler"));
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(Reference.MOD_ID + ".container.shulker_statue");
	}

	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return !(player.distanceToSqr((double) this.worldPosition.getX() + 0.5D,
					(double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
		}
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
		return new ShulkerStatueMenu(id, inventory, this);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		if (capability == ForgeCapabilities.ITEM_HANDLER && hasSpecialInteraction()) {
			return stackHolder.cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		if (hasSpecialInteraction())
			this.stackHolder.invalidate();
	}

	@Override
	public void reviveCaps() {
		super.reviveCaps();
		if (hasSpecialInteraction())
			this.stackHolder = net.minecraftforge.common.util.LazyOptional.of(() -> handler);
	}
}
