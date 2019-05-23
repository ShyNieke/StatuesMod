package com.svennieke.statues.handler;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.svennieke.statues.Statues;
import com.svennieke.statues.init.StatuesBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class MagicHandler {

	@SubscribeEvent
	public void worldTick(WorldTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
			return;

		if(!event.world.isRemote)
		{
			World world = event.world;
			ArrayList<Entity> entityList = new ArrayList<>(world.loadedEntityList);
			for(int i = 0; i < entityList.size(); i++)
			{
				Entity entity = entityList.get(i);
				if(entity instanceof EntityItem)
				{
					Statues.logger.info(entity.getClass() + " " + entity.getName());
					EntityItem itemE = (EntityItem)entity;

					if(itemE.getItem().getItem().equals(Items.DIAMOND))
					{
						AxisAlignedBB bb = itemE.getEntityBoundingBox().contract(0.1, 0.1, 0.1);
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

						CampfireData data = properStatuesFound(world, lavaPos.up(), StatuesBlocks.player_statue, StatuesBlocks.creeper_statue[0]);

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

							world.setBlockState(lavaPos, StatuesBlocks.campfire_statue[0].getDefaultState());
							world.spawnParticle(EnumParticleTypes.FLAME, lavaPos.down().getX(), lavaPos.down().getY(), lavaPos.down().getZ(), 0.0D, 0.0D, 0.0D, new int[0]);
							itemE.setDead();
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

	public CampfireData properStatuesFound(World worldIn, BlockPos pos, Block block, Block block1)
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

	public class CampfireData
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
