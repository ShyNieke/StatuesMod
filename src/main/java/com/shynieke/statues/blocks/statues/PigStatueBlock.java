package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class PigStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 5.5D, 11.0D);

	public PigStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (this.isDecorative(state) && placer != null) {
			if (level.getBlockState(pos.below()).is(Tags.Blocks.SAND)) {
				BlockPos downPos = pos.below();
				level.addParticle(ParticleTypes.EXPLOSION, downPos.getX(), downPos.getY(), downPos.getZ(), 1.0D, 0.0D, 0.0D);
				level.setBlockAndUpdate(pos.below(), StatueRegistry.WASTELAND_STATUE.get().defaultBlockState().setValue(FACING, placer.getDirection().getOpposite()));
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
		}
		super.setPlacedBy(level, pos, state, placer, stack);
	}

	public boolean isDecorative(BlockState state) {
		return !state.getValue(INTERACTIVE).booleanValue();
	}

	@Override
	public void executeStatueBehavior(StatueBlockEntity blockEntity, BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		blockEntity.giveItem(StatueLootList.getLootInfo(getLootName()).getLoot(), playerIn);
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
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.PIG_AMBIENT;
	}
}
