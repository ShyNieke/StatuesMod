package com.shynieke.statues.datagen.server.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.recipe.UpgradeType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class UpgradeRecipeBuilder {
	private final Ingredient center;
	private final List<Ingredient> catalysts;
	private ItemStack result = ItemStack.EMPTY;
	private boolean requireCore = false;
	private UpgradeType upgradeType = UpgradeType.CRAFTING;
	private int tier = -1;
	private String group;

	private UpgradeRecipeBuilder(Ingredient center, List<Ingredient> catalysts) {
		this.center = center;
		this.catalysts = catalysts;
	}

	public static UpgradeRecipeBuilder upgrade(Ingredient statueIngredient, List<Ingredient> catalysts) {
		return new UpgradeRecipeBuilder(statueIngredient, catalysts);
	}

	public UpgradeRecipeBuilder result(ItemLike resultIn) {
		this.result = new ItemStack(resultIn.asItem());
		return this;
	}

	public UpgradeRecipeBuilder result(ItemStack resultIn) {
		this.result = resultIn;
		return this;
	}

	public UpgradeRecipeBuilder upgradeType(UpgradeType upgradeType) {
		this.upgradeType = upgradeType;
		return this;
	}

	public UpgradeRecipeBuilder tier(int tier) {
		this.tier = tier;
		return this;
	}

	public UpgradeRecipeBuilder requiresCore() {
		this.requireCore = true;
		return this;
	}

	public UpgradeRecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new UpgradeRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.center,
				catalysts, result, requireCore, upgradeType, tier));
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final String group;
		private final Ingredient center;
		private final List<Ingredient> catalysts;
		private final ItemStack result;
		private final boolean requireCore;
		private final UpgradeType upgradeType;
		private final int tier;

		public Result(ResourceLocation idIn, String groupIn, Ingredient center, List<Ingredient> catalysts,
					  ItemStack result, boolean requireCore, UpgradeType upgradeType, int tier) {
			this.id = idIn;
			this.group = groupIn;
			this.center = center;
			this.catalysts = catalysts;
			this.result = result;
			this.requireCore = requireCore;
			this.upgradeType = upgradeType;
			this.tier = tier;
		}

		public void serializeRecipeData(JsonObject json) {
			if (!this.group.isEmpty())
				json.addProperty("group", this.group);

			json.add("center", this.center.toJson());

			JsonArray jsonarray = new JsonArray();

			for (Ingredient ingredient : this.catalysts)
				jsonarray.add(ingredient.toJson());

			json.add("catalysts", jsonarray);

			addStack(json, "result", this.result);

			if (requireCore)
				json.addProperty("requireCore", true);

			json.addProperty("upgradeType", upgradeType.name().toLowerCase(Locale.ROOT));

			if (tier != -1)
				json.addProperty("tier", tier);
		}

		private void addStack(JsonObject json, String property, ItemStack stack) {
			if (stack != null && !stack.isEmpty()) {
				JsonObject object = new JsonObject();

				object.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
				if (stack.getCount() != 1) {
					object.addProperty("count", stack.getCount());
				}
				if (stack.hasTag()) {
					object.addProperty("tag", stack.getTag().toString());
				}
				json.add(property, object);
			}
		}

		/**
		 * Gets the ID for the recipe.
		 */
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return StatuesRecipes.UPGRADE_SERIALIZER.get();
		}

		/**
		 * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
		 */
		@Nullable
		public JsonObject serializeAdvancement() {
			return null;
		}

		/**
		 * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementId()}
		 * is non-null.
		 */
		@Nullable
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}
