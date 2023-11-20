package com.shynieke.statues.blockentities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.Services;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PlayerBlockEntity extends BlockEntity implements Nameable {
	@Nullable
	private static GameProfileCache profileCache;
	@Nullable
	private static MinecraftSessionService sessionService;
	@Nullable
	private static Executor mainThreadExecutor;
	private static final Executor CHECKED_MAIN_THREAD_EXECUTOR = runnable -> {
		Executor executor = mainThreadExecutor;
		if (executor != null) {
			executor.execute(runnable);
		}
	};

	private GameProfile playerProfile;
	private boolean comparatorApplied;
	private boolean onlineChecking;
	private int checkerCooldown;

	public PlayerBlockEntity(BlockPos pos, BlockState state) {
		super(StatueBlockEntities.PLAYER.get(), pos, state);
		this.comparatorApplied = false;
		this.checkerCooldown = 0;
		this.onlineChecking = false;
	}

	public static void setup(GameProfileCache gameProfileCache, MinecraftSessionService service, Executor executor) {
		profileCache = gameProfileCache;
		sessionService = service;
		mainThreadExecutor = executor;
	}

	public static void setup(Services services, Executor executor) {
		setup(services.profileCache(), services.sessionService(), executor);
	}

	public static void clear() {
		profileCache = null;
		sessionService = null;
		mainThreadExecutor = null;
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);

		if (compound.contains("PlayerProfile", 10)) {
			this.setPlayerProfile(NbtUtils.readGameProfile(compound.getCompound("PlayerProfile")));
		}

		comparatorApplied = compound.getBoolean("comparatorApplied");
		onlineChecking = compound.getBoolean("OnlineChecking");
		checkerCooldown = compound.getInt("checkerCooldown");
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if (this.playerProfile != null) {
			CompoundTag tag = new CompoundTag();
			NbtUtils.writeGameProfile(tag, this.playerProfile);
			compound.put("PlayerProfile", tag);
		}
		compound.putBoolean("comparatorApplied", comparatorApplied);
		compound.putBoolean("OnlineChecking", onlineChecking);
		compound.putInt("checkerCooldown", checkerCooldown);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		CompoundTag compoundNBT = pkt.getTag();
		handleUpdateTag(compoundNBT);
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		super.handleUpdateTag(tag);
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

	@Override
	public boolean hasCustomName() {
		return this.playerProfile != null && !this.playerProfile.getName().isEmpty();
	}

	@Nullable
	public GameProfile getPlayerProfile() {
		return this.playerProfile;
	}

	public void setPlayerProfile(@Nullable GameProfile profile) {
		this.playerProfile = profile;
		this.updateOwnerProfile();
	}

	private void updateOwnerProfile() {
		if (this.playerProfile != null && !Util.isBlank(this.playerProfile.getName()) && !hasTextures(this.playerProfile)) {
			fetchGameProfile(this.playerProfile.getName()).thenAcceptAsync(profile -> {
				this.playerProfile = profile.orElse(this.playerProfile);
				this.setChanged();
			}, CHECKED_MAIN_THREAD_EXECUTOR);
		} else {
			this.setChanged();
		}
	}

	public void updateOnline() {
		BlockState state = getBlockState();
		boolean isStateOnline = state.getValue(PlayerStatueBlock.ONLINE);
		boolean checkAnswer = level.getPlayerByUUID(this.playerProfile.getId()) != null;
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
		return this.hasCustomName() ? Component.literal(this.playerProfile != null ? playerProfile.getName() : "") : Component.translatable("entity.statues.player_statue");
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

	@Nullable
	public static GameProfile getOrResolveGameProfile(CompoundTag tag) {
		if (tag.contains("SkullOwner", 10)) {
			return NbtUtils.readGameProfile(tag.getCompound("SkullOwner"));
		} else {
			if (tag.contains("SkullOwner", 8)) {
				String s = tag.getString("SkullOwner");
				if (!Util.isBlank(s)) {
					tag.remove("SkullOwner");
					resolveGameProfile(tag, s);
				}
			}

			return null;
		}
	}

	public static void resolveGameProfile(CompoundTag tag) {
		String s = tag.getString("SkullOwner");
		if (!Util.isBlank(s)) {
			resolveGameProfile(tag, s);
		}
	}

	public static void resolveGameProfile(CompoundTag compoundTag, String username) {
		fetchGameProfile(username)
				.thenAccept(
						profile -> compoundTag.put("SkullOwner",
								NbtUtils.writeGameProfile(new CompoundTag(), profile.orElse(new GameProfile(Util.NIL_UUID, username))))
				);
	}

	public static CompletableFuture<Optional<GameProfile>> fetchGameProfile(String username) {
		GameProfileCache gameprofilecache = profileCache;
		return gameprofilecache == null
				? CompletableFuture.completedFuture(Optional.empty())
				: gameprofilecache.getAsync(username)
				.thenCompose(profile -> profile.isPresent() ? fillProfileTextures(profile.get()) : CompletableFuture.completedFuture(Optional.empty()))
				.thenApplyAsync((profile -> {
					GameProfileCache cache = profileCache;
					if (cache != null) {
						profile.ifPresent(cache::add);
						return profile;
					} else {
						return Optional.empty();
					}
				}), CHECKED_MAIN_THREAD_EXECUTOR);
	}

	private static CompletableFuture<Optional<GameProfile>> fillProfileTextures(GameProfile profile) {
		return hasTextures(profile) ? CompletableFuture.completedFuture(Optional.of(profile)) : CompletableFuture.supplyAsync(() -> {
			MinecraftSessionService minecraftsessionservice = sessionService;
			if (minecraftsessionservice != null) {
				ProfileResult profileresult = minecraftsessionservice.fetchProfile(profile.getId(), true);
				return profileresult == null ? Optional.of(profile) : Optional.of(profileresult.profile());
			} else {
				return Optional.empty();
			}
		}, Util.backgroundExecutor());
	}

	private static boolean hasTextures(GameProfile profile) {
		return profile.getProperties().containsKey("textures");
	}
}
