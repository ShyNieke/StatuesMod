package com.shynieke.statues.blockentities;

import com.shynieke.statues.Reference;
import com.shynieke.statues.menu.ShulkerStatueMenu;
import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jspecify.annotations.Nullable;

public class ShulkerStatueBlockEntity extends StatueBlockEntity implements MenuProvider {
	private final ItemStacksResourceHandler handler = new ItemStacksResourceHandler(18) {

		@Override
		public boolean isValid(int index, ItemResource resource) {
			return super.isValid(index, resource) &&
					!(Block.byItem(resource.getItem()) instanceof ShulkerBoxBlock) &&
					!(Block.byItem(resource.getItem()) instanceof ShulkerBoxBlock) && resource.getItem().canFitInsideContainerItems();
		}

		@Override
		protected void onContentsChanged(int index, ItemStack previousContents) {
			super.onContentsChanged(index, previousContents);
			ShulkerStatueBlockEntity.this.refreshClient();
		}
	};

	public ShulkerStatueBlockEntity(BlockPos pos, BlockState state) {
		super(StatueBlockEntities.SHULKER_STATUE.get(), pos, state);
	}

	@Override
	public void onSpecialInteract(Level level, BlockPos pos, BlockState state, Player player, InteractionHand handIn, BlockHitResult result) {
		if (!level.isClientSide() && !player.isCrouching()) {
			player.openMenu(this, pos);
		}
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

	public ResourceHandler<ItemResource> getHandler(@Nullable Direction direction) {
		if (hasSpecialInteraction())
			return handler;
		else
			return null;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(Reference.MOD_ID + ".container.shulker_statue");
	}

	public boolean stillValid(Player player) {
		if (!hasSpecialInteraction())
			return false;
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
	public void refreshClient() {
		super.refreshClient();
	}
}
