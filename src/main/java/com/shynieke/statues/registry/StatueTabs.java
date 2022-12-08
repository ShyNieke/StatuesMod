package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class StatueTabs {
	private static CreativeModeTab STATUES_BLOCKS;

	private static CreativeModeTab STATUES_ITEMS;

	@SubscribeEvent
	public void registerCreativeTabs(final CreativeModeTabEvent.Register event) {
		STATUES_BLOCKS = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "blocks"), t ->
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.statues.blocks"))
						.icon(() -> new ItemStack(StatueRegistry.SLIME_STATUE.get())));
		STATUES_ITEMS = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "items"), t ->
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1).title(Component.translatable("itemGroup.statues.items"))
						.icon(() -> new ItemStack(StatueRegistry.STATUE_CORE.get())));
	}

	@SubscribeEvent
	public void registerCreativeTabs(final CreativeModeTabEvent.BuildContents event) {
		CreativeModeTab tab = event.getTab();
		if (tab == STATUES_BLOCKS) {
			List<ItemLike> blocks = StatueRegistry.BLOCKS.getEntries().stream().map(reg -> (ItemLike) reg.get().asItem()).toList();
			event.registerSimple(tab, blocks.toArray(new ItemLike[blocks.size()]));
		} else if (tab == STATUES_ITEMS) {
			List<ItemLike> items = StatueRegistry.ITEMS.getEntries().stream()
					.filter(reg -> reg.get() instanceof BlockItem).map(reg -> (ItemLike) reg.get().asItem()).toList();
			event.registerSimple(tab, items.toArray(new ItemLike[items.size()]));
		}
	}
}
