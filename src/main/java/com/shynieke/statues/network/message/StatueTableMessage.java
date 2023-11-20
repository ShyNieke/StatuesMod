package com.shynieke.statues.network.message;

import com.shynieke.statues.menu.StatueTableMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.NetworkEvent;

public class StatueTableMessage {

	public final boolean isButtonPressed;

	public StatueTableMessage(boolean buttonPressed) {
		this.isButtonPressed = buttonPressed;
	}

	public void encode(ByteBuf buf) {
		buf.writeBoolean(isButtonPressed);
	}

	public static StatueTableMessage decode(final ByteBuf packetBuffer) {
		return new StatueTableMessage(packetBuffer.readBoolean());
	}

	public void handle(NetworkEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isServer() && ctx.getSender() != null) {
				AbstractContainerMenu container = ctx.getSender().containerMenu;
				if (container instanceof StatueTableMenu menu) {
					if (isButtonPressed) {
						menu.getStatueBE().executeCraft();
						menu.slotsChanged(null);
					}
				}
			}
		});
		ctx.setPacketHandled(true);
	}
}
