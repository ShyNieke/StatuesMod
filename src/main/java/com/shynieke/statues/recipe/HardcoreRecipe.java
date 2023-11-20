package com.shynieke.statues.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
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
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HardcoreRecipe extends ShapedRecipe {
	static final int MAX_WIDTH = 3;
	static final int MAX_HEIGHT = 3;

	final int width;
	final int height;
	final NonNullList<Ingredient> recipeItems;
	final ItemStack result;
	final String group;
	final CraftingBookCategory category;
	final boolean showNotification;

	public HardcoreRecipe(String group, CraftingBookCategory category, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result, boolean showNotification) {
		super(group, category, width, height, recipeItems, result);
		this.group = group;
		this.category = category;
		this.width = width;
		this.height = height;
		this.recipeItems = recipeItems;
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
		private static final net.minecraft.resources.ResourceLocation NAME = new net.minecraft.resources.ResourceLocation("minecraft", "crafting_shaped");
		static final Codec<List<String>> PATTERN_CODEC = Codec.STRING.listOf().flatXmap(p_300940_ -> {
			if (p_300940_.size() > MAX_HEIGHT) {
				return DataResult.error(() -> "Invalid pattern: too many rows, %s is maximum".formatted(MAX_HEIGHT));
			} else if (p_300940_.isEmpty()) {
				return DataResult.error(() -> "Invalid pattern: empty pattern not allowed");
			} else {
				int i = p_300940_.get(0).length();

				for (String s : p_300940_) {
					if (s.length() > MAX_WIDTH) {
						return DataResult.error(() -> "Invalid pattern: too many columns, %s is maximum".formatted(MAX_WIDTH));
					}

					if (i != s.length()) {
						return DataResult.error(() -> "Invalid pattern: each row must be the same width");
					}
				}

				return DataResult.success(p_300940_);
			}
		}, DataResult::success);
		static final Codec<String> SINGLE_CHARACTER_STRING_CODEC = Codec.STRING.flatXmap(p_300861_ -> {
			if (p_300861_.length() != 1) {
				return DataResult.error(() -> "Invalid ingredient entry: '" + p_300861_ + "' is an invalid symbol (must be 1 character only).");
			} else {
				return " ".equals(p_300861_) ? DataResult.error(() -> "Invalid ingredient entry: ' ' is a reserved symbol.") : DataResult.success(p_300861_);
			}
		}, DataResult::success);
		private static final Codec<HardcoreRecipe> CODEC = RawHardcoreRecipe.CODEC.flatXmap(p_301248_ -> {
			String[] astring = HardcoreRecipe.shrink(p_301248_.pattern);
			int i = astring[0].length();
			int j = astring.length;
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);
			Set<String> set = Sets.newHashSet(p_301248_.key.keySet());

			for (int k = 0; k < astring.length; ++k) {
				String s = astring[k];

				for (int l = 0; l < s.length(); ++l) {
					String s1 = s.substring(l, l + 1);
					Ingredient ingredient = s1.equals(" ") ? Ingredient.EMPTY : p_301248_.key.get(s1);
					if (ingredient == null) {
						return DataResult.error(() -> "Pattern references symbol '" + s1 + "' but it's not defined in the ingredient");
					}

					set.remove(s1);
					nonnulllist.set(l + i * k, ingredient);
				}
			}

			if (!set.isEmpty()) {
				return DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + set);
			} else {
				HardcoreRecipe shapedrecipe = new HardcoreRecipe(p_301248_.group, p_301248_.category, i, j, nonnulllist, p_301248_.result, p_301248_.showNotification);
				return DataResult.success(shapedrecipe);
			}
		}, p_300934_ -> {
			throw new NotImplementedException("Serializing HardcoreRecipe is not implemented yet.");
		});

		@Override
		public Codec<HardcoreRecipe> codec() {
			return CODEC;
		}

		public HardcoreRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
			int i = friendlyByteBuf.readVarInt();
			int j = friendlyByteBuf.readVarInt();
			String s = friendlyByteBuf.readUtf();
			CraftingBookCategory craftingbookcategory = friendlyByteBuf.readEnum(CraftingBookCategory.class);
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

			for (int k = 0; k < nonnulllist.size(); ++k) {
				nonnulllist.set(k, Ingredient.fromNetwork(friendlyByteBuf));
			}

			ItemStack itemstack = friendlyByteBuf.readItem();
			boolean flag = friendlyByteBuf.readBoolean();
			return new HardcoreRecipe(s, craftingbookcategory, i, j, nonnulllist, itemstack, flag);
		}

		public void toNetwork(FriendlyByteBuf friendlyByteBuf, HardcoreRecipe hardcoreRecipe) {
			friendlyByteBuf.writeVarInt(hardcoreRecipe.width);
			friendlyByteBuf.writeVarInt(hardcoreRecipe.height);
			friendlyByteBuf.writeUtf(hardcoreRecipe.group);
			friendlyByteBuf.writeEnum(hardcoreRecipe.category);

			for (Ingredient ingredient : hardcoreRecipe.recipeItems) {
				ingredient.toNetwork(friendlyByteBuf);
			}

			friendlyByteBuf.writeItem(hardcoreRecipe.result);
			friendlyByteBuf.writeBoolean(hardcoreRecipe.showNotification);
		}


		static record RawHardcoreRecipe(
				String group, CraftingBookCategory category, Map<String, Ingredient> key, List<String> pattern,
				ItemStack result, boolean showNotification
		) {
			public static final Codec<RawHardcoreRecipe> CODEC = RecordCodecBuilder.create(
					instance -> instance.group(
									ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(p_301109_ -> p_301109_.group),
									CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_301117_ -> p_301117_.category),
									ExtraCodecs.strictUnboundedMap(HardcoreRecipe.Serializer.SINGLE_CHARACTER_STRING_CODEC, Ingredient.CODEC_NONEMPTY)
											.fieldOf("ingredient")
											.forGetter(p_301234_ -> p_301234_.key),
									HardcoreRecipe.Serializer.PATTERN_CODEC.fieldOf("pattern").forGetter(p_301164_ -> p_301164_.pattern),
									CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("result").forGetter(p_301076_ -> p_301076_.result),
									ExtraCodecs.strictOptionalField(Codec.BOOL, "show_notification", true).forGetter(p_301293_ -> p_301293_.showNotification)
							)
							.apply(instance, RawHardcoreRecipe::new)
			);
		}
	}
}
