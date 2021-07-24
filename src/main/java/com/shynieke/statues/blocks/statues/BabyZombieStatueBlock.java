package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.recipes.StatueLootList;
import com.shynieke.statues.tiles.StatueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BabyZombieStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 8.5D, 9.5D);

	public BabyZombieStatueBlock(Block.Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if(this.isDecorative(state)) {
			BlockState blockState = worldIn.getBlockState(pos.below());
			if (blockState.getBlock() == Blocks.LAPIS_BLOCK) {
				worldIn.addParticle(ParticleTypes.EXPLOSION, pos.below().getX(), pos.below().getY(), pos.below().getZ(), 0.0D, 0.0D, 0.0D);
				worldIn.setBlockAndUpdate(pos.below(), StatueRegistry.FLOOD_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
				worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.VILLAGER_YES, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
			if (blockState.getBlock() == StatueRegistry.CHICKEN_STATUE.get() && isDecorative(blockState)) {
				worldIn.addParticle(ParticleTypes.EXPLOSION, pos.below().getX(), pos.below().getY(), pos.below().getZ(), 0.0D, 0.0D, 0.0D);
				worldIn.setBlockAndUpdate(pos.below(), StatueRegistry.CHICKEN_JOCKEY_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
				worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				worldIn.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.VILLAGER_YES, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
		}
		super.setPlacedBy(worldIn, pos, state, placer, stack);
	}

	public boolean isDecorative(BlockState state) {
		return !state.getValue(INTERACTIVE).booleanValue();
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity tile, BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		tile.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);

//		tile.FakeMobs(new FakeZombie(worldIn), worldIn, pos, true);
	}

	@Override
	public boolean isBaby() {
		return true;
	}

	@Override
	public String getLootName() {
		return "baby_zombie";
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.ZOMBIE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ZOMBIE_AMBIENT;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
