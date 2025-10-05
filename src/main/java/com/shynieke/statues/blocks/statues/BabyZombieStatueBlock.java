package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BabyZombieStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 8.5D, 9.5D);

	public BabyZombieStatueBlock(Block.Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (this.isDecorative(state)) {
			BlockState blockState = level.getBlockState(pos.below());
			if (blockState.is(Blocks.LAPIS_BLOCK)) {
				level.addParticle(ParticleTypes.EXPLOSION, pos.below().getX(), pos.below().getY(), pos.below().getZ(), 0.0D, 0.0D, 0.0D);
				level.setBlockAndUpdate(pos.below(), StatueRegistry.FLOOD_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				level.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.VILLAGER_YES, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
			if (blockState.is(StatueRegistry.CHICKEN_STATUE.get()) && isDecorative(blockState)) {
				level.addParticle(ParticleTypes.EXPLOSION, pos.below().getX(), pos.below().getY(), pos.below().getZ(), 0.0D, 0.0D, 0.0D);
				level.setBlockAndUpdate(pos.below(), StatueRegistry.CHICKEN_JOCKEY_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				level.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.VILLAGER_YES, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
		}
		super.setPlacedBy(level, pos, state, placer, stack);
	}

	public boolean isDecorative(BlockState state) {
		return !state.getValue(INTERACTIVE).booleanValue();
	}

	@Override
	public boolean isBaby() {
		return true;
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
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
