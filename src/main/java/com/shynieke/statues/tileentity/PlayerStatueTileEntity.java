package com.shynieke.statues.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.shynieke.statues.blocks.Statues.BlockPlayer_Statue;
import com.shynieke.statues.init.StatuesBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PlayerStatueTileEntity extends TileEntity implements IWorldNameable, ITickable {

	public String BlockName;
	public GameProfile playerProfile;
	private boolean isSlim = false;
	public Boolean comparatorApplied;
	public Boolean OnlineChecking;
	public int checkerCooldown;

	public PlayerStatueTileEntity() {
		this.BlockName = "";
		this.comparatorApplied = false;
		this.checkerCooldown = 0;
		this.OnlineChecking = false;
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
		if (getTileData().hasKey("PlayerProfile")) {
			playerProfile = NBTUtil.readGameProfileFromNBT(getTileData().getCompoundTag("PlayerProfile"));
		}
		comparatorApplied = compound.getBoolean("comparatorApplied");
		OnlineChecking = compound.getBoolean("OnlineChecking");
		checkerCooldown = compound.getInteger("checkerCooldown");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("PlayerName", this.BlockName);
		if (this.playerProfile != null) {
			NBTTagCompound nbttagcompound = getTileData().getCompoundTag("PlayerProfile");
			NBTUtil.writeGameProfile(nbttagcompound, getPlayerProfile());
			getTileData().setTag("PlayerProfile", nbttagcompound);
		}
		compound.setBoolean("comparatorApplied", comparatorApplied);
		compound.setBoolean("OnlineChecking", OnlineChecking);
		compound.setInteger("checkerCooldown", checkerCooldown);
		return compound;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());

		IBlockState state = world.getBlockState(getPos());
		world.notifyBlockUpdate(getPos(), state, state, 3);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
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
	public GameProfile getPlayerProfile() {
		return this.playerProfile;
	}

	public boolean isSlim() {
		return this.isSlim;
	}

	public void setPlayerProfile(GameProfile profile) {
		this.playerProfile = profile;
		if (profile != null) {
			this.playerProfile = TileEntitySkull.updateGameProfile(playerProfile);
			if (this.world != null && this.world.isRemote && this.playerProfile.isComplete()) {
				Minecraft.getMinecraft().getSkinManager().loadProfileTextures(this.playerProfile, (textureType, textureLocation, profileTexture) -> {
					if (textureType.equals(MinecraftProfileTexture.Type.SKIN)) {
						String metadata = profileTexture.getMetadata("model");
						this.isSlim = metadata != null && metadata.equals("slim");
					}
				}, true);
			}
			this.markDirty();
		}
	}

	@Override
	public void update() {
		if (this.world.isRemote)
			return;


		IBlockState state = world.getBlockState(getPos());
		if (state.getBlock() == StatuesBlocks.player_statue && comparatorApplied) {
			boolean isStateOnline = state.getValue(BlockPlayer_Statue.ONLINE);

			if (!OnlineChecking) {
				++this.checkerCooldown;
				markDirty();
				if (this.checkerCooldown == 0)
					this.checkerCooldown = 200;

				if (this.checkerCooldown >= 200) {
					this.checkerCooldown = 0;
					setOnlineChecking(true);
				}
			} else {
				boolean checkAnswer = false;
				checkAnswer = world.getPlayerEntityByUUID(this.playerProfile.getId()) != null;

				IBlockState newState = state.withProperty(BlockPlayer_Statue.ONLINE, checkAnswer);

				if (isStateOnline != checkAnswer) {
					world.setBlockState(getPos(), state.withProperty(BlockPlayer_Statue.ONLINE, checkAnswer), 5);
					world.notifyBlockUpdate(getPos(), state, newState, 5);
				}
				setOnlineChecking(false);
			}
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return newSate.getBlock() != StatuesBlocks.player_statue;
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
}