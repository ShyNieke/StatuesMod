package com.shynieke.statues.network.message;

import com.shynieke.statues.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PlayerStatueScreenData(int entityID) implements CustomPacketPayload {
	public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "statue_screen");

	public PlayerStatueScreenData(FriendlyByteBuf buf) {
		this(buf.readInt());
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeInt(entityID);
	}

	@Override
	public ResourceLocation id() {
		return ID;
	}
}
