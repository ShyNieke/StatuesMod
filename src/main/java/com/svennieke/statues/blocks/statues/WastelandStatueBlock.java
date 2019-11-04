package com.svennieke.statues.blocks.statues;

import com.svennieke.statues.blocks.AbstractStatueBase;
import com.svennieke.statues.recipes.StatueLootList;
import com.svennieke.statues.tiles.StatueTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class WastelandStatueBlock extends AbstractStatueBase {

	public WastelandStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		PigEntity pig = new PigEntity(EntityType.PIG, worldIn);
		pig.setCustomName(new StringTextComponent("Wasteland Pig"));
		tile.summonMob(pig);
	}

	@Override
	public String getLootName() {
		return "wasteland_pig";
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}
}
