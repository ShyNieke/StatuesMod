package com.shynieke.statues.compat.rei.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.compat.rei.StatuesREIPlugin;
import com.shynieke.statues.recipe.LootRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LootDisplay implements Display {
	public static final DisplaySerializer<LootDisplay> SERIALIZER = DisplaySerializer.of(
			RecordCodecBuilder.mapCodec(instance -> instance.group(
					EntryIngredient.codec().fieldOf("input").forGetter(LootDisplay::inputEntry),
					EntryIngredient.codec().fieldOf("output1").forGetter(LootDisplay::outputEntry1),
					Codec.FLOAT.fieldOf("chance1").forGetter(LootDisplay::getChance1),
					EntryIngredient.codec().fieldOf("output2").forGetter(LootDisplay::outputEntry2),
					Codec.FLOAT.fieldOf("chance2").forGetter(LootDisplay::getChance2),
					EntryIngredient.codec().fieldOf("output3").forGetter(LootDisplay::outputEntry3),
					Codec.FLOAT.fieldOf("chance3").forGetter(LootDisplay::getChance3)
			).apply(instance, LootDisplay::new)),
			StreamCodec.composite(
					EntryIngredient.streamCodec(),
					d -> d.inputEntry,
					EntryIngredient.streamCodec(),
					d -> d.outputEntry1,
					ByteBufCodecs.FLOAT,
					d -> d.chance1,
					EntryIngredient.streamCodec(),
					d -> d.outputEntry2,
					ByteBufCodecs.FLOAT,
					d -> d.chance2,
					EntryIngredient.streamCodec(),
					d -> d.outputEntry3,
					ByteBufCodecs.FLOAT,
					d -> d.chance3,
					LootDisplay::new
			));

	private final float chance1;
	private final float chance2;
	private final float chance3;

	private final EntryIngredient inputEntry;
	private final EntryIngredient outputEntry1;
	private final EntryIngredient outputEntry2;
	private final EntryIngredient outputEntry3;

	public LootDisplay(EntryIngredient input,
	                   EntryIngredient output1, float chance1,
	                   EntryIngredient output2, float chance2,
	                   EntryIngredient output3, float chance3) {

		this.inputEntry = input;

		this.outputEntry1 = output1;
		this.chance1 = chance1;
		this.outputEntry2 = output2;
		this.chance2 = chance2;
		this.outputEntry3 = output3;
		this.chance3 = chance3;
	}

	public LootDisplay(RecipeHolder<LootRecipe> lootRecipeRecipeHolder) {
		LootRecipe recipe = lootRecipeRecipeHolder.value();
		this.inputEntry = EntryIngredients.ofIngredient(recipe.getIngredients().getFirst());

		this.outputEntry1 = EntryIngredients.of(VanillaEntryTypes.ITEM, List.of(recipe.getResultItem()));
		this.chance1 = recipe.getChance1();
		this.outputEntry2 = EntryIngredients.of(VanillaEntryTypes.ITEM, List.of(recipe.getResultItem2()));
		this.chance2 = recipe.getChance2();
		this.outputEntry3 = EntryIngredients.of(VanillaEntryTypes.ITEM, List.of(recipe.getResultItem3()));
		this.chance3 = recipe.getChance3();
	}

	public EntryIngredient inputEntry() {
		return inputEntry;
	}

	public EntryIngredient outputEntry1() {
		return outputEntry1;
	}

	public EntryIngredient outputEntry2() {
		return outputEntry2;
	}

	public EntryIngredient outputEntry3() {
		return outputEntry3;
	}

	public float getChance1() {
		return chance1;
	}

	public float getChance2() {
		return chance2;
	}

	public float getChance3() {
		return chance3;
	}

	@Override
	public List<EntryIngredient> getInputEntries() {
		return List.of(inputEntry);
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return List.of(outputEntry1, outputEntry2, outputEntry3);
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return StatuesREIPlugin.LOOT;
	}

	@Override
	public Optional<Identifier> getDisplayLocation() {
		return Optional.empty();
	}

	@Nullable
	@Override
	public DisplaySerializer<? extends Display> getSerializer() {
		return SERIALIZER;
	}
}
