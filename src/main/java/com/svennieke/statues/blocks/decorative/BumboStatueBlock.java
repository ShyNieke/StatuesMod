package com.svennieke.statues.blocks.decorative;

import com.svennieke.statues.blocks.AbstractStatueBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BumboStatueBlock extends AbstractStatueBase {

    private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);

    public BumboStatueBlock(Properties properties) {
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
}
