package com.svennieke.statues.blocks.statues;

import com.svennieke.statues.blocks.AbstractStatueBase;
import com.svennieke.statues.recipes.StatueLootList;
import com.svennieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class RabbitStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.5D, 10.0D);

	public RabbitStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		tile.summonMob(getRabbit(worldIn));
	}

	@Override
	public String getLootName() {
		return "rabbit";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.RABBIT;
	}

	public RabbitEntity getRabbit(World worldIn)
	{
		RabbitEntity evilRabbit = new RabbitEntity(EntityType.RABBIT, worldIn);
		evilRabbit.setRabbitType(99);

		return evilRabbit;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.SHAPE;
	}
}
