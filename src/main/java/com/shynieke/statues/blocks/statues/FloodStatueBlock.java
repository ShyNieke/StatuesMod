package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FloodStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.box(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D);

	public FloodStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Direction direction = state.getValue(FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity blockEntity, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		Vec3 hitPos = result.getLocation();

		blockEntity.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		blockEntity.floodBehavior(playerIn, pos, handIn, (float)hitPos.x, (float)hitPos.y, (float)hitPos.z);
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.VILLAGER_AMBIENT;
	}

	@Override
	public String getLootName() {
		return "flood";
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}
}
