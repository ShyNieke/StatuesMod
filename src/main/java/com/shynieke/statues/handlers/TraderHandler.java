package com.shynieke.statues.handlers;

import com.shynieke.statues.init.StatueRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class TraderHandler {
    @SubscribeEvent
    public void onWandererTradesEvent(WandererTradesEvent event) {
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.INFO_STATUE.get(), 1, 2, 32, 1));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.SOMBRERO.get(), 1, 10, 1, 1));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.DETECTIVE_PLATYPUS.get(), 1, 20, 1, 1));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.SLABFISH.get(), 1, 15, 1, 1));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(StatueRegistry.TOTEM_OF_UNDYING_STATUE.get(), 1, 32, 1, 5));
    }

    public static class ItemsForEmeraldsTrade implements ITrade {
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

        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.priceAmount), new ItemStack(this.outputStack.getItem(), this.outputAmount), this.maxUses, this.givenExp, this.priceMultiplier);
        }
    }
}
