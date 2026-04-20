package com.shynieke.statues.lootmodifiers;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.datacomponent.StatueStats;
import com.shynieke.statues.registry.StatueDataComponents;
import com.shynieke.statues.registry.StatueLootModifiers;
import com.shynieke.statues.registry.StatueTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CityStatuesLootModifier extends LootModifier {
	public static final Supplier<MapCodec<CityStatuesLootModifier>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, CityStatuesLootModifier::new)));

	public CityStatuesLootModifier(LootItemCondition[] conditionsIn, int priority) {
		super(conditionsIn, priority);
	}

	@NotNull
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (StatuesConfig.COMMON.ancientCityLoot.get()) {
			Optional<HolderSet.Named<Item>> optionalTag = BuiltInRegistries.ITEM.get(StatueTags.STATUES_ITEMS);
			if (optionalTag.isPresent()) {
				HolderSet.Named<Item> tag = optionalTag.get();
				RandomSource random = context.getRandom();
				Optional<Holder<Item>> randomItem = tag.getRandomElement(random);
				if (randomItem.isPresent()) {
					ItemStack statueStack = new ItemStack(randomItem.get());
					if (random.nextDouble() <= StatuesConfig.COMMON.ancientCityLootChance.get() && !statueStack.is(Items.EGG)) {
						StatueStats stats = new StatueStats(1, 2, getRandInRange(context.getRandom(), 6, 16));
						statueStack.set(StatueDataComponents.UPGRADED, true);
						statueStack.set(StatueDataComponents.STATS, stats);

						generatedLoot.add(statueStack);
					}
				}
			}
		}
		return generatedLoot;
	}

	public int getRandInRange(RandomSource random, int min, int max) {
		return random.nextIntBetweenInclusive(min, max);
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return StatueLootModifiers.STATUES_CITY_LOOT.get();
	}
}