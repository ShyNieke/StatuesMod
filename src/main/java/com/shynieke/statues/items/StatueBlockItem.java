package com.shynieke.statues.items;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueTabs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class StatueBlockItem extends BlockItem {

	public StatueBlockItem(Block blockIn, Item.Properties builder) {
		super(blockIn, builder.tab(StatueTabs.STATUES_BLOCKS));
	}

	@Nullable
	@Override
	public EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.HEAD;
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
	protected BlockState getPlacementState(BlockPlaceContext context) {
		BlockState state = super.getPlacementState(context);
		ItemStack stack = context.getItemInHand();
		if(Block.byItem(stack.getItem()) instanceof AbstractStatueBase) {
			if(stack.hasTag() && stack.getTag().get("Traits") != null) {
				state = state.setValue(AbstractStatueBase.INTERACTIVE, true);
			}
		}
		return state;
	}

//	@Override
//	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//		if(ModList.get().isLoaded("curios"))
//			return new StatueCurioCapabilityProvider(stack);
//		else
//			return null;
//	}
}
