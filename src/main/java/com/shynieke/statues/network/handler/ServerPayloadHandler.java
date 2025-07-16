package com.shynieke.statues.network.handler;

import com.shynieke.statues.Statues;
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
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.UUID;

public class ServerPayloadHandler {
	public static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

	public static ServerPayloadHandler getInstance() {
		return INSTANCE;
	}

	private static final List<String> allowedKeys = List.of(
			"NoGravity", "Small", "CustomNameVisible", "Invulnerable",
			"Pose", "DisabledSlots", "Pose", "Scale", "Move", "Rotation",
			"Model", "yOffset", "Locked"
	);

	public void handleSyncData(final PlayerStatueSyncData syncData, final IPayloadContext context) {
		// Do something with the data, on the main thread
		context.enqueueWork(() -> {
					//Sync big Player Statue data
					if (context.player() != null) {
						final CompoundTag data = syncData.tag();
						if (context.player() instanceof ServerPlayer serverPlayer) {
							final ServerLevel serverLevel = serverPlayer.serverLevel();
							Entity entity = serverLevel.getEntity(syncData.playerUUID());
							if (entity instanceof PlayerStatue playerStatue && serverPlayer != null) {
								if (!playerStatue.isLocked() || (playerStatue.getLockedBy().equals(serverPlayer.getUUID()))) {
									CompoundTag entityTag = playerStatue.saveWithoutId(new CompoundTag());
									CompoundTag entityTagCopy = entityTag.copy();
									if (!data.isEmpty()) {
										List<String> keysToRemove = data.getAllKeys().stream()
												.filter(key -> !allowedKeys.contains(key))
												.toList();
										Statues.LOGGER.info("Keys in tag: {}", data.getAllKeys());
										keysToRemove.forEach(data::remove);

										entityTagCopy.merge(data);
										playerStatue.load(entityTagCopy);
										playerStatue.setUUID(playerStatue.getUUID());

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
										double xOffset = tagList.getDouble(0);
										double yOffset = tagList.getDouble(1);
										double zOffset = tagList.getDouble(2);
										if (xOffset != 0 || yOffset != 0 || zOffset != 0)
											playerStatue.setPosRaw(playerStatue.getX() + xOffset,
													playerStatue.getY() + yOffset,
													playerStatue.getZ() + zOffset);
									}
								}
							}
						}
					}
				})
				.exceptionally(e -> {
					// Handle exception
					context.disconnect(Component.translatable("statues.networking.player_statue_sync.failed", e.getMessage()));
					return null;
				});
	}

	public void handleTableData(final StatueTableData tableData, final IPayloadContext context) {
		// Do something with the data, on the main thread
		context.enqueueWork(() -> {
					//Execute craft if button is pressed
					if (context.player() != null) {
						Player player = context.player();
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
					context.disconnect(Component.translatable("statues.networking.table.failed", e.getMessage()));
					return null;
				});
	}
}
