package com.shynieke.statues.proxy;

import com.shynieke.statues.Reference;
import com.shynieke.statues.entity.EntityStatueBat;
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
import com.shynieke.statues.entity.fakeentity.fakeprojectiles.FakeFireball;
import com.shynieke.statues.entity.fakeentity.fakeprojectiles.FakeLargeFireball;
import com.shynieke.statues.entity.fakeentity.fakeprojectiles.FakeShulkerBullet;
import com.shynieke.statues.init.StatuesBlocks;
import com.shynieke.statues.init.StatuesItems;
import com.shynieke.statues.renderer.PlayerInventoryRender;
import com.shynieke.statues.renderer.PlayerStatueRenderer;
import com.shynieke.statues.renderer.StatueBatRenderer;
import com.shynieke.statues.renderer.StatuesState;
import com.shynieke.statues.tileentity.PlayerStatueTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderHusk;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderPigZombie;
import net.minecraft.client.renderer.entity.RenderShulker;
import net.minecraft.client.renderer.entity.RenderShulkerBullet;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderStray;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.client.renderer.entity.RenderWitherSkeleton;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends ServerProxy {

	@Override
	public void Preinit() {
		registerRender();

		ClientRegistry.bindTileEntitySpecialRenderer(PlayerStatueTileEntity.class, new PlayerStatueRenderer());
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
		RenderingRegistry.registerEntityRenderingHandler(FakeSpider.class, renderManager -> new RenderSpider<>(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeStray.class, renderManager -> new RenderStray(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeZombiePigman.class, renderManager -> new RenderPigZombie(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(FakeWitherSkeleton.class, renderManager -> new RenderWitherSkeleton(renderManager));
	}

	@Override
	public void Init() {

	}

	@Override
	public void PostInit() {
		for (Item item : StatuesItems.ITEMS) {
			if (item == Item.getItemFromBlock(StatuesBlocks.player_statue)) {
				item.setTileEntityItemStackRenderer(new PlayerInventoryRender());
			}
		}
	}

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		for (Item item : StatuesItems.ITEMS) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ResourceTiersAreCool(item.getRegistryName().getPath()), "inventory"));
		}

		for (Block block : StatuesBlocks.BLOCKS) {
			Item item = Item.getItemFromBlock(block);
			if (isTiered(block.getRegistryName().getPath())) {
				ModelLoader.setCustomStateMapper(block, new StatuesState(ResourceTiersAreCool(block.getRegistryName().getPath())));
			} else {
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(ResourceTiersAreCool(block.getRegistryName().getPath()), "inventory"));
			}
		}
	}

	public static ResourceLocation ResourceTiersAreCool(String statue) {
		return new ResourceLocation(Reference.MOD_ID, statue.replace("t2", "").replace("t3", "").replace("t4", "").replace("t5", ""));
	}

	public static boolean isTiered(String statue) {
		return statue.contains("t2") || statue.contains("t3") || statue.contains("t4") || statue.contains("t5");
	}
}
