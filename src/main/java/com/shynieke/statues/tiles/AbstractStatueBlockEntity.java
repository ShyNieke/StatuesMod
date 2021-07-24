package com.shynieke.statues.tiles;

import com.shynieke.statues.blocks.AbstractStatueBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class AbstractStatueBlockEntity extends BlockEntity {
	public static int cooldown;
	public static int cooldownMax = 200; //TODO: Set cooldownMax with config
	public static boolean statueAble;

	private static int mobKilled;
	private static int statueLevel;
	private static boolean dropsItems;
	private static float dropMultiplier;
	private static boolean spawnsMobs;
	private static boolean makesSounds;
	private static boolean hasExternalUse;

	protected AbstractStatueBlockEntity(BlockEntityType<?> tileType, BlockPos pos, BlockState state) {
		super(tileType, pos, state);
		this.cooldown = 0;
		this.statueAble = false;
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		cooldown = compound.getInt("StatueCooldown");
		cooldownMax = compound.getInt("StatueMaxcooldown");
		statueAble = compound.getBoolean("StatueAble");
		this.loadFromNbt(compound);
	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		super.save(compound);
		compound.putInt("StatueCooldown", cooldown);
		compound.putInt("StatueMaxcooldown", cooldownMax);
		compound.putBoolean("StatueAble", statueAble);

		return this.saveToNbt(compound);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		load(pkt.getTag());

		BlockState state = level.getBlockState(getBlockPos());
		level.sendBlockUpdated(getBlockPos(), state, state, 3);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
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
		this.setChanged();
	}

	public void loadFromNbt(CompoundTag compound) {
		mobKilled = compound.getInt("mobKilled");
		statueLevel = compound.getInt("statueLevel");
		dropsItems = compound.getBoolean("dropsItems");
		spawnsMobs = compound.getBoolean("spawnsMobs");
		makesSounds = compound.getBoolean("makesSounds");
		hasExternalUse = compound.getBoolean("hasExternalUse");
		dropMultiplier = compound.getFloat("dropMultiplier");
	}

	public CompoundTag saveToNbt(CompoundTag compound) {
		saveAllTraits(compound);

		return compound;
	}

	public CompoundTag saveAllTraits(CompoundTag tag) {
		ListTag listnbt = new ListTag();

		CompoundTag compoundnbt = new CompoundTag();
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

	public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractStatueBlockEntity blockEntity) {
		if(state.getBlock() instanceof AbstractStatueBase && state.getValue(AbstractStatueBase.INTERACTIVE)) {
			if (!blockEntity.statueAble) {
				blockEntity.cooldown++;
				blockEntity.setChanged();

				if(blockEntity.cooldown >= blockEntity.cooldownMax) {
					blockEntity.cooldown = 0;
					blockEntity.setStatueAble(true);
				}
			}
		}

	}
}
