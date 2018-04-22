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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

@WailaPlugin
public class StatueTimerProvider implements IWailaDataProvider, IWailaPlugin {

	private static final String CONFIG_PLAYER_NAME = "statues.player.name";
	private static final String CONFIG_PLAYER_UUID = "statues.player.data";
	private static final String CONFIG_STATUE_TIMER = "statues.statue.timer";
	public static StatueProgressInfo info = null;
	
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
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {		
		TileEntity te = accessor.getTileEntity();
		
		if(te instanceof PlayerStatueTileEntity && !accessor.getTileEntity().isInvalid())
		{
            PlayerStatueTileEntity tile = (PlayerStatueTileEntity) te;
            
            GameProfile profile = tile.playerProfile;
            String name = tile.BlockName;
            
            if (config.getConfig(CONFIG_PLAYER_NAME) && (profile != null) && !accessor.getTileEntity().isInvalid()) {
            	tooltip.add(I18n.format("tooltip.statues.player.info") + name);
            }
            if (config.getConfig(CONFIG_PLAYER_UUID) && (profile != null) && !accessor.getTileEntity().isInvalid()) {
            	tooltip.add(I18n.format("tooltip.statues.player.info2") + profile.getId());
            }
		}
		
		if (te instanceof StatueTileEntity && !accessor.getTileEntity().isInvalid()) {
            StatueTileEntity tile = (StatueTileEntity) te;
            
            if(info != null)
            {
            	if(tile.getPos().equals(info.getPosition()))
            	{
            		int cooldown = info.getCooldown();
                	int cooldownMax = info.getCooldownMax();
                	boolean able = info.isAble();
                	
                	int cooldownProgress = (int) ((cooldown * 100.0f) / cooldownMax);
                    
                    if (config.getConfig(CONFIG_STATUE_TIMER) && !accessor.getTileEntity().isInvalid()) {
                    	if(able == true)
                    		tooltip.add(I18n.format("tooldown.statues.timer.finished"));
                    	if(able == false)
                    		tooltip.add(I18n.format("tooltip.statues.timer") + cooldownProgress + "%");
                    }
            	}
            	else
            	{
            		tooltip = setStatueInfo(tile, tooltip, accessor, config);
            	}
            }
            else
            {
            	tooltip = setStatueInfo(tile, tooltip, accessor, config);
            }
            
        }
		return tooltip;	
	}
	public List<String> setStatueInfo(StatueTileEntity tile, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		int cooldown = tile.getCooldown();
		int cooldownMax = tile.getCooldownMax();
    	boolean able = tile.isStatueAble();
        
    	int cooldownProgress = (int) ((cooldown * 100.0f) / cooldownMax);
        
        if (config.getConfig(CONFIG_STATUE_TIMER) && !accessor.getTileEntity().isInvalid()) {
        	if(able == true)
        		tooltip.add(I18n.format("tooldown.statues.timer.finished"));
        	if(able == false)
        		tooltip.add(I18n.format("tooltip.statues.timer") + cooldownProgress + "%");
        }
        
		return tooltip;
	}
	
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return tooltip;
	}
	
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return tooltip;
	}
}
