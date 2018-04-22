package com.svennieke.statues.blocks.Statues;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.StatueBase.BlockPlayer;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.util.SkinUtil;

import net.minecraft.block.ITileEntityProvider;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

public class BlockPlayer_Statue extends BlockPlayer implements iStatue, ITileEntityProvider{
	
	private String playername;
	
	public BlockPlayer_Statue(String unlocalised, String registry, String name) {
		super();
		this.playername = name;
		setUnlocalizedName(unlocalised);
		setRegistryName(registry);
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
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		String stackname = stack.getDisplayName();
		String tilename = getTE(worldIn, pos).getName();
		
		if (tilename != stackname)
		{
			this.playername = stackname;
			if((this.playername.contains(" ") || this.playername.equals("")) && placer instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) placer;
				getTE(worldIn, pos).setName(player.getName());
				
            	GameProfile profile = new GameProfile(SkinUtil.getUUIDFromName(player.getName()), player.getName());
				getTE(worldIn, pos).setPlayerProfile(profile);
			}
			else
			{
				GameProfile gameprofile = null;
            	GameProfile newProfile = null;
            	
            	if(!this.playername.isEmpty() && !this.playername.contains(" "))
        		{
            		newProfile = new GameProfile(SkinUtil.getUUIDFromName(this.playername), this.playername);
        		}

                if (stack.hasTagCompound())
                {
                    NBTTagCompound tag = stack.getTagCompound();
                    String playerName = null;
                    
                    if (tag.hasKey("PlayerProfile", 10))
                    {
                        gameprofile = NBTUtil.readGameProfileFromNBT(tag.getCompoundTag("PlayerProfile"));
                    }
                    else if (tag.hasKey("PlayerProfile", 8) && !StringUtils.isBlank(tag.getString("PlayerProfile")))
                    {
                        gameprofile = new GameProfile((UUID)null, tag.getString("PlayerProfile"));
                    }
                    
                    if (tag.hasKey("PlayerName"))
                    {
                    	playerName = tag.getString("PlayerName");
                    }
                    
                    if(gameprofile != null && playerName != null)
                    {
                    	if(gameprofile.getName() != this.playername)
                    	{
                    		System.out.println("Profile name is not the same");
            				getTE(worldIn, pos).setName(this.playername);
            				getTE(worldIn, pos).setPlayerProfile(gameprofile);
                    	}
                    	else
                    	{
                    		System.out.println("Already got a profile");
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
                else
                {
    				getTE(worldIn, pos).setPlayerProfile(newProfile);
                }
			}
			getTE(worldIn, pos).markDirty();
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
    				
    				if (tag.hasKey("PlayerProfile", 10))
                    {
                        gameprofile = NBTUtil.readGameProfileFromNBT(tag.getCompoundTag("PlayerProfile"));
                    }
                    else if (tag.hasKey("PlayerProfile", 8) && !StringUtils.isBlank(tag.getString("PlayerProfile")))
                    {
                        gameprofile = new GameProfile((UUID)null, tag.getString("PlayerProfile"));
                    }
    				
    				if(gameprofile != null)
            	    	tooltip.add("UUID: " + gameprofile.getId().toString());
    	    	}
			}
		}
	}
}
