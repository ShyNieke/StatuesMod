package com.shynieke.statues.blocks.statues;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class PlayerStatueBlock extends AbstractBaseBlock {

	private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	public static final BooleanProperty ONLINE = BooleanProperty.create("online");

	public PlayerStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
		this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE).with(ONLINE, false));
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
		return (PlayerTile) world.getTileEntity(pos);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderType getRenderType(BlockState p_149645_1_) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te, ItemStack stack) {
		if (te instanceof PlayerTile && ((INameable)te).hasCustomName()) {
			PlayerTile tile = (PlayerTile)te;
			player.addExhaustion(0.005F);

			if (worldIn.isRemote)
				return;

			Item item = this.asItem();

			if (item == Items.AIR)
				return;

			ItemStack itemstack = new ItemStack(item);
			itemstack.setDisplayName(((INameable)tile).getName());

			if (tile.getPlayerProfile() != null) {
				CompoundNBT stackTag = itemstack.getTag() != null ? itemstack.getTag() : new CompoundNBT();
				CompoundNBT nbttagcompound = new CompoundNBT();
				NBTUtil.writeGameProfile(nbttagcompound, tile.getPlayerProfile());
				stackTag.put("PlayerProfile", nbttagcompound);
				itemstack.setTag(stackTag);
				itemstack.setDisplayName(((INameable)tile).getName());
			}

			spawnAsEntity(worldIn, pos, itemstack);

			if(tile.getComparatorApplied()) {
				spawnAsEntity(worldIn, pos, new ItemStack(Blocks.COMPARATOR.asItem()));
			}
		} else {
			super.harvestBlock(worldIn, player, pos, state, null, stack);
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean p_196243_5_) {
		if (state.hasTileEntity() && newState.getBlock() != StatueRegistry.PLAYER_STATUE.get()) {
			worldIn.removeTileEntity(pos);
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof PlayerTile) {
			PlayerTile playerTile = (PlayerTile)tileentity;
			ItemStack stack = new ItemStack(state.getBlock());

			if (playerTile.getPlayerProfile() != null) {
				CompoundNBT tag = stack.getTag() != null ? stack.getTag() : new CompoundNBT();
				CompoundNBT tag2 = new CompoundNBT();
				NBTUtil.writeGameProfile(tag2, playerTile.getPlayerProfile());
				tag.put("PlayerProfile", tag2);
				stack.setTag(tag);
			}

			return stack.setDisplayName(playerTile.getName());
		} else {
			return new ItemStack(state.getBlock());
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state.with(ONLINE, false), placer, stack);

		if(stack.hasDisplayName()) {
			String stackName = stack.getDisplayName().getUnformattedComponentText();
			boolean spaceFlag = stackName.contains(" ");
			boolean emptyFlag = stackName.isEmpty();

			if(!spaceFlag && !emptyFlag) {
				GameProfile newProfile = new GameProfile((UUID)null, stackName);

				if (stack.hasTag() && stack.getTag() != null) {
					CompoundNBT tag = stack.getTag();
					if (tag.contains("PlayerProfile")) {
						GameProfile foundProfile = NBTUtil.readGameProfile(tag.getCompound("PlayerProfile"));
						if(foundProfile.getName().equalsIgnoreCase(stackName)) {
							newProfile = foundProfile;
						}
					}
				}

				getTE(worldIn, pos).setPlayerProfile(newProfile);
				getTE(worldIn, pos).markDirty();
			}
		} else {
			if(placer instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) placer;
				getTE(worldIn, pos).setPlayerProfile(player.getGameProfile());
			} else {
				getTE(worldIn, pos).setPlayerProfile(new GameProfile((UUID)null, "steve"));
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if(Screen.hasShiftDown()){
			if(stack.hasTag()) {
				CompoundNBT tag = stack.getTag();
				tooltip.add(new StringTextComponent("Username: " + stack.getDisplayName().getUnformattedComponentText()).mergeStyle(TextFormatting.GOLD));

				if(tag != null && tag.contains("PlayerProfile")) {
					CompoundNBT profileTag = (CompoundNBT)tag.get("PlayerProfile");
					if(profileTag != null) {
						GameProfile gameprofile = NBTUtil.readGameProfile(profileTag);

						if (!StringUtils.isBlank(tag.getString("PlayerProfile"))) {
							gameprofile = new GameProfile((UUID)null, tag.getString("PlayerProfile"));
						}

						if(gameprofile != null)
							tooltip.add(new StringTextComponent("UUID: " + gameprofile.getId().toString()).mergeStyle(TextFormatting.GOLD));
					}
				}
			}
		}
	}

	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		if (blockState.get(ONLINE))
			return 15;
		return 0;
	}

	@Override
	public boolean canProvidePower(BlockState state)
	{
		return true;
	}

	@Override
	public boolean canConnectRedstone(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return blockState.get(ONLINE);
	}

	@Override
	public boolean shouldCheckWeakPower(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
		return false;
	}

	@Override
	protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED, ONLINE);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = playerIn.getHeldItem(hand);
		GameProfile tileProfile = getTE(worldIn, pos).getPlayerProfile();
		if(!worldIn.isRemote) {
			String playerName = tileProfile!= null ? tileProfile.getName() : "Name not found";
			if(!playerIn.isSneaking() && stack.getItem() == Items.COMPASS && StatuesConfig.COMMON.playerCompass.get()) {
				if(tileProfile != null && worldIn.getPlayerByUuid(tileProfile.getId()) != null) {
					ItemStack playerCompass = new ItemStack(StatueRegistry.PLAYER_COMPASS.get());
					CompoundNBT locationTag = new CompoundNBT();

					PlayerEntity player = worldIn.getPlayerByUuid(tileProfile.getId());
					if(player != null && player.world.func_234923_W_().func_240901_a_().equals(playerIn.world.func_234923_W_().func_240901_a_())) {
						BlockPos playerPos = player.getPosition();
						locationTag.putLong("lastPlayerLocation", playerPos.toLong());
						locationTag.putString("playerTracking", tileProfile.getName());

						playerCompass.setTag(locationTag);

						stack.shrink(1);
						if (stack.isEmpty()) {
							playerIn.setHeldItem(hand, playerCompass);
						} else if (!playerIn.inventory.addItemStackToInventory(playerCompass)) {
							playerIn.dropItem(playerCompass, false);
						}
					} else {
						playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.dimension.failure", TextFormatting.GOLD + playerName), Util.DUMMY_UUID);
						stack.shrink(1);
						if (stack.isEmpty()) {
							playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
						} else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS))) {
							playerIn.dropItem(new ItemStack(Items.COMPASS), false);
						}
					}

				} else {
					playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.offline", TextFormatting.GOLD + playerName), Util.DUMMY_UUID);
					stack.shrink(1);
					if (stack.isEmpty()) {
						playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
					} else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS))) {
						playerIn.dropItem(new ItemStack(Items.COMPASS), false);
					}
				}
				return ActionResultType.SUCCESS;
			} else if(!playerIn.isSneaking() && stack.getItem() == StatueRegistry.PLAYER_COMPASS.get() && StatuesConfig.COMMON.playerCompass.get()) {
				if(tileProfile != null && worldIn.getPlayerByUuid(tileProfile.getId()) != null && tileProfile.getName() != null) {
					CompoundNBT locationTag = new CompoundNBT();

					PlayerEntity player = worldIn.getPlayerByUuid(tileProfile.getId());
					if(player != null && player.world.func_234923_W_().func_240901_a_().equals(playerIn.world.func_234923_W_().func_240901_a_())) {
						BlockPos playerPos = player.getPosition();
						locationTag.putLong("lastPlayerLocation", playerPos.toLong());
						locationTag.putString("playerTracking", tileProfile.getName());

						stack.setTag(locationTag);
					} else {
						playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.dimension.failure", TextFormatting.GOLD + playerName), Util.DUMMY_UUID);
						stack.shrink(1);
						if (stack.isEmpty()) {
							playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
						} else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS))) {
							playerIn.dropItem(new ItemStack(Items.COMPASS), false);
						}
					}

				} else {
					playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.offline", TextFormatting.GOLD + playerName), Util.DUMMY_UUID);
					stack.shrink(1);
					if (stack.isEmpty()) {
						playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
					} else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS))) {
						playerIn.dropItem(new ItemStack(Items.COMPASS), false);
					}

				}
				return ActionResultType.SUCCESS;
			} else if(!playerIn.isSneaking() && stack.getItem() == Blocks.COMPARATOR.asItem()) {
				if(!getTE(worldIn, pos).getComparatorApplied()) {
					stack.shrink(1);
					getTE(worldIn, pos).setComparatorApplied(true);
					return ActionResultType.SUCCESS;
				}
			} else if(playerIn.isSneaking() && stack.isEmpty() && getTE(worldIn, pos).getComparatorApplied()) {
				getTE(worldIn, pos).setComparatorApplied(false);
				playerIn.setHeldItem(hand, new ItemStack(Blocks.COMPARATOR.asItem()));
				return ActionResultType.SUCCESS;
			} else if(playerIn.isSneaking() && stack.getItem() == StatueRegistry.PLAYER_COMPASS.get()) {
				stack.shrink(1);
				if (stack.isEmpty()) {
					playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
				} else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS))) {
					playerIn.dropItem(new ItemStack(Items.COMPASS), false);
				}
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING))).with(ONLINE, false);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING))).with(ONLINE, false);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
