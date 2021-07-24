package com.shynieke.statues.packets;

import com.shynieke.statues.entity.PlayerStatue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerStatueSyncMessage {
    private UUID entityUUID;
    private CompoundTag data;

    private PlayerStatueSyncMessage(FriendlyByteBuf buf) {
        this.entityUUID = buf.readUUID();
        this.data = buf.readNbt();
    }

    public PlayerStatueSyncMessage(UUID playerUUID, CompoundTag tag) {
        this.entityUUID = playerUUID;
        this.data = tag;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(entityUUID);
        buf.writeNbt(data);
    }

    public static PlayerStatueSyncMessage decode(final FriendlyByteBuf packetBuffer) {
        return new PlayerStatueSyncMessage(packetBuffer.readUUID(), packetBuffer.readNbt());
    }

    public void handle(Supplier<Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide().isServer()) {
                final ServerPlayer player = ctx.getSender();
                final ServerLevel world = ctx.getSender().getLevel();
                Entity entity = world.getEntity(this.entityUUID);
                if (entity instanceof PlayerStatue && player != null) {
                    PlayerStatue playerStatue = (PlayerStatue)entity;
                    if(!playerStatue.isLocked() || (playerStatue.getLockedBy().equals(player.getUUID()))) {
                        CompoundTag entityTag = playerStatue.saveWithoutId(new CompoundTag());
                        CompoundTag entityTagCopy = entityTag.copy();

                        if(!this.data.isEmpty()) {
                            entityTagCopy.merge(this.data);
                            UUID uuid = playerStatue.getUUID();
                            playerStatue.load(entityTagCopy);
                            playerStatue.setUUID(uuid);
                        }

                        float YOffset = data.getFloat("yOffset");
                        playerStatue.setYOffset(YOffset);

                        boolean lockFlag = data.getBoolean("Locked");
                        if(lockFlag) {
                            if(!playerStatue.isLocked()) {
                                playerStatue.setLockedBy(player.getUUID());
                            }
                        } else {
                            if(playerStatue.isLocked()) {
                                playerStatue.setUnlocked();
                            }
                        }

                        boolean noGravityFlag = data.getBoolean("NoGravity");
                        if(noGravityFlag) {
                            playerStatue.setNoGravity(true);
                        } else {
                            playerStatue.setNoGravity(false);
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
