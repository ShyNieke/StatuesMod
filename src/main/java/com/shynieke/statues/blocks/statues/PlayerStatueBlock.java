package com.shynieke.statues.blocks.statues;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.init.StatueBlockEntities;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueTags;
import com.shynieke.statues.items.PlayerStatueSpawnItem;
import com.shynieke.statues.tiles.PlayerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class PlayerStatueBlock extends AbstractBaseBlock {

	private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	public static final BooleanProperty ONLINE = BooleanProperty.create("online");

	public PlayerStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE).setValue(ONLINE, false));
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
		return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType1, PlayerBlockEntity::serverTick);
	}

	private PlayerBlockEntity getBE(Level world, BlockPos pos) {
		return (PlayerBlockEntity) world.getBlockEntity(pos);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, BlockEntity te, ItemStack stack) {
		if (te instanceof PlayerBlockEntity && ((Nameable)te).hasCustomName()) {
			PlayerBlockEntity tile = (PlayerBlockEntity)te;
			player.causeFoodExhaustion(0.005F);

			if (worldIn.isClientSide)
				return;

			if (this == Blocks.AIR)
				return;

			ItemStack itemstack = new ItemStack(this);
			itemstack.setHoverName(((Nameable)tile).getName());

			if (tile.getPlayerProfile() != null) {
				CompoundTag stackTag = itemstack.getTag() != null ? itemstack.getTag() : new CompoundTag();
				CompoundTag nbttagcompound = new CompoundTag();
				NbtUtils.writeGameProfile(nbttagcompound, tile.getPlayerProfile());
				stackTag.put("PlayerProfile", nbttagcompound);
				itemstack.setTag(stackTag);
				itemstack.setHoverName(((Nameable)tile).getName());
			}

			popResource(worldIn, pos, itemstack);

			if(tile.getComparatorApplied()) {
				popResource(worldIn, pos, new ItemStack(Blocks.COMPARATOR.asItem()));
			}
		} else {
			super.playerDestroy(worldIn, player, pos, state, null, stack);
		}
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean p_196243_5_) {
		if (state.hasBlockEntity() && newState.getBlock() != StatueRegistry.PLAYER_STATUE.get()) {
			worldIn.removeBlockEntity(pos);
		}
	}

	@Override
	public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
		BlockEntity tileentity = level.getBlockEntity(pos);
		if (tileentity instanceof PlayerBlockEntity) {
			return getStatueWithName(level, pos, state);
		} else {
			return new ItemStack(state.getBlock());
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
		BlockEntity tileentity = level.getBlockEntity(pos);
		if (tileentity instanceof PlayerBlockEntity) {
			return getStatueWithName(level, pos, state);
		} else {
			return super.getPickBlock(state, target, level, pos, player);
		}
	}

	private ItemStack getStatueWithName(BlockGetter level, BlockPos pos, BlockState state) {
		BlockEntity tileentity = level.getBlockEntity(pos);
		if (tileentity instanceof PlayerBlockEntity) {
			PlayerBlockEntity playerTile = (PlayerBlockEntity)tileentity;
			ItemStack stack = new ItemStack(state.getBlock());

			GameProfile profile = playerTile.getPlayerProfile();
			if (profile != null) {
				CompoundTag tag = new CompoundTag();

				if (!StringUtil.isNullOrEmpty(profile.getName())) {
					GameProfile gameprofile = new GameProfile((UUID)null, profile.getName());
					PlayerBlockEntity.updateGameprofile(gameprofile, (newProfile) -> {
						tag.put("PlayerProfile", NbtUtils.writeGameProfile(new CompoundTag(), newProfile));
					});
				}
				stack.setTag(tag);
			}

			return stack.setHoverName(playerTile.getName());
		} else {
			return new ItemStack(state.getBlock());
		}
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state.setValue(ONLINE, false), placer, stack);

		if(!worldIn.isClientSide && getBE(worldIn, pos) != null) {
			PlayerBlockEntity tile = getBE(worldIn, pos);
			if(stack.hasCustomHoverName()) {
				String stackName = stack.getHoverName().getContents();
				boolean spaceFlag = stackName.contains(" ");
				boolean emptyFlag = stackName.isEmpty();

				if(!spaceFlag && !emptyFlag) {
					GameProfile newProfile = new GameProfile((UUID)null, stackName);

					if (stack.hasTag() && stack.getTag() != null) {
						CompoundTag tag = stack.getTag();
						if (tag.contains("PlayerProfile")) {
							GameProfile foundProfile = NbtUtils.readGameProfile(tag.getCompound("PlayerProfile"));
							if(foundProfile != null && foundProfile.getName().equalsIgnoreCase(stackName)) {
								newProfile = foundProfile;
							}
						}
					}

					tile.setPlayerProfile(newProfile);
				}
			} else {
				if(placer instanceof Player) {
					Player player = (Player) placer;
					tile.setPlayerProfile(player.getGameProfile());
				} else {
					tile.setPlayerProfile(new GameProfile((UUID)null, "steve"));
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if(Screen.hasShiftDown()){
			if(stack.hasTag()) {
				CompoundTag tag = stack.getTag();
				MutableComponent userComponent = new TextComponent("Username: ").withStyle(ChatFormatting.GOLD);
				userComponent.append(stack.getHoverName().plainCopy().withStyle(ChatFormatting.WHITE));
				tooltip.add(userComponent);

				if(tag != null && tag.contains("PlayerProfile")) {
					CompoundTag profileTag = (CompoundTag)tag.get("PlayerProfile");
					if(profileTag != null) {
						GameProfile gameprofile = NbtUtils.readGameProfile(profileTag);

						if(gameprofile != null && gameprofile.isComplete()) {
							MutableComponent UUIDComponent = new TextComponent("UUID: ").withStyle(ChatFormatting.GOLD);
							UUIDComponent.append(new TextComponent(gameprofile.getId().toString()).withStyle(ChatFormatting.WHITE));
							tooltip.add(UUIDComponent);
						}
					}
				}
			}
		}
	}

	@Override
	public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
		if (blockState.getValue(ONLINE))
			return 15;
		return 0;
	}

	@Override
	public boolean isSignalSource(BlockState state)
	{
		return state.getValue(ONLINE);
	}

	@Override
	public boolean shouldCheckWeakPower(BlockState state, LevelReader world, BlockPos pos, Direction side) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> blockStateBuilder) {
		blockStateBuilder.add(FACING, WATERLOGGED, ONLINE);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = playerIn.getItemInHand(hand);
		GameProfile tileProfile = getBE(level, pos).getPlayerProfile();
		PlayerBlockEntity tile = getBE(level, pos);
		if(!level.isClientSide && tile != null && tileProfile != null) {
			String playerName = tileProfile.getName();
			boolean onlineFlag = level.getPlayerByUUID(tileProfile.getId()) != null;

			if(playerIn.isShiftKeyDown()) {
				if(tile.getComparatorApplied()) {
					tile.setComparatorApplied(false);
					ItemStack comparatorStack = new ItemStack(Items.COMPARATOR);
					if(!playerIn.addItem(comparatorStack)) {
						level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), comparatorStack));
					}
				}
				return InteractionResult.SUCCESS;
			} else {
				if(StatuesConfig.COMMON.playerCompass.get()) {
					if(stack.getItem() == Items.COMPASS || stack.getItem() == StatueRegistry.PLAYER_COMPASS.get()) {
						boolean isPlayerCompass = stack.getItem() == StatueRegistry.PLAYER_COMPASS.get();
						if(onlineFlag) {
							ItemStack playerCompass = isPlayerCompass ? stack : new ItemStack(StatueRegistry.PLAYER_COMPASS.get());
							CompoundTag locationTag = new CompoundTag();

							Player player = level.getPlayerByUUID(tileProfile.getId());
							if(player != null && player.level.dimension().location().equals(playerIn.level.dimension().location())) {
								BlockPos playerPos = player.blockPosition();
								locationTag.putLong("lastPlayerLocation", playerPos.asLong());
								locationTag.putString("playerTracking", tileProfile.getName());

								playerCompass.setTag(locationTag);

								if(!isPlayerCompass) {
									if(!playerIn.getAbilities().instabuild) {
										stack.shrink(1);
									}
									if (stack.isEmpty()) {
										playerIn.setItemInHand(hand, playerCompass);
									} else if (!playerIn.getInventory().add(playerCompass)) {
										playerIn.drop(playerCompass, false);
									}
								}
							} else {
								playerIn.sendMessage(new TranslatableComponent("statues:player.compass.dimension.failure", ChatFormatting.GOLD + playerName), Util.NIL_UUID);
							}

						} else {
							playerIn.sendMessage(new TranslatableComponent("statues:player.compass.offline", ChatFormatting.GOLD + playerName), Util.NIL_UUID);
						}
						return InteractionResult.SUCCESS;
					}
					if(stack.getItem() == Items.COMPARATOR) {
						if(!tile.getComparatorApplied()) {
							if(!playerIn.getAbilities().instabuild) {
								stack.shrink(1);
							}
							tile.setComparatorApplied(true);
							tile.updateOnline();
							return InteractionResult.SUCCESS;
						}
					}
					if(stack.is(StatueTags.PLAYER_UPGRADE_ITEM)) {
						if (level instanceof ServerLevel) {
							ServerLevel serverworld = (ServerLevel) level;
							PlayerStatue playerStatueEntity = StatueRegistry.PLAYER_STATUE_ENTITY.get().create(serverworld, stack.getTag(), tile.getName(), playerIn, pos, MobSpawnType.SPAWN_EGG, true, true);
							if (playerStatueEntity == null) {
								return InteractionResult.FAIL;
							}
							serverworld.addFreshEntityWithPassengers(playerStatueEntity);
							float f = (float) Mth.floor((Mth.wrapDegrees(playerIn.getYRot() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
							playerStatueEntity.setGameProfile(tile.getPlayerProfile());
							playerStatueEntity.moveTo(playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), f, 0.0F);
							PlayerStatueSpawnItem.applyRandomRotations(playerStatueEntity, level.random);
							level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
							level.addFreshEntity(playerStatueEntity);
							level.playSound((Player)null, playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);

							if(!playerIn.getAbilities().instabuild) {
								stack.shrink(1);
							}
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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}