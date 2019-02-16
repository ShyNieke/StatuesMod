package com.svennieke.statues.handler;

import com.svennieke.statues.Reference;
import com.svennieke.statues.init.StatuesBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MagicHandler {
	
	@SubscribeEvent
	public static void worldTick(WorldTickEvent event)
	{
		World world = event.world;
		if(world != null && !world.isRemote)
		{
			ArrayList<Entity> entityList = new ArrayList<>(world.loadedEntityList);
			for(Entity entity : entityList)
			{
				if(entity instanceof EntityItem)
				{
					EntityItem itemE = (EntityItem)entity;
					
					if(itemE.getItem().getItem().equals(Items.DIAMOND))
					{
						//AxisAlignedBB bb = itemE.getBoundingBox().contract(0.1, 0.1, 0.1);
						BlockPos lavaPos = itemE.getPosition();
						
						Boolean lavaFound = false;
						Boolean requirementsFound = false;

						if(world.getBlockState(itemE.getPosition()).getBlock() == Blocks.LAVA)
						{
							lavaFound = true;
							lavaPos = itemE.getPosition();
						}
						else if(world.getBlockState(itemE.getPosition().down()).getBlock() == Blocks.LAVA)
						{
							lavaFound = true;
							lavaPos = itemE.getPosition().down();
						}
						else
						{
							lavaFound = false;
						}
						
						CampfireData data = properStatuesFound(world, lavaPos.up(), StatuesBlocks.player_statue, StatuesBlocks.creeper_statue_t1);
						if(data.getBool() && lavaFound)
						{
							requirementsFound = true;
						}
						else
						{
							requirementsFound = false;
						}
						
						if(requirementsFound)
						{
							world.setBlockState(lavaPos, Blocks.AIR.getDefaultState());
							world.setBlockState(data.getPos1(), Blocks.AIR.getDefaultState());
							world.setBlockState(data.getPos2(), Blocks.AIR.getDefaultState());
							
							world.setBlockState(lavaPos, StatuesBlocks.campfire_statue_t1.getDefaultState());
							world.spawnParticle(Particles.FLAME, lavaPos.down().getX(), lavaPos.down().getY(), lavaPos.down().getZ(), 0.0D, 0.0D, 0.0D);
							itemE.remove();
						}
						else
						{
							break;
						}
					}
				}
				else
				{
					break;
				}
			}
		}
	}
	
	public static CampfireData properStatuesFound(World worldIn, BlockPos pos, Block block, Block block1)
	{
		CampfireData data = new CampfireData(false, pos, pos);
		
		if(worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == block && worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == block1)
		{
			data = new CampfireData(true, pos.offset(EnumFacing.NORTH), pos.offset(EnumFacing.SOUTH));
		}
		if(worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == block1 && worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == block)
		{
			data = new CampfireData(true, pos.offset(EnumFacing.NORTH), pos.offset(EnumFacing.SOUTH));
		}
		if(worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == block && worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == block1)
		{
			data = new CampfireData(true, pos.offset(EnumFacing.WEST), pos.offset(EnumFacing.EAST));
		}
		if(worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == block1 && worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == block)
		{
			data = new CampfireData(true, pos.offset(EnumFacing.WEST), pos.offset(EnumFacing.EAST));
		}
		
		return data;
	}
	
	public static class CampfireData
	{
		private final Boolean bool;
		private final BlockPos pos1;
		private final BlockPos pos2;
		
		public CampfireData(boolean bool, BlockPos pos1, BlockPos pos2) {
			this.bool = bool;
			this.pos1 = pos1;
			this.pos2 = pos2;
		}
		
		public Boolean getBool() {
			return bool;
		}
		
		public BlockPos getPos1() {
			return pos1;
		}
		
		public BlockPos getPos2() {
			return pos2;
		}
	}
}
