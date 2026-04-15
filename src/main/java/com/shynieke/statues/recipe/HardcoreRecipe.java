package com.shynieke.statues.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

import java.util.List;

public class HardcoreRecipe extends NormalCraftingRecipe {
	public static final MapCodec<HardcoreRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
			i -> i.group(
							Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
							CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
							ShapedRecipePattern.MAP_CODEC.forGetter(o -> o.pattern),
							ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result)
					)
					.apply(i, HardcoreRecipe::new)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, HardcoreRecipe> STREAM_CODEC = StreamCodec.composite(
			Recipe.CommonInfo.STREAM_CODEC,
			o -> o.commonInfo,
			CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
			o -> o.bookInfo,
			ShapedRecipePattern.STREAM_CODEC,
			o -> o.pattern,
			ItemStackTemplate.STREAM_CODEC,
			o -> o.result,
			HardcoreRecipe::new
	);
	public static final RecipeSerializer<HardcoreRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
	public final ShapedRecipePattern pattern;
	private final ItemStackTemplate result;

	public HardcoreRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, ShapedRecipePattern pattern, ItemStackTemplate result) {
		super(commonInfo, bookInfo);
		this.pattern = pattern;
		this.result = result;
	}

	@Override
	public RecipeSerializer<HardcoreRecipe> getSerializer() {
		return StatuesRecipes.HARDCORE_SHAPED_SERIALIZER.get();
	}

	@Override
	protected PlacementInfo createPlacementInfo() {
		return PlacementInfo.createFromOptionals(this.pattern.ingredients());
	}

	public int getWidth() {
		return this.pattern.width();
	}

	public int getHeight() {
		return this.pattern.height();
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		return this.pattern.matches(input) && level.getLevelData().isHardcore();
	}

	@Override
	public ItemStack assemble(CraftingInput craftingInput) {
		return this.result.create();
	}

	@Override
	public List<RecipeDisplay> display() {
		return List.of(
				new ShapedCraftingRecipeDisplay(
						this.pattern.width(),
						this.pattern.height(),
						this.pattern.ingredients().stream().map(e -> e.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(),
						new SlotDisplay.ItemStackSlotDisplay(this.result),
						new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
				)
		);
	}
}
