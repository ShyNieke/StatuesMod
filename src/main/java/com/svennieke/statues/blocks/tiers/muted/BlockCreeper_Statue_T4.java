package com.svennieke.statues.blocks.tiers.muted;

import com.svennieke.statues.Reference;
import com.svennieke.statues.blocks.tiers.base.BlockCreeper_Statue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCreeper_Statue_T4 extends BlockCreeper_Statue{
		
	private final String TAG_COOLDOWN = "cooldown";
	public static double cooldown;
	
	public BlockCreeper_Statue_T4() {
		super();
		setUnlocalizedName(Reference.StatuesBlocks.CREEPERSTATUET4.getUnlocalisedName());
		setRegistryName(Reference.StatuesBlocks.CREEPERSTATUET4.getRegistryName());

	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		cooldown = Math.random();
		if (cooldown < 0.15) cooldown = StatueBehavior(this, playerIn, worldIn, pos);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
	
	public int StatueBehavior(BlockCreeper_Statue_T4 statue, EntityPlayer playerIn, World worldIn, BlockPos pos) {
		if (cooldown < 0.01){
			playerIn.dropItem(new ItemStack(Items.GUNPOWDER, 1), true);
		}
		else
		if (!worldIn.isRemote)
		{
        EntityCreeper entitycreeper = new EntityCreeper(worldIn);
        entitycreeper.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        NBTTagCompound tag = new NBTTagCompound();
        entitycreeper.writeEntityToNBT(tag);
        tag.setShort("ExplosionRadius", (short)0);
        tag.setShort("Fuse", (short)0);
        entitycreeper.readEntityFromNBT(tag);
        worldIn.spawnEntity(entitycreeper);
        entitycreeper.spawnExplosionParticle();
		}
		
		return 0;
	}
}
