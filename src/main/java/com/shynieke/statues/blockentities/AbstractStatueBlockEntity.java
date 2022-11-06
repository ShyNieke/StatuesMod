package com.shynieke.statues.blockentities;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.util.UpgradeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStatueBlockEntity extends BlockEntity {
	private final Map<String, Short> upgradeMap = new HashMap<>();

	public int cooldown;
	public int interactCooldown;
	public boolean statueAble;
	public boolean statueInteractable;

	private int mobKilled, statueLevel, upgradeSlots;

	protected AbstractStatueBlockEntity(BlockEntityType<?> tileType, BlockPos pos, BlockState state) {
		super(tileType, pos, state);
		this.cooldown = 200;
		this.interactCooldown = 200;
		this.statueAble = false;
		this.statueInteractable = false;
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		cooldown = compound.getInt("StatueCooldown");
		interactCooldown = compound.getInt("InteractionCooldown");
		statueAble = compound.getBoolean("StatueAble");
		statueInteractable = compound.getBoolean("StatueInteractable");
		this.loadFromNbt(compound);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putInt("StatueCooldown", cooldown);
		compound.putInt("InteractionCooldown", interactCooldown);
		compound.putBoolean("StatueAble", statueAble);
		compound.putBoolean("StatueInteractable", statueInteractable);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		load(pkt.getTag());

		BlockState state = level.getBlockState(getBlockPos());
		level.sendBlockUpdated(getBlockPos(), state, state, 3);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag nbt = new CompoundTag();
		this.saveAdditional(nbt);
		return nbt;
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag nbt = new CompoundTag();
		this.saveAdditional(nbt);
		return nbt;
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public int getInteractCooldown() { return this.interactCooldown; }

	public int getStatueLevel() {
		return statueLevel;
	}

	public boolean isStatueAble() {
		return this.statueAble;
	}

	public void setStatueAble(boolean statueAble) {
		this.statueAble = statueAble;
		this.setChanged();
	}

	public boolean isStatueInteractable() {
		return this.statueInteractable;
	}

	public void setStatueInteractable(boolean interactable) {
		this.statueInteractable = interactable;
		this.setChanged();
	}

	public void loadFromNbt(CompoundTag compound) {
		mobKilled = compound.getInt(Reference.KILL_COUNT);
		statueLevel = compound.getInt(Reference.LEVEL);
		upgradeSlots = compound.getInt(Reference.UPGRADE_SLOTS);

		upgradeMap.clear();
		upgradeMap.putAll(UpgradeHelper.loadUpgradeMap(compound));
	}

	public CompoundTag saveToNbt(CompoundTag compound) {
		saveUpgrades(compound);

		return compound;
	}

	public CompoundTag saveUpgrades(CompoundTag tag) {
		tag.putInt(Reference.KILL_COUNT, mobKilled);
		tag.putInt(Reference.LEVEL, statueLevel);
		tag.putInt(Reference.UPGRADE_SLOTS, upgradeSlots);

		UpgradeHelper.saveUpgradeMap(tag, upgradeMap);

		return tag;
	}

	public Map<String, Short> getUpgradeMap() {
		return upgradeMap;
	}

	public boolean isDecorative() {
		return !this.upgradeMap.isEmpty();
	}

	public boolean hasUpgrade(String id) {
		return this.upgradeMap.containsKey(id);
	}

	public int getUpgradeLevel(String id) {
		return this.upgradeMap.getOrDefault(id, (short) -1);
	}

	public boolean makesSounds() {
		return hasUpgrade("sound");
	}

	public boolean isSpawner() {
		return hasUpgrade("spawner");
	}

	public boolean hasSpecialInteraction() {
		return hasUpgrade("interaction");
	}

	public boolean canDropLoot() {
		return hasUpgrade("looting");
	}

	public void interact(Level level, BlockPos pos, BlockState state, Player player, InteractionHand handIn, BlockHitResult result) {

	}

	public AbstractStatueBase getStatue() {
		if (getBlockState().getBlock() instanceof AbstractStatueBase statueBase)
			return statueBase;
		return null;
	}

	public void playSound(SoundEvent sound, BlockPos pos) {
		playSound(sound, pos, 1F);
	}

	public void playSound(SoundEvent sound, BlockPos pos, float pitch) {
		level.playSound(null, pos, sound, SoundSource.NEUTRAL, 1F, pitch);
	}

	public static final int[] DYE_COLORS = new int[]{1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

	public ItemStack getFirework(RandomSource rand) {
		ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
		firework.getOrCreateTag();
		CompoundTag nbt = new CompoundTag();
		nbt.putBoolean("Flicker", true);
		nbt.putBoolean("Trail", true);

		int[] colors = new int[rand.nextInt(8) + 1];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = DYE_COLORS[rand.nextInt(16)];
		}
		nbt.putIntArray("Colors", colors);
		byte type = (byte) (rand.nextInt(3) + 1);
		type = type == 3 ? 4 : type;
		nbt.putByte("Type", type);

		ListTag explosions = new ListTag();
		explosions.add(nbt);

		CompoundTag fireworkTag = new CompoundTag();
		fireworkTag.put("Explosions", explosions);
		fireworkTag.putByte("Flight", (byte) 1);
		CompoundTag stackTag = firework.getOrCreateTag();
		stackTag.put("Fireworks", fireworkTag);
		firework.setTag(stackTag);

		return firework;
	}
}
