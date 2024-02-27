package com.shynieke.statues.lootmodifiers;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.Reference;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.registry.StatueTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
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
	public static final Supplier<Codec<CityStatuesLootModifier>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, CityStatuesLootModifier::new)));

	public CityStatuesLootModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@NotNull
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (StatuesConfig.COMMON.ancientCityLoot.get()) {
			Optional<HolderSet.Named<Item>> optionalTag = BuiltInRegistries.ITEM.getTag(StatueTags.STATUES_ITEMS);
			if (optionalTag.isPresent()) {
				HolderSet.Named<Item> tag = optionalTag.get();
				RandomSource random = context.getRandom();
				Optional<Holder<Item>> randomItem = tag.getRandomElement(random);
				if (randomItem.isPresent()) {
					ItemStack statueStack = new ItemStack(randomItem.get());
					if (random.nextDouble() <= StatuesConfig.COMMON.ancientCityLootChance.get() && !statueStack.is(Items.EGG)) {
						CompoundTag entityTag = new CompoundTag();
						entityTag.putInt(Reference.LEVEL, 1);
						entityTag.putBoolean(Reference.UPGRADED, true);
						entityTag.putInt(Reference.UPGRADE_SLOTS, 2);
						entityTag.putInt(Reference.KILL_COUNT, getRandInRange(context.getRandom(), 6, 16));

						statueStack.addTagElement("BlockEntityTag", entityTag);
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
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}