//package com.shynieke.statues.compat.rei.category;
//
//import com.shynieke.statues.Reference;
//import com.shynieke.statues.compat.rei.StatuesREIPlugin;
//import com.shynieke.statues.compat.rei.display.UpgradeDisplay;
//import com.shynieke.statues.registry.StatueRegistry;
//import me.shedaniel.math.Point;
//import me.shedaniel.math.Rectangle;
//import me.shedaniel.rei.api.client.gui.Renderer;
//import me.shedaniel.rei.api.client.gui.widgets.Widget;
//import me.shedaniel.rei.api.client.gui.widgets.Widgets;
//import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.entry.EntryIngredient;
//import me.shedaniel.rei.api.common.util.EntryStacks;
//import net.minecraft.ChatFormatting;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Font;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.MutableComponent;
//import net.minecraft.resources.Identifier;
//import org.joml.Matrix3x2fStack;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UpgradeCategory implements DisplayCategory<UpgradeDisplay> {
//	private static final Identifier TEXTURE = Reference.modLoc("textures/gui/jei/upgrade.png");
//
//	@Override
//	public CategoryIdentifier<? extends UpgradeDisplay> getCategoryIdentifier() {
//		return StatuesREIPlugin.UPGRADE;
//	}
//
//	@Override
//	public Component getTitle() {
//		return Component.translatable("statues.gui.jei.category.upgrade");
//	}
//
//	@Override
//	public Renderer getIcon() {
//		return EntryStacks.of(StatueRegistry.STATUE_TABLE.get());
//	}
//
//	@Override
//	public List<Widget> setupDisplay(UpgradeDisplay display, Rectangle bounds) {
//		Point centerPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
//		List<Widget> widgets = new ArrayList<>();
//		widgets.add(Widgets.createRecipeBase(bounds));
//
//		if (display.isCoreRequired())
//			widgets.add(Widgets.createSlot(new Point(bounds.getMinX() + 9, bounds.getMaxY() - 24)).entries(display.getCore()).markInput());
//		else
//			widgets.add(Widgets.createTexturedWidget(TEXTURE, bounds.getMinX() + 8, bounds.getMaxY() - 25, 0, 40, 18, 18));
//
//		widgets.add(Widgets.createSlot(new Point(centerPoint.x, centerPoint.y - 7)).entries(display.getCenter()).markInput());
//
//		widgets.add(Widgets.createSlotBackground(new Point(centerPoint.x - 18, centerPoint.y - 25)));
//		widgets.add(Widgets.createSlotBackground(new Point(centerPoint.x + 18, centerPoint.y - 25)));
//		widgets.add(Widgets.createSlotBackground(new Point(centerPoint.x - 18, centerPoint.y + 11)));
//		widgets.add(Widgets.createSlotBackground(new Point(centerPoint.x + 18, centerPoint.y + 11)));
//
//
//		List<EntryIngredient> catalysts = display.getCatalysts();
//		if (!catalysts.isEmpty()) {
//			widgets.add(Widgets.createSlot(new Point(centerPoint.x - 18, centerPoint.y - 25)).entries(catalysts.get(0)).disableBackground().markInput());
//			if (catalysts.size() > 1) {
//				widgets.add(Widgets.createSlot(new Point(centerPoint.x + 18, centerPoint.y - 25)).entries(catalysts.get(1)).disableBackground().markInput());
//				if (catalysts.size() > 2) {
//					widgets.add(Widgets.createSlot(new Point(centerPoint.x - 18, centerPoint.y + 11)).entries(catalysts.get(2)).disableBackground().markInput());
//					if (catalysts.size() > 3) {
//						widgets.add(Widgets.createSlot(new Point(centerPoint.x + 18, centerPoint.y + 11)).entries(catalysts.get(3)).disableBackground().markInput());
//					}
//				}
//			}
//		}
//
//		widgets.add(Widgets.createSlot(new Point(centerPoint.x + 46, centerPoint.y - 7)).entries(display.getResult()).markOutput());
//
//		widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) -> {
//			// Draw entity name
//			Matrix3x2fStack poseStack = guiGraphics.pose();
//			poseStack.pushMatrix();
//			Font font = Minecraft.getInstance().font;
//			guiGraphics.drawString(font, getUpgradeName(display.getUpgradeType().getSerializedName(), display.getTier() + 1).withStyle(ChatFormatting.DARK_GRAY), 3, 25, 0, false);
//
//			poseStack.popMatrix();
//		}), bounds.x + 3, bounds.y + 3));
//
//		return widgets;
//	}
//
//	private MutableComponent getUpgradeName(String id, int level) {
//		String descriptionID = "statues.upgrade." + id + ".name";
//		MutableComponent mutablecomponent = Component.translatable(descriptionID).withStyle(ChatFormatting.GRAY);
//
//		if (level > 0) {
//			mutablecomponent.append(" ").append(Component.translatable("enchantment.level." + level));
//		}
//
//		return mutablecomponent;
//	}
//
//	@Override
//	public int getDisplayWidth(UpgradeDisplay display) {
//		return 148;
//	}
//}
