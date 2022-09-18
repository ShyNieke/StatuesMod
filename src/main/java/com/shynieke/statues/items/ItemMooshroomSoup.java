package com.shynieke.statues.items;

import com.shynieke.statues.Reference;
import com.shynieke.statues.Statues;
import com.shynieke.statues.config.StatuesConfigGen;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemMooshroomSoup extends ItemFood {
	public ItemMooshroomSoup(int amount, float saturation, String unlocalised) {
		super(amount, saturation, false);
		setTranslationKey(Reference.MOD_PREFIX + unlocalised);
		setRegistryName("item" + unlocalised);
		setCreativeTab(CreativeTabs.FOOD);
		setCreativeTab(Statues.tabStatues);
	}

	@Override
	public Item setMaxStackSize(int maxStackSize) {
		int size = StatuesConfigGen.othersettings.SoupStack;

		if (size != 0) {
			return super.setMaxStackSize(size);
		} else {
			return super.setMaxStackSize(1);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode) {
			ItemStack bowlstack = new ItemStack(Items.BOWL);
			EntityPlayer entityplayer = (EntityPlayer) entity;
			InventoryPlayer playerInv = entityplayer.inventory;
			entityplayer.getFoodStats().addStats(this, stack);
			worldIn.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(stack, worldIn, entityplayer);

			if (entityplayer instanceof EntityPlayerMP) {
				CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityplayer, stack);
			}

			if (!worldIn.isRemote) {
				if (playerInv.getFirstEmptyStack() == -1) {
					entityplayer.entityDropItem(bowlstack, 0F);
				} else {
					playerInv.addItemStackToInventory(bowlstack);
				}
			}
			stack.shrink(1);
		}
		return stack;
	}
}
