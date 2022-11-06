package com.shynieke.statues.blocks.statues.fish;

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

public class DolphinStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.5D, 2.0D, 14.0D, 7.0D, 14.0D);

	public DolphinStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.DOLPHIN;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.DOLPHIN_AMBIENT;
	}
}
