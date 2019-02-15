package com.svennieke.statues.init;

public class StatuesHoliday {

	/*
	public static void registerSpawning() 
	{
		LocalDateTime now = LocalDateTime.now();
		if(now.getMonth() == Month.OCTOBER)
		{
			for (Biome biome : Biome.REGISTRY) {
				for (Biome.SpawnListEntry entry : new ArrayList<>(biome.getSpawns(EnumCreatureType.MONSTER))) {
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
	public static void registerSpawn(Biome.SpawnListEntry entry, Biome biome, Class<? extends Entity> oldEntity, Class<? extends EntityLiving> newEntity)
	{
		if(entry.entityClass == oldEntity)
		{
			EntityRegistry.addSpawn(newEntity, entry.itemWeight / StatuesConfigGen.events.fakeSpawningWeigth, entry.minGroupCount, entry.maxGroupCount, EnumCreatureType.MONSTER, biome);
		}
	}*/
}
