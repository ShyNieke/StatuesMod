package com.svennieke.statues.items;

import com.svennieke.statues.Statues;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemMooshroomSoup extends ItemFood {
	public static int stackSize = 8; //StatuesConfig.COMMON.soupStack.get();
	
	public ItemMooshroomSoup(Item.Properties builder, int amount, float saturation) {
		super(amount, saturation, false, builder.group(ItemGroup.FOOD).group(Statues.tabStatues).maxStackSize(stackSize));
//		setUnlocalizedName(Reference.MOD_PREFIX + unlocalised);
//		setCreativeTab(Statues.tabStatues);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity)
    {
		if (entity instanceof EntityPlayer && !((EntityPlayer) entity).abilities.isCreativeMode) {
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
