package com.shynieke.statues.lootmodifiers;

import com.google.gson.JsonObject;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.init.StatueTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import javax.annotation.Nonnull;

public class StatuesLootModifier extends LootModifier {
	public StatuesLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Nonnull
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (StatuesConfig.COMMON.ancientCityLoot.get()) {
			ITag<Item> statues = ForgeRegistries.ITEMS.tags().getTag(StatueTags.STATUES_ITEMS);
			RandomSource random = context.getRandom();
			ItemStack statueStack = new ItemStack(statues.getRandomElement(random).orElse(Items.EGG));
			if (random.nextDouble() <= StatuesConfig.COMMON.ancientCityLootChance.get()) {
				generatedLoot.add(statueStack);
			}
		}

		return generatedLoot;
	}


	public static class Serializer extends GlobalLootModifierSerializer<StatuesLootModifier> {
		@Override
		public StatuesLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn) {
			return new StatuesLootModifier(conditionsIn);
		}

		@Override
		public JsonObject write(StatuesLootModifier instance) {
			return makeConditions(instance.conditions);
		}
	}
}