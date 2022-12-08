package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KingCluckStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);

	public KingCluckStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public LivingEntity adjustSpawnedEntity(LivingEntity livingEntity) {
		if (livingEntity instanceof Chicken chicken) {
			chicken.setCustomName(Component.literal("King Cluck").withStyle(ChatFormatting.GOLD));
			return chicken;
		}
		return livingEntity;
	}

	@Override
	public boolean isHiddenStatue() {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.CHICKEN_AMBIENT;
	}
}
