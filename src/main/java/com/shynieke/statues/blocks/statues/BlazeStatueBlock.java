package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlazeStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 1.0D, 4.0D, 12.0D, 9.5D, 12.0D);

	public BlazeStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE).setLightLevel((p_235418_0_) -> {
			return 8;
		}));
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, pos, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F + 1.5F);
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
//		tile.FakeMobs(new FakeZombie(worldIn), worldIn, pos, true);
	}

	@Override
	public String getLootName() {
		return "blaze";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.BLAZE;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.SHAPE;
	}
}
