package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class PandaStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 1.0D, 12.0D, 11D, 15.0D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 4.0D, 15.0D, 11D, 12.0D);

	public PandaStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
	}

	@Override
	public String getLootName() {
		return "panda";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.PANDA;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ENTITY_PANDA_AMBIENT;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.get(HORIZONTAL_FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}
}
