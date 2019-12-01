package com.shynieke.statues.items;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class StatueBlockItem extends BlockItem {

	public StatueBlockItem(Block blockIn, Item.Properties builder) {
		super(blockIn, builder.group(StatueTabs.STATUES_BLOCKS));
	}

	@Nullable
	@Override
	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
		return EquipmentSlotType.HEAD;
	}

	public boolean isBaby() {
		if(this.getBlock() instanceof AbstractStatueBase) {
			return (((AbstractStatueBase) this.getBlock())).isBaby();
		} else {
			return false;
		}
	}

	public EntityType<?> getEntity() {
		if(this.getBlock() instanceof AbstractStatueBase) {
			return (((AbstractStatueBase) this.getBlock())).getEntity();
		} else {
			return EntityType.BAT;
		}
	}

	@Nullable
	@Override
	protected BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		ItemStack stack = context.getItem();
		if(Block.getBlockFromItem(stack.getItem()) instanceof AbstractStatueBase) {
			if(stack.hasTag() && stack.getTag().get("Traits") != null) {
				state = state.with(AbstractStatueBase.INTERACTIVE, true);
			}
		}
		return state;
	}
}
