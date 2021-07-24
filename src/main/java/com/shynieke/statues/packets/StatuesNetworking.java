package com.shynieke.statues.packets;

import com.shynieke.statues.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class StatuesNetworking {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(Reference.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	private static int id = 0;

	public static void init() {
		CHANNEL.registerMessage(0, PlayerStatueSyncMessage.class, PlayerStatueSyncMessage::encode, PlayerStatueSyncMessage::decode, PlayerStatueSyncMessage::handle);
		CHANNEL.registerMessage(1, PlayerStatueScreenMessage.class, PlayerStatueScreenMessage::encode, PlayerStatueScreenMessage::decode, PlayerStatueScreenMessage::handle);
	}
}
