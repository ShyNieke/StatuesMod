package com.shynieke.statues.blocks.table;

import com.shynieke.statues.blockentities.StatueTableBlockEntity;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class StatueTableBlock extends AbstractBaseBlock {
	private static final VoxelShape SHAPE = Stream.of(
			Block.box(2, 0, 2, 14, 2, 14),
			Block.box(2, 2, 2, 4, 5, 4),
			Block.box(2, 2, 12, 4, 5, 14),
			Block.box(12, 2, 12, 14, 5, 14),
			Block.box(12, 2, 2, 14, 5, 4),
			Block.box(2, 5, 2, 14, 6, 14),
			Block.box(7, 6, 7, 9, 7, 9),
			Block.box(3, 7, 3, 13, 8, 13)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	public StatueTableBlock(Properties builder) {
		super(builder);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity blockentity = level.getBlockEntity(pos);
		if (blockentity instanceof StatueTableBlockEntity statueTableBlockEntity) {
			if (!level.isClientSide) {
				statueTableBlockEntity.hasValidRecipe();
				NetworkHooks.openScreen((ServerPlayer) playerIn, (StatueTableBlockEntity) blockentity, pos);
			}

			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity blockentity = level.getBlockEntity(pos);
			if (blockentity instanceof StatueTableBlockEntity) {
				IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
				if(handler != null) {
					for (int i = 0; i < handler.getSlots(); ++i) {
						Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(i));
					}
				}
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}


	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
		return new StatueTableBlockEntity(pos, blockState);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return createTableTicker(level, blockEntityType, StatueBlockEntities.STATUE_TABLE.get());
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTableTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends StatueTableBlockEntity> blockEntityType1) {
		return level.isClientSide ? createTickerHelper(blockEntityType, blockEntityType1, StatueTableBlockEntity::renderTick) : createTickerHelper(blockEntityType, blockEntityType1, StatueTableBlockEntity::serverTick);
	}
}
