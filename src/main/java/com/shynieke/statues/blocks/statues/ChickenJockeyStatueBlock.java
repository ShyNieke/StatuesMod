package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChickenJockeyStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);

	public ChickenJockeyStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		tile.summonMob(new Chicken(EntityType.CHICKEN, worldIn));
		tile.summonMob(new Zombie(EntityType.ZOMBIE, worldIn));
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ZOMBIE_AMBIENT;
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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
