package com.svennieke.statues.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class AbstractStatueTile extends TileEntity implements ITickableTileEntity {
	public int cooldown;
	public int cooldownMax = 200; //TODO: Set cooldownMax with config
	public boolean statueAble;

	private int mobKilled;
	private int statueLevel;
	private boolean dropsItems;
	private float dropMultiplier;
	private boolean spawnsMobs;
	private boolean makesSounds;
	private boolean hasExternalUse;


	protected AbstractStatueTile(TileEntityType<?> tileType) {
		super(tileType);
		this.cooldown = 0;
		this.statueAble = false;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		cooldown = compound.getInt("StatueCooldown");
		cooldownMax = compound.getInt("StatueMaxcooldown");
		statueAble = compound.getBoolean("StatueAble");
		this.loadFromNbt(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("StatueCooldown", cooldown);
		compound.putInt("StatueMaxcooldown", cooldownMax);
		compound.putBoolean("StatueAble", statueAble);

		return this.saveToNbt(compound);
	}

	@Override
	public void tick() {
		if (!this.statueAble) {
			++this.cooldown;
			this.markDirty();

			if(this.cooldown >= this.cooldownMax) {
				this.cooldown = 0;
				setStatueAble(true);
			}
		}
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());

		BlockState state = world.getBlockState(getPos());
		world.notifyBlockUpdate(getPos(), state, state, 3);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public int getCooldownMax() {
		return this.cooldownMax;
	}

	public int getStatueLevel() {
		return statueLevel;
	}

	public float getDropMultiplier() {
		return dropMultiplier;
	}

	public boolean isStatueAble() {
		return this.statueAble;
	}

	public void setStatueAble(boolean statueAble) {
		this.statueAble = statueAble;
		this.markDirty();
	}

	public void loadFromNbt(CompoundNBT compound) {
		mobKilled = compound.getInt("mobKilled");
		statueLevel = compound.getInt("statueLevel");
		dropsItems = compound.getBoolean("dropsItems");
		spawnsMobs = compound.getBoolean("spawnsMobs");
		makesSounds = compound.getBoolean("makesSounds");
		hasExternalUse = compound.getBoolean("hasExternalUse");
		dropMultiplier = compound.getFloat("dropMultiplier");
	}

	public CompoundNBT saveToNbt(CompoundNBT compound) {
		saveAllTraits(compound);

		return compound;
	}

	public CompoundNBT saveAllTraits(CompoundNBT tag) {
		ListNBT listnbt = new ListNBT();

		CompoundNBT compoundnbt = new CompoundNBT();
		compoundnbt.putInt("mobKilled", mobKilled);
		compoundnbt.putInt("statueLevel", statueLevel);

		compoundnbt.putBoolean("dropsItems", dropsItems);
		compoundnbt.putBoolean("spawnsMobs", spawnsMobs);
		compoundnbt.putBoolean("makesSounds", makesSounds);
		compoundnbt.putBoolean("hasExternalUse", hasExternalUse);
		compoundnbt.putFloat("dropMultiplier", dropMultiplier);

		listnbt.add(compoundnbt);

		if (!listnbt.isEmpty()) {
			tag.put("Traits", listnbt);
		}

		return tag;
	}

	public boolean isDecorative() {
		return !this.dropsItems && !this.spawnsMobs && !this.makesSounds && !this.hasExternalUse;
	}

	public boolean canDropItems() {
		return this.dropsItems;
	}

	public boolean canSpawnMobs() {
		return this.spawnsMobs;
	}

	public boolean makesSounds() {
		return this.makesSounds;
	}

	public boolean hasExternalUse() {
		return this.hasExternalUse;
	}
}
