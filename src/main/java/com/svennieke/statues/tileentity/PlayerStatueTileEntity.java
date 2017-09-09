package com.svennieke.statues.tileentity;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class PlayerStatueTileEntity extends TileEntity implements ITickable{
	private String BlockName;
	
	public PlayerStatueTileEntity() {
		this.BlockName = "Player Statue";
	}
	
	public String setName(String name) {
		return this.BlockName = name;
	}
	
	public String getName() {
		//System.out.println(this.BlockName);
		return this.BlockName;
	}
	
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.BlockName = compound.getString("PlayerName");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("PlayerName", this.BlockName);
        return compound;
    }
    
    @Override
    public void update(){

    }
}