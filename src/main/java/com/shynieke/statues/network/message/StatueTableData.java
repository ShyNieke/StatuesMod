package com.shynieke.statues.network.message;

import com.shynieke.statues.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StatueTableData(boolean isButtonPressed) implements CustomPacketPayload {
	public static final StreamCodec<FriendlyByteBuf, StatueTableData> CODEC = CustomPacketPayload.codec(
			StatueTableData::write,
			StatueTableData::new);
	public static final Type<StatueTableData> ID = new Type<>(Reference.modLoc("table_message"));

	public StatueTableData(final FriendlyByteBuf packetBuffer) {
		this(packetBuffer.readBoolean());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeBoolean(isButtonPressed);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
