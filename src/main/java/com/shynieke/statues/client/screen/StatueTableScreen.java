package com.shynieke.statues.client.screen;

import com.shynieke.statues.Reference;
import com.shynieke.statues.menu.StatueTableMenu;
import com.shynieke.statues.network.StatuesNetworking;
import com.shynieke.statues.network.message.StatueTableMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class StatueTableScreen extends AbstractContainerScreen<StatueTableMenu> {

	private final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/statue_table.png");
	private Button buttonChisel;

	public StatueTableScreen(StatueTableMenu screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		this.imageHeight = 159;
	}

	@Override
	protected void init() {
		super.init();

		this.buttonChisel = this.addRenderableWidget(Button.builder(Component.literal("Chisel"), (button) -> {
			boolean flag = getMenu().validRecipe[0] == 1;
			if (flag) {
				StatuesNetworking.CHANNEL.send(PacketDistributor.SERVER.noArg(), new StatueTableMessage(true));
			}
		}).bounds(leftPos + 130, topPos + 46, 38, 20).build());
	}

	@Override
	protected void containerTick() {
		super.containerTick();

		boolean flag = getMenu().validRecipe[0] == 1;
		if (buttonChisel.active != flag) {
			buttonChisel.active = flag;
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);

		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
		guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		int actualMouseX = mouseX - ((this.width - this.imageWidth) / 2);
		int actualMouseY = mouseY - ((this.height - this.imageHeight) / 2);

		if (isHovering(39, 101, 12, 12, mouseX, mouseY)) {
			List<Component> text = new ArrayList<>();
			if (getMenu().validRecipe[0] == 1) {
				text.add(Component.translatable("gui.statues.statue_table.chisel.tooltip")
						.withStyle(ChatFormatting.GRAY));
			} else {
				text.add(Component.translatable("gui.statues.statue_table.invalid_recipe.tooltip")
						.withStyle(ChatFormatting.RED));
			}
			guiGraphics.renderComponentTooltip(font, text, actualMouseX, actualMouseY);
		}
	}
}