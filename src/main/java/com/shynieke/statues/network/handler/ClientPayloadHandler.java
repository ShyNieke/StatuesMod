package com.shynieke.statues.network.handler;

import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.network.message.PlayerStatueScreenData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientPayloadHandler {
	private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

	public static ClientPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleData(final PlayerStatueScreenData data, final PlayPayloadContext context) {
		context.workHandler().submitAsync(() -> {
					//Open Captcha Screen
					Minecraft mc = Minecraft.getInstance();
					Entity entity = mc.level.getEntity(data.entityID());
					if (entity instanceof PlayerStatue playerStatue) {
						com.shynieke.statues.client.screen.PlayerPoseScreen.openScreen(playerStatue);
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.packetHandler().disconnect(Component.translatable("statues.networking.player_statue_screen.failed", e.getMessage()));
					return null;
				});
	}
}
