package com.shynieke.statues.compat.patchouli;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class PatchouliCompat {
	public static void convertBook(Player playerIn) {
		Item guideBook = ForgeRegistries.ITEMS.getValue(new ResourceLocation("patchouli", "guide_book"));
		if (guideBook != null) {
			playerIn.getMainHandItem().shrink(1);
			ItemStack patchouliBook = new ItemStack(guideBook);
			CompoundTag tag = patchouliBook.getOrCreateTag();
			tag.putString("patchouli:book", "statues:statues");
			Level level = playerIn.level();
			if (!playerIn.addItem(patchouliBook)) {
				level.addFreshEntity(new ItemEntity(level, playerIn.getX(), playerIn.getY(), playerIn.getZ(), patchouliBook));
			}
			level.playSound(null, playerIn.blockPosition(), SoundEvents.CHICKEN_EGG, SoundSource.MASTER, 0.5F, 1.0F);
		}
	}
}
