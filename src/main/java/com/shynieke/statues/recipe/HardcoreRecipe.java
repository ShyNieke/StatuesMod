package com.shynieke.statues.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HardcoreRecipe extends ShapedRecipe {
	static int MAX_WIDTH = 3;
	static int MAX_HEIGHT = 3;


	public HardcoreRecipe(ResourceLocation location, String group, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
		super(location, group, width, height, recipeItems, result);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return StatuesRecipes.HARDCORE_SHAPED_SERIALIZER.get();
	}

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		return super.matches(container, level) && level != null && level.getLevelData().isHardcore();
	}

	@Override
	public ItemStack getResultItem() {
		ItemStack resultStack = super.getResultItem();
		CompoundTag display = resultStack.getOrCreateTagElement("display");
		if(!display.contains("lore")) {
			ListTag nbtTagList = new ListTag();
			nbtTagList.add(StringTag.valueOf(Component.Serializer.toJson(
					new TextComponent("Only craftable in Hardcore Mode").withStyle(ChatFormatting.DARK_PURPLE))));
			display.put("Lore", nbtTagList);
		}

		return resultStack;
	}

	@Override
	public ItemStack assemble(CraftingContainer container) {
		return this.getResultItem().copy();
	}

	static NonNullList<Ingredient> dissolvePattern(String[] p_44203_, Map<String, Ingredient> p_44204_, int p_44205_, int p_44206_) {
		NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_44205_ * p_44206_, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(p_44204_.keySet());
		set.remove(" ");

		for(int i = 0; i < p_44203_.length; ++i) {
			for(int j = 0; j < p_44203_[i].length(); ++j) {
				String s = p_44203_[i].substring(j, j + 1);
				Ingredient ingredient = p_44204_.get(s);
				if (ingredient == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
				}

				set.remove(s);
				nonnulllist.set(j + p_44205_ * i, ingredient);
			}
		}

		if (!set.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		} else {
			return nonnulllist;
		}
	}

	@VisibleForTesting
	static String[] shrink(String... p_44187_) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for(int i1 = 0; i1 < p_44187_.length; ++i1) {
			String s = p_44187_[i1];
			i = Math.min(i, firstNonSpace(s));
			int j1 = lastNonSpace(s);
			j = Math.max(j, j1);
			if (j1 < 0) {
				if (k == i1) {
					++k;
				}

				++l;
			} else {
				l = 0;
			}
		}

		if (p_44187_.length == l) {
			return new String[0];
		} else {
			String[] astring = new String[p_44187_.length - l - k];

			for(int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = p_44187_[k1 + k].substring(i, j + 1);
			}

			return astring;
		}
	}

	private static int firstNonSpace(String p_44185_) {
		int i;
		for(i = 0; i < p_44185_.length() && p_44185_.charAt(i) == ' '; ++i) {
		}

		return i;
	}

	private static int lastNonSpace(String p_44201_) {
		int i;
		for(i = p_44201_.length() - 1; i >= 0 && p_44201_.charAt(i) == ' '; --i) {
		}

		return i;
	}

	static Map<String, Ingredient> keyFromJson(JsonObject jsonObject) {
		Map<String, Ingredient> map = Maps.newHashMap();

		for(Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			if (entry.getKey().length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}

			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
		}

		map.put(" ", Ingredient.EMPTY);
		return map;
	}

	static String[] patternFromJson(JsonArray array) {
		String[] astring = new String[array.size()];
		if (astring.length > MAX_HEIGHT) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for(int i = 0; i < astring.length; ++i) {
				String s = GsonHelper.convertToString(array.get(i), "pattern[" + i + "]");
				if (s.length() > MAX_WIDTH) {
					throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
				}

				if (i > 0 && astring[0].length() != s.length()) {
					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}

				astring[i] = s;
			}

			return astring;
		}
	}

	public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<HardcoreRecipe> {

		public HardcoreRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
			String s = GsonHelper.getAsString(jsonObject, "group", "");
			Map<String, Ingredient> map = HardcoreRecipe.keyFromJson(GsonHelper.getAsJsonObject(jsonObject, "key"));
			String[] pattern = shrink(HardcoreRecipe.patternFromJson(GsonHelper.getAsJsonArray(jsonObject, "pattern")));
			int i = pattern[0].length();
			int j = pattern.length;
			NonNullList<Ingredient> nonnulllist = HardcoreRecipe.dissolvePattern(pattern, map, i, j);
			ItemStack itemstack = HardcoreRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
			return new HardcoreRecipe(location, s, i, j, nonnulllist, itemstack);
		}

		public HardcoreRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf byteBuf) {
			int i = byteBuf.readVarInt();
			int j = byteBuf.readVarInt();
			String s = byteBuf.readUtf();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

			for(int k = 0; k < nonnulllist.size(); ++k) {
				nonnulllist.set(k, Ingredient.fromNetwork(byteBuf));
			}

			ItemStack itemstack = byteBuf.readItem();
			return new HardcoreRecipe(location, s, i, j, nonnulllist, itemstack);
		}

		public void toNetwork(FriendlyByteBuf byteBuf, HardcoreRecipe recipe) {
			byteBuf.writeVarInt(recipe.getWidth());
			byteBuf.writeVarInt(recipe.getHeight());
			byteBuf.writeUtf(recipe.getGroup());

			for(Ingredient ingredient : recipe.getIngredients()) {
				ingredient.toNetwork(byteBuf);
			}

			byteBuf.writeItem(recipe.getResultItem());
		}
	}
}
