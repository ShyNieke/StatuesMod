package com.shynieke.statues.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

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
	public IRecipeSerializer<?> getSerializer() {
		return StatuesRecipes.HARDCORE_SHAPED_SERIALIZER.get();
	}

	@Override
	public boolean matches(CraftingInventory inventory, World world) {
		return super.matches(inventory, world) && world.getLevelData().isHardcore();
	}

	@Override
	public ItemStack getResultItem() {
		ItemStack resultStack = super.getResultItem();
		CompoundNBT display = resultStack.getOrCreateTagElement("display");
		if(!display.contains("lore")) {
			ListNBT nbtTagList = new ListNBT();
			nbtTagList.add(StringNBT.valueOf(TextComponent.Serializer.toJson(
					new StringTextComponent("Only craftable in Hardcore Mode").withStyle(TextFormatting.DARK_PURPLE))));
			display.put("Lore", nbtTagList);
		}

		return resultStack;
	}

	@Override
	public ItemStack assemble(CraftingInventory container) {
		return this.getResultItem().copy();
	}
	private static NonNullList<Ingredient> dissolvePattern(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
		NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_192402_2_ * p_192402_3_, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
		set.remove(" ");

		for(int i = 0; i < p_192402_0_.length; ++i) {
			for(int j = 0; j < p_192402_0_[i].length(); ++j) {
				String s = p_192402_0_[i].substring(j, j + 1);
				Ingredient ingredient = p_192402_1_.get(s);
				if (ingredient == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
				}

				set.remove(s);
				nonnulllist.set(j + p_192402_2_ * i, ingredient);
			}
		}

		if (!set.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		} else {
			return nonnulllist;
		}
	}

	private static Map<String, Ingredient> keyFromJson(JsonObject jsonObject) {
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

	@VisibleForTesting
	static String[] shrink(String... p_194134_0_) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for(int i1 = 0; i1 < p_194134_0_.length; ++i1) {
			String s = p_194134_0_[i1];
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

		if (p_194134_0_.length == l) {
			return new String[0];
		} else {
			String[] astring = new String[p_194134_0_.length - l - k];

			for(int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = p_194134_0_[k1 + k].substring(i, j + 1);
			}

			return astring;
		}
	}

	private static int firstNonSpace(String p_194135_0_) {
		int i;
		for(i = 0; i < p_194135_0_.length() && p_194135_0_.charAt(i) == ' '; ++i) {
		}

		return i;
	}

	private static int lastNonSpace(String p_194136_0_) {
		int i;
		for(i = p_194136_0_.length() - 1; i >= 0 && p_194136_0_.charAt(i) == ' '; --i) {
		}

		return i;
	}

	private static String[] patternFromJson(JsonArray p_192407_0_) {
		String[] astring = new String[p_192407_0_.size()];
		if (astring.length > MAX_HEIGHT) {
			throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
		} else if (astring.length == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		} else {
			for(int i = 0; i < astring.length; ++i) {
				String s = JSONUtils.convertToString(p_192407_0_.get(i), "pattern[" + i + "]");
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

	public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<HardcoreRecipe> {

		public HardcoreRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
			String s = JSONUtils.getAsString(jsonObject, "group", "");
			Map<String, Ingredient> map = HardcoreRecipe.keyFromJson(JSONUtils.getAsJsonObject(jsonObject, "key"));
			String[] pattern = shrink(HardcoreRecipe.patternFromJson(JSONUtils.getAsJsonArray(jsonObject, "pattern")));
			int i = pattern[0].length();
			int j = pattern.length;
			NonNullList<Ingredient> nonnulllist = HardcoreRecipe.dissolvePattern(pattern, map, i, j);
			ItemStack itemstack = HardcoreRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"));
			return new HardcoreRecipe(location, s, i, j, nonnulllist, itemstack);
		}

		public HardcoreRecipe fromNetwork(ResourceLocation location, PacketBuffer byteBuf) {
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

		public void toNetwork(PacketBuffer byteBuf, HardcoreRecipe recipe) {
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
