package com.svennieke.statues.blocks.StatueBase;

import java.util.List;

import com.svennieke.statues.blocks.BaseBlock.BaseCutout;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;

public class BlockKingCluck extends BaseCutout{
	
	private static final VoxelShape BOUNDING_BOX = Block.makeCuboidShape(6.5, 0, 6, 9.5, 4.5, 9);
	
	public BlockKingCluck(Block.Properties builder) {
		super(builder.sound(SoundType.CLOTH));
//		this.setCreativeTab(Statues.instance.tabStatues);
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }
    
//    @Override
//    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, VoxelShape entityBox, List<VoxelShape> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
//    {
//    	addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
//    }
	
    @Override
    public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip,
    		ITooltipFlag flagIn) {
    	tooltip.add(new TextComponentString(TextFormatting.GOLD + I18n.format("cluckington.info")));
    	super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
