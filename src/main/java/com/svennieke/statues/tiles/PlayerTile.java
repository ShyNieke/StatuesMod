package com.svennieke.statues.tiles;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.statues.PlayerStatueBlock;
import com.svennieke.statues.init.StatueBlocks;
import com.svennieke.statues.init.StatueTiles;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.INameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class PlayerTile extends TileEntity implements INameable, ITickableTileEntity {

    public PlayerTile() {
        super(StatueTiles.PLAYER);
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
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.BlockName = compound.getString("PlayerName");
        if(getTileData().contains("PlayerProfile"))
        {
            playerProfile = NBTUtil.readGameProfile(getTileData().getCompound("PlayerProfile"));
        }
        comparatorApplied = compound.getBoolean("comparatorApplied");
        OnlineChecking = compound.getBoolean("OnlineChecking");
        checkerCooldown = compound.getInt("checkerCooldown");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putString("PlayerName", this.BlockName);
        if (this.playerProfile != null)
        {
            CompoundNBT nbttagcompound = getTileData().getCompound("PlayerProfile");
            NBTUtil.writeGameProfile(nbttagcompound, getPlayerProfile());
            getTileData().put("PlayerProfile", nbttagcompound);
        }
        compound.putBoolean("comparatorApplied", comparatorApplied);
        compound.putBoolean("OnlineChecking", OnlineChecking);
        compound.putInt("checkerCooldown", checkerCooldown);
        return compound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());

        BlockState state = world.getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 0, this.getUpdateTag());
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
            this.playerProfile = SkullTileEntity.updateGameProfile(playerProfile);
            this.markDirty();
        }
    }

    @Override
    public void tick() {
        if (this.world.isRemote)
            return;


        BlockState state = world.getBlockState(getPos());
        if(state.getBlock() == StatueBlocks.player_statue && comparatorApplied)
        {
            boolean isStateOnline = state.get(PlayerStatueBlock.ONLINE);

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
                if(world.getPlayerByUuid(this.playerProfile.getId()) != null)
                    checkAnswer = true;
                else
                    checkAnswer = false;

                BlockState newState = state.with(PlayerStatueBlock.ONLINE, checkAnswer);

                if(isStateOnline != checkAnswer)
                {
                    world.setBlockState(getPos(), state.with(PlayerStatueBlock.ONLINE, checkAnswer), 5);
                    world.notifyBlockUpdate(getPos(), state, newState, 5);
                }
                setOnlineChecking(false);
            }
        }
    }

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
        return this.hasCustomName() ? new StringTextComponent(this.BlockName) : new TranslationTextComponent("statue.player");
    }

    @Override
    public ITextComponent getCustomName() {
        return null;
    }
}