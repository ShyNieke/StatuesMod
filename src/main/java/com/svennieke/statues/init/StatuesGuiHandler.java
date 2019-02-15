package com.svennieke.statues.init;

import com.svennieke.statues.renderer.gui.GuiShulkerStatue;
import com.svennieke.statues.tileentity.ShulkerStatueTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class StatuesGuiHandler{

	public static GuiScreen openShulkerGui(FMLPlayMessages.OpenContainer packetData){
        EntityPlayer player = Minecraft.getInstance().player;
		TileEntity tile = player.world.getTileEntity(player.getPosition());

        return new GuiShulkerStatue(player.inventory, (ShulkerStatueTileEntity) tile);
    }
}
