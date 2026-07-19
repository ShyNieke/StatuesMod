package com.shynieke.statues.network.message;

import com.shynieke.statues.Reference;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public record PlayerStatueSyncData(UUID playerUUID, CompoundTag tag) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, PlayerStatueSyncData> CODEC = StreamCodec.composite(
			UUIDUtil.STREAM_CODEC,
			o -> o.playerUUID,
			ByteBufCodecs.COMPOUND_TAG,
			o -> o.tag,
			PlayerStatueSyncData::new);
	public static final Type<PlayerStatueSyncData> ID = new Type<>(Reference.modLoc("statue_sync"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
