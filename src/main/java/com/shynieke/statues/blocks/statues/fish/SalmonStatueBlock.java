package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.blockentities.StatueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SalmonStatueBlock extends FishStatueBlock {
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);

    public SalmonStatueBlock(Properties builder) {
        super(builder.sound(SoundType.STONE), 1);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
        //TODO: Fish stuff? What should it do.
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.getValue(INTERACTIVE).booleanValue();
    }

    @Override
    public EntityType<?> getEntity() {
        return EntityType.SALMON;
    }

    @Override
    public SoundEvent getSound(BlockState state) {
        return SoundEvents.SALMON_FLOP;
    }
}
