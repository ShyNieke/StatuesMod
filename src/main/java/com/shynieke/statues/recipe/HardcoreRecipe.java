package com.shynieke.statues.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HardcoreRecipe extends ShapedRecipe {
	final ShapedRecipePattern pattern;
	final ItemStack result;
	final String group;
	final CraftingBookCategory category;
	final boolean showNotification;

	public HardcoreRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
		super(group, category, pattern, result);
		this.group = group;
		this.category = category;
		this.pattern = pattern;
		this.result = result;
		this.showNotification = showNotification;
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
	public ItemStack getResultItem(RegistryAccess access) {
		ItemStack resultStack = super.getResultItem(access);
		CompoundTag display = resultStack.getOrCreateTagElement("display");
		if (!display.contains("lore")) {
			ListTag nbtTagList = new ListTag();
			nbtTagList.add(StringTag.valueOf(Component.Serializer.toJson(
					Component.literal("Only craftable in Hardcore Mode").withStyle(ChatFormatting.DARK_PURPLE))));
			display.put("Lore", nbtTagList);
		}

		return resultStack;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		return this.getResultItem(access).copy();
	}

	static NonNullList<Ingredient> dissolvePattern(String[] p_44203_, Map<String, Ingredient> p_44204_, int p_44205_, int p_44206_) {
		NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_44205_ * p_44206_, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(p_44204_.keySet());
		set.remove(" ");

		for (int i = 0; i < p_44203_.length; ++i) {
			for (int j = 0; j < p_44203_[i].length(); ++j) {
				String s = p_44203_[i].substring(j, j + 1);
				Ingredient ingredient = p_44204_.get(s);
				if (ingredient == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the ingredient");
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
	static String[] shrink(List<String> p_301102_) {
		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;

		for (int i1 = 0; i1 < p_301102_.size(); ++i1) {
			String s = p_301102_.get(i1);
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

		if (p_301102_.size() == l) {
			return new String[0];
		} else {
			String[] astring = new String[p_301102_.size() - l - k];

			for (int k1 = 0; k1 < astring.length; ++k1) {
				astring[k1] = p_301102_.get(k1 + k).substring(i, j + 1);
			}

			return astring;
		}
	}

	private static int firstNonSpace(String p_44185_) {
		int i = 0;

		while (i < p_44185_.length() && p_44185_.charAt(i) == ' ') {
			++i;
		}

		return i;
	}

	private static int lastNonSpace(String p_44201_) {
		int i = p_44201_.length() - 1;

		while (i >= 0 && p_44201_.charAt(i) == ' ') {
			--i;
		}

		return i;
	}

	public static class Serializer implements RecipeSerializer<HardcoreRecipe> {
		public static final Codec<HardcoreRecipe> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(hardcoreRecipe -> hardcoreRecipe.group),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(hardcoreRecipe -> hardcoreRecipe.category),
								ShapedRecipePattern.MAP_CODEC.forGetter(hardcoreRecipe -> hardcoreRecipe.pattern),
								ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(hardcoreRecipe -> hardcoreRecipe.result),
								ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter(hardcoreRecipe -> hardcoreRecipe.showNotification)
						)
						.apply(instance, HardcoreRecipe::new)
		);

		@Override
		public Codec<HardcoreRecipe> codec() {
			return CODEC;
		}

		public HardcoreRecipe fromNetwork(FriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf();
			CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
			ShapedRecipePattern recipePattern = ShapedRecipePattern.fromNetwork(byteBuf);
			ItemStack itemstack = byteBuf.readItem();
			boolean flag = byteBuf.readBoolean();
			return new HardcoreRecipe(s, craftingbookcategory, recipePattern, itemstack, flag);
		}

		public void toNetwork(FriendlyByteBuf byteBuf, HardcoreRecipe hardcoreRecipe) {
			byteBuf.writeUtf(hardcoreRecipe.group);
			byteBuf.writeEnum(hardcoreRecipe.category);
			hardcoreRecipe.pattern.toNetwork(byteBuf);
			byteBuf.writeItem(hardcoreRecipe.result);
			byteBuf.writeBoolean(hardcoreRecipe.showNotification);
		}
	}
}
