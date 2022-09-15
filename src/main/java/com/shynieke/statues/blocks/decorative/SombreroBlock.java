package com.shynieke.statues.blocks.decorative;

import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SombreroBlock extends AbstractBaseBlock {

	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);

	public SombreroBlock(Properties properties) {
		super(properties.sound(SoundType.STONE));
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		Block block = level.getBlockState(pos.below()).getBlock();
		if (block == Blocks.CACTUS && placer != null) {
			BlockPos downPos = pos.below();
			level.addParticle(ParticleTypes.EXPLOSION, downPos.getX(), downPos.getY(), downPos.getZ(), 1.0D, 0.0D, 0.0D);
			level.setBlockAndUpdate(pos.below(), StatueRegistry.BUMBO_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
		super.setPlacedBy(level, pos, state, placer, stack);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		if (!level.isClientSide && handIn == InteractionHand.MAIN_HAND) {
			if (canPlaySound(level, pos, state)) {
				level.playSound(null, pos, SoundEvents.ANVIL_LAND, SoundSource.NEUTRAL, 1F, getPitch());
			}
		}
		return InteractionResult.SUCCESS;
	}

	public float getPitch() {
		return (this.RANDOM.nextFloat() - this.RANDOM.nextFloat()) * 0.2F + 1.0F;
	}

	public boolean canPlaySound(Level level, BlockPos pos, BlockState state) {
		return level.getBlockState(pos.below()).getBlock() instanceof NoteBlock;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
