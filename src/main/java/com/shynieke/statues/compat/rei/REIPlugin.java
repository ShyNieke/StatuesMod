package com.shynieke.statues.compat.rei;

import com.shynieke.statues.Reference;
import com.shynieke.statues.compat.rei.category.LootCategory;
import com.shynieke.statues.compat.rei.category.UpgradeCategory;
import com.shynieke.statues.compat.rei.display.LootDisplay;
import com.shynieke.statues.compat.rei.display.UpgradeDisplay;
import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeRecipe;
import com.shynieke.statues.recipe.UpgradeType;
import com.shynieke.statues.registry.StatueRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

@REIPluginClient
public class REIPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<LootDisplay> LOOT = CategoryIdentifier.of(Reference.MOD_ID, "plugins/loot");
	public static final CategoryIdentifier<UpgradeDisplay> UPGRADE = CategoryIdentifier.of(Reference.MOD_ID, "plugins/upgrade");

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new LootCategory());
		registry.add(new UpgradeCategory());

		registry.addWorkstations(LOOT, EntryStacks.of(StatueRegistry.STATUE_TABLE.get()));
		registry.addWorkstations(UPGRADE, EntryStacks.of(StatueRegistry.STATUE_TABLE.get()));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		if (level == null) {
			throw new NullPointerException("level must not be null.");
		}
		RegistryAccess registryAccess = level.registryAccess();

		List<RecipeHolder<LootRecipe>> lootHolders = registry.getRecipeManager().getAllRecipesFor(StatuesRecipes.LOOT_RECIPE.get());
		lootHolders.forEach((holder) -> {
			LootRecipe recipe = holder.value();
			registry.add(new LootDisplay(
					recipe.getIngredients().get(0),
					recipe.getResultItem(registryAccess),
					recipe.getChance1(),
					recipe.getResultItem2(registryAccess),
					recipe.getChance2(),
					recipe.getResultItem3(registryAccess),
					recipe.getChance3())
			);
		});


		List<RecipeHolder<UpgradeRecipe>> upgradeHolders = registry.getRecipeManager().getAllRecipesFor(StatuesRecipes.UPGRADE_RECIPE.get());
		upgradeHolders.forEach((holder) -> {
			UpgradeRecipe recipe = holder.value();
			int tier = recipe.getTier();
			List<ItemStack> centerList = new ArrayList<>();
			for (ItemStack centerStack : recipe.getCenter().getItems()) {
				ItemStack stack = centerStack.copy();
				if (recipe.getUpgradeType() != UpgradeType.UPGRADE)
					fillInTag(stack, recipe);

				if (recipe.getUpgradeType() != UpgradeType.UPGRADE) {
					setUpgradeSlots(stack, 1);
				}
				if (recipe.getUpgradeType() == UpgradeType.UNGLOWING) {
					UpgradeType.GLOWING.apply(stack, -1);
				}

				if (tier > -1) {
					for (int i = 0; i < tier; i++) {
						recipe.getUpgradeType().apply(stack, i);
					}
				}

				centerList.add(stack);
			}
			ItemStack result = recipe.getResultItem(registryAccess).copy();
			List<ItemStack> stacks = new ArrayList<>();
			if (result.isEmpty()) {
				for (ItemStack centerStack : centerList) {
					ItemStack stack = centerStack.copy();

					recipe.getUpgradeType().apply(stack, tier);

					if (recipe.getUpgradeType().isSubsequentUsesSlot())
						setUpgradeSlots(stack, 0);
					stacks.add(stack);
				}
			} else {
				stacks.add(result);
			}

			registry.add(new UpgradeDisplay(
					centerList,
					recipe.getCatalysts(),
					stacks,
					recipe.requiresCore(),
					recipe.getUpgradeType(),
					tier)
			);
		});
	}


	private void fillInTag(ItemStack stack, UpgradeRecipe recipe) {
		CompoundTag entityTag = new CompoundTag();
		int tier = recipe.getTier();
		entityTag.putInt(Reference.LEVEL, tier == -1 ? recipe.getUpgradeType() == UpgradeType.UPGRADE ? 0 : 1 : tier + 1);
		entityTag.putBoolean(Reference.UPGRADED, true);
		entityTag.putInt(Reference.UPGRADE_SLOTS, 20);
		stack.addTagElement("BlockEntityTag", entityTag);
	}

	private void setUpgradeSlots(ItemStack stack, int count) {
		CompoundTag blockData = stack.getTagElement("BlockEntityTag");
		if (blockData == null)
			blockData = new CompoundTag();
		blockData.putInt(Reference.UPGRADE_SLOTS, count);
		stack.addTagElement("BlockEntityTag", blockData);
	}
}
