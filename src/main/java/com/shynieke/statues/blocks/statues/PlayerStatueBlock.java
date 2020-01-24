package com.shynieke.statues.blocks.statues;

import com.mojang.authlib.GameProfile;
import com.shynieke.statues.blocks.AbstractBaseBlock;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueBlocks;
import com.shynieke.statues.init.StatueItems;
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
	private String playerName;

	public PlayerStatueBlock(Properties builder) {
		super(builder.sound(SoundType.STONE));
		this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)).with(ONLINE, false));
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
		if (te instanceof PlayerTile && ((INameable)te).hasCustomName())
		{
			PlayerTile tile = (PlayerTile)te;
			player.addExhaustion(0.005F);

			if (worldIn.isRemote)
				return;

			Item item = this.asItem();

			if (item == Items.AIR)
				return;

			@SuppressWarnings("deprecation")
			ItemStack itemstack = new ItemStack(item);
			itemstack.setDisplayName(((INameable)tile).getName());

			if (tile.getPlayerProfile() != null)
			{
				itemstack.setTag(new CompoundNBT());
				CompoundNBT nbttagcompound = new CompoundNBT();
				NBTUtil.writeGameProfile(nbttagcompound, tile.getPlayerProfile());
				itemstack.getTag().put("PlayerProfile", nbttagcompound);
				itemstack.setDisplayName(((INameable)tile).getName());
			}

			spawnAsEntity(worldIn, pos, itemstack);

			if(tile.getComparatorApplied())
			{
				spawnAsEntity(worldIn, pos, new ItemStack(Blocks.COMPARATOR.asItem()));
			}
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean p_196243_5_) {
		if (state.hasTileEntity() && newState.getBlock() != StatueBlocks.player_statue) {
			worldIn.removeTileEntity(pos);
		}
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof PlayerTile)
		{
			PlayerTile playerTile = (PlayerTile)tileentity;
			ItemStack stack = new ItemStack(state.getBlock());

			if (playerTile != null && playerTile.getPlayerProfile() != null)
			{
				stack.setTag(new CompoundNBT());
				CompoundNBT nbttagcompound = new CompoundNBT();
				NBTUtil.writeGameProfile(nbttagcompound, playerTile.getPlayerProfile());
				stack.getTag().put("PlayerProfile", nbttagcompound);
			}

			return stack.setDisplayName(playerTile.getName());
		}
		else
		{
			return new ItemStack(state.getBlock());
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state.with(ONLINE, false), placer, stack);
		String stackname = stack.getDisplayName().getString();
		String tilename = getTE(worldIn, pos).getName().getString();

		if (tilename != stackname)
		{
			this.playerName = stackname;
			if((this.playerName.contains(" ") || this.playerName.isEmpty()) && placer instanceof PlayerEntity)
			{
				PlayerEntity player = (PlayerEntity) placer;
				getTE(worldIn, pos).setName(player.getName().getString());

				getTE(worldIn, pos).setPlayerProfile(new GameProfile((UUID) null, player.getName().getString()));
			}
			else
			{
				GameProfile gameprofile = null;
				GameProfile newProfile = null;

				if(!this.playerName.isEmpty() && !this.playerName.contains(" "))
				{
					newProfile = new GameProfile((UUID)null, this.playerName);
				}

				if (stack.hasTag())
				{
					CompoundNBT tag = stack.getTag();
					String playerName = null;

					if (tag.contains("PlayerProfile"))
					{
						newProfile = NBTUtil.readGameProfile(tag.getCompound("PlayerProfile"));
					}
					else if (tag.contains("PlayerProfile", 8) && !StringUtils.isBlank(this.playerName))
					{
						gameprofile = new GameProfile((UUID)null, this.playerName);
					}

					if (tag.contains("PlayerName"))
					{
						playerName = tag.getString("PlayerName");
					}

					if(!newProfile.getName().equals(this.playerName))
					{
						newProfile = new GameProfile((UUID)null, this.playerName);
						getTE(worldIn, pos).setName(this.playerName);
						if(newProfile != null) {
							getTE(worldIn, pos).setPlayerProfile(newProfile);
						}
					}
					else
					{
						if(gameprofile != null && playerName != null)
						{
							if(gameprofile.getName() != this.playerName)
							{
								getTE(worldIn, pos).setName(this.playerName);
								getTE(worldIn, pos).setPlayerProfile(gameprofile);
							}
							else
							{
								getTE(worldIn, pos).setName(this.playerName);
								getTE(worldIn, pos).setPlayerProfile(newProfile);
							}
						}
						else
						{
							getTE(worldIn, pos).setName(this.playerName);
							getTE(worldIn, pos).setPlayerProfile(newProfile);
						}
					}
				}
				else
				{
					getTE(worldIn, pos).setPlayerProfile(newProfile);
				}
			}
			getTE(worldIn, pos).markDirty();
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if(Screen.hasShiftDown()){
			if(stack.hasTag())
			{
				CompoundNBT tag = stack.getTag();
				tooltip.add(new StringTextComponent("Username: " + stack.getDisplayName().getFormattedText()).applyTextStyle(TextFormatting.GOLD));

				if(tag.contains("PlayerProfile"))
				{
					GameProfile gameprofile = null;

					if (tag.contains("PlayerProfile"))
					{
						gameprofile = NBTUtil.readGameProfile((CompoundNBT)tag.get("PlayerProfile"));
					}
					else if (tag.contains("PlayerProfile") && !StringUtils.isBlank(tag.getString("PlayerProfile")))
					{
						gameprofile = new GameProfile((UUID)null, tag.getString("PlayerProfile"));
					}

					if(gameprofile != null)
						tooltip.add(new StringTextComponent("UUID: " + gameprofile.getId().toString()).applyTextStyle(TextFormatting.GOLD));
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
		if(!worldIn.isRemote)
		{
			String playerName = getTE(worldIn, pos).getPlayerProfile().getName();
			if(!playerIn.isShiftKeyDown() && stack.getItem() == Items.COMPASS && StatuesConfig.COMMON.playerCompass.get()) {
				if(getTE(worldIn, pos).getPlayerProfile() != null && worldIn.getPlayerByUuid(getTE(worldIn, pos).getPlayerProfile().getId()) != null) {
					ItemStack playerCompass = new ItemStack(StatueItems.player_compass);
					CompoundNBT locationTag = new CompoundNBT();

					PlayerEntity player = worldIn.getPlayerByUuid(getTE(worldIn, pos).getPlayerProfile().getId());
					if(player.dimension == playerIn.dimension) {
						BlockPos playerPos = player.getPosition();
						locationTag.putLong("lastPlayerLocation", playerPos.toLong());
						locationTag.putString("playerTracking", getTE(worldIn, pos).getPlayerProfile().getName());

						playerCompass.setTag(locationTag);

						stack.shrink(1);
						if (stack.isEmpty())
						{
							playerIn.setHeldItem(hand, playerCompass);
						}
						else if (!playerIn.inventory.addItemStackToInventory(playerCompass))
						{
							playerIn.dropItem(playerCompass, false);
						}
					} else {
						playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.dimension.failure", new Object[] {TextFormatting.GOLD + playerName}));
						stack.shrink(1);
						if (stack.isEmpty())
						{
							playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
						}
						else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS)))
						{
							playerIn.dropItem(new ItemStack(Items.COMPASS), false);
						}
					}

				} else {
					playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.offline", new Object[] {TextFormatting.GOLD + playerName}));
					stack.shrink(1);
					if (stack.isEmpty())
					{
						playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
					}
					else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS)))
					{
						playerIn.dropItem(new ItemStack(Items.COMPASS), false);
					}

				}
				return ActionResultType.SUCCESS;
			} else if(!playerIn.isShiftKeyDown() && stack.getItem() == StatueItems.player_compass && StatuesConfig.COMMON.playerCompass.get()) {
				if(getTE(worldIn, pos).getPlayerProfile() != null && worldIn.getPlayerByUuid(getTE(worldIn, pos).getPlayerProfile().getId()) != null && getTE(worldIn, pos).getPlayerProfile().getName() != null) {
					CompoundNBT locationTag = new CompoundNBT();

					PlayerEntity player = worldIn.getPlayerByUuid(getTE(worldIn, pos).getPlayerProfile().getId());
					if(player.dimension == playerIn.dimension)
					{
						BlockPos playerPos = player.getPosition();
						locationTag.putLong("lastPlayerLocation", playerPos.toLong());
						locationTag.putString("playerTracking", getTE(worldIn, pos).getPlayerProfile().getName());

						stack.setTag(locationTag);
					}
					else
					{
						playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.dimension.failure", new Object[] {TextFormatting.GOLD + playerName}));
						stack.shrink(1);
						if (stack.isEmpty())
						{
							playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
						}
						else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS)))
						{
							playerIn.dropItem(new ItemStack(Items.COMPASS), false);
						}
					}

				}
				else
				{
					playerIn.sendMessage(new TranslationTextComponent("statues:player.compass.offline", new Object[] {TextFormatting.GOLD + playerName}));
					stack.shrink(1);
					if (stack.isEmpty())
					{
						playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
					}
					else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS)))
					{
						playerIn.dropItem(new ItemStack(Items.COMPASS), false);
					}

				}
				return ActionResultType.SUCCESS;
			}
			else if(!playerIn.isShiftKeyDown() && stack.getItem() == Blocks.COMPARATOR.asItem())
			{
				if(!getTE(worldIn, pos).getComparatorApplied())
				{
					stack.shrink(1);
					getTE(worldIn, pos).setComparatorApplied(true);
					return ActionResultType.SUCCESS;
				}
			}
			else if(playerIn.isShiftKeyDown() && stack.isEmpty() && getTE(worldIn, pos).getComparatorApplied())
			{
				getTE(worldIn, pos).setComparatorApplied(false);
				playerIn.setHeldItem(hand, new ItemStack(Blocks.COMPARATOR.asItem()));
				return ActionResultType.SUCCESS;
			}
			else if(playerIn.isShiftKeyDown() && stack.getItem() == StatueItems.player_compass)
			{
				stack.shrink(1);
				if (stack.isEmpty())
				{
					playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
				}
				else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS)))
				{
					playerIn.dropItem(new ItemStack(Items.COMPASS), false);
				}
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return (BlockState) state.with(HORIZONTAL_FACING, rot.rotate((Direction) state.get(HORIZONTAL_FACING))).with(ONLINE, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation((Direction) state.get(HORIZONTAL_FACING))).with(ONLINE, false);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.SHAPE;
	}
}
