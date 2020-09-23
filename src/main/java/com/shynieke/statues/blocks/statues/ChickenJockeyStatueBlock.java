package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ChickenJockeyStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);

	public ChickenJockeyStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		tile.summonMob(new ChickenEntity(EntityType.CHICKEN, worldIn));
		tile.summonMob(new ZombieEntity(EntityType.ZOMBIE, worldIn));
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
	}

	@Override
	public String getLootName() {
		return "chicken_jockey";
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
