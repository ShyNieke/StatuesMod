package com.shynieke.statues.client.screen;

import com.shynieke.statues.Reference;
import com.shynieke.statues.menu.ShulkerStatueMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class ShulkerStatueScreen extends AbstractContainerScreen<ShulkerStatueMenu> {
	private final Identifier TEXTURE = Reference.modLoc("textures/gui/container/shulker_statue.png");


	public ShulkerStatueScreen(ShulkerStatueMenu screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn, 176, 166);
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
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
		this.extractTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
	}
}