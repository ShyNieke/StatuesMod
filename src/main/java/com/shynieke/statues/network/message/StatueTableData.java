package com.shynieke.statues.network.message;

import com.shynieke.statues.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record StatueTableData(boolean isButtonPressed) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, StatueTableData> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			o -> o.isButtonPressed,
			StatueTableData::new);
	public static final Type<StatueTableData> ID = new Type<>(Reference.modLoc("table_message"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
