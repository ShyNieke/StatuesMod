package com.shynieke.statues.items;

import com.shynieke.statues.Reference;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.init.StatueTabs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.List;

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
		return this.getBlock() instanceof AbstractStatueBase statueBase ? statueBase.isBaby() : false;
	}

	public EntityType<?> getEntity() {
		return this.getBlock() instanceof AbstractStatueBase statueBase ? statueBase.getEntity() : null;
	}

	public boolean matchesEntity(LivingEntity livingEntity) {
		return getEntity() != null && getEntity() == livingEntity.getType() && livingEntity.isBaby() == isBaby();
	}

	@Nullable
	@Override
	protected BlockState getPlacementState(BlockPlaceContext context) {
		BlockState state = super.getPlacementState(context);
		ItemStack stack = context.getItemInHand();
		if (Block.byItem(stack.getItem()) instanceof AbstractStatueBase) {
			if (stack.hasTag() && stack.getTag().get("Traits") != null) {
				state = state.setValue(AbstractStatueBase.INTERACTIVE, true);
			}
		}
		return state;
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		if (ModList.get().isLoaded("curios")) {
			return com.shynieke.statues.compat.curios.CuriosCompat.getCapability(stack);
		}
		return super.initCapabilities(stack, nbt);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, level, components, tooltipFlag);
//		if (stack.hasTag()) {
//			CompoundTag tag = stack.getTag();
//
//			if (tag.contains(Reference.LEVEL))
//				components.add(Component.literal("Level: " + tag.getInt(Reference.LEVEL)));
//			if (tag.contains(Reference.KILL_COUNT))
//				components.add(Component.literal("Kills: " + tag.getInt(Reference.KILL_COUNT)));
//		}
	}
}
