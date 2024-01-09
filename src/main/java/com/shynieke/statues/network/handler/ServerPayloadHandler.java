package com.shynieke.statues.network.handler;

import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.menu.StatueTableMenu;
import com.shynieke.statues.network.message.PlayerStatueSyncData;
import com.shynieke.statues.network.message.StatueTableData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.UUID;

public class ServerPayloadHandler {
	public static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

	public static ServerPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleSyncData(final PlayerStatueSyncData syncData, final PlayPayloadContext context) {
		// Do something with the data, on the main thread
		context.workHandler().submitAsync(() -> {
					//Sync big Player Statue data
					if (context.player().isPresent()) {
						final CompoundTag data = syncData.tag();
						if (context.player().get() instanceof ServerPlayer serverPlayer) {
							final ServerLevel serverLevel = serverPlayer.serverLevel();
							Entity entity = serverLevel.getEntity(syncData.playerUUID());
							if (entity instanceof PlayerStatue playerStatue && serverPlayer != null) {
								if (!playerStatue.isLocked() || (playerStatue.getLockedBy().equals(serverPlayer.getUUID()))) {
									CompoundTag entityTag = playerStatue.saveWithoutId(new CompoundTag());
									CompoundTag entityTagCopy = entityTag.copy();
									if (!data.isEmpty()) {
										entityTagCopy.merge(data);
										UUID uuid = playerStatue.getUUID();
										playerStatue.load(entityTagCopy);
										playerStatue.setUUID(uuid);
									}
									float YOffset = data.getFloat("yOffset");
									playerStatue.setYOffset(YOffset);
									String modelType = data.getString("Model");
									playerStatue.setModel(modelType);
									boolean lockFlag = data.getBoolean("Locked");
									if (lockFlag) {
										if (!playerStatue.isLocked()) {
											playerStatue.setLockedBy(serverPlayer.getUUID());
										}
									} else {
										if (playerStatue.isLocked()) {
											playerStatue.setUnlocked();
										}
									}
									ListTag tagList = data.getList("Move", Tag.TAG_DOUBLE);
									double x = tagList.getDouble(0);
									double y = tagList.getDouble(1);
									double z = tagList.getDouble(2);
									playerStatue.setPos(playerStatue.getX() + x,
											playerStatue.getY() + y,
											playerStatue.getZ() + z);
									boolean noGravityFlag = data.getBoolean("NoGravity");
									playerStatue.setNoGravity(noGravityFlag);
								}
							}
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.packetHandler().disconnect(Component.translatable("statues.networking.player_statue_sync.failed", e.getMessage()));
					return null;
				});
	}

	public void handleTableData(final StatueTableData tableData, final PlayPayloadContext context) {
		// Do something with the data, on the main thread
		context.workHandler().submitAsync(() -> {
					//Execute craft if button is pressed
					if (context.player().isPresent()) {
						Player player = context.player().get();
						AbstractContainerMenu container = player.containerMenu;
						if (container instanceof StatueTableMenu menu) {
							if (tableData.isButtonPressed()) {
								menu.getStatueBE().executeCraft();
							}
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.packetHandler().disconnect(Component.translatable("statues.networking.table.failed", e.getMessage()));
					return null;
				});
	}
}
