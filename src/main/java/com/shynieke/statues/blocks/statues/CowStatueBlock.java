package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueBlockEntity;
import net.minecraft.core.BlockPos;
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

public class CowStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D);

	public CowStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		tile.cowBehavior(playerIn, pos, handIn);
	}

	@Override
	public String getLootName() {
		return "cow";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.COW;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.COW_AMBIENT;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
