package com.shynieke.statues.blocks.decorative;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class EndermiteStatueBlock extends AbstractStatueBase {

    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.1, 0, 0.1, 16, 4, 16);

    public EndermiteStatueBlock(Properties properties) {
        super(properties.sound(SoundType.STONE).setOpaque(EndermiteStatueBlock::isntSolid));
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
    public EntityType<?> getEntity() {
        return EntityType.ENDERMITE;
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

}
