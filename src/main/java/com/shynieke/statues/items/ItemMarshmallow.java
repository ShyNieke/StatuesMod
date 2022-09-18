package com.shynieke.statues.items;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.init.StatuesItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;

public class ItemMarshmallow extends ItemFood {
	public ItemMarshmallow(int amount, float saturation, String unlocalised) {
		super(amount, saturation, false);
		setTranslationKey(Reference.MOD_PREFIX + unlocalised);
		setRegistryName("item" + unlocalised);
		setCreativeTab(CreativeTabs.FOOD);
		setCreativeTab(Statues.tabStatues);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);

		if (!worldIn.isRemote) {
			if (this == StatuesItems.marshmallow_golden) {
				ArrayList<Potion> potionList = new ArrayList<>(ForgeRegistries.POTIONS.getValuesCollection());
				potionList.remove(MobEffects.NAUSEA);

				int i = itemRand.nextInt(potionList.size());
				int amplifier = itemRand.nextInt(2);
				Potion randomPotion = potionList.get(i);
				PotionEffect randomEffect = new PotionEffect(randomPotion, 200, amplifier);
				player.addPotionEffect(randomEffect);
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		if (this == StatuesItems.marshmallow_golden) {
			return true;
		} else {
			return super.hasEffect(stack);
		}
	}
}
