package com.shynieke.statues.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatueSavedData extends SavedData {
	public static final StatueSavedData blank = new StatueSavedData();

	private static final Identifier DATA_NAME = Reference.modLoc("statue_data");
	public static final Codec<StatueSavedData> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							Codec.unboundedMap(ResourceKey.codec(Registries.DIMENSION), Codec.list(BlockPos.CODEC))
									.fieldOf("DespawnerMap").forGetter(data -> data.despawnerMap)
					)
					.apply(instance, StatueSavedData::new)
	);
	private final Map<ResourceKey<Level>, List<BlockPos>> despawnerMap = new HashMap<>();

	public BlockPos getNearestDespawner(ResourceKey<Level> dimension, BlockPos pos, int range) {
		if (despawnerMap.containsKey(dimension)) {
			List<BlockPos> posList = despawnerMap.get(dimension).stream().filter(storedPos -> storedPos.distManhattan(pos) <= range).toList();
			return !posList.isEmpty() ? posList.getFirst() : null;
		}
		return null;
	}

	public void addPosition(ResourceKey<Level> dimension, BlockPos pos) {
		List<BlockPos> posList = despawnerMap.getOrDefault(dimension, new ArrayList<>());
		posList.add(pos);
		despawnerMap.put(dimension, posList);
		setDirty();
	}

	public void removePosition(ResourceKey<Level> dimension, BlockPos pos) {
		List<BlockPos> posList = despawnerMap.getOrDefault(dimension, new ArrayList<>());
		posList.remove(pos);
		despawnerMap.put(dimension, posList);
		setDirty();
	}

	public StatueSavedData() {
	}

	public StatueSavedData(Map<ResourceKey<Level>, List<BlockPos>> map) {
		this.despawnerMap.clear();
		this.despawnerMap.putAll(map);
	}

	public static SavedDataType<StatueSavedData> type() {
		return new SavedDataType<>(DATA_NAME, StatueSavedData::new, CODEC);
	}

	private static ResourceKey<Level> getLevelKey(String location) {
		return ResourceKey.create(Registries.DIMENSION, Identifier.parse(location));
	}

	public static StatueSavedData get() {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)

			return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage()
					.computeIfAbsent(type());
		else
			return blank;
	}
}