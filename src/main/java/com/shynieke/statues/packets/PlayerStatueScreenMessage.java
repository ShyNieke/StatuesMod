package com.shynieke.statues.packets;

import com.shynieke.statues.entity.PlayerStatue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class PlayerStatueScreenMessage {
	private int entityID;

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

	public void handle(Supplier<Context> context) {
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isClient()) {
				Minecraft mc = Minecraft.getInstance();
				Entity entity = mc.level.getEntity(entityID);
				if (entity instanceof PlayerStatue) {
					PlayerStatue playerStatue = (PlayerStatue) entity;
					com.shynieke.statues.client.screen.PlayerPoseScreen.openScreen(playerStatue);
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
