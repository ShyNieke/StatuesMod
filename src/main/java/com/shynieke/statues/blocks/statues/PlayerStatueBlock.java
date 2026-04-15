package com.shynieke.statues.blocks.statues;

import com.shynieke.statues.blockentities.PlayerBlockEntity;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.datacomponent.PlayerCompassData;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.items.PlayerStatueSpawnItem;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueDataComponents;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class PlayerStatueBlock extends AbstractBaseBlock {
	private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	public static final BooleanProperty ONLINE = BooleanProperty.create("online");

	public PlayerStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
		this.registerDefaultState(this.defaultBlockState()
				.setValue(FACING, Direction.NORTH)
				.setValue(WATERLOGGED, Boolean.FALSE)
				.setValue(ONLINE, false)
		);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PlayerBlockEntity(pos, state);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return createStatueTicker(level, blockEntityType, StatueBlockEntities.PLAYER.get());
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createStatueTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends PlayerBlockEntity> blockEntityType1) {
		return level.isClientSide() ? null : createTickerHelper(blockEntityType, blockEntityType1, PlayerBlockEntity::serverTick);
	}

	private PlayerBlockEntity getBE(Level level, BlockPos pos) {
		return (PlayerBlockEntity) level.getBlockEntity(pos);
	}

	@NotNull
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.INVISIBLE;
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity be, ItemStack stack) {
		if (be instanceof PlayerBlockEntity playerBlockEntity && ((Nameable) be).hasCustomName()) {
			player.causeFoodExhaustion(0.005F);

			if (level.isClientSide())
				return;

			if (this == Blocks.AIR)
				return;

			ItemStack itemstack = new ItemStack(this);
			itemstack.set(DataComponents.CUSTOM_NAME, ((Nameable) playerBlockEntity).getName());

			playerBlockEntity.saveToItem(itemstack, level.registryAccess());

			popResource(level, pos, itemstack);

			if (playerBlockEntity.getComparatorApplied()) {
				popResource(level, pos, new ItemStack(Blocks.COMPARATOR.asItem()));
			}
		} else {
			super.playerDestroy(level, player, pos, state, null, stack);
		}
	}

	@NotNull
	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
		ItemStack stack = super.getCloneItemStack(level, pos, state, includeData, player);
		if (level.getBlockEntity(pos) instanceof PlayerBlockEntity playerBlockEntity) {
			playerBlockEntity.saveToItem(stack, level.registryAccess());
		}
		return stack;
	}

	@Override
	public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		if (blockState.getValue(ONLINE))
			return 15;
		return 0;
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return state.getValue(ONLINE);
	}

	@Override
	public boolean shouldCheckWeakPower(BlockState state, SignalGetter level, BlockPos pos, Direction side) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> blockStateBuilder) {
		blockStateBuilder.add(FACING, WATERLOGGED, ONLINE);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult result) {
		ResolvableProfile resolvableProfile = getBE(level, pos).getPlayerProfile();
		PlayerBlockEntity playerBlockEntity = getBE(level, pos);
		if (playerIn instanceof ServerPlayer serverPlayer && playerBlockEntity != null && resolvableProfile != null) {
			String playerName = resolvableProfile.name().orElse("Unknown");
			UUID id = resolvableProfile.partialProfile().id();
			boolean onlineFlag = level.getPlayerByUUID(id) != null;

			if (playerIn.isShiftKeyDown()) {
				if (playerBlockEntity.getComparatorApplied()) {
					playerBlockEntity.setComparatorApplied(false);
					ItemStack comparatorStack = new ItemStack(Items.COMPARATOR);
					if (!playerIn.addItem(comparatorStack)) {
						level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), comparatorStack));
					}
				}
				return InteractionResult.SUCCESS;
			} else {
				if (StatuesConfig.COMMON.playerCompass.get()) {
					if (stack.getItem() == Items.COMPASS || stack.getItem() == StatueRegistry.PLAYER_COMPASS.get()) {
						boolean isPlayerCompass = stack.getItem() == StatueRegistry.PLAYER_COMPASS.get();
						if (onlineFlag) {
							ItemStack playerCompass = isPlayerCompass ? stack : new ItemStack(StatueRegistry.PLAYER_COMPASS.get());
							Player player = level.getPlayerByUUID(id);
							if (player != null && player.level().dimension().identifier().equals(playerIn.level().dimension().identifier())) {
								GlobalPos playerPos = GlobalPos.of(player.level().dimension(), player.blockPosition());
								playerCompass.set(StatueDataComponents.PLAYER_COMPASS_DATA.get(), new PlayerCompassData(playerPos, resolvableProfile.name().orElse("Unknown")));

								if (!isPlayerCompass) {
									stack.consume(1, playerIn);
									if (stack.isEmpty()) {
										playerIn.setItemInHand(hand, playerCompass);
									} else if (!playerIn.getInventory().add(playerCompass)) {
										playerIn.drop(playerCompass, false);
									}
								}
							} else {
								serverPlayer.sendSystemMessage(Component.translatable("statues.player.compass.dimension.failure", ChatFormatting.GOLD + playerName));
							}

						} else {
							serverPlayer.sendSystemMessage(Component.translatable("statues.player.compass.offline", ChatFormatting.GOLD + playerName));
						}
						return InteractionResult.SUCCESS;
					}
					if (stack.getItem() == Items.COMPARATOR) {
						if (!playerBlockEntity.getComparatorApplied()) {
							stack.consume(1, playerIn);
							playerBlockEntity.setComparatorApplied(true);
							playerBlockEntity.updateOnline();
							return InteractionResult.SUCCESS;
						}
					}
					if (stack.is(StatueTags.PLAYER_UPGRADE_ITEM)) {
						if (level instanceof ServerLevel serverLevel) {
							Consumer<PlayerStatue> consumer = EntityType.appendCustomEntityStackConfig((p_263581_) -> {
							}, serverLevel, stack, playerIn);
							PlayerStatue playerStatueEntity = StatueRegistry.PLAYER_STATUE_ENTITY.get().create(serverLevel, consumer, pos, EntitySpawnReason.SPAWN_ITEM_USE, true, true);
							if (playerStatueEntity == null) {
								return InteractionResult.FAIL;
							}

							serverLevel.addFreshEntityWithPassengers(playerStatueEntity);
							float f = (float) Mth.floor((Mth.wrapDegrees(playerIn.getYRot() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
							playerStatueEntity.setResolvableProfile(playerBlockEntity.getPlayerProfile());
							playerStatueEntity.snapTo(playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), f, 0.0F);
							PlayerStatueSpawnItem.applyRandomRotations(playerStatueEntity, level.getRandom());
							level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
							level.addFreshEntity(playerStatueEntity);
							level.playSound((Player) null, playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);

							stack.consume(1, playerIn);
						}
					}
				}
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING))).setValue(ONLINE, false);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING))).setValue(ONLINE, false);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
		super.animateTick(state, level, pos, randomSource);

		if (level.isClientSide()) {
			PlayerBlockEntity playerBlockEntity = getBE(level, pos);
			if (playerBlockEntity != null && playerBlockEntity.getPlayerProfile() != null &&
					com.shynieke.statues.client.ClientHandler.TRANSLATORS.contains(playerBlockEntity.getPlayerProfile().partialProfile().id())) {
				level.addParticle(ParticleTypes.ENCHANT,
						(double) pos.getX() + 0.5D, (double) pos.getY() + 2.0D, (double) pos.getZ() + 0.5D,
						(double) ((float) (level.getRandom().nextFloat() - 0.5) * 3 + randomSource.nextFloat()) - 0.5D,
						(double) ((float) (level.getRandom().nextFloat() - 0.5) * 3 - randomSource.nextFloat() - 1.0F),
						(double) ((float) (level.getRandom().nextFloat() - 0.5) * 3 + randomSource.nextFloat()) - 0.5D);
			}
		}
	}

	@Override
	protected VoxelShape getOcclusionShape(BlockState state) {
		return Shapes.empty();
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}
}