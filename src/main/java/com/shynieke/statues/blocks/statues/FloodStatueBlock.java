package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FloodStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D);

	public FloodStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.get(HORIZONTAL_FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		Vec3d hitPos = result.getHitVec();

		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		tile.floodBehavior(playerIn, pos, handIn, (float)hitPos.x, (float)hitPos.y, (float)hitPos.z);
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ENTITY_VILLAGER_AMBIENT;
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
