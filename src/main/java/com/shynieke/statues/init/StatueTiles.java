package com.shynieke.statues.init;

import com.google.common.base.Supplier;
import com.shynieke.statues.Reference;
import com.shynieke.statues.tiles.PlayerTile;
import com.shynieke.statues.tiles.StatueTile;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class StatueTiles {

	@ObjectHolder(Reference.MOD_ID + "statue_tile")
	public static TileEntityType<?> STATUE;

	@ObjectHolder(Reference.MOD_ID + "player_tile")
	public static TileEntityType<?> PLAYER;

	@SubscribeEvent
	public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event)
	{
		event.getRegistry().register(TileEntityType.Builder.create((Supplier<TileEntity>) StatueTile::new, StatueBlocks.STATUES.toArray(new Block[StatueBlocks.STATUES.size()])).build(null).setRegistryName(Reference.MOD_ID + "statue_tile"));
		event.getRegistry().register(TileEntityType.Builder.create((Supplier<TileEntity>) PlayerTile::new, StatueBlocks.player_statue).build(null).setRegistryName(Reference.MOD_ID + "player_tile"));
	}
}
