package com.svennieke.statues.items;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import com.svennieke.statues.config.StatuesConfigGen;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemMooshroomSoup extends ItemFood {
	public ItemMooshroomSoup(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setMaxStackSize(StatuesConfigGen.general.SoupStack);
		setUnlocalizedName(Reference.StatuesItems.MOOSHROOMSOUP.getUnlocalisedName());
		setRegistryName(Reference.StatuesItems.MOOSHROOMSOUP.getRegistryName());
		setCreativeTab(CreativeTabs.FOOD);
		setCreativeTab(Statues.tabStatues);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity)
    {
		if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode) {
			ItemStack bowlstack = new ItemStack(Items.BOWL);
			EntityPlayer entityplayer = (EntityPlayer)entity;
			InventoryPlayer playerInv = entityplayer.inventory;
			entityplayer.getFoodStats().addStats(this, stack);
		    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
		    this.onFoodEaten(stack, worldIn, entityplayer);
			
		    if (entityplayer instanceof EntityPlayerMP)
		    {
		        CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
		    }
		    
			if(!worldIn.isRemote)
			{
				if(playerInv.getFirstEmptyStack() == -1)
				{
					entityplayer.entityDropItem(bowlstack, 0F);
				}
				else
				{
					playerInv.addItemStackToInventory(bowlstack);
				}
			}
			stack.shrink(1);
			
		}
		return stack;
    }
}
