package com.shynieke.statues.storage;

import com.shynieke.statues.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatueSavedData extends SavedData {
	public static final StatueSavedData blank = new StatueSavedData();

	private static final String DATA_NAME = Reference.MOD_ID + "_world_data";

	private static final Map<ResourceKey<Level>, List<BlockPos>> despawnerMap = new HashMap<>();

	public boolean isDespawnerNearby(ResourceKey<Level> dimension, BlockPos pos, int range) {
		if (despawnerMap.containsKey(dimension)) {
			List<BlockPos> posList = despawnerMap.get(dimension).stream().filter(storedPos -> storedPos.distManhattan(pos) <= range).toList();
			return !posList.isEmpty();
		}
		return false;
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

	@Override
	public CompoundTag save(CompoundTag compound) {
		ListTag despawnerList = new ListTag();
		despawnerMap.forEach((dimension, posList) -> {
			CompoundTag tag = new CompoundTag();
			tag.putString("Dimension", dimension.location().toString());
			ListTag posListTag = new ListTag();
			for (BlockPos pos : posList) {
				CompoundTag tag1 = new CompoundTag();
				tag1.putLong("Pos", pos.asLong());
				posListTag.add(tag1);
			}
			tag.put("Positions", posListTag);
			despawnerList.add(tag);
		});
		compound.put("DespawnerMap", despawnerList);
		return compound;
	}

	public static StatueSavedData load(CompoundTag tag) {
		if (tag.contains("DespawnerMap")) {
			ListTag despawnerList = tag.getList("DespawnerMap", Tag.TAG_COMPOUND);
			despawnerList.forEach(t -> {
				CompoundTag despawnTag = (CompoundTag) t;
				String dimensionLocation = despawnTag.getString("Dimension");
				List<BlockPos> posList = new ArrayList<>();
				ListTag posListTag = despawnTag.getList("Positions", Tag.TAG_COMPOUND);
				posListTag.forEach(t2 -> {
					CompoundTag tag1 = (CompoundTag) t2;
					BlockPos pos = BlockPos.of(tag1.getLong("Pos"));
					posList.add(pos);
				});
				ResourceKey<Level> dimension = getLevelKey(dimensionLocation);
				despawnerMap.put(dimension, posList);
			});
		}
		return new StatueSavedData();
	}

	private static ResourceKey<Level> getLevelKey(String location) {
		return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(location));
	}

	public static StatueSavedData get() {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage()
					.computeIfAbsent(StatueSavedData::load, StatueSavedData::new, DATA_NAME);
		else
			return blank;
	}
}