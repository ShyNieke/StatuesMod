package com.shynieke.statues.blocks.statues;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.entity.PlayerStatueEntity;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueTags;
import com.shynieke.statues.items.PlayerStatueItem;
import com.shynieke.statues.tiles.PlayerTile;
import io.netty.util.internal.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.INameable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new PlayerTile();
	}

	private PlayerTile getTE(World world, BlockPos pos) {
		return (PlayerTile) world.getBlockEntity(pos);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void playerDestroy(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
		if (te instanceof PlayerTile && ((INameable)te).hasCustomName()) {
			PlayerTile tile = (PlayerTile)te;
			player.causeFoodExhaustion(0.005F);

			if (worldIn.isClientSide)
				return;

			if (this == Blocks.AIR)
				return;

			ItemStack itemstack = new ItemStack(this);
			itemstack.setHoverName(((INameable)tile).getName());

			if (tile.getPlayerProfile() != null) {
				CompoundNBT stackTag = itemstack.getTag() != null ? itemstack.getTag() : new CompoundNBT();
				CompoundNBT nbttagcompound = new CompoundNBT();
				NBTUtil.writeGameProfile(nbttagcompound, tile.getPlayerProfile());
				stackTag.put("PlayerProfile", nbttagcompound);
				itemstack.setTag(stackTag);
				itemstack.setHoverName(((INameable)tile).getName());
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
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean p_196243_5_) {
		if (state.hasTileEntity() && newState.getBlock() != StatueRegistry.PLAYER_STATUE.get()) {
			worldIn.removeBlockEntity(pos);
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader level, BlockPos pos, PlayerEntity player) {
		TileEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof PlayerTile) {
			return getStatueWithName(level, pos, state);
		} else {
			return new ItemStack(state.getBlock());
		}
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader level, BlockPos pos, BlockState state) {
		TileEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof PlayerTile) {
			return getStatueWithName(level, pos, state);
		} else {
			return super.getCloneItemStack(level, pos, state);
		}
	}

	private ItemStack getStatueWithName(IBlockReader level, BlockPos pos, BlockState state) {
		TileEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof PlayerTile) {
			PlayerTile playerBlockEntity = (PlayerTile) blockEntity;
			ItemStack stack = new ItemStack(state.getBlock());

			GameProfile profile = playerBlockEntity.getPlayerProfile();
			if (profile != null) {
				CompoundNBT tag = new CompoundNBT();

				if (!StringUtil.isNullOrEmpty(profile.getName())) {
					GameProfile gameprofile = new GameProfile((UUID)null, profile.getName());
					gameprofile = PlayerTile.updateGameProfile(gameprofile);
					tag.put("PlayerProfile", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
				}
				stack.setTag(tag);
			}

			return stack.setHoverName(playerBlockEntity.getName());
		} else {
			return new ItemStack(state.getBlock());
		}
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state.setValue(ONLINE, false), placer, stack);

		if(!worldIn.isClientSide && getTE(worldIn, pos) != null) {
			PlayerTile tile = getTE(worldIn, pos);
			if(stack.hasCustomHoverName()) {
				String stackName = stack.getHoverName().getContents();
				boolean spaceFlag = stackName.contains(" ");
				boolean emptyFlag = stackName.isEmpty();

				if(!spaceFlag && !emptyFlag) {
					GameProfile newProfile = new GameProfile((UUID)null, stackName);

					if (stack.hasTag() && stack.getTag() != null) {
						CompoundNBT tag = stack.getTag();
						if (tag.contains("PlayerProfile")) {
							GameProfile foundProfile = NBTUtil.readGameProfile(tag.getCompound("PlayerProfile"));
							if(foundProfile != null && foundProfile.getName().equalsIgnoreCase(stackName)) {
								newProfile = foundProfile;
							}
						}
					}

					tile.setPlayerProfile(newProfile);
				}
			} else {
				if(placer instanceof PlayerEntity) {
					PlayerEntity player = (PlayerEntity) placer;
					tile.setPlayerProfile(player.getGameProfile());
				} else {
					tile.setPlayerProfile(new GameProfile((UUID)null, "steve"));
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(Screen.hasShiftDown()){
			if(stack.hasTag()) {
				CompoundNBT tag = stack.getTag();
				IFormattableTextComponent userComponent = new StringTextComponent("Username: ").withStyle(TextFormatting.GOLD);
				userComponent.append(stack.getHoverName().plainCopy().withStyle(TextFormatting.WHITE));
				tooltip.add(userComponent);

				if(tag != null && tag.contains("PlayerProfile")) {
					CompoundNBT profileTag = (CompoundNBT)tag.get("PlayerProfile");
					if(profileTag != null) {
						GameProfile gameprofile = NBTUtil.readGameProfile(profileTag);

						if(gameprofile != null && gameprofile.isComplete()) {
							IFormattableTextComponent UUIDComponent = new StringTextComponent("UUID: ").withStyle(TextFormatting.GOLD);
							UUIDComponent.append(new StringTextComponent(gameprofile.getId().toString()).withStyle(TextFormatting.WHITE));
							tooltip.add(UUIDComponent);
						}
					}
				}
			}
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public int getSignal(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		if (blockState.getValue(ONLINE))
			return 15;
		return 0;
	}

	@Override
	public boolean isSignalSource(BlockState state)
	{
		return true;
	}

	@Override
	public boolean canConnectRedstone(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.getValue(ONLINE);
	}

	@Override
	public boolean shouldCheckWeakPower(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(net.minecraft.state.StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, ONLINE);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = playerIn.getItemInHand(hand);
		GameProfile tileProfile = getTE(worldIn, pos).getPlayerProfile();
		PlayerTile tile = getTE(worldIn, pos);
		if(!worldIn.isClientSide && tile != null && tileProfile != null) {
			String playerName = tileProfile.getName();
			boolean onlineFlag = worldIn.getPlayerByUUID(tileProfile.getId()) != null;

			if(playerIn.isShiftKeyDown()) {
				if(tile.getComparatorApplied()) {
					tile.setComparatorApplied(false);
					ItemStack comparatorStack = new ItemStack(Items.COMPARATOR);
					if(!playerIn.addItem(comparatorStack)) {
						worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY() + 0.5, pos.getZ(), comparatorStack));
					}
				}
				return ActionResultType.SUCCESS;
			} else {
				if(StatuesConfig.COMMON.playerCompass.get()) {
					if(stack.getItem() == Items.COMPASS || stack.getItem() == StatueRegistry.PLAYER_COMPASS.get()) {
						boolean isPlayerCompass = stack.getItem() == StatueRegistry.PLAYER_COMPASS.get();
						if(onlineFlag) {
							ItemStack playerCompass = isPlayerCompass ? stack : new ItemStack(StatueRegistry.PLAYER_COMPASS.get());
							CompoundNBT locationTag = new CompoundNBT();

							PlayerEntity player = worldIn.getPlayerByUUID(tileProfile.getId());
							if(player != null && player.level.dimension().location().equals(playerIn.level.dimension().location())) {
								BlockPos playerPos = player.blockPosition();
								locationTag.putLong("lastPlayerLocation", playerPos.asLong());
								locationTag.putString("playerTracking", tileProfile.getName());

								playerCompass.setTag(locationTag);

								if(!isPlayerCompass) {
									if(!playerIn.abilities.instabuild) {
										stack.shrink(1);
									}
									if (stack.isEmpty()) {
										playerIn.setItemInHand(hand, playerCompass);
									} else if (!playerIn.inventory.add(playerCompass)) {
										playerIn.drop(playerCompass, false);
									}
								}
							} else {
								playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.dimension.failure", TextFormatting.GOLD + playerName), Util.NIL_UUID);
							}

						} else {
							playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.offline", TextFormatting.GOLD + playerName), Util.NIL_UUID);
						}
						return ActionResultType.SUCCESS;
					}
					if(stack.getItem() == Items.COMPARATOR) {
						if(!tile.getComparatorApplied()) {
							if(!playerIn.abilities.instabuild) {
								stack.shrink(1);
							}
							tile.setComparatorApplied(true);
							tile.updateOnline();
							return ActionResultType.SUCCESS;
						}
					}
					if(stack.getItem().is(StatueTags.PLAYER_UPGRADE_ITEM)) {
						if (worldIn instanceof ServerWorld) {
							ServerWorld serverworld = (ServerWorld)worldIn;
							PlayerStatueEntity playerStatueEntity = StatueRegistry.PLAYER_STATUE_ENTITY.get().create(serverworld, stack.getTag(), tile.getName(), playerIn, pos, SpawnReason.SPAWN_EGG, true, true);
							if (playerStatueEntity == null) {
								return ActionResultType.FAIL;
							}

							serverworld.addFreshEntityWithPassengers(playerStatueEntity);
							float f = (float) MathHelper.floor((MathHelper.wrapDegrees(playerIn.yRot - 180.0F) + 22.5F) / 45.0F) * 45.0F;
							playerStatueEntity.moveTo(playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), f, 0.0F);
							PlayerStatueItem.applyRandomRotations(playerStatueEntity, worldIn.random);
							playerStatueEntity.setGameProfile(tile.getPlayerProfile());
							worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
							worldIn.addFreshEntity(playerStatueEntity);
							worldIn.playSound((PlayerEntity)null, playerStatueEntity.getX(), playerStatueEntity.getY(), playerStatueEntity.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);

							if(!playerIn.abilities.instabuild) {
								stack.shrink(1);
							}
						}
					}
				}
			}
		}
		return ActionResultType.PASS;
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
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}