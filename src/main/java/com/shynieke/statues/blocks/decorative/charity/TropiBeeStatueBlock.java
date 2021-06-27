package com.shynieke.statues.blocks.decorative.charity;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class TropiBeeStatueBlock extends AbstractStatueBase {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 11D, 12.0D);

    public TropiBeeStatueBlock(Properties properties) {
        super(properties.sound(SoundType.STONE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canBeUpgraded() {
        return false;
    }

    @Override
    public SoundEvent getSound(BlockState state) {
        return SoundEvents.ENTITY_BEE_LOOP;
    }
}
