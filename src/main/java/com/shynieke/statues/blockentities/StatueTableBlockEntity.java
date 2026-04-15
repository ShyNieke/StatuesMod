package com.shynieke.statues.blockentities;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.menu.StatueTableMenu;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueTags;
import com.shynieke.statues.util.MultipleRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StatueTableBlockEntity extends BlockEntity implements MenuProvider {
	public int time;

	public static final int SLOT_CENTER = 0;
	public static final int SLOT_CORE = 1;
	public static final int[] SLOT_CATALYSTS = new int[]{2, 3, 4, 5};
	protected RecipeHolder<UpgradeRecipe> currentRecipe;

	private final ItemStacksResourceHandler handler = new ItemStacksResourceHandler(6) {

		@Override
		protected int getCapacity(int index, @NotNull ItemResource resource) {
			if (index == SLOT_CENTER || index == SLOT_CORE) {
				return 1;
			} else {
				return Item.DEFAULT_MAX_STACK_SIZE;
			}
		}

		@Override
		public boolean isValid(int index, ItemResource resource) {
			if (index == SLOT_CENTER) {
				return resource.is(StatueTags.UPGRADEABLE_STATUES);
			} else if (index == SLOT_CORE) {
				return resource.is(StatueTags.STATUE_CORE);
			} else {
				return super.isValid(index, resource);
			}
		}

		@Override
		protected void onContentsChanged(int index, ItemStack previousContents) {
			super.onContentsChanged(index, previousContents);
			refreshClient();
		}
	};

	public StatueTableBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
	}

	public StatueTableBlockEntity(BlockPos pos, BlockState state) {
		this(StatueBlockEntities.STATUE_TABLE.get(), pos, state);
	}

	protected void updateCachedRecipe() {
		if (this.level == null || this.level.isClientSide()) return;

		if (getCenterResource().isEmpty()) {
			this.currentRecipe = null;
			return;
		}
		List<ItemStack> inputs = new ArrayList<>();
		ItemStacksResourceHandler handler = getHandler();
		if (handler == null) return;
		for (int i = 0; i < handler.size(); i++) {
			inputs.add(i, handler.getResource(i).toStack());
		}
		this.currentRecipe = ((ServerLevel) this.level).recipeAccess().getRecipeFor(StatuesRecipes.UPGRADE_RECIPE.get(), new MultipleRecipeInput(inputs), this.level).orElse(null);
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
			ItemStacksResourceHandler handler = getHandler();
			try (Transaction tx = Transaction.openRoot()) {
				if (recipe.requiresCore()) {
					ItemResource coreResource = getCoreSlot();
					if (coreResource.isEmpty()) {
						return;
					}
					if (handler.extract(SLOT_CORE, coreResource, 1, tx) != 1) {
						return;
					}
				}
				for (int slot : SLOT_CATALYSTS) {
					ItemResource resource = handler.getResource(slot);
					if (!resource.isEmpty() && handler.extract(slot, resource, 1, tx) != 1) {
						return;
					}
				}

				ItemStack resultStack = recipe.getResultItem();
				ItemResource centerResource = getCenterResource();
				ItemStack centerStack = centerResource.toStack();
				if (resultStack.isEmpty()) {
					if (!recipe.getUpgradeType().apply(centerStack, recipe.getTier())) {
						handler.set(SLOT_CENTER, ItemResource.of(centerStack), centerStack.getCount());
						Statues.LOGGER.debug("Failed to apply upgrade {} to {}", currentRecipe.id(), resultStack);
					}
				} else {
					if (handler.extract(SLOT_CENTER, centerResource, 1, tx) != 1) {
						return;
					}
					handler.set(SLOT_CENTER, ItemResource.of(resultStack), resultStack.getCount());
				}

				tx.commit();
				this.currentRecipe = null;
				return;
			}
		}
		level.playSound(null, getBlockPos(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
		updateCachedRecipe();

		refreshClient();
	}

	/**
	 * Returns a copy of the ItemStack in the center slot.
	 *
	 * @return ItemStack in the center slot.
	 */
	public ItemResource getCenterResource() {
		if (handler == null) return ItemResource.EMPTY;
		return handler.getResource(SLOT_CENTER);
	}

	public ItemResource getCoreSlot() {
		if (handler == null) return ItemResource.EMPTY;
		return handler.getResource(SLOT_CORE);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		handler.deserialize(input);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		handler.serialize(output);
	}

	public ItemStacksResourceHandler getHandler(@Nullable Direction direction) {
		return handler;
	}

	public ItemStacksResourceHandler getHandler() {
		return getHandler(null);
	}

	@Override
	public void onDataPacket(Connection net, ValueInput valueInput) {
		super.onDataPacket(net, valueInput);

		BlockState state = level.getBlockState(getBlockPos());
		level.sendBlockUpdated(getBlockPos(), state, state, 3);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		CompoundTag tag = new CompoundTag();
		try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Statues.LOGGER)) {
			TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
			this.saveAdditional(output);
			tag.merge(output.buildResult());
		}
		return tag;
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag tag = new CompoundTag();
		try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Statues.LOGGER)) {
			HolderLookup.Provider lookupProvider = this.level != null ? this.level.registryAccess() : VanillaRegistries.createLookup();
			TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
			this.saveAdditional(output);
			tag.merge(output.buildResult());
		}
		return tag;
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

	@Override
	public void preRemoveSideEffects(BlockPos pos, BlockState state) {
		super.preRemoveSideEffects(pos, state);
		try (Transaction tx = Transaction.openRoot()) {
			for (int i = 0; i < handler.size(); ++i) {
				if (!handler.getResource(i).isEmpty())
					Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), handler.getResource(i).toStack());
			}
			tx.commit();
		}
	}
}
