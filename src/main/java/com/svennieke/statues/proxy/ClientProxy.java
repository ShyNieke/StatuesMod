package com.svennieke.statues.proxy;

import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.entity.fakeentity.FakeBlaze;
import com.svennieke.statues.entity.fakeentity.FakeCreeper;
import com.svennieke.statues.entity.fakeentity.FakeEnderman;
import com.svennieke.statues.entity.fakeentity.FakeGhast;
import com.svennieke.statues.entity.fakeentity.FakeGuardian;
import com.svennieke.statues.entity.fakeentity.FakeHusk;
import com.svennieke.statues.entity.fakeentity.FakeMagmaCube;
import com.svennieke.statues.entity.fakeentity.FakeShulker;
import com.svennieke.statues.entity.fakeentity.FakeSkeleton;
import com.svennieke.statues.entity.fakeentity.FakeSlime;
import com.svennieke.statues.entity.fakeentity.FakeWitch;
import com.svennieke.statues.entity.fakeentity.FakeZombie;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeFireball;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeLargeFireball;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeShulkerBullet;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesItems;
import com.svennieke.statues.renderer.PlayerStatueRenderer;
import com.svennieke.statues.renderer.StatueBatRenderer;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;

import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderHusk;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderShulker;
import net.minecraft.client.renderer.entity.RenderShulkerBullet;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy{

	@Override
	public void Preinit() {
		StatuesBlocks.registerRenders();
		StatuesItems.registerRenders();
		registerRender();
	}
	
	public static void registerRender() {
		RenderingRegistry.registerEntityRenderingHandler(EntityStatueBat.class, StatueBatRenderer.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(FakeZombie.class, renderManager -> new RenderZombie(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeHusk.class, renderManager -> new RenderHusk(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeSkeleton.class, renderManager -> new RenderSkeleton(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeBlaze.class, renderManager -> new RenderBlaze(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeFireball.class, renderManager -> new RenderFireball(renderManager, 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(FakeCreeper.class, renderManager -> new RenderCreeper(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeEnderman.class, renderManager -> new RenderEnderman(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeGhast.class, renderManager -> new RenderGhast(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeLargeFireball.class, renderManager -> new RenderFireball(renderManager, 2.0F));
		RenderingRegistry.registerEntityRenderingHandler(FakeGuardian.class, renderManager -> new RenderGuardian(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeSlime.class, renderManager -> new RenderSlime(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeMagmaCube.class, renderManager -> new RenderMagmaCube(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeShulker.class, renderManager -> new RenderShulker(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeShulkerBullet.class, renderManager -> new RenderShulkerBullet(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeWitch.class, renderManager -> new RenderWitch(renderManager));
	}
	@Override
	public void Init() {
		ClientRegistry.bindTileEntitySpecialRenderer(PlayerStatueTileEntity.class, new PlayerStatueRenderer());
	}
}
