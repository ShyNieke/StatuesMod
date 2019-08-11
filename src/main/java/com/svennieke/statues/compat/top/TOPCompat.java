package com.svennieke.statues.compat.top;

import com.google.common.base.Function;
import com.mojang.authlib.GameProfile;
import com.svennieke.statues.Reference;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.tileentity.StatueTileEntity;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TOPCompat {
	private static boolean registered;

    public static void register() {
        if (registered)
            return;
        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.svennieke.statues.compat.top.TOPCompat$GetTheOneProbe");
    }
    
    public static final class GetTheOneProbe implements Function<ITheOneProbe, Void> {

		@Override
		public Void apply(ITheOneProbe input) {
			input.registerProvider(new TOPCompat.StatuesInfo());
			return null;
		}
    }
    
    public static final class StatuesInfo implements IProbeInfoProvider {

		@Override
		public String getID() {
			return Reference.MOD_ID;
		}

		@Override
		public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
				IBlockState blockState, IProbeHitData data) {
			final TileEntity te = world.getTileEntity(data.getPos());
			final Block block = world.getBlockState(data.getPos()).getBlock();
			if(te != null){
				if(te instanceof StatueTileEntity)
				{
					StatueTileEntity tile = (StatueTileEntity) te;
					String blockName = block.getRegistryName().toString();
		            if(blockName.contains("t3") || blockName.contains("t4") || blockName.contains("t5"))
		            {
			            int cooldown = tile.getCooldown();
			            int cooldownMax = tile.getCooldownMax();
	                	boolean able = tile.isStatueAble();
	                	
	                	int cooldownProgress = (int) ((cooldown * 100.0f) / cooldownMax);

	                	if(able == true)
	                		probeInfo.text(I18n.format("tooldown.statues.timer.finished"));
	                	if(able == false)
	                		probeInfo.text(I18n.format("tooltip.statues.timer") + cooldownProgress + "%");
		            }
				}
				else if(te instanceof PlayerStatueTileEntity)
				{
					PlayerStatueTileEntity tile = (PlayerStatueTileEntity) te;
		            
		            GameProfile profile = tile.playerProfile;
		            String name = tile.getName();
		            probeInfo.text(TextFormatting.GRAY + I18n.format("tooltip.statues.player.info") + TextFormatting.GOLD + name);
				}
			}
		}
    }
}
