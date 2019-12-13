package com.shynieke.statues.compat.jei.statueloot;

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

public class StatueLootCategory implements IRecipeCategory {

	public static final String UID = "statues.Loot";
    private final IDrawableStatic background;
    private final String title;
    private final IDrawableStatic icon;
	
	public StatueLootCategory(IGuiHelper guiHelper) {
		title = Translator.translateToLocal("gui.statue.loot");
		 
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/loot.png");
		background = guiHelper.createDrawable(location, 0, 0, 100, 62);

		ResourceLocation iconLocation = new ResourceLocation(Reference.MOD_ID, "textures/gui/loot_icon.png");
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
		
		int input = 0;
		guiItemStacks.init(input, true, 0, 22);
        guiItemStacks.set(ingredients);
		
        int slot1 = 1;
        guiItemStacks.init(slot1, false, 54, 4);
        guiItemStacks.set(ingredients);

        int slot2 = 2;
        guiItemStacks.init(slot2, false, 54, 22);
        guiItemStacks.set(ingredients);	
        
        int slot3 = 3;
        guiItemStacks.init(slot3, false, 54, 40);
        guiItemStacks.set(ingredients);	
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}
}
