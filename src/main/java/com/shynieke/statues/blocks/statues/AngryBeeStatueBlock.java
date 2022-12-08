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

public class AngryBeeStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(4.0D, 6.0D, 4.0D, 12.0D, 11D, 12.0D);

	public AngryBeeStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.BEE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.BEE_LOOP;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
