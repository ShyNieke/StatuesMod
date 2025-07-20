package com.shynieke.statues.network.handler;

import com.shynieke.statues.Statues;
import com.shynieke.statues.entity.PlayerStatue;
import com.shynieke.statues.menu.StatueTableMenu;
import com.shynieke.statues.network.message.PlayerStatueSyncData;
import com.shynieke.statues.network.message.StatueTableData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

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
							final ServerLevel serverLevel = serverPlayer.level();
							Entity entity = serverLevel.getEntity(syncData.playerUUID());
							if (entity instanceof PlayerStatue playerStatue && serverPlayer != null) {
								if (!playerStatue.isLocked() || (playerStatue.getLockedBy().equals(serverPlayer.getUUID()))) {
									try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Statues.LOGGER)) {
										TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, playerStatue.registryAccess());
										// Save the armor stand's current state without an ID
										playerStatue.saveWithoutId(output);
										// Build the result compound tag from the output
										CompoundTag outputCompound = output.buildResult();

										if (!data.isEmpty()) {
											List<String> keysToRemove = data.keySet().stream()
													.filter(key -> !allowedKeys.contains(key))
													.toList();
											keysToRemove.forEach(data::remove);

											outputCompound.merge(data);
											playerStatue.load(TagValueInput.create(ProblemReporter.DISCARDING, playerStatue.registryAccess(), outputCompound));
											playerStatue.setUUID(playerStatue.getUUID());

											Optional<Float> YOffset = data.getFloat("yOffset");
											if (YOffset.isPresent())
												playerStatue.setYOffset(YOffset.get());
											Optional<String> modelType = data.getString("Model");
											if (modelType.isPresent())
												playerStatue.setModel(modelType.get());
											boolean lockFlag = data.getBooleanOr("Locked", false);
											if (lockFlag) {
												if (!playerStatue.isLocked()) {
													playerStatue.setLockedBy(serverPlayer.getUUID());
												}
											} else {
												if (playerStatue.isLocked()) {
													playerStatue.setUnlocked();
												}
											}
											Vec3 offset = data.read("Move", Vec3.CODEC).orElse(Vec3.ZERO);
											double xOffset = offset.x();
											double yOffset = offset.y();
											double zOffset = offset.z();
											if (xOffset != 0 || yOffset != 0 || zOffset != 0)
												playerStatue.setPosRaw(playerStatue.getX() + xOffset,
														playerStatue.getY() + yOffset,
														playerStatue.getZ() + zOffset);
										}
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
