package com.shynieke.statues.blocks.decorative.charity;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EagleRayStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(1.0D, 0, 1.0D, 15.0D, 6.0D, 15.0D);

	public EagleRayStatueBlock(Properties properties) {
		super(properties.sound(SoundType.STONE));
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.SQUID_AMBIENT;
	}
}
