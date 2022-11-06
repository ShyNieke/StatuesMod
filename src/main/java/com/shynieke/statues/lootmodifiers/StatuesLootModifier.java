package com.shynieke.statues.lootmodifiers;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.Reference;
import com.shynieke.statues.config.StatuesConfig;
import com.shynieke.statues.registry.StatueTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import javax.annotation.Nonnull;

public class StatuesLootModifier extends LootModifier {
	public static final Supplier<Codec<StatuesLootModifier>> CODEC = Suppliers.memoize(() ->
			RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, StatuesLootModifier::new)));

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
			if (random.nextDouble() <= StatuesConfig.COMMON.ancientCityLootChance.get() && !statueStack.is(Items.EGG)) {
				CompoundTag tag = statueStack.getOrCreateTag();
				tag.putBoolean(Reference.BLOCKED, true);
				statueStack.setTag(tag);
				generatedLoot.add(statueStack);
			}
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}