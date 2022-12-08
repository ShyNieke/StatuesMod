package com.shynieke.statues.network;

import com.shynieke.statues.Reference;
import com.shynieke.statues.network.message.PlayerStatueScreenMessage;
import com.shynieke.statues.network.message.PlayerStatueSyncMessage;
import com.shynieke.statues.network.message.StatueTableMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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
		CHANNEL.registerMessage(id++, PlayerStatueSyncMessage.class, PlayerStatueSyncMessage::encode, PlayerStatueSyncMessage::decode, PlayerStatueSyncMessage::handle);
		CHANNEL.registerMessage(id++, PlayerStatueScreenMessage.class, PlayerStatueScreenMessage::encode, PlayerStatueScreenMessage::decode, PlayerStatueScreenMessage::handle);
		CHANNEL.registerMessage(id++, StatueTableMessage.class, StatueTableMessage::encode, StatueTableMessage::decode, StatueTableMessage::handle);
	}
}
