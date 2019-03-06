package com.svennieke.statues.items;

import com.svennieke.statues.Statues;
import com.svennieke.statues.init.StatuesItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ItemMarshmallow extends ItemFood {
	public ItemMarshmallow(Item.Properties builder, int amount, float saturation) {
		super(amount, saturation, false, builder.group(ItemGroup.FOOD).group(Statues.tabStatues));
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);

		if(!worldIn.isRemote)
		{
			if(this == StatuesItems.marshmallow_golden) {
				ArrayList<Potion> potionList = new ArrayList<>(ForgeRegistries.POTIONS.getValues());
				potionList.remove(MobEffects.NAUSEA);

				int i = random.nextInt(potionList.size());
				int amplifier = random.nextInt(2);
				Potion randomPotion = potionList.get(i);
				PotionEffect randomEffect = new PotionEffect(randomPotion, 200, amplifier);
				player.addPotionEffect(randomEffect);
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		if(this == StatuesItems.marshmallow_golden) {
			return true;
		}
		else
		{
			return super.hasEffect(stack);
		}
	}
}
