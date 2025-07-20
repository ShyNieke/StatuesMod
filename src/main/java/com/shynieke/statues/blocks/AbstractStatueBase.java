package com.shynieke.statues.blocks;

import com.shynieke.statues.blockentities.AbstractStatueBlockEntity;
import com.shynieke.statues.blockentities.StatueBlockEntity;
import com.shynieke.statues.registry.StatueBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStatueBase extends AbstractBaseBlock implements EntityBlock {
	private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	public static final BooleanProperty INTERACTIVE = BooleanProperty.create("interactive");

	public AbstractStatueBase(Block.Properties builder) {
		super(builder.strength(0.6F));
		this.registerDefaultState(this.defaultBlockState()
				.setValue(FACING, Direction.NORTH)
				.setValue(WATERLOGGED, Boolean.FALSE)
				.setValue(INTERACTIVE, false));
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
		if (!level.isClientSide) {
			if (canPlaySound(level, pos, state)) {
				level.playSound(null, pos, getSound(state), SoundSource.NEUTRAL, 1F, getPitch());
			}
		}
		return super.useWithoutItem(state, level, pos, player, result);
	}
	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
	                                          InteractionHand hand, BlockHitResult result) {
		if (state.getValue(INTERACTIVE) && hand == InteractionHand.MAIN_HAND) {
			if (!level.isClientSide && (getBE(level, pos) != null)) {
				return getBE(level, pos).interact(level, pos, state, player, hand, result);
			}
		}
		return InteractionResult.SUCCESS;
	}

	public StatueBlockEntity getBE(BlockGetter getter, BlockPos pos) {
		if (getter.getBlockEntity(pos) instanceof StatueBlockEntity statueBlockEntity) {
			return statueBlockEntity;
		}
		return null;
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		StatueBlockEntity statueBlockEntity = getBE(level, pos);
		if (statueBlockEntity != null && statueBlockEntity.getUpgradeLevel("glowing") > 0) {
			return statueBlockEntity.getUpgradeLevel("glowing");
		}
		return super.getLightEmission(state, level, pos);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StatueBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (state.getValue(INTERACTIVE)) {
			return createStatueTicker(level, blockEntityType, StatueBlockEntities.STATUE.get());
		} else {
			return null;
		}
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createStatueTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends StatueBlockEntity> blockEntityType1) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType1, StatueBlockEntity::serverTick);
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
		ItemStack stack = super.getCloneItemStack(level, pos, state, includeData, player);
		if (level.getBlockEntity(pos) instanceof AbstractStatueBlockEntity statueBlockEntity) {
			statueBlockEntity.saveToItem(stack, level.registryAccess());
		}
		return stack;
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (state.getValue(INTERACTIVE) && level.getBlockEntity(pos) instanceof StatueBlockEntity statueBlockEntity) {
			if (!level.isClientSide && !player.hasInfiniteMaterials()) {
				ItemStack itemstack = new ItemStack(this.asItem());
				statueBlockEntity.saveToItem(itemstack, level.registryAccess());

				ItemEntity itementity = new ItemEntity(level,
						(double) pos.getX() + 0.5D,
						(double) pos.getY() + 0.5D,
						(double) pos.getZ() + 0.5D, itemstack);
				itementity.setDefaultPickUpDelay();
				level.addFreshEntity(itementity);
			}
		}

		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		if (state.getValue(INTERACTIVE)) {
			return new ArrayList<>();
		}
		return super.getDrops(state, builder);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, INTERACTIVE);
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state) {
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

	public LivingEntity adjustSpawnedEntity(LivingEntity livingEntity) {
		return livingEntity;
	}

	public SoundEvent getSound(BlockState state) {
		return SoundEvents.ANVIL_LAND;
	}

	public float getPitch() {
		return this.isBaby() ? (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.5F : (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F;
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
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, @Nullable Orientation orientation, boolean isMoving) {
		if (!level.isClientSide) {
			if (canPlaySound(level, pos, state) && level.hasNeighborSignal(pos)) {
				level.playSound(null, pos, getSound(state), SoundSource.NEUTRAL, 1F,
						(level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.5F);
			}
		}
		super.neighborChanged(state, level, pos, blockIn, orientation, isMoving);
	}
}
