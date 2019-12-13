package com.shynieke.statues.compat.jei.filling;

import com.shynieke.statues.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;

public class StatueFillingCategory implements IRecipeCategory {

	public static final String UID = "statues.fill";
    private final IDrawableStatic background;
    private final String title;
    private final IDrawableStatic icon;
	
	public StatueFillingCategory(IGuiHelper guiHelper) {
		title = Translator.translateToLocal("gui.statue.fill");
		 
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/fill.png");
		background = guiHelper.createDrawable(location, 0, 0, 72, 62);

		ResourceLocation iconLocation = new ResourceLocation(Reference.MOD_ID, "textures/gui/fill_icon.png");
		icon = guiHelper.createDrawable(iconLocation, 0, 0, 16, 16);
	}
	
	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getModName() {
		return Reference.MOD_NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		int statueSlot = 0;
		guiItemStacks.init(statueSlot, true, 27, 0);
        guiItemStacks.set(ingredients);
		
		int inputSlot = 1;
        guiItemStacks.init(inputSlot, true, 0, 22);
        guiItemStacks.set(ingredients);

        int outputSlot = 2;
        guiItemStacks.init(outputSlot, false, 54, 22);
        guiItemStacks.set(ingredients);
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}
}
