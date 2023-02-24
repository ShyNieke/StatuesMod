package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class StatueTabs {
	private static CreativeModeTab STATUES_BLOCKS;

	private static CreativeModeTab STATUES_ITEMS;

	@SubscribeEvent
	public void registerCreativeTabs(final CreativeModeTabEvent.Register event) {
		STATUES_BLOCKS = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "blocks"), builder ->
				builder.icon(() -> new ItemStack(StatueRegistry.SLIME_STATUE.get()))
						.title(Component.translatable("itemGroup.statues.blocks"))
						.displayItems((features, output, hasPermissions) -> {
							List<ItemStack> stacks = StatueRegistry.BLOCKS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
							output.acceptAll(stacks);
						}));

		STATUES_ITEMS = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "items"), builder ->
				builder.icon(() -> new ItemStack(StatueRegistry.STATUE_CORE.get()))
						.title(Component.translatable("itemGroup.statues.items"))
						.displayItems((features, output, hasPermissions) -> {
							List<ItemStack> stacks = StatueRegistry.ITEMS.getEntries().stream()
									.filter(reg -> !(reg.get() instanceof BlockItem)).map(reg -> new ItemStack(reg.get())).toList();
							output.acceptAll(stacks);
						}));
	}
}
