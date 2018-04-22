package com.svennieke.statues.compat.waila;

import java.util.List;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.tileentity.StatueTileEntity;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@WailaPlugin
public class StatueTimerProvider implements IWailaDataProvider, IWailaPlugin {

	private static final String CONFIG_PLAYER_NAME = "statues.player.name";
	private static final String CONFIG_PLAYER_UUID = "statues.player.data";
	private static final String CONFIG_STATUE_TIMER = "statues.statue.timer";
	
	@Override
	public void register(IWailaRegistrar registrar) {
		for(Block block : StatuesBlocks.BLOCKS)
		{
			registrar.registerBodyProvider(this, block.getClass());
		}
		
		registrar.addConfig("Statues", CONFIG_PLAYER_NAME);
		registrar.addConfig("Statues", CONFIG_PLAYER_UUID);
		registrar.addConfig("Statues", CONFIG_STATUE_TIMER);
	}
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world,
			BlockPos pos) {
		te.writeToNBT(tag);
		return tag;
	}
	
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {		
		TileEntity te = accessor.getTileEntity();
		final NBTTagCompound nbt = accessor.getNBTData();
		
		if(te instanceof PlayerStatueTileEntity && !accessor.getTileEntity().isInvalid())
		{
            PlayerStatueTileEntity tile = (PlayerStatueTileEntity) te;
            
            GameProfile profile = NBTUtil.readGameProfileFromNBT(accessor.getNBTData().getCompoundTag("PlayerProfile"));
            String name = accessor.getNBTData().getString("PlayerName");
            
            if (config.getConfig(CONFIG_PLAYER_NAME) && (profile!= null) && !accessor.getTileEntity().isInvalid()) {
            	tooltip.add(I18n.format("tooltip.statues.player.info") + name);
            }
            if (config.getConfig(CONFIG_PLAYER_UUID) && (profile != null) && !accessor.getTileEntity().isInvalid()) {
            	tooltip.add(I18n.format("tooltip.statues.player.info2") + profile.getId());
            }
		}
		
		if (te instanceof StatueTileEntity && !accessor.getTileEntity().isInvalid()) {
            StatueTileEntity tile = (StatueTileEntity) te;
            
            //System.out.println(nbt);
            int cooldown = nbt.getInteger("StatueCooldown");
            int maxCooldown = nbt.getInteger("StatueMaxCooldown");
            int cooldownProgress = (int) ((cooldown * 100.0f) / maxCooldown);
            
            if (config.getConfig(CONFIG_STATUE_TIMER) && !accessor.getTileEntity().isInvalid()) {
            	if(nbt.getBoolean("StatueAble"))
            		tooltip.add(I18n.format("tooldown.statues.timer.finished"));
            	else
            		tooltip.add(I18n.format("tooltip.statues.timer") + cooldownProgress + "%");
            }
        }
		return tooltip;	
	}
}
