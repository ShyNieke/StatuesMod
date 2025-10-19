package com.shynieke.statues.blocks.table;

import com.shynieke.statues.blockentities.StatueTableBlockEntity;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
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
import org.jetbrains.annotations.NotNull;
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
	public InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
	                                        @NotNull Player playerIn, @NotNull BlockHitResult hit) {
		BlockEntity blockentity = level.getBlockEntity(pos);
		if (blockentity instanceof StatueTableBlockEntity statueTableBlockEntity) {
			if (!level.isClientSide()) {
				statueTableBlockEntity.hasValidRecipe();
				playerIn.openMenu(statueTableBlockEntity, pos);
			}

			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
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
		return level.isClientSide() ? createTickerHelper(blockEntityType, blockEntityType1, StatueTableBlockEntity::renderTick) : createTickerHelper(blockEntityType, blockEntityType1, StatueTableBlockEntity::serverTick);
	}
}
