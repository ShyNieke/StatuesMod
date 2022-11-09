package com.shynieke.statues.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.shynieke.statues.Reference;
import com.shynieke.statues.menu.ShulkerStatueMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShulkerStatueScreen extends AbstractContainerScreen<ShulkerStatueMenu> {
	private final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/shulker_statue.png");


	public ShulkerStatueScreen(ShulkerStatueMenu screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		this.imageHeight = 166;
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void containerTick() {
		super.containerTick();
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);

		super.render(poseStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(poseStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
		RenderSystem.setShaderTexture(0, TEXTURE);
		this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}
}