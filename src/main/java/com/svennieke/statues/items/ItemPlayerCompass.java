package com.svennieke.statues.items;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPlayerCompass extends Item {
	
	public ItemPlayerCompass(String unlocalised) {
		super();
        setTranslationKey(Reference.MOD_PREFIX + unlocalised);
		setRegistryName("item" + unlocalised);
		setCreativeTab(CreativeTabs.TOOLS);
		setCreativeTab(Statues.tabStatues);
		
		addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            double rotation;
            @SideOnly(Side.CLIENT)
            double rota;
            @SideOnly(Side.CLIENT)
            long lastUpdateTick;
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null && !stack.isOnItemFrame())
                {
                    return 0.0F;
                }
                else
                {
                    boolean flag = entityIn != null;
                    Entity entity = (Entity)(flag ? entityIn : stack.getItemFrame());

                    if (worldIn == null)
                    {
                        worldIn = entity.world;
                    }

                    double d1 = flag ? (double)entity.rotationYaw : this.getFrameRotation((EntityItemFrame)entity);
                    d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                    double d2 = this.getLastLocationToAngle(worldIn, entity, stack) / (Math.PI * 2D);
                    double d0 = 0.5D - (d1 - 0.25D - d2);

                    if (flag)
                    {
                        d0 = this.wobble(worldIn, d0);
                    }

                    return MathHelper.positiveModulo((float)d0, 1.0F);
                }
            }
            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double p_185093_2_)
            {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick)
                {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = p_185093_2_ - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }
            @SideOnly(Side.CLIENT)
            private double getFrameRotation(EntityItemFrame itemFrameIn)
            {
                return (double)MathHelper.wrapDegrees(180 + itemFrameIn.facingDirection.getHorizontalIndex() * 90);
            }
            @SideOnly(Side.CLIENT)
            private double getLastLocationToAngle(World worldIn, Entity entityIn, ItemStack stack)
            {
                if(stack.hasTagCompound())
                {
                	BlockPos lastLocation = worldIn.getSpawnPoint();
                    NBTTagCompound tag = stack.getTagCompound();
                    if (tag.hasKey("lastPlayerLocation"))
                    {
                    	Long location = tag.getLong("lastPlayerLocation");
                    	if(location != 0L)
                    	{
                        	lastLocation = BlockPos.fromLong(location);
                    	}
                    	
                    }
                    return Math.atan2((double)lastLocation.getZ() - entityIn.posZ, (double)lastLocation.getX() - entityIn.posX);
                }
                else
                {
                    return Math.atan2((double)entityIn.getPosition().getZ() - entityIn.posZ, (double)entityIn.getPosition().getX() - entityIn.posX);
                }
            }
        });
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
		if(stack.hasTagCompound())
        {
            NBTTagCompound tag = stack.getTagCompound();
            if (!tag.getString("playerTracking").isEmpty())
            {
                tooltip.add(TextFormatting.GOLD + I18n.format(Reference.MOD_PREFIX + "last.known.location", new Object[] {tag.getString("playerTracking")}));
            }
        }
    }
	
	public boolean hasThreeCommas(String theString)
	{
		int count = 0;
	    for (int i = 0; i < theString.length(); i++) {
	        if (theString.substring(i).startsWith(",")) {
	            count++;
	        }
	    }
	   return count == 3;
	}
}
