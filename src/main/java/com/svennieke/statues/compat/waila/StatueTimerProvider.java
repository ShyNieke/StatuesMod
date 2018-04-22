package com.svennieke.statues.compat.waila;

import java.util.List;

import com.svennieke.statues.blocks.iStatue;
import com.svennieke.statues.blocks.Statues.BlockPlayer_Statue;
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
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		final Block block = Block.getBlockFromItem(accessor.getStack().getItem());
		boolean playerStatue = block instanceof BlockPlayer_Statue;
		
		if(playerStatue && !accessor.getTileEntity().isInvalid())
		{
            final PlayerStatueTileEntity tile = (PlayerStatueTileEntity) accessor.getTileEntity();
            
            if (config.getConfig(CONFIG_PLAYER_NAME) && (tile.getPlayerProfile() != null) && !accessor.getTileEntity().isInvalid()) {
            	tooltip.add(I18n.format("tooltip.statues.player.info") + tile.getName());
            }
            if (config.getConfig(CONFIG_PLAYER_UUID) && (tile.getPlayerProfile() != null) && !accessor.getTileEntity().isInvalid()) {
            	tooltip.add(I18n.format("tooltip.statues.player.info2") + tile.getPlayerProfile().getId());
            }
		}
		
		if (block instanceof iStatue && !playerStatue && !accessor.getTileEntity().isInvalid()) {
            final StatueTileEntity tile = (StatueTileEntity) accessor.getTileEntity();

            if (config.getConfig(CONFIG_STATUE_TIMER) && !accessor.getTileEntity().isInvalid()) {
            	if(tile.getCooldown() > 0)
                	tooltip.add(I18n.format("tooltip.statues.timer") + tile.getCooldown());
            	else
                	tooltip.add(I18n.format("tooldown.statues.timer.finished"));
            }
        }
		return tooltip;	
	}
	
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return tooltip;
	}
	
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return list;
	}
}
