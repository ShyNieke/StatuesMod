package com.shynieke.statues.init;

import com.shynieke.statues.renderer.gui.GuiShulkerStatue;
import com.shynieke.statues.tileentity.ShulkerStatueTileEntity;
import com.shynieke.statues.tileentity.container.ContainerShulkerStatue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class StatuesGuiHandler implements IGuiHandler {

	public static final int SHULKER_BOX = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		if (ID == SHULKER_BOX && tile != null && tile instanceof ShulkerStatueTileEntity)
        {
            return new ContainerShulkerStatue(player.inventory, (ShulkerStatueTileEntity) tile, player);
        }
        else
        {
            return null;
        }
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		if (ID == SHULKER_BOX && tile != null && tile instanceof ShulkerStatueTileEntity)
        {
            return new GuiShulkerStatue(player.inventory, (ShulkerStatueTileEntity) tile);
        }
        else
        {
            return null;
        }
	}

}
