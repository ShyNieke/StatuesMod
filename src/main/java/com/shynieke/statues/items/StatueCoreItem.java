package com.shynieke.statues.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
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
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		CompoundNBT tag = stack.hasTag() ? stack.getTag() : new CompoundNBT();
		if (tag != null && !tag.getString(entityTag).isEmpty()) {
			tooltip.add(new TranslationTextComponent("statues.core.info,", tag.getString(entityTag)).withStyle(TextFormatting.GOLD));
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entityIn, Hand handIn) {
		if (!(entityIn instanceof PlayerEntity) && !isLocked) {
			CompoundNBT tag = stack.hasTag() ? stack.getTag() : new CompoundNBT();
			if(tag != null) {
				if(!tag.getString(entityTag).isEmpty()) {
					this.isLocked = true;
					return ActionResultType.FAIL;
				} else {
					if (entityIn.isAlive()) {
						if (entityIn instanceof MobEntity) {
							tag.putString(entityTag, String.valueOf(ForgeRegistries.ENTITIES.getKey(entityIn.getType())));
							stack.setTag(tag);
						}
					}
				}
			}

			return ActionResultType.SUCCESS;
		} else {
			return ActionResultType.FAIL;
		}
	}
}
