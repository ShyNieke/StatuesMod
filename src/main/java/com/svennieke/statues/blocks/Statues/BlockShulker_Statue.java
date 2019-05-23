package com.svennieke.statues.blocks.Statues;

import java.util.List;

import javax.annotation.Nullable;

import com.svennieke.statues.Statues;
import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.StatueBase.BlockShulker;
import com.svennieke.statues.entity.fakeentity.FakeShulker;
import com.svennieke.statues.init.StatuesGuiHandler;
import com.svennieke.statues.tileentity.ShulkerStatueTileEntity;
import com.svennieke.statues.tileentity.StatueTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockShulker_Statue extends BlockShulker implements IStatue, ITileEntityProvider{
	
	private int TIER;
	
	public BlockShulker_Statue(String unlocalised) {
		super();
		setTranslationKey(unlocalised);
	}
	
	@Override
	public Block setTier(int tier)
	{
		this.TIER = tier;
		setTranslationKey(super.getTranslationKey().replace("tile.", "") + (tier > 1 ? "t" + tier : ""));
		setRegistryName("block" + super.getTranslationKey().replace("tile.", ""));
		return this;
	}
	
	@Override
	public int getTier()
	{
		return this.TIER;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (this.TIER == 2)
			return new StatueTileEntity(this.TIER);
		else if(this.TIER >= 3)
	        return new ShulkerStatueTileEntity();
		else
			return null;
	}
	
	private StatueTileEntity getTE(World world, BlockPos pos) {
        return (StatueTileEntity) world.getTileEntity(pos);
    }
	
	private ShulkerStatueTileEntity getShulkerTE(World world, BlockPos pos) {
        return (ShulkerStatueTileEntity) world.getTileEntity(pos);
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(this.TIER >= 2)
		{
	        if (!worldIn.isRemote) {
	        	if(this.TIER == 2)
	        	{
		        	StatueTileEntity tile = getTE(worldIn, pos);
		        	
	        		int statuetier = tile.getTier();
		        	if(statuetier != this.TIER)
		        	{
		        		tile.setTier(this.TIER);
		        	}
		        	
		        	tile.PlaySound(SoundEvents.ENTITY_SHULKER_AMBIENT, pos, worldIn);
	        	}
	        	
	        	if(this.TIER >= 3)
	        	{
		        	ShulkerStatueTileEntity tile = getShulkerTE(worldIn, pos);
		        	
	        		int statuetier = tile.getTier();
		        	if(statuetier != this.TIER)
		        	{
		        		tile.setTier(this.TIER);
		        	}
		        	
		        	tile.ShootBullet(pos, worldIn, playerIn, facing.getAxis());
		        	tile.PlaySound(SoundEvents.ENTITY_SHULKER_AMBIENT, pos, worldIn);
		        	
		        	tile.holidayCheck(new FakeShulker(worldIn), worldIn, pos, false);
			        playerIn.openGui(Statues.instance, StatuesGuiHandler.SHULKER_BOX, worldIn, pos.getX(), pos.getY(), pos.getZ());
		        }
	        }
	        return true;
		}
		else
		return false;
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (worldIn.getTileEntity(pos) instanceof ShulkerStatueTileEntity)
        {
			ShulkerStatueTileEntity tileentityshulkerbox = (ShulkerStatueTileEntity)worldIn.getTileEntity(pos);
            tileentityshulkerbox.setDestroyedByCreativePlayer(player.capabilities.isCreativeMode);
            tileentityshulkerbox.fillWithLoot(player);
        }
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof ShulkerStatueTileEntity)
            {
                ((ShulkerStatueTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof ShulkerStatueTileEntity)
        {
        	ShulkerStatueTileEntity tileentityshulkerstatue = (ShulkerStatueTileEntity)tileentity;

            if (!tileentityshulkerstatue.isCleared() && tileentityshulkerstatue.shouldDrop())
            {
                ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound.setTag("BlockEntityTag", ((ShulkerStatueTileEntity)tileentity).saveToNbt(nbttagcompound1));
                itemstack.setTagCompound(nbttagcompound);

                if (tileentityshulkerstatue.hasCustomName())
                {
                    itemstack.setStackDisplayName(tileentityshulkerstatue.getName());
                    tileentityshulkerstatue.setCustomName("");
                }

                spawnAsEntity(worldIn, pos, itemstack);
            }

            worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        }
        
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);
        if(this.TIER >= 3)
        {
        	NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag", 10))
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");

                if (nbttagcompound1.hasKey("LootTable", 8))
                {
                    tooltip.add("???????");
                }

                if (nbttagcompound1.hasKey("Items", 9))
                {
                    NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems(nbttagcompound1, nonnulllist);
                    int i = 0;
                    int j = 0;

                    for (ItemStack itemstack : nonnulllist)
                    {
                        if (!itemstack.isEmpty())
                        {
                            ++j;

                            if (i <= 4)
                            {
                                ++i;
                                tooltip.add(String.format("%s x%d", itemstack.getDisplayName(), itemstack.getCount()));
                            }
                        }
                    }

                    if (j - i > 0)
                    {
                        tooltip.add(String.format(TextFormatting.ITALIC + I18n.format("statues:container.shulkerStatue.more"), j - i));
                    }
                }
            }
        }
    }
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }
	
	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstoneFromInventory((IInventory)worldIn.getTileEntity(pos));
    }
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		ItemStack itemstack = super.getPickBlock(state, target, world, pos, player);
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof ShulkerStatueTileEntity)
        {
        	ShulkerStatueTileEntity tileentityshulkerbox = (ShulkerStatueTileEntity)te;
            NBTTagCompound nbttagcompound = tileentityshulkerbox.saveToNbt(new NBTTagCompound());

            if (!nbttagcompound.isEmpty())
            {
                itemstack.setTagInfo("BlockEntityTag", nbttagcompound);
            }
        }

        return itemstack;
	}
	
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (te instanceof IWorldNameable && ((IWorldNameable)te).hasCustomName())
        {
            player.addStat(StatList.getBlockStats(this));
            player.addExhaustion(0.005F);

            if (worldIn.isRemote)
            {
                return;
            }

            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Item item = this.getItemDropped(state, worldIn.rand, i);

            if (item == Items.AIR)
            {
                return;
            }

            ItemStack itemstack = new ItemStack(item, this.quantityDropped(worldIn.rand));
            itemstack.setStackDisplayName(((IWorldNameable)te).getName());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if(this.TIER <= 2)
		{
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
		}
	}
}
