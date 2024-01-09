package com.shynieke.statues.network;

import com.shynieke.statues.Reference;
import com.shynieke.statues.network.handler.ClientPayloadHandler;
import com.shynieke.statues.network.handler.ServerPayloadHandler;
import com.shynieke.statues.network.message.PlayerStatueScreenData;
import com.shynieke.statues.network.message.PlayerStatueSyncData;
import com.shynieke.statues.network.message.StatueTableData;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class StatuesNetworking {
	public static void setupPackets(final RegisterPayloadHandlerEvent event) {
		final IPayloadRegistrar registrar = event.registrar(Reference.MOD_ID);


		registrar.play(PlayerStatueScreenData.ID, PlayerStatueScreenData::new, handler -> handler
				.client(ClientPayloadHandler.getInstance()::handleData));
		registrar.play(StatueTableData.ID, StatueTableData::new, handler -> handler
				.server(ServerPayloadHandler.getInstance()::handleTableData));
		registrar.play(PlayerStatueSyncData.ID, PlayerStatueSyncData::new, handler -> handler
				.server(ServerPayloadHandler.getInstance()::handleSyncData));
	}
}
