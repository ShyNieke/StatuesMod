package com.shynieke.statues.compat.jei;

import com.shynieke.statues.Reference;
import com.shynieke.statues.compat.jei.category.LootCategory;
import com.shynieke.statues.compat.jei.category.UpgradeCategory;
import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.registry.StatueRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Objects;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

	public static final ResourceLocation PLUGIN_UID = new ResourceLocation(Reference.MOD_ID, "main");

	public static final ResourceLocation LOOT_BACKGROUND = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/loot.png");
	public static final RecipeType<LootRecipe> LOOT_TYPE = RecipeType.create(Reference.MOD_ID, "loot", LootRecipe.class);

	public static final ResourceLocation UPGRADE_BACKGROUND = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/upgrade.png");
	public static final RecipeType<UpgradeRecipe> UPGRADE_TYPE = RecipeType.create(Reference.MOD_ID, "upgrade", UpgradeRecipe.class);

	@Nullable
	private IRecipeCategory<LootRecipe> lootCategory;

	@Nullable
	private IRecipeCategory<UpgradeRecipe> upgradeCategory;

	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_UID;
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(StatueRegistry.STATUE_TABLE.get()), LOOT_TYPE);
		registration.addRecipeCatalyst(new ItemStack(StatueRegistry.STATUE_TABLE.get()), UPGRADE_TYPE);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registration.addRecipeCategories(
				lootCategory = new LootCategory(guiHelper),
				upgradeCategory = new UpgradeCategory(guiHelper)
		);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		ClientLevel clientLevel = Objects.requireNonNull(Minecraft.getInstance().level);
		registration.addRecipes(LOOT_TYPE, clientLevel.getRecipeManager().getAllRecipesFor(StatuesRecipes.LOOT_RECIPE.get()));
		registration.addRecipes(UPGRADE_TYPE, clientLevel.getRecipeManager().getAllRecipesFor(StatuesRecipes.UPGRADE_RECIPE.get()));
	}
}
