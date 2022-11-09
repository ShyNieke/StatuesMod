package com.shynieke.statues.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.shynieke.statues.Reference;
import com.shynieke.statues.menu.StatueTableMenu;
import com.shynieke.statues.network.StatuesNetworking;
import com.shynieke.statues.network.message.StatueTableMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class StatueTableScreen extends AbstractContainerScreen<StatueTableMenu> {

	private Inventory inventory;
	private final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/statue_table.png");
	private Button buttonChisel;

	public StatueTableScreen(StatueTableMenu screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		this.inventory = inv;
		this.imageHeight = 159;
	}

	@Override
	protected void init() {
		super.init();

		buttonChisel = this.addRenderableWidget(new Button(leftPos + 130, topPos + 46, 38, 20, Component.literal("Chisel"), (button) -> {
			boolean flag = getMenu().validRecipe[0] == 1;
			if (flag) {
				StatuesNetworking.CHANNEL.send(PacketDistributor.SERVER.noArg(), new StatueTableMessage(true));
			}
		}) {
			@Override
			public void renderButton(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
//				boolean flag = getMenu().validRecipe[0] == 1;
//				if (flag) {
				super.renderButton(ms, mouseX, mouseY, partialTicks);
//				}
			}
		});
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

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
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
			renderComponentTooltip(poseStack, text, actualMouseX, actualMouseY);
		}
	}
}