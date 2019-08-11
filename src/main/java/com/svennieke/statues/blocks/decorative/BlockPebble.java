package com.svennieke.statues.blocks.decorative;

import com.svennieke.statues.Statues;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPebble extends BlockFalling{

	public BlockPebble(String unlocalised, String registry) {
		super(Material.SAND);
		setTranslationKey(unlocalised);
		setRegistryName(registry);
		setHardness(0.6F);
		setSoundType(SoundType.GROUND);
		this.setCreativeTab(Statues.instance.tabStatues);

	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.STONE;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getDustColor(IBlockState state)
    {
        return -8356741;
    }
}
