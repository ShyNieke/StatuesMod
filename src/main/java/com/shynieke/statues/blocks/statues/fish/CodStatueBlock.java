package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CodStatueBlock extends FishStatueBlock {
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);

    public CodStatueBlock(Properties builder) {
        super(builder.sound(SoundType.STONE), 1);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
        //TODO: Fish stuff? What should it do.
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.getValue(INTERACTIVE).booleanValue();
    }

    @Override
    public EntityType<?> getEntity() {
        return EntityType.COD;
    }

    @Override
    public SoundEvent getSound(BlockState state) {
        return SoundEvents.COD_FLOP;
    }
}
