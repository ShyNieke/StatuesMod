package com.shynieke.statues.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

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
	public RecipeSerializer<? extends ShapedRecipe> getSerializer() {
		return StatuesRecipes.HARDCORE_SHAPED_SERIALIZER.get();
	}

	@Override
	public boolean matches(CraftingInput craftingInput, Level level) {
		return super.matches(craftingInput, level) && level.getLevelData().isHardcore();
	}

//	@Override
//	public ItemStack getResultItem(HolderLookup.Provider lookupProvider) {
//		ItemStack resultStack = super.getResultItem(lookupProvider);
//
//		ItemLore lore = ItemLore.EMPTY;
//		lore = lore.withLineAdded(Component.literal("Only craftable in Hardcore Mode").withStyle(ChatFormatting.DARK_PURPLE));
//		resultStack.set(DataComponents.LORE, lore);
//
//		return resultStack;
//	}

	@Override
	public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider lookupProvider) {
		return this.result.copy();
	}

	public static class Serializer implements RecipeSerializer<HardcoreRecipe> {
		public static final MapCodec<HardcoreRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(p_311729_ -> p_311729_.group),
								CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(p_311732_ -> p_311732_.category),
								ShapedRecipePattern.MAP_CODEC.forGetter(p_311733_ -> p_311733_.pattern),
								ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_311730_ -> p_311730_.result),
								Codec.BOOL.optionalFieldOf("show_notification", Boolean.TRUE).forGetter(p_311731_ -> p_311731_.showNotification)
						)
						.apply(instance, HardcoreRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, HardcoreRecipe> STREAM_CODEC = StreamCodec.of(
				HardcoreRecipe.Serializer::toNetwork, HardcoreRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<HardcoreRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, HardcoreRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		public static HardcoreRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
			String s = byteBuf.readUtf();
			CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
			ShapedRecipePattern recipePattern = ShapedRecipePattern.STREAM_CODEC.decode(byteBuf);
			ItemStack itemstack = ItemStack.STREAM_CODEC.decode(byteBuf);
			boolean flag = byteBuf.readBoolean();
			return new HardcoreRecipe(s, craftingbookcategory, recipePattern, itemstack, flag);
		}

		public static void toNetwork(RegistryFriendlyByteBuf byteBuf, HardcoreRecipe hardcoreRecipe) {
			byteBuf.writeUtf(hardcoreRecipe.group);
			byteBuf.writeEnum(hardcoreRecipe.category);
			ShapedRecipePattern.STREAM_CODEC.encode(byteBuf, hardcoreRecipe.pattern);
			ItemStack.STREAM_CODEC.encode(byteBuf, hardcoreRecipe.result);
			byteBuf.writeBoolean(hardcoreRecipe.showNotification);
		}
	}
}
