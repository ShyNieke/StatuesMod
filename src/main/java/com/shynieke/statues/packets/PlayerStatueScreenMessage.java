package com.shynieke.statues.packets;

import com.shynieke.statues.entity.PlayerStatueEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.UUID;
import java.util.function.Supplier;

public class PlayerStatueScreenMessage {
    private int entityID;

    private PlayerStatueScreenMessage(PacketBuffer buf) {
        this.entityID = buf.readInt();
    }

    public PlayerStatueScreenMessage(int playerUUID) {
        this.entityID = playerUUID;
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(entityID);
    }

    public static PlayerStatueScreenMessage decode(final PacketBuffer packetBuffer) {
        return new PlayerStatueScreenMessage(packetBuffer.readInt());
    }

    public void handle(Supplier<Context> context) {
        Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide().isClient()) {
                Minecraft mc = Minecraft.getInstance();
                Entity entity = mc.world.getEntityByID(entityID);
                if (entity instanceof PlayerStatueEntity) {
                    PlayerStatueEntity playerStatue = (PlayerStatueEntity)entity;
                    com.shynieke.statues.client.screen.PlayerPoseScreen.openScreen(playerStatue);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
