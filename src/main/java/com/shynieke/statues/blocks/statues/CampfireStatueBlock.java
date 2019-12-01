package com.shynieke.statues.blocks.statues;

import com.google.common.collect.ImmutableList;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatuesSounds;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import com.shynieke.statues.util.ListHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.List;

public class CampfireStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SOUTH_EAST_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 7.0D, 16.0D);
	private static final VoxelShape NORTH_WEST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 5.0D, 16.0D, 7.0D, 11.0D);

	public CampfireStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE).lightValue(12));
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.get(HORIZONTAL_FACING);
		return direction.getAxis() == Direction.Axis.X ? NORTH_WEST_SHAPE : SOUTH_EAST_SHAPE;
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.playSound(getRandomCampfire(), pos, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F + 1.5F);
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		tile.summonMob(getGeneral(worldIn));
	}

	public CreeperEntity getGeneral(World worldIn)
	{
		CreeperEntity general = new CreeperEntity(EntityType.CREEPER, worldIn);
		general.setCustomName(new StringTextComponent("General Spazz"));
		CompoundNBT tag = new CompoundNBT();
		tag.putByte("ExplosionRadius", (byte) 0);
		general.writeAdditional(tag);

		return general;
	}

	public static List<SoundEvent> campfire_sounds = ImmutableList.of(
			StatuesSounds.campfire_bye_random,
			StatuesSounds.campfire_cold_random,
			StatuesSounds.campfire_greetings_random,
			StatuesSounds.campfire_hello_random,
			StatuesSounds.campfire_snacks_random);

	public static SoundEvent getRandomCampfire()
	{
		SoundEvent sound = ListHelper.getRandomFromList(campfire_sounds);
		return sound;
	}

	@Override
	public String getLootName() {
		return "etho";
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}
}
