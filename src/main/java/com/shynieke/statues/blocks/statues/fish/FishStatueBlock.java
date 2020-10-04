package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FishStatueBlock extends AbstractStatueBase {
    public static final IntegerProperty MAIN_COLOR = IntegerProperty.create("main_color", 0, 15);
    public static final IntegerProperty SECONDARY_COLOR = IntegerProperty.create("secondary_color", 0, 15);

    private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);
    private static final VoxelShape SHAPE_BIG = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D);
    private final int size;

    public FishStatueBlock(Properties builder, int size) {
        super(builder.sound(SoundType.STONE));
        this.setDefaultState(this.getDefaultState().with(MAIN_COLOR, 0).with(SECONDARY_COLOR, 0));
        this.size = size;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (size == 1) {
            return SHAPE_BIG;
        }
        return SHAPE;
    }

    @Override
    public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
        //TODO: Fish stuff? What should it do.
    }

    @Override
    public EntityType<?> getEntity() {
        return EntityType.TROPICAL_FISH;
    }

    @Override
    public SoundEvent getSound(BlockState state) {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED, INTERACTIVE, MAIN_COLOR, SECONDARY_COLOR);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(MAIN_COLOR, RANDOM.nextInt(16)).with(SECONDARY_COLOR, RANDOM.nextInt(16));
    }

    @OnlyIn(Dist.CLIENT)
    public static int getColor(BlockState state, IBlockReader world, BlockPos pos, int tintIndex) {
        return tintIndex == 1 ? fromColor(state.get(MAIN_COLOR)) : tintIndex == 2 ? fromColor(state.get(SECONDARY_COLOR)) : -1;
    }

    @OnlyIn(Dist.CLIENT)
    public static int fromColor(int color) {
        return DyeColor.byId(color).getColorValue();
    }
}
