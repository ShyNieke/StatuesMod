package com.shynieke.statues.blocks.decorative;

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

public class EndermiteStatueBlock extends AbstractStatueBase {

	private static final VoxelShape SHAPE = Block.box(0.1, 0, 0.1, 16, 4, 16);

	public EndermiteStatueBlock(Properties properties) {
		super(properties.sound(SoundType.STONE).isRedstoneConductor(EndermiteStatueBlock::isntSolid));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ENDERMITE_AMBIENT;
	}

	@Override
	public boolean canBeUpgraded() {
		return false;
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.ENDERMITE;
	}

	private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
		return false;
	}
}
