package com.shynieke.statues.commands;

import com.mojang.brigadier.context.CommandContext;
import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipe.StatuesRecipes;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class containing all the dev commands for the Statues mod
 */
public class StatuesDevCommands {

	/**
	 * A dev only command to check if any blocks with loot are missing from the Statue block entity valid blocks
	 *
	 * @param ctx The command context
	 */
	static int checkLoot(CommandContext<CommandSourceStack> ctx) {
		Set<Block> beBlocks = new HashSet<>(StatueBlockEntities.STATUE.get().getValidBlocks());
		beBlocks.addAll(StatueBlockEntities.SHULKER_STATUE.get().getValidBlocks());
		beBlocks.addAll(StatueBlockEntities.TROPICAL_FISH.get().getValidBlocks());
		List<Identifier> missingBlocks = new ArrayList<>();
		ctx.getSource().getLevel().recipeAccess().recipeMap().byType(StatuesRecipes.LOOT_RECIPE.get()).forEach(recipe -> {
			if (recipe.id().identifier().getNamespace().equals(Reference.MOD_ID)) {
				for (Ingredient ingredient : recipe.value().getIngredients()) {
					for (Holder<Item> item : ingredient.getValues()) {
						if (item.value() instanceof BlockItem blockItem) {
							if (!beBlocks.contains(blockItem.getBlock()))
								missingBlocks.add(BuiltInRegistries.BLOCK.getKey(blockItem.getBlock()));
						}
					}
				}
			}
		});
		if (missingBlocks.isEmpty()) {
			ctx.getSource().sendSuccess(() -> Component.literal("No blocks with loot are missing from the Statue block entity valid blocks"), false);
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("The following blocks are missing from the Statue block entity valid blocks: ");
			for (Identifier location : missingBlocks) {
				builder.append(location.toString()).append(", ");
			}
			ctx.getSource().sendFailure(Component.literal(builder.toString()));

		}
		return 0;
	}

	/**
	 * A dev only command to check if any blocks with loot are missing from the Statue block entity valid blocks
	 *
	 * @param ctx The command context
	 */
	static int checkBlockEntity(CommandContext<CommandSourceStack> ctx) {
		Set<Block> beBlocks = new HashSet<>(StatueBlockEntities.STATUE.get().getValidBlocks());
		beBlocks.addAll(StatueBlockEntities.SHULKER_STATUE.get().getValidBlocks());
		beBlocks.addAll(StatueBlockEntities.TROPICAL_FISH.get().getValidBlocks());
		List<Identifier> missingBlocks = new ArrayList<>();

		for (DeferredHolder<Block, ? extends Block> holder : StatueRegistry.BLOCKS.getEntries()) {
			if (holder.get() instanceof AbstractStatueBase base) {
				if (!beBlocks.contains(base)) {
					missingBlocks.add(BuiltInRegistries.BLOCK.getKey(base));
				}
			}
		}
		if (missingBlocks.isEmpty()) {
			ctx.getSource().sendSuccess(() -> Component.literal("No blocks are missing from the Statue block entity valid blocks"), false);
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("The following blocks are missing from the Statue block entity valid blocks: ");
			for (Identifier location : missingBlocks) {
				builder.append(location.toString()).append(", ");
			}
			ctx.getSource().sendFailure(Component.literal(builder.toString()));

		}
		return 0;
	}
}
