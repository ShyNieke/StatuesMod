package com.shynieke.statues.blockentities;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.menu.StatueTableMenu;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class StatueTableBlockEntity extends BlockEntity implements MenuProvider {
	public int time;

	public static final int SLOT_CENTER = 0;
	public static final int SLOT_CORE = 1;
	public static final int[] SLOT_CATALYSTS = new int[]{2, 3, 4, 5};
	protected RecipeHolder<UpgradeRecipe> currentRecipe;

	public StatueTableBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
	}

	public StatueTableBlockEntity(BlockPos pos, BlockState state) {
		this(StatueBlockEntities.STATUE_TABLE.get(), pos, state);
	}

	private IItemHandler getCap() {
		if(this.level == null) return null;
		if(this.level.isClientSide) return this.level.getCapability(Capabilities.ItemHandler.BLOCK, this.worldPosition, Direction.UP);
		BlockCapabilityCache<IItemHandler, Direction> cache = BlockCapabilityCache.create(Capabilities.ItemHandler.BLOCK, (ServerLevel) level, this.worldPosition, Direction.UP);
		return cache.getCapability();
	}

	public ItemStackHandler getHandler() {
		if(getCap() instanceof ItemStackHandler handler) {
			return handler;
		}
		return null;
	}

	protected void updateCachedRecipe() {
		if (this.level == null) return;

		if (getCenterSlot().isEmpty()) {
			this.currentRecipe = null;
			return;
		}
		SimpleContainer container = new SimpleContainer(6);
		IItemHandler handler = getHandler();
		if(handler == null) return;
		for (int i = 0; i < handler.getSlots(); i++) {
			container.setItem(i, handler.getStackInSlot(i));
		}

		this.currentRecipe = this.level.getRecipeManager().getRecipeFor(StatuesRecipes.UPGRADE_RECIPE.get(), container, this.level).orElse(null);
	}

	@Override
	public void setLevel(Level level) {
		super.setLevel(level);
		updateCachedRecipe();
	}

	public boolean hasValidRecipe() {
		return currentRecipe != null;
	}

	public void executeCraft() {
		if (hasValidRecipe()) {
			UpgradeRecipe recipe = this.currentRecipe.value();
			if (recipe.requiresCore()) {
				getCoreSlot().shrink(1);
			}
			ItemStackHandler handler = getHandler();
			for (int slot : SLOT_CATALYSTS) {
				handler.getStackInSlot(slot).shrink(1);
			}

			ItemStack resultStack = recipe.getResultItem(level.registryAccess()).copy();
			ItemStack centerStack = getCenterSlot();
			if (resultStack.isEmpty()) {
				if (!recipe.getUpgradeType().apply(centerStack, recipe.getTier())) {
					Statues.LOGGER.debug("Failed to apply upgrade {} to {}", currentRecipe.id(), resultStack);
					this.currentRecipe = null;
					return;
				}
			} else {
				centerStack.shrink(1);
				handler.setStackInSlot(SLOT_CENTER, resultStack);
			}
		}
		level.playSound(null, getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
		updateCachedRecipe();

		refreshClient();
	}

	public ItemStack getCenterSlot() {
		IItemHandler handler = getHandler();
		if(handler == null) return ItemStack.EMPTY;
		return handler.getStackInSlot(SLOT_CENTER);
	}

	public ItemStack getCoreSlot() {
		IItemHandler handler = getHandler();
		if(handler == null) return ItemStack.EMPTY;
		return handler.getStackInSlot(SLOT_CORE);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		updateCachedRecipe();
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
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

	public void refreshClient() {
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
}
