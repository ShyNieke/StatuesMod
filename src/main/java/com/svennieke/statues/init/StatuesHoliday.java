package com.svennieke.statues.init;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import com.svennieke.statues.Statues;
import com.svennieke.statues.entity.fakeentity.FakeBlaze;
import com.svennieke.statues.entity.fakeentity.FakeCreeper;
import com.svennieke.statues.entity.fakeentity.FakeEnderman;
import com.svennieke.statues.entity.fakeentity.FakeGuardian;
import com.svennieke.statues.entity.fakeentity.FakeMagmaCube;
import com.svennieke.statues.entity.fakeentity.FakeShulker;
import com.svennieke.statues.entity.fakeentity.FakeSlime;
import com.svennieke.statues.entity.fakeentity.FakeSpider;
import com.svennieke.statues.entity.fakeentity.FakeWitch;
import com.svennieke.statues.entity.fakeentity.FakeZombie;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class StatuesHoliday {
	
	public static void registerSpawning() 
	{
		LocalDateTime now = LocalDateTime.now();
		if(now.getMonth() == Month.OCTOBER)
		{
			for (Biome biome : Biome.REGISTRY) {
				for (Biome.SpawnListEntry entry : new ArrayList<>(biome.getSpawnableList(EnumCreatureType.MONSTER))) {
					registerSpawn(entry, biome, EntityCreeper.class, FakeCreeper.class);
					registerSpawn(entry, biome, EntityEnderman.class, FakeEnderman.class);
					registerSpawn(entry, biome, EntityZombie.class, FakeZombie.class);
					registerSpawn(entry, biome, EntityWitch.class, FakeWitch.class);
					registerSpawn(entry, biome, EntitySlime.class, FakeSlime.class);
					registerSpawn(entry, biome, EntitySpider.class, FakeSpider.class);
					registerSpawn(entry, biome, EntityMagmaCube.class, FakeMagmaCube.class);
					registerSpawn(entry, biome, EntityBlaze.class, FakeBlaze.class);
					registerSpawn(entry, biome, EntityGuardian.class, FakeGuardian.class);
					registerSpawn(entry, biome, EntityShulker.class, FakeShulker.class);
	            }
			}
			Statues.logger.debug("Registered Holiday Mobs");
		}
	}
	public static void registerSpawn(Biome.SpawnListEntry entry, Biome biome, Class<? extends Entity> oldEntity, Class<? extends EntityLiving> newEntity)
	{
		if(entry.entityClass == oldEntity)
		{
			EntityRegistry.addSpawn(newEntity, entry.itemWeight / 2, entry.minGroupCount / 2, entry.maxGroupCount / 2, EnumCreatureType.MONSTER, biome);
		}
	}
}
