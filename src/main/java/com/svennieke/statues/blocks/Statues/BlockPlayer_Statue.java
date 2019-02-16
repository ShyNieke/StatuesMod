package com.svennieke.statues.blocks.Statues;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.StatueBase.BlockPlayer;
import com.svennieke.statues.config.StatuesConfig;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.INameable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

public class BlockPlayer_Statue extends BlockPlayer{
	
	public static final BooleanProperty ONLINE = BooleanProperty.create("online");
	private String playername;
	
	public BlockPlayer_Statue(Block.Properties builder) {
		super(builder);
//		setUnlocalizedName(unlocalised);
//		//setRegistryName(registry);
	    this.setDefaultState((IBlockState)((IBlockState)this.getDefaultState().with(HORIZONTAL_FACING, EnumFacing.NORTH)).with(WATERLOGGED, Boolean.valueOf(false)).with(ONLINE, false));

	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return new PlayerStatueTileEntity();
	}
	
	private PlayerStatueTileEntity getTE(World world, BlockPos pos) {
        return (PlayerStatueTileEntity) world.getTileEntity(pos);
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		if (te instanceof PlayerStatueTileEntity && ((INameable)te).hasCustomName())
        {
			PlayerStatueTileEntity tile = (PlayerStatueTileEntity)te;
			
            player.addExhaustion(0.005F);

            if (worldIn.isRemote)
                return;

            Item item = this.getItemDropped(state, worldIn, pos, 0).asItem();

            if (item == Items.AIR)
                return;

            @SuppressWarnings("deprecation")
			ItemStack itemstack = new ItemStack(item, this.quantityDropped(state, worldIn.rand));
            itemstack.setDisplayName(((INameable)tile).getName());
            
            if (tile.getPlayerProfile() != null)
            {
                itemstack.setTag(new NBTTagCompound());
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTUtil.writeGameProfile(nbttagcompound, tile.getPlayerProfile());
                itemstack.getTag().setTag("PlayerProfile", nbttagcompound);
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
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			EntityPlayer player) {
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof PlayerStatueTileEntity)
		{
			PlayerStatueTileEntity playerTile = (PlayerStatueTileEntity)tileentity;
			ItemStack stack = new ItemStack(state.getBlock(), 1);
			
			if (playerTile != null)
            {
				stack.setTag(new NBTTagCompound());
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTUtil.writeGameProfile(nbttagcompound, playerTile.getPlayerProfile());
                stack.getTag().setTag("PlayerProfile", nbttagcompound);
            }
			
			return new ItemStack(state.getBlock(), 1).setDisplayName(playerTile.getName());
		}
		else
		{
			return new ItemStack(state.getBlock(), 1);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state.with(ONLINE, false), placer, stack);
		String stackname = stack.getDisplayName().getString();
		String tilename = getTE(worldIn, pos).getName().getString();
		
		if (tilename != stackname)
		{
			this.playername = stackname;
			if((this.playername.contains(" ") || this.playername.isEmpty()) && placer instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) placer;
				getTE(worldIn, pos).setName(player.getName().getString());
				
				getTE(worldIn, pos).setPlayerProfile(new GameProfile((UUID)null, player.getName().getString()));
			}
			else
			{
				GameProfile gameprofile = null;
            	GameProfile newProfile = null;
            	
            	if(!this.playername.isEmpty() && !this.playername.contains(" "))
        		{
            		newProfile = new GameProfile((UUID)null, this.playername);
        		}

                if (stack.hasTag())
                {
                    NBTTagCompound tag = stack.getTag();
                    String playerName = null;
                    
                    if (tag.hasKey("PlayerProfile"))
                    {
                    	newProfile = NBTUtil.readGameProfile(tag.getCompound("PlayerProfile"));
                    }
                    else if (tag.contains("PlayerProfile", 8) && !StringUtils.isBlank(this.playername))
                    {
                        gameprofile = new GameProfile((UUID)null, this.playername);
                    }
                    
                    if (tag.hasKey("PlayerName"))
                    {
                    	playerName = tag.getString("PlayerName");
                    }
                    
                    if(!newProfile.getName().equals(this.playername))
                    {
                    	newProfile = new GameProfile((UUID)null, this.playername);
                    	getTE(worldIn, pos).setName(this.playername);
        				getTE(worldIn, pos).setPlayerProfile(newProfile);
                    }
                    else
                    {
                    	if(gameprofile != null && playerName != null)
                        {
                        	if(gameprofile.getName() != this.playername)
                        	{
                				getTE(worldIn, pos).setName(this.playername);
                				getTE(worldIn, pos).setPlayerProfile(gameprofile);
                        	}
                        	else
                        	{
                				getTE(worldIn, pos).setName(this.playername);
                				getTE(worldIn, pos).setPlayerProfile(newProfile);
                        	}
                        }
                        else
                        {
            				getTE(worldIn, pos).setName(this.playername);
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
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip,
			ITooltipFlag flagIn) {
		if(GuiScreen.isShiftKeyDown()){
			if(stack.hasTag())
			{
                NBTTagCompound tag = stack.getTag();
    	    	tooltip.add(new TextComponentString("Username: " + stack.getDisplayName()));
    	    	
    	    	if(tag.hasKey("PlayerProfile"))
    	    	{
    	    		GameProfile gameprofile = null;
    				
    				if (tag.hasKey("PlayerProfile"))
                    {
                        gameprofile = NBTUtil.readGameProfile((NBTTagCompound)tag.getTag("PlayerProfile"));
                    }
                    else if (tag.hasKey("PlayerProfile") && !StringUtils.isBlank(tag.getString("PlayerProfile")))
                    {
                        gameprofile = new GameProfile((UUID)null, tag.getString("PlayerProfile"));
                    }
    				
    				if(gameprofile != null)
            	    	tooltip.add(new TextComponentString("UUID: " + gameprofile.getId().toString()));
    	    	}
			}
		}
	}
	@Override
	public int getWeakPower(IBlockState blockState, IBlockReader blockAccess, BlockPos pos, EnumFacing side) {
		if (blockState.get(ONLINE))
			return 15;
		return 0;
	}
    
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

	@Override
	public boolean canConnectRedstone(IBlockState blockState, IBlockReader blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.get(ONLINE);
	}
	
	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IWorldReader world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(3 & meta)).withProperty(ONLINE, (meta & 4) == 4);
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(ONLINE) ? 4 : 0);
//    }
	
	@Override
	protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED, ONLINE);
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		if(!worldIn.isRemote)
		{
			String playerName = getTE(worldIn, pos).getPlayerProfile().getName();
			if(!playerIn.isSneaking() && stack.getItem() == Items.COMPASS && StatuesConfig.COMMON.playerCompass.get())
			{
				if(getTE(worldIn, pos).getPlayerProfile() != null && 
					worldIn.getPlayerEntityByUUID(getTE(worldIn, pos).getPlayerProfile().getId()) != null)
				{

					ItemStack playerCompass = new ItemStack(StatuesItems.player_compass);
					NBTTagCompound locationTag = new NBTTagCompound();
					
					EntityPlayer player = worldIn.getPlayerEntityByUUID(getTE(worldIn, pos).getPlayerProfile().getId());
					if(player.dimension == playerIn.dimension)
					{
						BlockPos playerPos = player.getPosition();
						locationTag.setLong("lastPlayerLocation", playerPos.toLong());
						locationTag.setString("playerTracking", getTE(worldIn, pos).getPlayerProfile().getName());
						
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
					}
					else
					{
						playerIn.sendMessage(new TextComponentTranslation("statues:player.compass.dimension.failure", new Object[] {TextFormatting.GOLD + playerName}));
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
					
					return true;
				}
				else
				{
					playerIn.sendMessage(new TextComponentTranslation("statues:player.compass.offline", new Object[] {TextFormatting.GOLD + playerName}));
					stack.shrink(1);
					if (stack.isEmpty())
		            {
						playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
		            }
		            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS)))
		            {
		            	playerIn.dropItem(new ItemStack(Items.COMPASS), false);
		            }
					
					return true;
				}
			}
			else if(!playerIn.isSneaking() && stack.getItem() == StatuesItems.player_compass && StatuesConfig.COMMON.playerCompass.get())
			{
				if(getTE(worldIn, pos).getPlayerProfile() != null && 
						worldIn.getPlayerEntityByUUID(getTE(worldIn, pos).getPlayerProfile().getId()) != null && 
						getTE(worldIn, pos).getPlayerProfile().getName() != null)
				{
					NBTTagCompound locationTag = new NBTTagCompound();
					
					EntityPlayer player = worldIn.getPlayerEntityByUUID(getTE(worldIn, pos).getPlayerProfile().getId());
					if(player.dimension == playerIn.dimension)
					{
						BlockPos playerPos = player.getPosition();
						locationTag.setLong("lastPlayerLocation", playerPos.toLong());
						locationTag.setString("playerTracking", getTE(worldIn, pos).getPlayerProfile().getName());
						
						stack.setTag(locationTag);
					}
					else
					{
						playerIn.sendMessage(new TextComponentTranslation("statues:player.compass.dimension.failure", new Object[] {TextFormatting.GOLD + playerName}));
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
					
					return true;
				}
				else
				{
					playerIn.sendMessage(new TextComponentTranslation("statues:player.compass.offline", new Object[] {TextFormatting.GOLD + playerName}));
					stack.shrink(1);
					if (stack.isEmpty())
		            {
						playerIn.setHeldItem(hand, new ItemStack(Items.COMPASS));
		            }
		            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS)))
		            {
		            	playerIn.dropItem(new ItemStack(Items.COMPASS), false);
		            }
					
					return true;
				}
			}
			else if(!playerIn.isSneaking() && stack.getItem() == Blocks.COMPARATOR.asItem())
			{
				if(!getTE(worldIn, pos).getComparatorApplied())
				{
					stack.shrink(1);
					getTE(worldIn, pos).setComparatorApplied(true);
					return true;
				}
			}
			else if(playerIn.isSneaking() && stack.isEmpty() && getTE(worldIn, pos).getComparatorApplied())
			{
				getTE(worldIn, pos).setComparatorApplied(false);
				playerIn.setHeldItem(hand, new ItemStack(Blocks.COMPARATOR.asItem()));
				return true;
			}
			else if(playerIn.isSneaking() && stack.getItem() == StatuesItems.player_compass)
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
				return true;
			}
			return false;
		}
		else
		return false;
	}
	
	@Override
	public IBlockState rotate(IBlockState state, Rotation rot) {
		return (IBlockState) state.with(HORIZONTAL_FACING, rot.rotate((EnumFacing) state.get(HORIZONTAL_FACING))).with(ONLINE, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation((EnumFacing) state.get(HORIZONTAL_FACING))).with(ONLINE, false);
	}
	
//	@Override
//	public IBlockState withRotation(IBlockState state, Rotation rot)
//    {
//		return state.withProperty(FACING, rot.rotatef((EnumFacing)state.getValue(FACING))).withProperty(ONLINE, false);
//    }
//
//	@Override
//    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
//    {
//        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING))).withProperty(ONLINE, false);
//    }
//
//	@Override
//    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
//    {
//        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ONLINE, false);
//    }
}
