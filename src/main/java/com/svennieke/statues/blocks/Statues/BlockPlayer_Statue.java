package com.svennieke.statues.blocks.Statues;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.StatueBase.BlockPlayer;
import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

public class BlockPlayer_Statue extends BlockPlayer implements ITileEntityProvider{
	
	public static final PropertyBool ONLINE = PropertyBool.create("online");
	private String playername;
	
	public BlockPlayer_Statue(String unlocalised, String registry, String name) {
		super();
		this.playername = name;
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
		this.getDefaultState().withProperty(ONLINE, false);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new PlayerStatueTileEntity();
	}
	
	private PlayerStatueTileEntity getTE(World world, BlockPos pos) {
        return (PlayerStatueTileEntity) world.getTileEntity(pos);
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		if (te instanceof PlayerStatueTileEntity && ((IWorldNameable)te).hasCustomName())
        {
			PlayerStatueTileEntity tile = (PlayerStatueTileEntity)te;
			
            player.addExhaustion(0.005F);

            if (worldIn.isRemote)
                return;

            Item item = this.getItemDropped(state, worldIn.rand, 0);

            if (item == Items.AIR)
                return;

            ItemStack itemstack = new ItemStack(item, this.quantityDropped(worldIn.rand));
            itemstack.setStackDisplayName(((IWorldNameable)tile).getName());
            
            if (tile.getPlayerProfile() != null)
            {
                itemstack.setTagCompound(new NBTTagCompound());
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTUtil.writeGameProfile(nbttagcompound, tile.getPlayerProfile());
                itemstack.getTagCompound().setTag("PlayerProfile", nbttagcompound);
                itemstack.setStackDisplayName(((IWorldNameable)tile).getName());
            }
            
            spawnAsEntity(worldIn, pos, itemstack);
            
            if(tile.getComparatorApplied())
            {
                spawnAsEntity(worldIn, pos, new ItemStack(Items.COMPARATOR));
            }
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		if (getTE(world, pos).hasCustomName())
		{
			ItemStack stack = new ItemStack(state.getBlock(), 1);
			
			if (getTE(world, pos).getPlayerProfile() != null)
            {
				stack.setTagCompound(new NBTTagCompound());
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTUtil.writeGameProfile(nbttagcompound, getTE(world, pos).getPlayerProfile());
                stack.getTagCompound().setTag("PlayerProfile", nbttagcompound);
            }
			
			return new ItemStack(state.getBlock(), 1).setStackDisplayName(getTE(world, pos).getName());
		}
		else
		{
			return new ItemStack(state.getBlock(), 1);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
    	PlayerStatueTileEntity tile = getTE(worldIn, pos);
    	
		super.onBlockPlacedBy(worldIn, pos, state.withProperty(ONLINE, false), placer, stack);
		String stackname = stack.getDisplayName();
		String tilename = tile.getName();
		
		if (tilename != stackname)
		{
			this.playername = stackname;
			if((this.playername.contains(" ") || this.playername.isEmpty()) && placer instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) placer;
				tile.setName(player.getName());
				
				tile.setPlayerProfile(new GameProfile((UUID)null, player.getName()));
			}
			else
			{
				GameProfile gameprofile = null;
            	GameProfile newProfile = null;
            	
            	if(!this.playername.isEmpty() && !this.playername.contains(" "))
        		{
            		newProfile = new GameProfile((UUID)null, this.playername);
        		}

                if (stack.hasTagCompound())
                {
                    NBTTagCompound tag = stack.getTagCompound();
                    String playerName = null;
                    
                    if (tag.hasKey("PlayerProfile"))
                    {
                    	newProfile = NBTUtil.readGameProfileFromNBT(tag.getCompoundTag("PlayerProfile"));
                    }
                    else if (tag.hasKey("PlayerProfile", 8) && !StringUtils.isBlank(this.playername))
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
                    	tile.setName(this.playername);
        				tile.setPlayerProfile(newProfile);
                    }
                    else
                    {
                    	if(gameprofile != null && playerName != null)
                        {
                        	if(gameprofile.getName() != this.playername)
                        	{
                				tile.setName(this.playername);
                				tile.setPlayerProfile(gameprofile);
                        	}
                        	else
                        	{
                				tile.setName(this.playername);
                				tile.setPlayerProfile(newProfile);
                        	}
                        }
                        else
                        {
            				tile.setName(this.playername);
            				tile.setPlayerProfile(newProfile);
                        }
                    }
                }
                else
                {
    				tile.setPlayerProfile(newProfile);
                }
			}
			tile.markDirty();
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(GuiScreen.isShiftKeyDown()){
			if(stack.hasTagCompound())
			{
                NBTTagCompound tag = stack.getTagCompound();
    	    	tooltip.add("Username: " + stack.getDisplayName());
    	    	
    	    	if(tag.hasKey("PlayerProfile"))
    	    	{
    	    		GameProfile gameprofile = null;
    				
    				if (tag.hasKey("PlayerProfile"))
                    {
                        gameprofile = NBTUtil.readGameProfileFromNBT(tag.getCompoundTag("PlayerProfile"));
                    }
                    else if (tag.hasKey("PlayerProfile") && !StringUtils.isBlank(tag.getString("PlayerProfile")))
                    {
                        gameprofile = new GameProfile((UUID)null, tag.getString("PlayerProfile"));
                    }
    				
    				if(gameprofile != null)
            	    	tooltip.add("UUID: " + gameprofile.getId().toString());
    	    	}
			}
		}
	}
	
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (state.getValue(ONLINE))
			return 15;
		return 0;
	}
    
    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(ONLINE);
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(3 & meta)).withProperty(ONLINE, (meta & 4) == 4);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(ONLINE) ? 4 : 0);
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, ONLINE});
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		if(!worldIn.isRemote)
		{
        	PlayerStatueTileEntity tile = getTE(worldIn, pos);
        	
			String playerName = tile.getPlayerProfile().getName();
			if(!playerIn.isSneaking() && stack.getItem() == Items.COMPASS && StatuesConfigGen.player.PlayerCompass)
			{
				if(tile.getPlayerProfile() != null && 
					worldIn.getPlayerEntityByUUID(tile.getPlayerProfile().getId()) != null)
				{

					ItemStack playerCompass = new ItemStack(StatuesItems.player_compass);
					NBTTagCompound locationTag = new NBTTagCompound();
					
					EntityPlayer player = worldIn.getPlayerEntityByUUID(tile.getPlayerProfile().getId());
					if(player.dimension == playerIn.dimension)
					{
						BlockPos playerPos = player.getPosition();
						locationTag.setLong("lastPlayerLocation", playerPos.toLong());
						locationTag.setString("playerTracking", tile.getPlayerProfile().getName());
						
						playerCompass.setTagCompound(locationTag);

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
			else if(!playerIn.isSneaking() && stack.getItem() == StatuesItems.player_compass && StatuesConfigGen.player.PlayerCompass)
			{
				if(tile.getPlayerProfile() != null && 
						worldIn.getPlayerEntityByUUID(tile.getPlayerProfile().getId()) != null && 
						tile.getPlayerProfile().getName() != null)
				{
					NBTTagCompound locationTag = new NBTTagCompound();
					
					EntityPlayer player = worldIn.getPlayerEntityByUUID(tile.getPlayerProfile().getId());
					if(player.dimension == playerIn.dimension)
					{
						BlockPos playerPos = player.getPosition();
						locationTag.setLong("lastPlayerLocation", playerPos.toLong());
						locationTag.setString("playerTracking", tile.getPlayerProfile().getName());
						
						stack.setTagCompound(locationTag);
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
			else if(!playerIn.isSneaking() && stack.getItem() == Items.COMPARATOR)
			{
				if(!tile.getComparatorApplied())
				{
					stack.shrink(1);
					tile.setComparatorApplied(true);
					return true;
				}
			}
			else if(playerIn.isSneaking() && stack.isEmpty() && tile.getComparatorApplied())
			{
				tile.setComparatorApplied(false);
				playerIn.setHeldItem(hand, new ItemStack(Items.COMPARATOR));
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
	public IBlockState withRotation(IBlockState state, Rotation rot)
    {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING))).withProperty(ONLINE, false);
    }

	@Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING))).withProperty(ONLINE, false);
    }

	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ONLINE, false);
    }
}
