package com.svennieke.statues.tileentity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class PlayerStatueTileEntity extends TileEntity implements ITickable{
	private String BlockName;
	private int Rotation;
	
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
        this.Rotation = compound.getByte("Rot");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("PlayerName", this.BlockName);
        compound.setByte("Rot", (byte) (this.Rotation & 255));
        return compound;
    }
    
    @Override
    public void update(){

    }
	
	public void setRotation(int rot) {
        this.Rotation = rot;
    }

    public int getRotation(){
        return this.Rotation;
}
}