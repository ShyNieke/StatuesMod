package com.svennieke.statues.init;

import com.google.common.base.Preconditions;
import com.svennieke.statues.Reference;
import com.svennieke.statues.tileentity.PlayerStatueTileEntity;
import com.svennieke.statues.tileentity.ShulkerStatueTileEntity;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MOD_ID)
public class StatuesTileTypes {
	public static final TileEntityType<PlayerStatueTileEntity> PLAYER_STATUE = register("player_statue_tileentity", TileEntityType.Builder.create(PlayerStatueTileEntity::new));
	public static final TileEntityType<ShulkerStatueTileEntity> SHULKER_STATUE = register("shulker_statue_tileentity", TileEntityType.Builder.create(ShulkerStatueTileEntity::new));
	public static final TileEntityType<StatueTileEntity> STATUE = register("statue_tileentity", TileEntityType.Builder.create(StatueTileEntity::new));

	public static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder) {
		TileEntityType<T> entitytype = builder.build(null);
		ResourceLocation name = new ResourceLocation(Reference.MOD_ID, id);
		entitytype.setRegistryName(name);

		return entitytype;
	}
	   
	public static void register(TileEntityType<?> tile, String name, RegistryEvent.Register<TileEntityType<?>> event) {
		Preconditions.checkNotNull(tile, "registryName");
		event.getRegistry().register(tile);
	}
	
	@SubscribeEvent
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event)
	{
		register(PLAYER_STATUE, "player_statue_tileentity", event);
		register(SHULKER_STATUE, "shulker_statue_tileentity", event);
		register(STATUE, "statue_tileentity", event);
	}
}
