package com.shynieke.statues.blockentities;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.shynieke.statues.Statues;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.Services;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

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

	public static void setup(final Services services, Executor p_mainThreadExecutor) {
		mainThreadExecutor = p_mainThreadExecutor;
		final BooleanSupplier booleansupplier = () -> profileCacheById == null;
		profileCacheByName = CacheBuilder.newBuilder()
				.expireAfterAccess(Duration.ofMinutes(10L))
				.maximumSize(256L)
				.build(new CacheLoader<String, CompletableFuture<Optional<GameProfile>>>() {
					public CompletableFuture<Optional<GameProfile>> load(String username) {
						return PlayerBlockEntity.fetchProfileByName(username, services);
					}
				});
		profileCacheById = CacheBuilder.newBuilder()
				.expireAfterAccess(Duration.ofMinutes(10L))
				.maximumSize(256L)
				.build(new CacheLoader<UUID, CompletableFuture<Optional<GameProfile>>>() {
					public CompletableFuture<Optional<GameProfile>> load(UUID id) {
						return PlayerBlockEntity.fetchProfileById(id, services, booleansupplier);
					}
				});
	}

	static CompletableFuture<Optional<GameProfile>> fetchProfileByName(String name, Services services) {
		return services.profileCache()
				.getAsync(name)
				.thenCompose(
						optionalProfile -> {
							LoadingCache<UUID, CompletableFuture<Optional<GameProfile>>> loadingcache = profileCacheById;
							return loadingcache != null && !optionalProfile.isEmpty()
									? loadingcache.getUnchecked(optionalProfile.get().getId()).thenApply(p_339543_ -> p_339543_.or(() -> optionalProfile))
									: CompletableFuture.completedFuture(Optional.empty());
						}
				);
	}

	static CompletableFuture<Optional<GameProfile>> fetchProfileById(UUID id, Services services, BooleanSupplier cacheUninitialized) {
		return CompletableFuture.supplyAsync(() -> {
			if (cacheUninitialized.getAsBoolean()) {
				return Optional.empty();
			} else {
				ProfileResult profileresult = services.sessionService().fetchProfile(id, true);
				return Optional.ofNullable(profileresult).map(ProfileResult::profile);
			}
		}, Util.backgroundExecutor());
	}

	public static void clear() {
		mainThreadExecutor = null;
		profileCacheByName = null;
		profileCacheById = null;
	}

	@Override
	public void loadAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
		super.loadAdditional(compound, lookupProvider);

		if (compound.contains("profile")) {
			ResolvableProfile.CODEC
					.parse(NbtOps.INSTANCE, compound.get("profile"))
					.resultOrPartial(error -> Statues.LOGGER.error("Failed to load profile from player statue: {}", error))
					.ifPresent(this::setPlayerProfile);
		}

		comparatorApplied = compound.getBoolean("comparatorApplied");
		onlineChecking = compound.getBoolean("OnlineChecking");
		checkerCooldown = compound.getInt("checkerCooldown");
	}

	@Override
	public void saveAdditional(CompoundTag compound, HolderLookup.Provider lookupProvider) {
		super.saveAdditional(compound, lookupProvider);
		if (this.playerProfile != null) {
			compound.put("profile", ResolvableProfile.CODEC.encodeStart(NbtOps.INSTANCE, this.playerProfile).getOrThrow());
		}
		compound.putBoolean("comparatorApplied", comparatorApplied);
		compound.putBoolean("OnlineChecking", onlineChecking);
		compound.putInt("checkerCooldown", checkerCooldown);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
		CompoundTag compoundNBT = pkt.getTag();
		handleUpdateTag(compoundNBT, lookupProvider);
	}

	@Override
	public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
		super.handleUpdateTag(tag, lookupProvider);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
		CompoundTag nbt = new CompoundTag();
		this.saveAdditional(nbt, lookupProvider);
		return nbt;
	}

	@Override
	public CompoundTag getPersistentData() {
		CompoundTag nbt = new CompoundTag();
		this.saveAdditional(nbt, level != null ? level.registryAccess() : VanillaRegistries.createLookup());
		return nbt;
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
		synchronized (this) {
			this.playerProfile = profile;
		}

		this.updateOwnerProfile();
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
				GameProfile newProfile = new GameProfile(Util.NIL_UUID, stackName);
				this.setPlayerProfile(new ResolvableProfile(newProfile));
			}
		} else {
			this.setPlayerProfile(new ResolvableProfile(new GameProfile(Util.NIL_UUID, "steve")));
		}
	}

	private void updateOwnerProfile() {
		if (this.playerProfile != null && !this.playerProfile.isResolved()) {
			this.resolve(this.playerProfile).thenAcceptAsync(profile -> {
				this.playerProfile = profile;
				this.setChanged();
			}, CHECKED_MAIN_THREAD_EXECUTOR);
		} else {
			this.setChanged();
		}
	}

	public void updateOnline() {
		BlockState state = getBlockState();
		boolean isStateOnline = state.getValue(PlayerStatueBlock.ONLINE);
		boolean checkAnswer = level.getPlayerByUUID(this.playerProfile.id().orElse(Util.NIL_UUID)) != null;
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

	@Override
	public void saveToItem(ItemStack stack, HolderLookup.Provider registries) {
		super.saveToItem(stack, registries);
	}

	@Override
	protected void applyImplicitComponents(BlockEntity.DataComponentInput input) {
		super.applyImplicitComponents(input);
		if(input.get(DataComponents.PROFILE) != null) {
			this.setPlayerProfile(input.get(DataComponents.PROFILE));
		} else {
			this.setPlayerProfileFromName(input.get(DataComponents.CUSTOM_NAME));
		}
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(DataComponents.CUSTOM_NAME, this.getName());
		builder.set(DataComponents.PROFILE, this.playerProfile);
	}

	@Override
	public void removeComponentsFromTag(CompoundTag tag) {
		super.removeComponentsFromTag(tag);
		tag.remove("profile");
		tag.remove("OnlineChecking");
		tag.remove("checkerCooldown");
		tag.remove("comparatorApplied");
	}

	public static CompletableFuture<Optional<GameProfile>> fetchGameProfile(String profileName) {
		LoadingCache<String, CompletableFuture<Optional<GameProfile>>> loadingcache = profileCacheByName;
		return loadingcache != null && StringUtil.isValidPlayerName(profileName)
				? loadingcache.getUnchecked(profileName)
				: CompletableFuture.completedFuture(Optional.empty());
	}

	public static CompletableFuture<Optional<GameProfile>> fetchGameProfile(UUID profileUuid) {
		LoadingCache<UUID, CompletableFuture<Optional<GameProfile>>> loadingcache = profileCacheById;
		return loadingcache != null ? loadingcache.getUnchecked(profileUuid) : CompletableFuture.completedFuture(Optional.empty());
	}

	public static CompletableFuture<ResolvableProfile> resolve(ResolvableProfile resolvableProfile) {
		if (resolvableProfile.isResolved()) {
			return CompletableFuture.completedFuture(resolvableProfile);
		} else {
			return resolvableProfile.id().isPresent() ? fetchGameProfile(resolvableProfile.id().get()).thenApply(p_332081_ -> {
				GameProfile gameprofile = p_332081_.orElseGet(() -> new GameProfile(resolvableProfile.id().get(), resolvableProfile.name().orElse("")));
				return new ResolvableProfile(gameprofile);
			}) : fetchGameProfile(resolvableProfile.name().orElseThrow()).thenApply(p_339530_ -> {
				GameProfile gameprofile = p_339530_.orElseGet(() -> new GameProfile(Util.NIL_UUID, resolvableProfile.name().get()));
				return new ResolvableProfile(gameprofile);
			});
		}
	}
}
