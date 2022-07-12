package com.shynieke.statues.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
		CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
		if (tag != null && !tag.getString(entityTag).isEmpty()) {
			tooltips.add(Component.translatable("statues.core.info,", tag.getString(entityTag)).withStyle(ChatFormatting.GOLD));
		}
		super.appendHoverText(stack, level, tooltips, flag);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity entityIn, InteractionHand handIn) {
		if (!(entityIn instanceof Player) && !isLocked) {
			CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
			if (tag != null) {
				if (!tag.getString(entityTag).isEmpty()) {
					this.isLocked = true;
					return InteractionResult.FAIL;
				} else {
					if (entityIn.isAlive()) {
						if (entityIn instanceof Mob) {
							tag.putString(entityTag, String.valueOf(ForgeRegistries.ENTITY_TYPES.getKey(entityIn.getType())));
							stack.setTag(tag);
						}
					}
				}
			}

			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.FAIL;
		}
	}
}
