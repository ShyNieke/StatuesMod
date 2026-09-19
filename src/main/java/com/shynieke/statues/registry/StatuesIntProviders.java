package com.shynieke.statues.registry;

import com.shynieke.statues.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class StatuesIntProviders {

	public static final ResourceKey<ContextIntProvider> COOKING_TIME_CHARRED_MARSHMALLOW = createKey("cooking/time_charred_marshmallow");

	private static ResourceKey<ContextIntProvider> createKey(String location) {
		return ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, Reference.modLoc(location));
	}

	public static void bootstrap(BootstrapContext<ContextIntProvider> context) {
		HolderGetter<LootItemCondition> predicates = context.lookup(Registries.PREDICATE);
		Holder.Reference<ContextIntProvider> normalBurnTime = context.register(ContextIntProviders.COOKING_NORMAL_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(1));
		Holder.Reference<ContextIntProvider> fastBurnTime = context.register(ContextIntProviders.COOKING_FAST_BURN_TIME_REDUCTION_FACTOR, new ConstantValue(2));

		context.register(COOKING_TIME_CHARRED_MARSHMALLOW, ContextIntProviders.cooking(predicates, normalBurnTime, fastBurnTime, 500));
	}
}
