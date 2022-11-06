package com.shynieke.statues.handlers;

import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TraderHandler {
	@SubscribeEvent
	public void onWandererTradesEvent(WandererTradesEvent event) {
		event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.INFO_STATUE.get(), 1, 2, 32, 1));
		event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.SOMBRERO.get(), 1, 10, 1, 1));
		event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.DETECTIVE_PLATYPUS.get(), 1, 20, 1, 1));
		event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.SLABFISH.get(), 1, 15, 1, 1));
		event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.TOTEM_OF_UNDYING_STATUE.get(), 1, 32, 1, 5));
	}

	public static class ItemsForEmeraldsTrade implements ItemListing {
		private final ItemStack outputStack;
		private final int outputAmount;
		private final int priceAmount;
		private final int maxUses;
		private final int givenExp;
		private final float priceMultiplier;

		public ItemsForEmeraldsTrade(Block block, int outputAmount, int priceAmount, int maxUses, int givenExp) {
			this(new ItemStack(block), priceAmount, outputAmount, maxUses, givenExp);
		}

		public ItemsForEmeraldsTrade(Item item, int outputAmount, int priceAmount, int givenExp) {
			this(new ItemStack(item), priceAmount, outputAmount, 12, givenExp);
		}

		public ItemsForEmeraldsTrade(Item item, int outputAmount, int priceAmount, int maxUses, int givenExp) {
			this(new ItemStack(item), priceAmount, outputAmount, maxUses, givenExp);
		}

		public ItemsForEmeraldsTrade(ItemStack outputStack, int priceAmount, int outputAmount, int maxUses, int givenExp) {
			this(outputStack, priceAmount, outputAmount, maxUses, givenExp, 0.05F);
		}

		public ItemsForEmeraldsTrade(ItemStack outputStack, int priceAmount, int outputAmount, int maxUses, int givenExp, float priceMultiplier) {
			this.priceAmount = priceAmount;
			this.outputStack = outputStack;
			this.outputAmount = outputAmount;
			this.maxUses = maxUses;
			this.givenExp = givenExp;
			this.priceMultiplier = priceMultiplier;
		}

		public MerchantOffer getOffer(Entity trader, RandomSource rand) {
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.priceAmount), new ItemStack(this.outputStack.getItem(), this.outputAmount), this.maxUses, this.givenExp, this.priceMultiplier);
		}
	}
}
