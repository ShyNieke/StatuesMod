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

public class PufferfishStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);
	private static final VoxelShape SHAPE_MEDIUM = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D);
	private static final VoxelShape SHAPE_BIG = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10D, 13.0D);
	private final int size;

	public PufferfishStatueBlock(Properties builder, int size) {
		super(builder.sound(SoundType.STONE));
		this.size = size;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (size) {
			default -> SHAPE;
			case 1 -> SHAPE_MEDIUM;
			case 2 -> SHAPE_BIG;
		};
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.PUFFERFISH;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.PUFFER_FISH_BLOW_UP;
	}
}
