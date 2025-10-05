package com.shynieke.statues.blocks.decorative;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DisplayStandBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final VoxelShape SHAPE = Stream.of(
			Block.box(2, 4, 2, 14, 6, 14),
			Block.box(0, 0, 0, 16, 2, 16),
			Block.box(1, 2, 1, 15, 4, 15),
			Block.box(3, 6, 3, 13, 10, 13),
			Block.box(2, 10, 2, 14, 12, 14),
			Block.box(1, 12, 1, 15, 14, 15),
			Block.box(0, 14, 0, 16, 15, 16),
			Block.box(0, 15, 0, 16, 16, 2),
			Block.box(0, 15, 14, 16, 16, 16),
			Block.box(0, 15, 2, 2, 16, 14),
			Block.box(14, 15, 2, 16, 16, 14),
			Block.box(2, 15, 2, 14, 16, 4),
			Block.box(2, 15, 12, 14, 16, 14),
			Block.box(2, 15, 4, 4, 16, 12),
			Block.box(12, 15, 4, 14, 16, 12),
			Block.box(4, 15, 4, 12, 16, 5),
			Block.box(4, 15, 11, 12, 16, 12),
			Block.box(4, 15, 5, 5, 16, 11),
			Block.box(11, 15, 5, 12, 16, 11),
			Block.box(5, 15, 5, 11, 16, 11)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	public DisplayStandBlock(Properties properties) {
		super(properties.isRedstoneConductor(DisplayStandBlock::isntSolid));
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
		return false;
	}

	private static boolean isntSolid(BlockState state, BlockGetter getter, BlockPos pos) {
		return false;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		FluidState fluidstate = context.getLevel().getFluidState(blockpos);
		return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
	}
}