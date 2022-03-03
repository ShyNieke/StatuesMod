package com.shynieke.statues.blocks.decorative;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TotemOfUndyingStatueBlock extends AbstractStatueBase {

	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 14, 12);

	public TotemOfUndyingStatueBlock(Properties properties) {
		super(properties.sound(SoundType.STONE));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean canBeUpgraded() {
		return false;
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}
}
