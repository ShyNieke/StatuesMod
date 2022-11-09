package com.shynieke.statues.network.message;

import com.shynieke.statues.menu.StatueTableMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class StatueTableMessage {

	public boolean isButtonPressed;

	public StatueTableMessage(boolean buttonPressed) {
		this.isButtonPressed = buttonPressed;
	}

	public void encode(ByteBuf buf) {
		buf.writeBoolean(isButtonPressed);
	}

	public static StatueTableMessage decode(final ByteBuf packetBuffer) {
		return new StatueTableMessage(packetBuffer.readBoolean());
	}

	public void handle(Supplier<Context> context) {
		Context ctx = context.get();
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
