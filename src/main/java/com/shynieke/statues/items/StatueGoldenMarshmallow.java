package com.shynieke.statues.items;

import com.shynieke.statues.registry.StatueFoods;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Collectors;

public class StatueGoldenMarshmallow extends Item {
	public StatueGoldenMarshmallow(Properties builder) {
		super(builder.food(StatueFoods.GOLDEN_MARSHMALLOW));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityIn) {
		if (stack.has(DataComponents.FOOD)) {
			if (!level.isClientSide()) {
				if (this == StatueRegistry.MARSHMALLOW_GOLDEN.get()) {
					List<Holder<MobEffect>> effectList = BuiltInRegistries.MOB_EFFECT.listElements().collect(Collectors.toList());
					effectList.remove(MobEffects.NAUSEA);

					int i = level.random.nextInt(effectList.size());
					int amplifier = level.random.nextInt(2);
					Holder<MobEffect> randomPotion = effectList.get(i);
					MobEffectInstance randomEffect = new MobEffectInstance(randomPotion, 200, amplifier);
					entityIn.addEffect(randomEffect);
				}
			}
			Consumable consumable = stack.get(DataComponents.CONSUMABLE);
			if (consumable != null) {
				consumable.onConsume(level, entityIn, stack);
			}

			return stack;
		}

		return stack;
	}

	@Override
	public boolean isFoil(ItemStack p_77636_1_) {
		return true;
	}
}
