package com.shynieke.statues.blocks.statues.fish;

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

public class CodStatueBlock extends FishStatueBlock {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);

	public CodStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE), 1);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public boolean hasTileEntity(BlockState state) {
		return state.getValue(INTERACTIVE).booleanValue();
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.COD;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.COD_FLOP;
	}
}
