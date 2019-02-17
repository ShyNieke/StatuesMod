package com.svennieke.statues.init;

import com.svennieke.statues.renderer.gui.GuiShulkerStatue;
import com.svennieke.statues.tileentity.ShulkerStatueTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class StatuesGuiHandler{

	public static GuiScreen openGui(FMLPlayMessages.OpenContainer packetData){
        BlockPos pos = packetData.getAdditionalData().readBlockPos();
        return new GuiShulkerStatue(Minecraft.getInstance().player.inventory, (ShulkerStatueTileEntity) Minecraft.getInstance().world.getTileEntity(pos));

    }
}
