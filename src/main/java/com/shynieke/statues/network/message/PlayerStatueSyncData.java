package com.shynieke.statues.network.message;

import com.shynieke.statues.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record PlayerStatueSyncData(UUID playerUUID, CompoundTag tag) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, PlayerStatueSyncData> CODEC = CustomPacketPayload.codec(
			PlayerStatueSyncData::write,
			PlayerStatueSyncData::new);
	public static final Type<PlayerStatueSyncData> ID = new Type<>(Reference.modLoc("statue_sync"));

	public PlayerStatueSyncData(final FriendlyByteBuf buf) {
		this(buf.readUUID(), buf.readNbt());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeUUID(playerUUID);
		buf.writeNbt(tag);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
