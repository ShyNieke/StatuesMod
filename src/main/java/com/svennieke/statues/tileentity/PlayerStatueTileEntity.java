package com.svennieke.statues.tileentity;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.IWorldNameable;

public class PlayerStatueTileEntity extends TileEntity implements IWorldNameable{
	public String BlockName;
    public GameProfile playerProfile;

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
        	NBTTagCompound nbttagcompound = getTileData().getCompoundTag("PlayerProfile");
            NBTUtil.writeGameProfile(nbttagcompound, getPlayerProfile());
            getTileData().setTag("PlayerProfile", nbttagcompound);
        }
        return compound;
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	readFromNBT(pkt.getNbtCompound());
    	
    	IBlockState state = world.getBlockState(getPos());
    	world.notifyBlockUpdate(getPos(), state, state, 3);
    }
    
    @Override
	public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
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