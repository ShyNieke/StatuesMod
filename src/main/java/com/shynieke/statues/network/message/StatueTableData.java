package com.shynieke.statues.network.message;

import com.shynieke.statues.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StatueTableData(boolean isButtonPressed) implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "table_message");

	public StatueTableData(final FriendlyByteBuf packetBuffer) {
		this(packetBuffer.readBoolean());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeBoolean(isButtonPressed);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}
