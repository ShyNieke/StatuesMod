package com.svennieke.statues.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.IWorldNameable;

public class PlayerStatueTileEntity extends TileEntity implements ITickable, IWorldNameable{
	private String BlockName;
	
	public PlayerStatueTileEntity() {
		this.BlockName = "";
	}
	
	public String setName(String name) {
		return this.BlockName = name;
	}
	
	public String getName() {
		return this.hasCustomName() ? this.BlockName : "statue.player";
	}
	
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        
        if (compound.hasKey("CustomName", 8))
        {
            this.BlockName = compound.getString("PlayerName");
        }
        
        //this.BlockName = compound.getString("PlayerName");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        
        if (this.hasCustomName())
        {
            compound.setString("PlayerName", this.BlockName);
        }
        
        //compound.setString("PlayerName", this.BlockName);
        return compound;
    }
    
    @Override
    public void update(){

    }

	@Override
	public boolean hasCustomName() {
		return this.BlockName != null && !this.BlockName.isEmpty();
	}
}