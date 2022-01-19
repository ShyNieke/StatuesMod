package com.shynieke.statues.blockentities;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.init.StatueBlockEntities;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class PlayerBlockEntity extends BlockEntity implements Nameable {
    @Nullable
    private static GameProfileCache profileCache;
    @Nullable
    private static MinecraftSessionService sessionService;
    @Nullable
    private static Executor mainThreadExecutor;

    private GameProfile playerProfile;
    private boolean isSlim = false;
    private boolean comparatorApplied;
    private boolean onlineChecking;
    private int checkerCooldown;

    public PlayerBlockEntity(BlockPos pos, BlockState state) {
        super(StatueBlockEntities.PLAYER.get(), pos, state);
        this.comparatorApplied = false;
        this.checkerCooldown = 0;
        this.onlineChecking = false;
    }

    public static void setProfileCache(GameProfileCache cache) {
        profileCache = cache;
    }

    public static void setSessionService(MinecraftSessionService session) {
        sessionService = session;
    }

    public static void setMainThreadExecutor(Executor executor) {
        mainThreadExecutor = executor;
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
    public CompoundTag getTileData() {
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

    public boolean isSlim() {
        return this.isSlim;
    }

    public void setPlayerProfile(@Nullable GameProfile profile) {
        synchronized(this) {
            this.playerProfile = profile;
            if (this.level != null && this.level.isClientSide && this.playerProfile != null && this.playerProfile.isComplete() ) {
                Minecraft.getInstance().getSkinManager().registerSkins(this.playerProfile, (textureType, textureLocation, profileTexture) -> {
                    if (textureType.equals(MinecraftProfileTexture.Type.SKIN))  {
                        String metadata = profileTexture.getMetadata("model");
                        this.isSlim = metadata != null && metadata.equals("slim");
                    }
                }, true);
            }
        }

        this.updateOwnerProfile();
    }

    private void updateOwnerProfile() {
        updateGameprofile(this.playerProfile, (profile) -> {
            this.playerProfile = profile;
            this.setChanged();
        });
    }

    @Nullable
    public static void updateGameprofile(@Nullable GameProfile profile, Consumer<GameProfile> profileConsumer) {
        if (profile != null && !StringUtil.isNullOrEmpty(profile.getName()) && (!profile.isComplete() || !profile.getProperties().containsKey("textures")) && profileCache != null && sessionService != null) {
            profileCache.getAsync(profile.getName(), (gameProfile) -> {
                Util.backgroundExecutor().execute(() -> {
                    Util.ifElse(gameProfile, (gameProfile1) -> {
                        Property property = Iterables.getFirst(gameProfile1.getProperties().get("textures"), (Property)null);
                        if (property == null) {
                            gameProfile1 = sessionService.fillProfileProperties(gameProfile1, true);
                        }

                        GameProfile gameprofile = gameProfile1;
                        mainThreadExecutor.execute(() -> {
                            profileCache.add(gameprofile);
                            profileConsumer.accept(gameprofile);
                        });
                    }, () -> {
                        mainThreadExecutor.execute(() -> {
                            profileConsumer.accept(profile);
                        });
                    });
                });
            });
        } else {
            profileConsumer.accept(profile);
        }
    }

    public void updateOnline() {
        BlockState state = getBlockState();
        boolean isStateOnline = state.getValue(PlayerStatueBlock.ONLINE);
        boolean checkAnswer = level.getPlayerByUUID(this.playerProfile.getId()) != null;
        if(isStateOnline != checkAnswer) {
            BlockState newState = state.setValue(PlayerStatueBlock.ONLINE, checkAnswer);
            level.setBlockAndUpdate(getBlockPos(), newState);
            level.sendBlockUpdated(getBlockPos(), state, newState, 3);
        }
    }


    public void setComparatorApplied(boolean comparatorApplied) {
        this.comparatorApplied = comparatorApplied;
        if(!comparatorApplied) {
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
        return this.hasCustomName() ? new TextComponent(this.playerProfile != null ? playerProfile.getName() : "") : new TranslatableComponent("statue.player");
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return null;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PlayerBlockEntity blockEntity) {
        if(level != null) {
            if(state.is(StatueRegistry.PLAYER_STATUE.get()) && blockEntity.comparatorApplied) {
                if(!blockEntity.onlineChecking) {
                    blockEntity.checkerCooldown++;
                    blockEntity.setChanged();
                    if(blockEntity.checkerCooldown == 0)
                        blockEntity.checkerCooldown = 200;

                    if(blockEntity.checkerCooldown >= 200) {
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
}
