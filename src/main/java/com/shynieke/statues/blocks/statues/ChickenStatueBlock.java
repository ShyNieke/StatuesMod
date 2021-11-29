package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ChickenStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);

	public ChickenStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if(this.isDecorative(state) && placer != null) {
			Block block = worldIn.getBlockState(pos.below()).getBlock();
			if (block == Blocks.GOLD_BLOCK) {
				BlockPos downPos = pos.below();
				worldIn.addParticle(ParticleTypes.EXPLOSION, downPos.getX(), downPos.getY(), downPos.getZ(), 1.0D, 0.0D, 0.0D);
				worldIn.setBlockAndUpdate(pos.below(), StatueRegistry.KING_CLUCK_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
				worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
		}
		super.setPlacedBy(worldIn, pos, state, placer, stack);
	}

	public boolean isDecorative(BlockState state) {
		return !state.getValue(INTERACTIVE).booleanValue();
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

		tile.summonMob(new ChickenEntity(EntityType.CHICKEN, worldIn));
	}

	@Override
	public String getLootName() {
		return "chicken";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.CHICKEN;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.CHICKEN_AMBIENT;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
