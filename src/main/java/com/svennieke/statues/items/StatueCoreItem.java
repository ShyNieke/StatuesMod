package com.svennieke.statues.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class StatueCoreItem extends Item {

	private final String entityTag = "locked_entity";
	private boolean isLocked = false;

	public StatueCoreItem(Item.Properties builder) {
		super(builder);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World WorldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if(stack.hasTag()) {
			if(!stack.getTag().isEmpty()) {
				CompoundNBT nbt = stack.getTag();
				if(!nbt.getString(entityTag).isEmpty()) {
					tooltip.add(new TranslationTextComponent("statues.core.info,", new Object[] {nbt.getString(entityTag)}).applyTextStyle(TextFormatting.GOLD));
				}
			}
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entityIn, Hand handIn) {
		if (!(entityIn instanceof PlayerEntity) && !isLocked) {
			if(stack.hasTag()) {
				CompoundNBT nbt = stack.getTag();
				if(!nbt.getString(entityTag).isEmpty()) {
					this.isLocked = true;
					return false;
				} else {
					if (entityIn.isAlive()) {
						if (entityIn instanceof MobEntity) {
							CompoundNBT tag = new CompoundNBT();
							tag.putString(entityTag, String.valueOf(ForgeRegistries.ENTITIES.getKey(entityIn.getType())));
							stack.setTag(tag);
						}
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}
}
