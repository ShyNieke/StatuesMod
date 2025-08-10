package com.shynieke.statues.network.message;

import com.shynieke.statues.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record PlayerStatueScreenData(int entityID) implements CustomPacketPayload {

	public static final StreamCodec<FriendlyByteBuf, PlayerStatueScreenData> CODEC = CustomPacketPayload.codec(
			PlayerStatueScreenData::write,
			PlayerStatueScreenData::new);
	public static final Type<PlayerStatueScreenData> ID = new Type<>(Reference.modLoc("statue_screen"));


	public PlayerStatueScreenData(FriendlyByteBuf buf) {
		this(buf.readInt());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeInt(entityID);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
