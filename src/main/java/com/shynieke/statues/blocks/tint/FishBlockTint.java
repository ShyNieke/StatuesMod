package com.shynieke.statues.blocks.tint;

import com.shynieke.statues.blockentities.TropicalFishBlockEntity;
import com.shynieke.statues.blocks.statues.fish.FishStatueBlock;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FishBlockTint implements BlockTintSource {
	@Override
	public int color(BlockState state) {
		return 0;
	}

	@Override
	public int colorInWorld(BlockState state, BlockAndTintGetter getter, BlockPos pos) {
		int tintIndex = 1;
		if (pos != null) {
			BlockEntity blockEntity = getter.getBlockEntity(pos);
			if (blockEntity instanceof TropicalFishBlockEntity tropicalFishBlockEntity) {
				return tintIndex == 1 ? FishStatueBlock.fromColor(tropicalFishBlockEntity.getMainColor()) : tintIndex == 2 ? FishStatueBlock.fromColor(tropicalFishBlockEntity.getSecondaryColor()) : -1;
			}
		}
		return -1;
	}
}
