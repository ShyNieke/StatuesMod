//package com.shynieke.statues.compat.rei;
//
//import com.shynieke.statues.Statues;
//import com.shynieke.statues.blocks.AbstractStatueBase;
//import com.shynieke.statues.compat.rei.category.LootCategory;
//import com.shynieke.statues.compat.rei.category.UpgradeCategory;
//import com.shynieke.statues.config.StatuesConfig;
//import com.shynieke.statues.items.StatueBlockItem;
//import com.shynieke.statues.registry.StatueRegistry;
//import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
//import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
//import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
//import me.shedaniel.rei.api.common.util.EntryStacks;
//import me.shedaniel.rei.forge.REIPluginClient;
//import me.shedaniel.rei.plugin.client.BuiltinClientPlugin;
//import net.minecraft.ChatFormatting;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.entity.EntityType;
//
//import java.util.List;
//
//@REIPluginClient
//public class StatuesREIClientPlugin implements REIClientPlugin {
//	@Override
//	public void registerCategories(CategoryRegistry registry) {
//		registry.add(new LootCategory());
//		registry.add(new UpgradeCategory());
//
//		registry.addWorkstations(StatuesREIPlugin.LOOT, EntryStacks.of(StatueRegistry.STATUE_TABLE.get()));
//		registry.addWorkstations(StatuesREIPlugin.UPGRADE, EntryStacks.of(StatueRegistry.STATUE_TABLE.get()));
//	}
//
//	@Override
//	public void registerDisplays(DisplayRegistry registry) {
//		BuiltinClientPlugin clientPlugin = BuiltinClientPlugin.getInstance();
//		List<StatueBlockItem> statues = StatueRegistry.ITEMS.getEntries().stream()
//				.filter(registryObject -> registryObject.get() instanceof StatueBlockItem statueBlock &&
//						statueBlock.getBlock() instanceof AbstractStatueBase statueBase && !statueBase.isHiddenStatue())
//				.map(registryObject -> (StatueBlockItem) registryObject.get()).toList();
//		double chance = StatuesConfig.COMMON.statueDropChance.get();
//		for (StatueBlockItem statue : statues) {
//			if (statue.getEntity() == null) {
//				Statues.LOGGER.error("Tried adding info to statue but statue {} has no entity linked", BuiltInRegistries.ITEM.getKey(statue));
//			} else {
//				clientPlugin.registerInformation(EntryStacks.of(statue.getDefaultInstance()), Component.empty(),
//						list -> {
//							int chancePercentage = (int) (chance * 100);
//							if (statue.getEntity() == EntityType.WARDEN || statue.getEntity() == EntityType.ELDER_GUARDIAN) {
//								chancePercentage = 100;
//							} else if (statue.getEntity() == EntityType.RAVAGER) {
//								chancePercentage = 25;
//							}
//							list.add(Component.translatable("statues.gui.jei.statue.info",
//									Component.translatable(statue.getDescriptionId()).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD),
//									Component.literal(chancePercentage + "%").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD),
//									Component.translatable(statue.getEntity().getDescriptionId()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD)));
//							return list;
//						}
//				);
//			}
//		}
//		clientPlugin.registerInformation(EntryStacks.of(StatueRegistry.STATUE_CORE.get().getDefaultInstance()), Component.empty(),
//				list -> {
//					list.add(Component.translatable("statues.gui.jei.statue_core.info",
//							Component.translatable(StatueRegistry.STATUE_CORE.get().getDescriptionId()).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD),
//							Component.translatable(StatueRegistry.STATUE_BAT.get().getDescriptionId()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD)
//					));
//					list.add(Component.translatable("statues.gui.jei.statue_core.info2",
//							Component.translatable(StatueRegistry.STATUE_BAT.get().getDescriptionId()).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD)
//					));
//					return list;
//				}
//		);
//		clientPlugin.registerInformation(EntryStacks.of(StatueRegistry.STATUE_TABLE.get().asItem().getDefaultInstance()), Component.empty(),
//				list -> {
//					list.add(Component.translatable("statues.gui.jei.statue_table.info"));
//					list.add(Component.translatable("statues.gui.jei.statue_table.info2"));
//					list.add(Component.translatable("statues.gui.jei.statue_table.info3"));
//					list.add(Component.translatable("statues.gui.jei.statue_table.info4"));
//					list.add(Component.translatable("statues.gui.jei.statue_table.info5"));
//					list.add(Component.translatable("statues.gui.jei.statue_table.info6"));
//					list.add(Component.translatable("statues.gui.jei.statue_table.info7"));
//					list.add(Component.translatable("statues.gui.jei.statue_table.info8"));
//					return list;
//				}
//		);
//	}
//}
