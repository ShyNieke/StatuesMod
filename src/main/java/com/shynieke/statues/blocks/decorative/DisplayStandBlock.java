package com.shynieke.statues.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class DisplayStandBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(2, 4, 2, 14, 6, 14),
            Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
            Block.makeCuboidShape(1, 2, 1, 15, 4, 15),
            Block.makeCuboidShape(3, 6, 3, 13, 10, 13),
            Block.makeCuboidShape(2, 10, 2, 14, 12, 14),
            Block.makeCuboidShape(1, 12, 1, 15, 14, 15),
            Block.makeCuboidShape(0, 14, 0, 16, 15, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 2),
            Block.makeCuboidShape(0, 15, 14, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 2, 2, 16, 14),
            Block.makeCuboidShape(14, 15, 2, 16, 16, 14),
            Block.makeCuboidShape(2, 15, 2, 14, 16, 4),
            Block.makeCuboidShape(2, 15, 12, 14, 16, 14),
            Block.makeCuboidShape(2, 15, 4, 4, 16, 12),
            Block.makeCuboidShape(12, 15, 4, 14, 16, 12),
            Block.makeCuboidShape(4, 15, 4, 12, 16, 5),
            Block.makeCuboidShape(4, 15, 11, 12, 16, 12),
            Block.makeCuboidShape(4, 15, 5, 5, 16, 11),
            Block.makeCuboidShape(11, 15, 5, 12, 16, 11),
            Block.makeCuboidShape(5, 15, 5, 11, 16, 11)
    ).reduce((v1, v2) -> { return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);} ).get();

    public DisplayStandBlock(Properties properties) {
        super(properties.setOpaque(DisplayStandBlock::isntSolid));
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(false)));
    }
    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER));
    }
}