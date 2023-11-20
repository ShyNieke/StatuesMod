package com.shynieke.statues.items;

import com.shynieke.statues.registry.StatueFoods;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class StatueGoldenMarshmallow extends Item {
	public StatueGoldenMarshmallow(Properties builder) {
		super(builder.food(StatueFoods.GOLDEN_MARSHMALLOW));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityIn) {
		if (this.isEdible()) {
			if (!level.isClientSide) {
				if (this == StatueRegistry.MARSHMALLOW_GOLDEN.get()) {
					List<MobEffect> effectList = BuiltInRegistries.MOB_EFFECT.stream().toList();
					effectList.remove(MobEffects.CONFUSION);

					int i = level.random.nextInt(effectList.size());
					int amplifier = level.random.nextInt(2);
					MobEffect randomPotion = effectList.get(i);
					MobEffectInstance randomEffect = new MobEffectInstance(randomPotion, 200, amplifier);
					entityIn.addEffect(randomEffect);
				}
			}
			return entityIn.eat(level, stack);
		}

		return stack;
	}

	@Override
	public boolean isFoil(ItemStack p_77636_1_) {
		return true;
	}
}
