package com.svennieke.statues.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.svennieke.statues.config.StatuesConfigGen;
import com.svennieke.statues.init.StatuesItems;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class StatueTileEntity extends TileEntity implements ITickable{
	private int Cooldown;
	private boolean able;
	private int tier;
	
	public StatueTileEntity() {
		this.Cooldown = 0;
	}
	
	public int setTier(int theTier) {
		return this.tier = theTier;
	}
	
	public int getTier() {
		return this.tier;
	}
	
	public void PlaySound(SoundEvent Mobsound, BlockPos pos, World worldIn) {
		if(tier == 2 || tier == 3)
		{
			worldIn.playSound(null, pos, Mobsound, SoundCategory.NEUTRAL, 1F, 1F);
		}
	}

	public void SendInfoMessage(EntityPlayer entity, World worldIn, BlockPos pos) {
			EntityPlayer player = (EntityPlayer)entity;
			int random = worldObj.rand.nextInt(100);
			
			if (!worldObj.isRemote) {
				int idx = new Random().nextInt(StatuesConfigGen.messages.info_messages.length);
				String randommessage = (StatuesConfigGen.messages.info_messages[idx]);
				
				player.addChatMessage(new TextComponentTranslation(randommessage));;
				
			}
	}
	
	public void GiveEffect(BlockPos pos, World worldIn, EntityPlayer entity, Potion effect) {
		if(isAble())
		{
			EntityPlayer player = (EntityPlayer)entity;
			int random = worldObj.rand.nextInt(100);
			if(tier == 3 || tier == 4)
			{
				if(random < 10)
				{
					if (!worldObj.isRemote) {
						if (player.getActivePotionEffect(effect) == null) {
							player.addPotionEffect(new PotionEffect(effect, 20 * 20, 1, true, true));
						}
					}
				}
			}
		}
	}
	
	public void SpecialInteraction(boolean isCow, boolean isMooshroom, boolean isFlood, Block statue, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		EntityFireworkRocket firework = new EntityFireworkRocket(worldIn, (double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ), stack);
			int random = worldObj.rand.nextInt(100);
			
			if(isCow)
			{
				if(!worldIn.isRemote)
				{
					if (stack != null && stack.getItem() == Items.BUCKET)
					{
						worldIn.playSound(null, pos, SoundEvents.ENTITY_COW_MILK, SoundCategory.NEUTRAL, 1F, 1F);
						if (--stack.stackSize == 0)
			            {
			                playerIn.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
			            }
			            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET)))
			            {
			            	playerIn.dropItem(new ItemStack(Items.MILK_BUCKET), false);
			            }
					}
				}
			}
			
			if(isMooshroom)
			{	
				if(!worldIn.isRemote)
				{
					if (stack != null && stack.getItem() == Items.BOWL && !playerIn.capabilities.isCreativeMode)
			        {
						//System.out.println("NO");
			            if (--stack.stackSize == 0)
			            {
			                playerIn.setHeldItem(hand, new ItemStack(StatuesItems.soup));
			            }
			            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(StatuesItems.soup)))
			            {
			            	playerIn.dropItem(new ItemStack(StatuesItems.soup), false);
			            }
			        }
				}
			}
		
			if(isFlood)
			{
				if(!worldIn.isRemote)
					if (stack != null && stack.getItem() == Items.BUCKET)
					{
						worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1F, 1F);
						ItemStack floodbucket = new ItemStack(Items.WATER_BUCKET); 
						floodbucket.setStackDisplayName("The Flood");
						
						if (--stack.stackSize == 0)
			            {
							playerIn.setHeldItem(hand, floodbucket);
							floodbucket.setStackDisplayName("The Flood");
			            }
			            else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
			            {
			            	playerIn.entityDropItem(floodbucket, 0);
			            	floodbucket.setStackDisplayName("The Flood");
			            }
					}
				
					if (random < 50){
						 worldIn.spawnEntityInWorld(firework);
					}	
			}
		}
	
	public void StatueBehavior(@Nullable ItemStack stack1, @Nullable ItemStack stack2, @Nullable ItemStack stack3, 
			EntityLiving spawnableentity, boolean spawnEntity, boolean isCreeper,Block statue, EntityPlayer playerIn, 
			World worldIn, BlockPos pos) {
		
		if(isAble()) 
		{
			int random = worldObj.rand.nextInt(100);
			if(tier == 3 || tier == 4)
			{
				if (random < 100 && stack1 != null){playerIn.dropItem(stack1, true);}
				
				if (random < 10 && spawnEntity)
				{
					if (!worldIn.isRemote)
					{
					spawnableentity.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
			        NBTTagCompound tag = new NBTTagCompound();
			        spawnableentity.writeEntityToNBT(tag);
			        
				        if(isCreeper)
				        {
				        tag.setShort("ExplosionRadius", (short)0);
				        tag.setShort("Fuse", (short)0);
				        }
			        
			        spawnableentity.readEntityFromNBT(tag);
			        worldIn.spawnEntityInWorld(spawnableentity);
			        
				        if(isCreeper)
				        {
				        spawnableentity.spawnExplosionParticle();
				    	}
			        
					}
				}
				
				if(stack2 != null){
					if(random < 50)
					{
						playerIn.dropItem(stack2, true);
					}
				}
				
				if(stack3 != null){
					if(random < 10)
					{
						playerIn.dropItem(stack3, true);
					}
				}
			}
			setAble(false);
		}
	}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.tier = compound.getInteger("StatueTier");
        this.Cooldown = compound.getInteger("StatueCooldown");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("StatueCooldown", this.Cooldown);
        compound.setInteger("StatueTier", this.tier);
        return compound;
    }
    @Override
    public void update(){
	    if (!worldObj.isRemote) {
	    	if (isAble() == false)
	    	{
	    		int cooldown = this.Cooldown + 1;
            	//Statues.logger.info(cooldown);
	            this.Cooldown = cooldown;
	            if(cooldown == (StatuesConfigGen.general.InteractionTimer * 20)){
	                this.Cooldown = 0;
	                setAble(true);
	                System.out.println(isAble());
	            }
	            else
	            {
	            	//Statues.logger.info(isAble());
	            	setAble(false);
	            }
	    	}
	    }
    }
    
    public boolean setAble(Boolean isable)
    {
    	return this.able = isable;
    }
    
    public boolean isAble()
    {
    	return this.able;
    }
}