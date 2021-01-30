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
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class PigStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.5D, 11.0D);

	public PigStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if(this.isDecorative(state) && placer != null) {
			Block block = worldIn.getBlockState(pos.down()).getBlock();
			if (block.isIn(Tags.Blocks.SAND)) {
				BlockPos downPos = pos.down();
				worldIn.addParticle(ParticleTypes.EXPLOSION, downPos.getX(), downPos.getY(), downPos.getZ(), 1.0D, 0.0D, 0.0D);
				worldIn.setBlockState(pos.down(), StatueRegistry.WASTELAND_STATUE.get().getDefaultState().with(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite()));
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	public boolean isDecorative(BlockState state) {
		return !state.get(INTERACTIVE).booleanValue();
	}

	@Override
	public void executeStatueBehavior(StatueTile tile, BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
	}

	@Override
	public String getLootName() {
		return "pig";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.PIG;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ENTITY_PIG_AMBIENT;
	}
}
