//package com.shynieke.statues.compat.rei.display;
//
//import com.shynieke.statues.compat.rei.REIPlugin;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.display.Display;
//import me.shedaniel.rei.api.common.entry.EntryIngredient;
//import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
//import me.shedaniel.rei.api.common.util.EntryIngredients;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public final class LootDisplay implements Display {
//	private final float chance1;
//	private final float chance2;
//	private final float chance3;
//
//	private final List<EntryIngredient> inputEntries;
//	private final List<EntryIngredient> outputEntries;
//
//	public LootDisplay(Ingredient ingredient,
//	                   ItemStack stack1, float chance1,
//	                   ItemStack stack2, float chance2,
//	                   ItemStack stack3, float chance3) {
//		this.chance1 = chance1;
//		this.chance2 = chance2;
//		this.chance3 = chance3;
//
//		this.inputEntries = List.of(EntryIngredients.ofIngredient(ingredient));
//
//		this.outputEntries = new ArrayList<>();
//		this.outputEntries.add(EntryIngredients.of(VanillaEntryTypes.ITEM, List.of(stack1)));
//		this.outputEntries.add(EntryIngredients.of(VanillaEntryTypes.ITEM, List.of(stack2)));
//		this.outputEntries.add(EntryIngredients.of(VanillaEntryTypes.ITEM, List.of(stack3)));
//	}
//
//	public float getChance1() {
//		return chance1;
//	}
//
//	public float getChance2() {
//		return chance2;
//	}
//
//	public float getChance3() {
//		return chance3;
//	}
//
//	@Override
//	public List<EntryIngredient> getInputEntries() {
//		return inputEntries;
//	}
//
//	@Override
//	public List<EntryIngredient> getOutputEntries() {
//		return outputEntries;
//	}
//
//	@Override
//	public CategoryIdentifier<?> getCategoryIdentifier() {
//		return REIPlugin.LOOT;
//	}
//}
