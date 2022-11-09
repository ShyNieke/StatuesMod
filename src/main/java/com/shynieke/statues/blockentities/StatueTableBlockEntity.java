package com.shynieke.statues.blockentities;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.menu.StatueTableMenu;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class StatueTableBlockEntity extends BlockEntity implements MenuProvider {
	public int time;

	public static final int SLOT_CENTER = 0;
	public static final int SLOT_CORE = 1;
	public static final int[] SLOT_CATALYSTS = new int[]{2, 3, 4, 5};
	protected UpgradeRecipe currentRecipe;

	public final ItemStackHandler handler = new ItemStackHandler(6) {
		@Override
		protected int getStackLimit(int slot, @NotNull ItemStack stack) {
			if (slot == SLOT_CENTER || slot == SLOT_CORE) {
				return 1;
			} else {
				return 64;
			}
		}

		@Override
		public boolean isItemValid(int slot, @NotNull ItemStack stack) {
			if (slot == SLOT_CENTER) {
				return stack.is(StatueTags.UPGRADEABLE_STATUES);
			} else if (slot == SLOT_CORE) {
				return stack.is(StatueTags.STATUE_CORE);
			} else {
				return super.isItemValid(slot, stack);
			}
		}

		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			refreshClient();
		}
	};
	private LazyOptional<IItemHandler> stackHolder = LazyOptional.of(() -> handler);

	public StatueTableBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
	}

	public StatueTableBlockEntity(BlockPos pos, BlockState state) {
		this(StatueBlockEntities.STATUE_TABLE.get(), pos, state);
	}

	protected void updateCachedRecipe() {
		if (getCenterSlot().isEmpty()) {
			this.currentRecipe = null;
			return;
		}
		SimpleContainer container = new SimpleContainer(6);
		for (int i = 0; i < handler.getSlots(); i++) {
			container.setItem(i, handler.getStackInSlot(i));
		}
		this.currentRecipe = this.level.getRecipeManager().getRecipeFor(StatuesRecipes.UPGRADE_RECIPE.get(), container, this.level).orElse(null);
	}

	public boolean hasValidRecipe() {
		return currentRecipe != null;
	}

	public void executeCraft() {
		if (hasValidRecipe()) {
			if (currentRecipe.requiresCore()) {
				getCoreSlot().shrink(1);
			}
			for (int slot : SLOT_CATALYSTS) {
				handler.getStackInSlot(slot).shrink(1);
			}

			ItemStack resultStack = currentRecipe.getResultItem().copy();
			ItemStack centerStack = getCenterSlot();
			if (resultStack.isEmpty()) {
				if (!currentRecipe.getUpgradeType().apply(centerStack, currentRecipe.getTier())) {
					Statues.LOGGER.debug("Failed to apply upgrade {} to {}", currentRecipe.getId(), resultStack);
					this.currentRecipe = null;
					return;
				}
			} else {
				centerStack.shrink(1);
				handler.setStackInSlot(SLOT_CENTER, resultStack);
			}
		}
		updateCachedRecipe();

		refreshClient();
	}

	public ItemStack getCenterSlot() {
		return handler.getStackInSlot(SLOT_CENTER);
	}

	public ItemStack getCoreSlot() {
		return handler.getStackInSlot(SLOT_CORE);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		handler.deserializeNBT(compound.getCompound("ItemStackHandler"));
		updateCachedRecipe();
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.put("ItemStackHandler", handler.serializeNBT());
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		load(pkt.getTag());

		BlockState state = level.getBlockState(getBlockPos());
		level.sendBlockUpdated(getBlockPos(), state, state, 3);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = new CompoundTag();
		this.saveAdditional(nbt);
		return nbt;
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag nbt = new CompoundTag();
		this.saveAdditional(nbt);
		return nbt;
	}

	public static void renderTick(Level level, BlockPos pos, BlockState state, StatueTableBlockEntity tableBlockEntity) {
		++tableBlockEntity.time;
	}

	private void refreshClient() {
		setChanged();
		BlockState state = level.getBlockState(worldPosition);
		level.sendBlockUpdated(worldPosition, state, state, 2);

		updateCachedRecipe();
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, StatueTableBlockEntity tableBlockEntity) {

	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(Reference.MOD_ID + ".container.statue_table");
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
		return new StatueTableMenu(id, inventory, this);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		if (capability == ForgeCapabilities.ITEM_HANDLER) {
			return stackHolder.cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		this.stackHolder.invalidate();
	}

	@Override
	public void reviveCaps() {
		super.reviveCaps();
		this.stackHolder = net.minecraftforge.common.util.LazyOptional.of(() -> handler);
	}
}
