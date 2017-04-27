package com.svennieke.statues.blocks.tiers.functional;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockSheep_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSheepShaven_Statue_T3 extends BlockSheep_Statue{
		
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockSheepShaven_Statue_T3() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.SHEEPSHAVENSTATUET3.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.SHEEPSHAVENSTATUET3.getRegistryName());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
	
	public int StatueBehavior(BlockSheepShaven_Statue_T3 statue, EntityPlayer playerIn) {
		playerIn.playSound(SoundEvents.ENTITY_SHEEP_AMBIENT, 1F, 1F);
		playerIn.dropItem(new ItemStack(Items.MUTTON, 1), true);
		
		return 0;
	}
}
