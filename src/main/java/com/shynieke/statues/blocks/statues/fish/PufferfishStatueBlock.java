package com.shynieke.statues.blocks.statues.fish;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class PufferfishStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D);
	private static final VoxelShape SHAPE_MEDIUM = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D);
	private static final VoxelShape SHAPE_BIG = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 10D, 13.0D);
	private final int size;

	public PufferfishStatueBlock(Properties builder, int size) {
		super(builder.sound(SoundType.STONE));
		this.size = size;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(size) {
			default:
				return SHAPE;
			case 1:
				return SHAPE_MEDIUM;
			case 2:
				return SHAPE_BIG;
		}
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		tile.giveEffect(pos, playerIn, Effects.POISON);
	}

	@Override
	public String getLootName() {
		return "pufferfish";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.PUFFERFISH;
	}
}
