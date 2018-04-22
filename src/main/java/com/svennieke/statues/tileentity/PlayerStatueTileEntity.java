package com.svennieke.statues.tileentity;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.util.SkinUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.IWorldNameable;

public class PlayerStatueTileEntity extends TileEntity implements IWorldNameable{
	private String BlockName;
    private GameProfile playerProfile;

	public PlayerStatueTileEntity() {
		this.BlockName = "";
	}
	
	public String setName(String name) {
		return this.BlockName = name;
	}
	
	@Override
	public String getName() {
		return this.hasCustomName() ? this.BlockName : "statue.player";
	}
	
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.BlockName = compound.getString("PlayerName");
        if(getTileData().hasKey("PlayerProfile"))
        {
        	 playerProfile = NBTUtil.readGameProfileFromNBT(getTileData().getCompoundTag("PlayerProfile"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("PlayerName", this.BlockName);
        if (this.playerProfile != null)
        {
        	if(this.playerProfile.getName() != this.BlockName) 
        	{
        		if(!this.BlockName.isEmpty())
        		{
        			setPlayerProfile(new GameProfile(SkinUtil.getUUIDFromName(this.BlockName), this.BlockName));

            		NBTTagCompound nbttagcompound = getTileData().getCompoundTag("PlayerProfile");
                    NBTUtil.writeGameProfile(nbttagcompound, playerProfile);
                    getTileData().setTag("PlayerProfile", nbttagcompound);
            	}
        	}
        	else
        	{
        		NBTTagCompound nbttagcompound = getTileData().getCompoundTag("PlayerProfile");
                NBTUtil.writeGameProfile(nbttagcompound, getPlayerProfile());
                getTileData().setTag("PlayerProfile", nbttagcompound);
        	}
        }
        return compound;
    }
    
	@Override
	public NBTTagCompound getUpdateTag() {
		//NBTTagCompound tag = super.getUpdateTag();
		//tag.setString("PlayerName", BlockName);
		//NBTUtil.writeGameProfile(tag, this.playerProfile);
		//return tag;
        return this.writeToNBT(new NBTTagCompound());

	}
    
    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
    	super.handleUpdateTag(tag);
    	BlockName = tag.getString("PlayerName");
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	super.onDataPacket(net, pkt);
    	readFromNBT(pkt.getNbtCompound());
    	final IBlockState state = getWorld().getBlockState(getPos());
    	getWorld().notifyBlockUpdate(getPos(), state, state, 3);
    	
    }
    
    @Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, this.getUpdateTag());
	}

	@Override
	public boolean hasCustomName() {
		return this.BlockName != null && !this.BlockName.isEmpty();
	}
	
	@Nullable
    public GameProfile getPlayerProfile()
    {
        return this.playerProfile;
    }

    public void setPlayerProfile(GameProfile playerProfile) {
    	if(playerProfile != null)
    	{
    		this.playerProfile = playerProfile;
            this.playerProfile = TileEntitySkull.updateGameprofile(this.playerProfile);
            this.markDirty();
    	}
    }
}