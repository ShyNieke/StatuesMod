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

public class SnowGolemStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 11.5D, 10.0D);

	public SnowGolemStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.SNOW_GOLEM;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.SNOW_GOLEM_AMBIENT;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
