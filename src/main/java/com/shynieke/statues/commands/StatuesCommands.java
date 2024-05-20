package com.shynieke.statues.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shynieke.statues.Reference;
import com.shynieke.statues.recipe.UpgradeType;
import com.shynieke.statues.registry.StatueTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.command.EnumArgument;

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
			CompoundTag tag = heldStack.getTagElement("BlockEntityTag");
			if (tag == null || !tag.contains(Reference.UPGRADED) || tag.getInt(Reference.UPGRADE_SLOTS) < 1) {
				fillInTag(heldStack, upgrade, tier);
			}
			for (int i = 0; i < tier; i++) {
				upgrade.apply(heldStack, i);
			}
			ctx.getSource().sendSuccess(
					() -> Component.translatable("commands.statues.upgrade.success", I18n.get(heldStack.getDescriptionId())).withStyle(ChatFormatting.GREEN),
					true
			);
		} else {
			ctx.getSource().sendFailure(
					Component.translatable("commands.statues.upgrade.invalid", I18n.get(heldStack.getDescriptionId())).withStyle(ChatFormatting.RED)
			);
		}
		return 0;
	}

	private static void fillInTag(ItemStack stack, UpgradeType type, int tier) {
		CompoundTag entityTag = new CompoundTag();
		entityTag.putInt(Reference.LEVEL, tier == -1 ? type == UpgradeType.UPGRADE ? 0 : 1 : tier + 1);
		entityTag.putBoolean(Reference.UPGRADED, true);
		entityTag.putInt(Reference.UPGRADE_SLOTS, 20);

		stack.addTagElement("BlockEntityTag", entityTag);
	}
}
