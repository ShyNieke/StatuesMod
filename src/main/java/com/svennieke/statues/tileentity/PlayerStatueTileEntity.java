package com.svennieke.statues.tileentity;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesTileTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.INameable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;

public class PlayerStatueTileEntity extends TileEntity implements INameable, ITickable{
	
	public PlayerStatueTileEntity() {
		super(StatuesTileTypes.PLAYER_STATUE);
		this.BlockName = "";
		this.comparatorApplied = false;
		this.checkerCooldown = 0;
		this.OnlineChecking = false;
	}

	public String BlockName;
    public GameProfile playerProfile;
    public Boolean comparatorApplied;
    public Boolean OnlineChecking;
    public int checkerCooldown;
    public BlockPos playerPos;

	
	
	public String setName(String name) {
		return this.BlockName = name;
	}
	
    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        this.BlockName = compound.getString("PlayerName");
        if(getTileData().hasKey("PlayerProfile"))
        {
        	 playerProfile = NBTUtil.readGameProfile(getTileData().getCompound("PlayerProfile"));
        }
        comparatorApplied = compound.getBoolean("comparatorApplied");
        OnlineChecking = compound.getBoolean("OnlineChecking");
        checkerCooldown = compound.getInt("checkerCooldown");
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
        compound.setString("PlayerName", this.BlockName);
        if (this.playerProfile != null)
        {
        	NBTTagCompound nbttagcompound = getTileData().getCompound("PlayerProfile");
            NBTUtil.writeGameProfile(nbttagcompound, getPlayerProfile());
            getTileData().setTag("PlayerProfile", nbttagcompound);
        }
        compound.setBoolean("comparatorApplied", comparatorApplied);
        compound.setBoolean("OnlineChecking", OnlineChecking);
        compound.setInt("checkerCooldown", checkerCooldown);
        return compound;
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    	read(pkt.getNbtCompound());
    	
    	IBlockState state = world.getBlockState(getPos());
    	world.notifyBlockUpdate(getPos(), state, state, 3);
    }
    
    @Override
	public NBTTagCompound getUpdateTag()
    {
        return this.write(new NBTTagCompound());
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
    		this.playerProfile = TileEntitySkull.updateGameProfile(playerProfile);
            this.markDirty();
    	}
    }

	@Override
	public void tick() {
		if (this.world.isRemote)
    		return;
		
		
		IBlockState state = world.getBlockState(getPos());
		if(state.getBlock() == StatuesBlocks.player_statue && comparatorApplied)
		{
			boolean isStateOnline = state.get(BlockPlayer_Statue.ONLINE);

			if(!OnlineChecking)
			{
				++this.checkerCooldown;
	    		markDirty();
	    		if(this.checkerCooldown == 0)
	        		this.checkerCooldown = 200;
	    		
	            if(this.checkerCooldown >= 200){
	                this.checkerCooldown = 0;
	                setOnlineChecking(true);
	            }
			}
			else
			{
				boolean checkAnswer = false;
				if(world.getPlayerEntityByUUID(this.playerProfile.getId()) != null)
					checkAnswer = true;
				else
					checkAnswer = false;
				
				IBlockState newState = state.with(BlockPlayer_Statue.ONLINE, checkAnswer);

				if(isStateOnline != checkAnswer)
				{
					world.setBlockState(getPos(), state.with(BlockPlayer_Statue.ONLINE, checkAnswer), 5);
			    	world.notifyBlockUpdate(getPos(), state, newState, 5);
				}
				setOnlineChecking(false);
			}
		}
	}

	/*
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return newSate.getBlock() != StatuesBlocks.player_statue;
	}
	*/
	
	public void setComparatorApplied(Boolean comparatorApplied) {
		this.comparatorApplied = comparatorApplied;
		this.markDirty();
	}
	
	public Boolean getComparatorApplied() {
		return comparatorApplied;
	}
	
	public int getCooldown() {
		return this.checkerCooldown;
	}
	
	public void setOnlineChecking(boolean onlineChecking) {
		this.OnlineChecking = onlineChecking;
		this.markDirty();
	}

	@Override
	public ITextComponent getName() {
		return this.hasCustomName() ? new TextComponentString(this.BlockName) : new TextComponentTranslation("statue.player");
	}

	@Override
	public ITextComponent getCustomName() {
		return null;
	}
}