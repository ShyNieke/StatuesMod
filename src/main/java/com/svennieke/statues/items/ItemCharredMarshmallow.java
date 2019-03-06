package com.svennieke.statues.items;

import com.svennieke.statues.Statues;
import com.svennieke.statues.init.StatuesItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ItemCharredMarshmallow extends Item {
	public ItemCharredMarshmallow(Properties builder) {
		super(builder.group(ItemGroup.FOOD).group(Statues.tabStatues));
	}

	@Override
	public int getBurnTime(ItemStack itemStack) {
		return 500;
	}
}
