package com.shynieke.statues.blockentities;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.datacomponent.StatueStats;
import com.shynieke.statues.datacomponent.StatueUpgrades;
import com.shynieke.statues.registry.StatueDataComponents;
import com.shynieke.statues.storage.StatueSavedData;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractStatueBlockEntity extends BlockEntity {
	private final Map<String, Short> upgrades = new HashMap<>();
	protected EnergyStorage energyStorage = new EnergyStorage(10000, 100);

	@Nullable
	private StatueStats stats;

	public int cooldown;
	public int interactCooldown;
	public boolean statueAble;
	public boolean statueInteractable;

	public boolean statueUpgraded;

	protected AbstractStatueBlockEntity(BlockEntityType<?> tileType, BlockPos pos, BlockState state) {
		super(tileType, pos, state);
		this.stats = StatueStats.empty();
		this.cooldown = StatuesConfig.COMMON.statueCooldown.get();
		this.interactCooldown = StatuesConfig.COMMON.statueCooldown.get();
		this.statueAble = false;
		this.statueInteractable = false;
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		cooldown = input.getIntOr("StatueCooldown", 0);
		interactCooldown = input.getIntOr("InteractionCooldown", 0);
		statueAble = input.getBooleanOr("StatueAble", false);
		statueInteractable = input.getBooleanOr("StatueInteractable", false);

		energyStorage.deserialize(input);
		this.loadFromNbt(input);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		output.putInt("StatueCooldown", cooldown);
		output.putInt("InteractionCooldown", interactCooldown);
		output.putBoolean("StatueAble", statueAble);
		output.putBoolean("StatueInteractable", statueInteractable);
		energyStorage.serialize(output);
		this.saveToNbt(output);
	}

	public void saveToItem(ItemStack stack, HolderLookup.Provider provider) {
		stack.set(StatueDataComponents.STATS, this.stats);
		stack.set(StatueDataComponents.UPGRADED, true);
		stack.set(StatueDataComponents.UPGRADES, new StatueUpgrades(this.upgrades));
	}


	@Override
	protected void applyImplicitComponents(DataComponentGetter getter) {
		super.applyImplicitComponents(getter);
		this.stats = getter.getOrDefault(StatueDataComponents.STATS, StatueStats.empty());
		Map<String, Short> upgradeMap = getter.getOrDefault(StatueDataComponents.UPGRADES, StatueUpgrades.empty()).upgradeMap();
		if (!upgradeMap.isEmpty())
			this.upgrades.putAll(upgradeMap);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(StatueDataComponents.UPGRADES, new StatueUpgrades(this.upgrades));
		builder.set(StatueDataComponents.STATS, this.stats);
	}

	@Override
	public void removeComponentsFromTag(ValueOutput output) {
		super.removeComponentsFromTag(output);
		output.discard("tag");
		output.discard("stats");
		output.discard("energy");
	}

	public EnergyStorage getEnergyStorage(@Nullable Direction facing) {
		if (StatuesConfig.COMMON.requiresPower.get()) {
			return energyStorage;
		}
		return null;
	}

	@Override
	public void onLoad() {
		super.onLoad();
		if (level != null && isDespawner()) {
			StatueSavedData.get().addPosition(level.dimension(), getBlockPos());
		}
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		if (level != null && isDespawner()) {
			StatueSavedData.get().removePosition(level.dimension(), getBlockPos());
		}
	}

	@Override
	public void onDataPacket(Connection net, ValueInput valueInput) {
		super.onDataPacket(net, valueInput);

		BlockState state = level.getBlockState(getBlockPos());
		level.sendBlockUpdated(getBlockPos(), state, state, 3);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		CompoundTag tag = new CompoundTag();
		try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Statues.LOGGER)) {
			TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
			this.saveAdditional(output);
			tag.merge(output.buildResult());
		}
		return tag;
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag tag = new CompoundTag();
		try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Statues.LOGGER)) {
			HolderLookup.Provider lookupProvider = this.level != null ? this.level.registryAccess() : VanillaRegistries.createLookup();
			TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
			this.saveAdditional(output);
			tag.merge(output.buildResult());
		}
		return tag;
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public int getCooldown() {
		return Math.max(StatuesConfig.COMMON.statueMinCooldown.get(), StatuesConfig.COMMON.statueCooldown.get() - getSpeedTicks());
	}

	public int getInteractCooldown() {
		return Math.max(StatuesConfig.COMMON.statueMinCooldown.get(), StatuesConfig.COMMON.statueCooldown.get() - getSpeedTicks());
	}

	public int getSpeedTicks() {
		return (getSpeed() * StatuesConfig.COMMON.statueSpeedUpgrade.get());
	}

	public int getStatueLevel() {
		return stats != null ? stats.level() : -1;
	}

	public int getUpgradeSlots() {
		return stats != null ? stats.upgradeSlots() : -1;
	}

	public int getKillCount() {
		return stats != null ? stats.killCount() : -1;
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

	public void loadFromNbt(ValueInput input) {
		statueUpgraded = input.getBooleanOr(Reference.UPGRADED, false);

		Optional<StatueUpgrades> upgrades = input.read("upgrades", StatueUpgrades.CODEC);
		upgrades.ifPresent((statueUpgrades) -> {
			this.upgrades.clear();
			this.upgrades.putAll(statueUpgrades.upgradeMap());
		});

		Optional<StatueStats> stats = input.read("stats", StatueStats.CODEC);
		stats.ifPresent(this::setStats);
	}

	public void setStats(StatueStats stats) {
		this.stats = stats;
	}

	public void saveToNbt(ValueOutput output) {
		saveUpgrades(output);
		if (this.upgrades != null) {
			output.store("upgrades", StatueUpgrades.CODEC, new StatueUpgrades(this.upgrades));
		}
		if (this.stats != null) {
			output.store("stats", StatueStats.CODEC, this.stats);
		}
	}

	public void saveUpgrades(ValueOutput output) {
		output.putBoolean(Reference.UPGRADED, statueUpgraded);
	}

	protected void refreshClient() {
		setChanged();
		BlockState state = level.getBlockState(worldPosition);
		level.sendBlockUpdated(worldPosition, state, state, 2);
	}

	public Map<String, Short> getUpgrades() {
		return upgrades;
	}

	public boolean isDecorative() {
		return !getUpgrades().isEmpty();
	}

	public boolean hasUpgrade(String id) {
		return getUpgrades().containsKey(id);
	}

	public int getUpgradeLevel(String id) {
		return getUpgrades().getOrDefault(id, (short) -1);
	}

	public boolean makesSounds() {
		return hasUpgrade("sound");
	}

	public boolean isSpawner() {
		return hasUpgrade("spawner");
	}

	public boolean isKiller() {
		return hasUpgrade("mob_killer");
	}

	public boolean isDespawner() {
		return hasUpgrade("despawner");
	}

	public int getSpawnerLevel() {
		return getUpgradeLevel("spawner");
	}

	public boolean hasSpecialInteraction() {
		return hasUpgrade("interaction");
	}

	public boolean canDropLoot() {
		return hasUpgrade("looting");
	}

	public int getLooting() {
		return getUpgradeLevel("looting") - 1;
	}

	public boolean canAutomate() {
		return hasUpgrade("automation");
	}

	public int getSpeed() {
		return hasUpgrade("speed") ? getUpgradeLevel("speed") + 1 : 0;
	}

	private int getAdjustedPower(int power) {
		return Mth.ceil(power * (canAutomate() ? 1.2 : 1.0));
	}

	public int getItemPowerUsage() {
		double lootingMultiplier = switch (getLooting()) {
			case 2 -> 1.25;
			case 3 -> 1.5;
			default -> 1.0;
		};
		int amount = StatuesConfig.COMMON.itemPowerUsage.get();
		if (amount == 0) return 0;
		return getAdjustedPower(Mth.ceil(amount * lootingMultiplier));
	}

	public int getKillPowerUsage() {
		int amount = StatuesConfig.COMMON.killPowerUsage.get();
		if (amount == 0) return 0;
		return getAdjustedPower(amount);
	}

	public int getSummonPowerUsage() {
		return StatuesConfig.COMMON.summonPowerUsage.get();
	}

	public int getDespawnPowerUsage() {
		return StatuesConfig.COMMON.despawnPowerUsage.get();
	}

	public int getPassiveDrain() {
		return 100;
	}

	public boolean usesPower() {
		return StatuesConfig.COMMON.requiresPower.get();
	}

	public boolean drainPower(int amount) {
		if (usesPower() && amount > 0) {
			boolean hasEnergy = energyStorage.getEnergyStored() >= amount;
			if (!hasEnergy) return false;
			energyStorage.extractEnergy(amount, false);
		}
		return true;
	}

	public InteractionResult interact(Level level, BlockPos pos, BlockState state, Player player, InteractionHand handIn, BlockHitResult result) {
		return InteractionResult.PASS;
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

		List<FireworkExplosion> explosions = new ArrayList<>();
		int[] colors = new int[rand.nextInt(8) + 1];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = DYE_COLORS[rand.nextInt(16)];
		}
		IntList colorList = IntList.of(colors);
		byte type = (byte) (rand.nextInt(3) + 1);
		type = type == 3 ? 4 : type;
		FireworkExplosion explosion = new FireworkExplosion(FireworkExplosion.Shape.byId(type), colorList, IntList.of(), true, true);
		explosions.add(explosion);

		Fireworks fireworks = new Fireworks(1, explosions);
		firework.set(DataComponents.FIREWORKS, fireworks);

		return firework;
	}

	protected class BiggestInventory implements Comparable<BiggestInventory> {
		private final int inventorySize;
		private final BlockPos tilePos;
		private final Direction direction;

		public BiggestInventory(BlockPos pos, int size, Direction dir) {
			this.tilePos = pos;
			this.inventorySize = size;
			this.direction = dir;
		}

		@SuppressWarnings("deprecation")
		protected IItemHandler getIItemHandler(ServerLevel level) {
			if (level.isAreaLoaded(worldPosition, 1)) {
				BlockEntity blockEntity = level.getBlockEntity(tilePos);
				BlockCapabilityCache<IItemHandler, Direction> cache = BlockCapabilityCache.create(Capabilities.ItemHandler.BLOCK, level, tilePos, direction);
				if (!blockEntity.isRemoved() && blockEntity.hasLevel() && cache.getCapability() != null) {
					return cache.getCapability();
				}
			}
			return null;
		}

		@Override
		public int compareTo(BiggestInventory otherInventory) {
			return Integer.compare(this.inventorySize, otherInventory.inventorySize);
		}
	}
}
