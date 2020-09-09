package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BrownMooshroomStatueBlock extends MooshroomStatueBlock {
	public BrownMooshroomStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		tile.mooshroomBehavior(playerIn, pos, handIn);
	}

	@Override
	public String getLootName() {
		return "red_mooshroom";
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ENTITY_COW_AMBIENT;
	}
}
