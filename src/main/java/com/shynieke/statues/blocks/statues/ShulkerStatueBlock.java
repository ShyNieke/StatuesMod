package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blockentities.ShulkerStatueBlockEntity;
import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShulkerStatueBlock extends AbstractStatueBase {
	private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);

	public ShulkerStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
	}

	@Override
	public EntityType<?> getEntity() {
		return EntityType.SHULKER;
	}

	@Override
	public SoundEvent getSound(BlockState state) {
		return SoundEvents.SHULKER_AMBIENT;
	}

	@Override
	public StatueBlockEntity getBE(BlockGetter getter, BlockPos pos) {
		return super.getBE(getter, pos);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ShulkerStatueBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (state.getValue(INTERACTIVE).booleanValue()) {
			return createStatueTicker(level, blockEntityType, StatueBlockEntities.SHULKER_STATUE.get());
		} else {
			return null;
		}
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createStatueTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends StatueBlockEntity> blockEntityType1) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType1, ShulkerStatueBlockEntity::serverTick);
	}
}
