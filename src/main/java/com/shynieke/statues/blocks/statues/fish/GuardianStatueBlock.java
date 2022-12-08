package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GuardianStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.box(3.0D, 0.0D, 0.0D, 13.0D, 14.0D, 16.0D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.box(0.0D, 0.0D, 3.0D, 16.0D, 14.0D, 13.0D);

	public GuardianStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.GUARDIAN;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.GUARDIAN_AMBIENT;
	}
}
