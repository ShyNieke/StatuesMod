package com.shynieke.statues.blocks;

import com.shynieke.statues.blockentities.AbstractStatueBlockEntity;
import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.init.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public abstract class AbstractStatueBase extends AbstractBaseBlock implements EntityBlock {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	public static final BooleanProperty INTERACTIVE = BooleanProperty.create("interactive");

	public AbstractStatueBase(Block.Properties builder) {
		super(builder.strength(0.6F));
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(INTERACTIVE, false));
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {
		if (!level.isClientSide && handIn == InteractionHand.MAIN_HAND) {
			if (canPlaySound(level, pos, state)) {
				level.playSound(null, pos, getSound(state), SoundSource.NEUTRAL, 1F, getPitch());
			}
		}
		if (state.getValue(INTERACTIVE).booleanValue() && handIn == InteractionHand.MAIN_HAND) {
			if (!level.isClientSide && (getTE(level, pos) != null)) {
				executeStatueBehavior(getTE(level, pos), state, level, pos, playerIn, handIn, result);
			}
		}
		return InteractionResult.SUCCESS;
	}

	public StatueBlockEntity getTE(BlockGetter getter, BlockPos pos) {
		return getter.getBlockEntity(pos) instanceof StatueBlockEntity ? (StatueBlockEntity) getter.getBlockEntity(pos) : null;
	}

	public void executeStatueBehavior(StatueBlockEntity blockEntity, BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult result) {

	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		if (state.getValue(INTERACTIVE).booleanValue()) {
			return new StatueBlockEntity(pos, state);
		} else {
			return null;
		}
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (state.getValue(INTERACTIVE).booleanValue()) {
			return createStatueTicker(level, blockEntityType, StatueBlockEntities.STATUE.get());
		} else {
			return null;
		}
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createStatueTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends AbstractStatueBlockEntity> blockEntityType1) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType1, AbstractStatueBlockEntity::serverTick);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter getter, BlockPos pos, BlockState state) {
		ItemStack itemstack = super.getCloneItemStack(getter, pos, state);
		StatueBlockEntity statueBlockEntity = getTE(getter, pos);
		if (statueBlockEntity != null && state.getValue(INTERACTIVE)) {
			CompoundTag compoundnbt = statueBlockEntity.saveToNbt(new CompoundTag());
			if (!compoundnbt.isEmpty()) {
				itemstack.addTagElement("BlockEntityTag", compoundnbt);
			}
		}

		return itemstack;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, INTERACTIVE);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			super.onRemove(state, level, pos, newState, isMoving);
			level.removeBlockEntity(pos);
		}
	}

	public boolean canBeUpgraded() {
		return true;
	}

	public boolean isHiddenStatue() {
		return false;
	}

	public boolean isBaby() {
		return false;
	}

	public EntityType<?> getEntity() {
		return EntityType.EGG;
	}

	public String getLootName() {
		return "baby_zombie";
	}

	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ANVIL_LAND;
	}

	public float getPitch() {
		return this.isBaby() ? (this.RANDOM.nextFloat() - this.RANDOM.nextFloat()) * 0.2F + 1.5F : (this.RANDOM.nextFloat() - this.RANDOM.nextFloat()) * 0.2F + 1.0F;
	}

	public boolean canPlaySound(Level level, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		boolean flag = state.getValue(INTERACTIVE) && (blockEntity instanceof StatueBlockEntity statueBlockEntity && statueBlockEntity.makesSounds());
		boolean flag2 = level.getBlockState(pos.below()).getBlock() instanceof NoteBlock;
		return flag || flag2;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			if (canPlaySound(level, pos, state) && level.hasNeighborSignal(pos)) {
				level.playSound(null, pos, getSound(state), SoundSource.NEUTRAL, 1F, (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.5F);
			}
		}
		super.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
	}
}
