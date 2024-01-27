package com.shynieke.statues.compat.rei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.shynieke.statues.compat.rei.REIPlugin;
import com.shynieke.statues.compat.rei.display.LootDisplay;
import com.shynieke.statues.registry.StatueRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class LootCategory implements DisplayCategory<LootDisplay> {
	@Override
	public CategoryIdentifier<? extends LootDisplay> getCategoryIdentifier() {
		return REIPlugin.LOOT;
	}

	@Override
	public Component getTitle() {
		return Component.translatable("statues.gui.jei.category.loot");
	}

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(StatueRegistry.STATUE_TABLE.get());
	}

	@Override
	public List<Widget> setupDisplay(LootDisplay display, Rectangle bounds) {
		Point centerPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.createRecipeBase(bounds));
		widgets.add(Widgets.createArrow(new Point(centerPoint.x - 20, centerPoint.y - 7)));

		widgets.add(Widgets.createSlot(new Point(bounds.getMinX() + 8, centerPoint.y - 7)).entries(display.getInputEntries().get(0)).markInput());
		if (display.getOutputEntries().size() != 3) {
			throw new IndexOutOfBoundsException("A Loot Recipe must have 3 outputs!, found " + display.getOutputEntries().size() + " outputs!. Please report this to the mod author!");
		} else {
			widgets.add(Widgets.createSlot(new Point(centerPoint.x + 8, bounds.getMinY() + 5)).entries(display.getOutputEntries().get(0)).markOutput());
			widgets.add(Widgets.createSlot(new Point(centerPoint.x + 8, bounds.getMinY() + 24)).entries(display.getOutputEntries().get(1)).markOutput());
			widgets.add(Widgets.createSlot(new Point(centerPoint.x + 8, bounds.getMinY() + 43)).entries(display.getOutputEntries().get(2)).markOutput());
		}
		widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) -> {
			// Draw entity name
			PoseStack poseStack = guiGraphics.pose();
			poseStack.pushPose();
			Font font = Minecraft.getInstance().font;
			guiGraphics.drawString(font, Component.literal((int) (100 * display.getChance1()) + "%"), 74, 8, 0, false);
			guiGraphics.drawString(font, Component.literal((int) (100 * display.getChance2()) + "%"), 74, 27, 0, false);
			guiGraphics.drawString(font, Component.literal((int) (100 * display.getChance3()) + "%"), 74, 45, 0, false);

			poseStack.popPose();
		}), bounds.x + 3, bounds.y + 3, 0));

		return widgets;
	}

	@Override
	public int getDisplayWidth(LootDisplay display) {
		return 100;
	}
}
