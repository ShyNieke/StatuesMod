package com.svennieke.statues.init;

import java.time.LocalDateTime;
import java.time.Month;

import com.svennieke.statues.Statues;
import com.svennieke.statues.entity.fakeentity.FakeCreeper;
import com.svennieke.statues.entity.fakeentity.FakeEnderman;
import com.svennieke.statues.entity.fakeentity.FakeSlime;
import com.svennieke.statues.entity.fakeentity.FakeWitch;
import com.svennieke.statues.entity.fakeentity.FakeZombie;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;

public class StatuesHoliday {
	
	public static void registerSpawning() 
	{
		LocalDateTime now = LocalDateTime.now();
		if(now.getMonth() == Month.OCTOBER || now.getMonth() == Month.NOVEMBER)
		{
			for (Biome biome : Biome.REGISTRY) {
			    biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(FakeZombie.class, 5, 4, 4));
			    biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(FakeCreeper.class, 5, 4, 4));
			    biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(FakeSlime.class, 5, 4, 4));
			    biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(FakeEnderman.class, 5, 1, 4));
			    biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(FakeWitch.class, 5, 1, 1));
			}
			Statues.logger.debug("Registered Holiday Mobs");
		}
	}
}
