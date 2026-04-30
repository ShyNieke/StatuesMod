package com.shynieke.statues.blockentities;

import com.google.common.cache.LoadingCache;
import com.mojang.authlib.GameProfile;
import com.shynieke.statues.Statues;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PlayerBlockEntity extends BlockEntity implements Nameable {
	@Nullable
	private static Executor mainThreadExecutor;
	@Nullable
	private static LoadingCache<String, CompletableFuture<Optional<GameProfile>>> profileCacheByName;
	@Nullable
	private static LoadingCache<UUID, CompletableFuture<Optional<GameProfile>>> profileCacheById;
	public static final Executor CHECKED_MAIN_THREAD_EXECUTOR = p_294078_ -> {
		Executor executor = mainThreadExecutor;
		if (executor != null) {
			executor.execute(p_294078_);
		}
	};

	@Nullable
	private ResolvableProfile playerProfile;
	private boolean comparatorApplied;
	private boolean onlineChecking;
	private int checkerCooldown;

	public PlayerBlockEntity(BlockPos pos, BlockState state) {
		super(StatueBlockEntities.PLAYER.get(), pos, state);
		this.comparatorApplied = false;
		this.checkerCooldown = 0;
		this.onlineChecking = false;
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		Optional<ResolvableProfile> optionalProfile = input.read("profile", ResolvableProfile.CODEC);
		optionalProfile.ifPresent(this::setPlayerProfile);

		comparatorApplied = input.getBooleanOr("comparatorApplied", false);
		onlineChecking = input.getBooleanOr("OnlineChecking", false);
		checkerCooldown = input.getIntOr("checkerCooldown", 0);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		if (this.playerProfile != null)
			output.store("profile", ResolvableProfile.CODEC, this.playerProfile);

		output.putBoolean("comparatorApplied", comparatorApplied);
		output.putBoolean("OnlineChecking", onlineChecking);
		output.putInt("checkerCooldown", checkerCooldown);
	}

	@Override
	public void onDataPacket(Connection net, ValueInput valueInput) {
		super.onDataPacket(net, valueInput);
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

	@Override
	public boolean hasCustomName() {
		return this.playerProfile != null && !this.playerProfile.name().isEmpty();
	}

	@Nullable
	public ResolvableProfile getPlayerProfile() {
		return this.playerProfile;
	}

	public void setPlayerProfile(@Nullable ResolvableProfile profile) {
		this.playerProfile = profile;
	}

	public void setPlayerProfileFromName(@Nullable Component component) {
		if (this.playerProfile != null) {
			return;
		}
		if (component != null) {
			String stackName = component.getString().toLowerCase(Locale.ROOT);
			boolean spaceFlag = stackName.contains(" ");
			boolean emptyFlag = stackName.isEmpty();

			if (!spaceFlag && !emptyFlag) {
				this.setPlayerProfile(ResolvableProfile.createUnresolved(stackName));
			}
		} else {
			this.setPlayerProfile(ResolvableProfile.createUnresolved("steve"));
		}
	}

	public void updateOnline() {
		BlockState state = getBlockState();
		boolean isStateOnline = state.getValue(PlayerStatueBlock.ONLINE);
		boolean checkAnswer = level.getPlayerByUUID(this.playerProfile.partialProfile().id()) != null;
		if (isStateOnline != checkAnswer) {
			BlockState newState = state.setValue(PlayerStatueBlock.ONLINE, checkAnswer);
			level.setBlockAndUpdate(getBlockPos(), newState);
			level.sendBlockUpdated(getBlockPos(), state, newState, 3);
		}
	}


	public void setComparatorApplied(boolean comparatorApplied) {
		this.comparatorApplied = comparatorApplied;
		if (!comparatorApplied) {
			BlockState state = getBlockState();
			BlockState newState = state.setValue(PlayerStatueBlock.ONLINE, false);
			level.setBlockAndUpdate(getBlockPos(), newState);
			level.sendBlockUpdated(getBlockPos(), state, newState, 3);
		}
		this.setChanged();
	}

	public boolean getComparatorApplied() {
		return comparatorApplied;
	}

	public int getCooldown() {
		return this.checkerCooldown;
	}

	public void setOnlineChecking(boolean onlineChecking) {
		this.onlineChecking = onlineChecking;
		this.setChanged();
	}

	@Override
	public Component getName() {
		return this.hasCustomName() ? Component.literal(this.playerProfile != null ?
				playerProfile.name().orElse("") : "") :
				Component.translatable("entity.statues.player_statue");
	}

	@Nullable
	@Override
	public Component getCustomName() {
		return null;
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, PlayerBlockEntity blockEntity) {
		if (level != null) {
			if (state.is(StatueRegistry.PLAYER_STATUE.get()) && blockEntity.comparatorApplied) {
				if (!blockEntity.onlineChecking) {
					blockEntity.checkerCooldown++;
					blockEntity.setChanged();
					if (blockEntity.checkerCooldown == 0)
						blockEntity.checkerCooldown = 200;

					if (blockEntity.checkerCooldown >= 200) {
						blockEntity.checkerCooldown = 0;
						blockEntity.setOnlineChecking(true);
					}
				} else {
					blockEntity.updateOnline();
					blockEntity.setOnlineChecking(false);
				}
			}
		}
	}

	public void saveToItem(ItemStack stack, HolderLookup.Provider registries) {
		try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Statues.LOGGER)) {
			TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, registries);
			saveCustomOnly(output);
			removeComponentsFromTag(output);

			BlockItem.setBlockEntityData(stack, this.getType(), output);
			stack.applyComponents(this.collectComponents());
		}
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter getter) {
		super.applyImplicitComponents(getter);
		this.setPlayerProfileFromName(getter.get(DataComponents.CUSTOM_NAME));
		if (getter.has(DataComponents.PROFILE))
			this.setPlayerProfile(getter.get(DataComponents.PROFILE));
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(DataComponents.CUSTOM_NAME, this.getName());
		builder.set(DataComponents.PROFILE, this.playerProfile);
	}

	@Override
	public void removeComponentsFromTag(ValueOutput output) {
		super.removeComponentsFromTag(output);
		output.discard("profile");
		output.discard("OnlineChecking");
		output.discard("checkerCooldown");
		output.discard("comparatorApplied");
	}
}
