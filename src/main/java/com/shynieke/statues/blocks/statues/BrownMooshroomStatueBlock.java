package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BrownMooshroomStatueBlock extends MooshroomStatueBlock {
	public BrownMooshroomStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity blockEntity, BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		blockEntity.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
		blockEntity.mooshroomBehavior(playerIn, pos, handIn);
	}

	@Override
	public String getLootName() {
		return "red_mooshroom";
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.COW_AMBIENT;
	}
}
