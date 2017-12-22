package com.svennieke.statues.items;

import java.util.List;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import com.svennieke.statues.init.StatuesItems;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemTea extends ItemFood {
	public ItemTea() {
		super(6, 2F, false);
		setUnlocalizedName(Reference.StatuesItems.TEA.getUnlocalisedName());
		setRegistryName(Reference.StatuesItems.TEA.getRegistryName());
		setCreativeTab(CreativeTabs.FOOD);
		setCreativeTab(Statues.tabStatues);
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity)
    {
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = entity instanceof EntityPlayer ? (EntityPlayer)entity : null;
	
			
			entityplayer.getFoodStats().addStats(this, stack);
			this.onFoodEaten(stack, worldIn, entityplayer);
	        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
	        {
	            stack.shrink(1);
	        }
	        
	        if (entityplayer instanceof EntityPlayerMP)
	        {
	            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
	        }
		    
	        if (entityplayer != null)
	        {
	            entityplayer.addStat(StatList.getObjectUseStats(this));
	        }
	        
	        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
	        {
	            if (stack.isEmpty())
	            {
	                return new ItemStack(StatuesItems.cup);
	            }
	
	            if (entityplayer != null)
	            {
	                entityplayer.inventory.addItemStackToInventory(new ItemStack(StatuesItems.cup));
	            }
	        }
		}

        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        	tooltip.add(TextFormatting.GOLD + I18n.translateToLocal("tea.info"));
    }
}
