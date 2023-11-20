package com.shynieke.statues.network.message;

import com.shynieke.statues.entity.PlayerStatue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.NetworkEvent;

public class PlayerStatueScreenMessage {
	private final int entityID;

	private PlayerStatueScreenMessage(FriendlyByteBuf buf) {
		this.entityID = buf.readInt();
	}

	public PlayerStatueScreenMessage(int playerUUID) {
		this.entityID = playerUUID;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(entityID);
	}

	public static PlayerStatueScreenMessage decode(final FriendlyByteBuf packetBuffer) {
		return new PlayerStatueScreenMessage(packetBuffer.readInt());
	}

	public void handle(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				Minecraft mc = Minecraft.getInstance();
				Entity entity = mc.level.getEntity(entityID);
				if (entity instanceof PlayerStatue playerStatue) {
					com.shynieke.statues.client.screen.PlayerPoseScreen.openScreen(playerStatue);
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
