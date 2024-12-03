package com.shynieke.statues.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.statues.datacomponent.StatueStats;
import com.shynieke.statues.recipe.UpgradeType;
import com.shynieke.statues.registry.StatueDataComponents;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.server.command.EnumArgument;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatuesCommands {
	public static void initializeCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
		final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("statues");

		List<String> upgrades = Arrays.stream(UpgradeType.values())
				.filter(value -> value != UpgradeType.CRAFTING).map(Enum::name).collect(Collectors.toList());

		root.requires((source) -> source.hasPermission(2))
				.then(Commands.literal("upgrade")
						.then(Commands.argument("player", EntityArgument.player())
								.then(Commands.argument("upgrade", EnumArgument.enumArgument(UpgradeType.class))
										.suggests((cs, builder) -> SharedSuggestionProvider.suggest(upgrades, builder))
										.then(Commands.argument("tier", IntegerArgumentType.integer(0))
												.executes(StatuesCommands::upgradeStatue)
										)
								)
						)
				);

		if (!FMLLoader.isProduction()) {
			root.requires((source) -> source.hasPermission(2))
					.then(Commands.literal("checkLoot")
							.executes(StatuesDevCommands::checkLoot));
			root.requires((source) -> source.hasPermission(2))
					.then(Commands.literal("checkBlockEntity")
							.executes(StatuesDevCommands::checkBlockEntity));
		}

		dispatcher.register(root);
	}

	private static int upgradeStatue(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		final UpgradeType upgrade = ctx.getArgument("upgrade", UpgradeType.class);
		final ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
		int tier = ctx.getArgument("tier", Integer.class);
		if (tier > upgrade.getCap()) {
			ctx.getSource().sendFailure(
					Component.translatable("commands.statues.upgrade.too_high", upgrade.getCap()).withStyle(ChatFormatting.RED)
			);
			return 0;
		}
		ItemStack heldStack = player.getMainHandItem();
		if (!heldStack.isEmpty() && heldStack.is(StatueTags.UPGRADEABLE_STATUES)) {
			if (heldStack.has(StatueDataComponents.UPGRADED) ||
					heldStack.getOrDefault(StatueDataComponents.STATS, StatueStats.empty()).upgradeSlots() < 1) {
				fillInTag(heldStack, tier);
			}
			for (int i = 0; i < tier; i++) {
				upgrade.apply(heldStack, i);
			}
			ctx.getSource().sendSuccess(
					() -> Component.translatable("commands.statues.upgrade.success", Component.translatable(heldStack.getItem().getDescriptionId()).getString()).withStyle(ChatFormatting.GREEN),
					true
			);
		} else {
			ctx.getSource().sendFailure(
					Component.translatable("commands.statues.upgrade.invalid", Component.translatable(heldStack.getItem().getDescriptionId()).getString()).withStyle(ChatFormatting.RED)
			);
		}
		return 0;
	}

	private static void fillInTag(ItemStack stack, int tier) {
		stack.set(StatueDataComponents.UPGRADED.get(), true);

		StatueStats stats = stack.getOrDefault(StatueDataComponents.STATS.get(), StatueStats.empty());
		stats.setLevel(tier == -1 ? 0 : tier + 1);
		stats.setUpgradeSlots(20);
		stack.set(StatueDataComponents.STATS.get(), stats);
	}
}
