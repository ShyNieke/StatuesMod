package com.svennieke.statues.blocks.tiers.functional;

import java.util.List;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockKingCluck_Statue;
import com.svennieke.statues.init.StatuesItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKingCluck_Statue_T3 extends BlockKingCluck_Statue{
		
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockKingCluck_Statue_T3() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.KINGCLUCKSTATUET3.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.KINGCLUCKSTATUET3.getRegistryName());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	public int StatueBehavior(BlockKingCluck_Statue_T3 statue, EntityPlayer playerIn) {
		playerIn.playSound(SoundEvents.ENTITY_CHICKEN_AMBIENT , 1F, 1F);
		if (cooldown < 0.01){
			playerIn.dropItem(new ItemStack(Items.GOLD_NUGGET, 1), true);
		}
		else
		playerIn.dropItem(new ItemStack(StatuesItems.nugget, 1), true);
		
		return 0;
	}
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        	tooltip.add(TextFormatting.GOLD + I18n.translateToLocal("cluckington.info"));
    }
}
