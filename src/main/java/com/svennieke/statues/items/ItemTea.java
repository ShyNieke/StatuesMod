package com.svennieke.statues.items;

import java.util.List;

import com.svennieke.statues.Statues;
import com.svennieke.statues.init.StatuesItems;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemTea extends ItemFood {
	public ItemTea(Item.Properties builder) {
		super(6, 2F, false, builder.maxStackSize(1).group(ItemGroup.FOOD).group(Statues.tabStatues));
//		setUnlocalizedName(Reference.MOD_PREFIX + unlocalised);
//		setCreativeTab(Statues.tabStatues);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity)
    {
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = entity instanceof EntityPlayer ? (EntityPlayer)entity : null;
	
			
			entityplayer.getFoodStats().addStats(this, stack);
			this.onFoodEaten(stack, worldIn, entityplayer);
	        if (entityplayer == null || !entityplayer.abilities.isCreativeMode)
	        {
	            stack.shrink(1);
	        }
	        
	        if (entityplayer instanceof EntityPlayerMP)
	        {
	            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
	        }
		    
	        if (entityplayer != null)
	        {
	            entityplayer.addStat(StatList.ITEM_USED.get(this));
	        }
	        
	        if (entityplayer == null || !entityplayer.abilities.isCreativeMode)
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
	public int getUseDuration(ItemStack stack)
    {
        return 32;
    }
	
	@Override
	public EnumAction getUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("statues.tea.info").applyTextStyle(TextFormatting.GOLD));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
