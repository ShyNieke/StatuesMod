package com.shynieke.statues.tiles;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.shynieke.statues.blocks.statues.PlayerStatueBlock;
import com.shynieke.statues.init.StatueBlockEntities;
import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.Util;
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
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        if (this.playerProfile != null) {
            CompoundTag compoundnbt = new CompoundTag();
            NbtUtils.writeGameProfile(compoundnbt, this.playerProfile);
            compound.put("PlayerProfile", compoundnbt);
        }
        compound.putBoolean("comparatorApplied", comparatorApplied);
        compound.putBoolean("OnlineChecking", onlineChecking);
        compound.putInt("checkerCooldown", checkerCooldown);
        return compound;
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

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.save(new CompoundTag());
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
        synchronized(this) {
            this.playerProfile = profile;
        }

        this.updateOwnerProfile();
    }

    private void updateOwnerProfile() {
        updateGameprofile(this.playerProfile, (p_155747_) -> {
            this.playerProfile = p_155747_;
            this.setChanged();
        });
    }

    @Nullable
    public static void updateGameprofile(@Nullable GameProfile profile, Consumer<GameProfile> profileConsumer) {
        if (profile != null && !StringUtil.isNullOrEmpty(profile.getName()) && (!profile.isComplete() || !profile.getProperties().containsKey("textures")) && profileCache != null && sessionService != null) {
            profileCache.getAsync(profile.getName(), (p_182470_) -> {
                Util.backgroundExecutor().execute(() -> {
                    Util.ifElse(p_182470_, (p_182479_) -> {
                        Property property = Iterables.getFirst(p_182479_.getProperties().get("textures"), (Property)null);
                        if (property == null) {
                            p_182479_ = sessionService.fillProfileProperties(p_182479_, true);
                        }

                        GameProfile gameprofile = p_182479_;
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