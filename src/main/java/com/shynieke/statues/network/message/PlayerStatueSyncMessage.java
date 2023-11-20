package com.shynieke.statues.network.message;

import com.shynieke.statues.entity.PlayerStatue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.UUID;

public class PlayerStatueSyncMessage {
	private final UUID entityUUID;
	private final CompoundTag data;

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

	public void handle(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isServer()) {
				final ServerPlayer player = ctx.getSender();
				final ServerLevel serverLevel = ctx.getSender().serverLevel();
				Entity entity = serverLevel.getEntity(this.entityUUID);
				if (entity instanceof PlayerStatue playerStatue && player != null) {
					if (!playerStatue.isLocked() || (playerStatue.getLockedBy().equals(player.getUUID()))) {
						CompoundTag entityTag = playerStatue.saveWithoutId(new CompoundTag());
						CompoundTag entityTagCopy = entityTag.copy();

						if (!this.data.isEmpty()) {
							entityTagCopy.merge(this.data);
							UUID uuid = playerStatue.getUUID();
							playerStatue.load(entityTagCopy);
							playerStatue.setUUID(uuid);
						}

						float YOffset = data.getFloat("yOffset");
						playerStatue.setYOffset(YOffset);

						String modelType = data.getString("Model");
						playerStatue.setModel(modelType);

						boolean lockFlag = data.getBoolean("Locked");
						if (lockFlag) {
							if (!playerStatue.isLocked()) {
								playerStatue.setLockedBy(player.getUUID());
							}
						} else {
							if (playerStatue.isLocked()) {
								playerStatue.setUnlocked();
							}
						}

						ListTag tagList = data.getList("Move", Tag.TAG_DOUBLE);
						double x = tagList.getDouble(0);
						double y = tagList.getDouble(1);
						double z = tagList.getDouble(2);
						playerStatue.setPos(playerStatue.getX() + x,
								playerStatue.getY() + y,
								playerStatue.getZ() + z);

						boolean noGravityFlag = data.getBoolean("NoGravity");
						if (noGravityFlag) {
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
