package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlazeStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(4.0D, 1.0D, 4.0D, 12.0D, 9.5D, 12.0D);

	public BlazeStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE).lightLevel((p_235418_0_) -> 8));
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.BLAZE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.BLAZE_AMBIENT;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
