package com.shynieke.statues.compat.jei.category;

import com.shynieke.statues.compat.jei.JEIPlugin;
import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.registry.StatueRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class LootCategory implements IRecipeCategory<LootRecipe> {
	private final IDrawable background;
	private final IDrawable icon;
	private final Component localizedName;

	public LootCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(JEIPlugin.LOOT_BACKGROUND, 0, 0, 100, 62);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(StatueRegistry.STATUE_TABLE.get()));
		this.localizedName = Component.translatable("statues.gui.jei.category.loot");
	}

	@Override
	public IRecipeType<LootRecipe> getRecipeType() {
		return JEIPlugin.LOOT_TYPE;
	}

	@Override
	public int getWidth() {
		return 100;
	}

	@Override
	public int getHeight() {
		return 62;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, LootRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 1, 23).add(recipe.getIngredients().getFirst());

		builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 5).add(recipe.getResultItem());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 23).add(recipe.getResultItem2());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 55, 41).add(recipe.getResultItem3());
	}

	@Override
	public void draw(LootRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics);

		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		guiGraphics.drawString(font, Component.literal((int) (100 * recipe.getChance1()) + "%"), 74, 8, 0, false);
		guiGraphics.drawString(font, Component.literal((int) (100 * recipe.getChance2()) + "%"), 74, 27, 0, false);
		guiGraphics.drawString(font, Component.literal((int) (100 * recipe.getChance3()) + "%"), 74, 45, 0, false);
	}

	@Override
	public Component getTitle() {
		return localizedName;
	}
}
