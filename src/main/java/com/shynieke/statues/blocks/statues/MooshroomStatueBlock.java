package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MooshroomStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.box(5.0D, 0.0D, 3.0D, 11.0D, 12D, 13.0D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.box(3.0D, 0.0D, 5.0D, 13.0D, 12D, 11.0D);

	public MooshroomStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity blockEntity, BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		blockEntity.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		blockEntity.mooshroomBehavior(playerIn, pos, handIn);
	}

	@Override
	public String getLootName() {
		return "mooshroom";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.MOOSHROOM;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.COW_AMBIENT;
	}
}
