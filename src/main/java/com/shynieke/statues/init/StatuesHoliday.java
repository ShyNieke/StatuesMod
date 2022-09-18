package com.shynieke.statues.init;

import com.shynieke.statues.Statues;
import com.shynieke.statues.config.StatuesConfigGen;
import com.shynieke.statues.entity.fakeentity.FakeBlaze;
import com.shynieke.statues.entity.fakeentity.FakeCreeper;
import com.shynieke.statues.entity.fakeentity.FakeEnderman;
import com.shynieke.statues.entity.fakeentity.FakeGhast;
import com.shynieke.statues.entity.fakeentity.FakeGuardian;
import com.shynieke.statues.entity.fakeentity.FakeHusk;
import com.shynieke.statues.entity.fakeentity.FakeMagmaCube;
import com.shynieke.statues.entity.fakeentity.FakeShulker;
import com.shynieke.statues.entity.fakeentity.FakeSkeleton;
import com.shynieke.statues.entity.fakeentity.FakeSlime;
import com.shynieke.statues.entity.fakeentity.FakeSpider;
import com.shynieke.statues.entity.fakeentity.FakeStray;
import com.shynieke.statues.entity.fakeentity.FakeWitch;
import com.shynieke.statues.entity.fakeentity.FakeWitherSkeleton;
import com.shynieke.statues.entity.fakeentity.FakeZombie;
import com.shynieke.statues.entity.fakeentity.FakeZombiePigman;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class StatuesHoliday {

	public static void registerSpawning() {
		LocalDateTime now = LocalDateTime.now();
		if (now.getMonth() == Month.OCTOBER) {
			for (Biome biome : Biome.REGISTRY) {
				for (Biome.SpawnListEntry entry : new ArrayList<>(biome.getSpawnableList(EnumCreatureType.MONSTER))) {
					registerSpawn(entry, biome, EntityBlaze.class, FakeBlaze.class);
					registerSpawn(entry, biome, EntityCreeper.class, FakeCreeper.class);
					registerSpawn(entry, biome, EntityEnderman.class, FakeEnderman.class);
					registerSpawn(entry, biome, EntityGhast.class, FakeGhast.class);
					registerSpawn(entry, biome, EntityGuardian.class, FakeGuardian.class);
					registerSpawn(entry, biome, EntityHusk.class, FakeHusk.class);
					registerSpawn(entry, biome, EntityMagmaCube.class, FakeMagmaCube.class);
					registerSpawn(entry, biome, EntityShulker.class, FakeShulker.class);
					registerSpawn(entry, biome, EntitySkeleton.class, FakeSkeleton.class);
					registerSpawn(entry, biome, EntitySlime.class, FakeSlime.class);
					registerSpawn(entry, biome, EntitySpider.class, FakeSpider.class);
					registerSpawn(entry, biome, EntityStray.class, FakeStray.class);
					registerSpawn(entry, biome, EntityWitch.class, FakeWitch.class);
					registerSpawn(entry, biome, EntityWitherSkeleton.class, FakeWitherSkeleton.class);
					registerSpawn(entry, biome, EntityZombie.class, FakeZombie.class);
					registerSpawn(entry, biome, EntityPigZombie.class, FakeZombiePigman.class);

				}
			}
			Statues.logger.debug("Registered Holiday Mobs");
		}
	}

	public static void registerSpawn(Biome.SpawnListEntry entry, Biome biome, Class<? extends Entity> oldEntity, Class<? extends EntityLiving> newEntity) {
		if (entry.entityClass == oldEntity) {
			EntityRegistry.addSpawn(newEntity, entry.itemWeight / StatuesConfigGen.events.fakeSpawningWeigth, entry.minGroupCount, entry.maxGroupCount, EnumCreatureType.MONSTER, biome);
		}
	}
}
