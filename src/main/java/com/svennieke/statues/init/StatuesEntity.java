package com.svennieke.statues.init;

import com.google.common.base.Preconditions;
import com.svennieke.statues.Reference;
import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.entity.fakeentity.*;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeFireball;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeLargeFireball;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeShulkerBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class StatuesEntity {
	
	public static final EntityType<EntityStatueBat> STATUE_BAT = register("statue_bat", EntityType.Builder.create(EntityStatueBat.class, EntityStatueBat::new));
	
	public static final EntityType<FakeZombie> FAKE_ZOMBIE = register("fake_zombie", EntityType.Builder.create(FakeZombie.class, FakeZombie::new));
	public static final EntityType<FakeHusk> FAKE_HUSK = register("fake_husk", EntityType.Builder.create(FakeHusk.class, FakeHusk::new));
	public static final EntityType<FakeSkeleton> FAKE_SKELETON = register("fake_skeleton", EntityType.Builder.create(FakeSkeleton.class, FakeSkeleton::new));
	public static final EntityType<FakeBlaze> FAKE_BLAZE = register("fake_blaze", EntityType.Builder.create(FakeBlaze.class, FakeBlaze::new));
	public static final EntityType<FakeFireball> FAKE_FIREBALL = register("fake_fireball", EntityType.Builder.create(FakeFireball.class, FakeFireball::new));
	public static final EntityType<FakeCreeper> FAKE_CREEPER = register("fake_creeper", EntityType.Builder.create(FakeCreeper.class, FakeCreeper::new));
	public static final EntityType<FakeEnderman> FAKE_ENDERMAN = register("fake_enderman", EntityType.Builder.create(FakeEnderman.class, FakeEnderman::new));
	public static final EntityType<FakeGhast> FAKE_GHAST = register("fake_ghast", EntityType.Builder.create(FakeGhast.class, FakeGhast::new));
	public static final EntityType<FakeLargeFireball> FAKE_LARGE_FIREBALL = register("fake_large_fireball", EntityType.Builder.create(FakeLargeFireball.class, FakeLargeFireball::new));
	public static final EntityType<FakeGuardian> FAKE_GUARDIAN = register("fake_guardian", EntityType.Builder.create(FakeGuardian.class, FakeGuardian::new));
	public static final EntityType<FakeSlime> FAKE_SLIME = register("fake_slime", EntityType.Builder.create(FakeSlime.class, FakeSlime::new));
	public static final EntityType<FakeMagmaCube> FAKE_MAGMA_CUBE = register("fake_magma_cube", EntityType.Builder.create(FakeMagmaCube.class, FakeMagmaCube::new));
	public static final EntityType<FakeShulker> FAKE_SHULKER = register("fake_shulker", EntityType.Builder.create(FakeShulker.class, FakeShulker::new));
	public static final EntityType<FakeShulkerBullet> FAKE_SHULKER_BULLET = register("fake_shulker_bullet", EntityType.Builder.create(FakeShulkerBullet.class, FakeShulkerBullet::new));
	public static final EntityType<FakeWitch> FAKE_WITCH = register("fake_witch", EntityType.Builder.create(FakeWitch.class, FakeWitch::new));
	public static final EntityType<FakeSpider> FAKE_SPIDER = register("fake_spider", EntityType.Builder.create(FakeSpider.class, FakeSpider::new));
	public static final EntityType<FakeStray> FAKE_STRAY = register("fake_stray", EntityType.Builder.create(FakeStray.class, FakeStray::new));
	public static final EntityType<FakeZombiePigman> FAKE_ZOMBIE_PIGMAN = register("fake_zombie_pigman", EntityType.Builder.create(FakeZombiePigman.class, FakeZombiePigman::new));
	public static final EntityType<FakeWitherSkeleton> FAKE_WITHER_SKELETON = register("fake_wither_skeleton", EntityType.Builder.create(FakeWitherSkeleton.class, FakeWitherSkeleton::new));

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        EntityType<T> entitytype = builder.build("");
        ResourceLocation name = new ResourceLocation(Reference.MOD_ID, id);

        entitytype.setRegistryName(name);

        return entitytype;
	}
	
	
	public static void register(EntityType<?> entity, String name, RegistryEvent.Register<EntityType<?>> event) {
        Preconditions.checkNotNull(entity, "registryName");
        event.getRegistry().register(entity);
	}
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
	{
		register(STATUE_BAT, "statue_bat", event);
		
		register(FAKE_ZOMBIE, "fake_zombie", event);
		register(FAKE_HUSK, "fake_husk", event);
		register(FAKE_SKELETON, "fake_skeleton", event);
		register(FAKE_BLAZE, "fake_blaze", event);
		register(FAKE_FIREBALL, "fake_fireball", event);
		register(FAKE_CREEPER, "fake_creeper", event);
		register(FAKE_ENDERMAN, "fake_enderman", event);
		register(FAKE_GHAST, "fake_ghast", event);
		register(FAKE_LARGE_FIREBALL, "fake_large_fireball", event);
		register(FAKE_GUARDIAN, "fake_guardian", event);
		register(FAKE_SLIME, "fake_slime", event);
		register(FAKE_MAGMA_CUBE, "fake_magma_cube", event);
		register(FAKE_SHULKER, "fake_shulker", event);
		register(FAKE_SHULKER_BULLET, "fake_shulker_bullet", event);
		register(FAKE_WITCH, "fake_witch", event);
		register(FAKE_SPIDER, "fake_spider", event);
		register(FAKE_STRAY, "fake_stray", event);
		register(FAKE_ZOMBIE_PIGMAN, "fake_zombie_pigman", event);
		register(FAKE_WITHER_SKELETON, "fake_wither_skeleton", event);
	}

}
