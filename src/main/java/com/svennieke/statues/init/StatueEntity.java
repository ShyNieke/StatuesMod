package com.svennieke.statues.init;

import com.svennieke.statues.Reference;
import com.svennieke.statues.Statues;
import com.svennieke.statues.entity.EntityStatueBat;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StatueEntity {
	
	public static void register() {		
		EntityRegistry.registerModEntity(EntityStatueBat.class, "StatueBat", 0, Statues.instance, 80, 3, true, 3421236, 3556687);
		System.out.println("Registered Statues Bat");
		
		GameRegistry.registerTileEntity(StatueTileEntity.class, Reference.MOD_ID + "_tileentity");
		GameRegistry.registerTileEntity(PlayerStatueTileEntity.class, Reference.MOD_ID + "_playertileentity");
	}
}
