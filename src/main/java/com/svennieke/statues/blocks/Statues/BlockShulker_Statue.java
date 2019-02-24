package com.svennieke.statues.blocks.Statues;

import com.svennieke.statues.blocks.IStatue;
import com.svennieke.statues.blocks.StatueBase.BlockShulker;
import com.svennieke.statues.entity.fakeentity.FakeShulker;
import com.svennieke.statues.tileentity.ShulkerStatueTileEntity;
import com.svennieke.statues.tileentity.StatueTileEntity;
import com.svennieke.statues.tileentity.container.ContainerShulkerStatue;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
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
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class BlockShulker_Statue extends BlockShulker implements IStatue{
	
	private int TIER;
	
	public BlockShulker_Statue(Block.Properties builder) {
		super(builder);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Block setTier(int tier)
	{
		this.TIER = tier;
		return this;
	}
	
	@Override
	public int getTier()
	{
		return this.TIER;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		if (this.TIER >= 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		if (this.TIER == 2)
			return new StatueTileEntity(this.TIER);
		else if(this.TIER >= 3)
	        return new ShulkerStatueTileEntity(this.TIER);
		else
			return null;
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(this.TIER >= 2) {
			if (!worldIn.isRemote) {
				TileEntity tile = worldIn.getTileEntity(pos);

				if(this.TIER == 2) {
					if(tile instanceof StatueTileEntity)
					{
						StatueTileEntity statueTile = (StatueTileEntity)tile;

						if(statueTile.getTier() != this.TIER){
							statueTile.setTier(this.TIER);
						}

						statueTile.PlaySound(SoundEvents.ENTITY_SHULKER_AMBIENT, pos, worldIn);
					}
				}

				if(this.TIER >= 3) {
					if(tile instanceof ShulkerStatueTileEntity)
					{
						ShulkerStatueTileEntity shulkerTile = (ShulkerStatueTileEntity)tile;

						if(shulkerTile.getTier() != this.TIER){
							shulkerTile.setTier(this.TIER);
						}

						shulkerTile.ShootBullet(pos, worldIn, playerIn, ((EnumFacing)state.get(HORIZONTAL_FACING)).getAxis());
						shulkerTile.PlaySound(SoundEvents.ENTITY_SHULKER_AMBIENT, pos, worldIn);

						shulkerTile.holidayCheck(new FakeShulker(worldIn), worldIn, pos, false);

						NetworkHooks.openGui((EntityPlayerMP) playerIn, new ShulkerInterface(worldIn, pos, this), (buf) -> buf.writeBlockPos(shulkerTile.getPos()));
					}
				}
				return true;
			}
			return true;
		}
		else
			return super.onBlockActivated(state, worldIn, pos, playerIn, hand, side, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof ShulkerStatueTileEntity)
        {
			ShulkerStatueTileEntity shulkerTile = (ShulkerStatueTileEntity)tile;
			shulkerTile.setDestroyedByCreativePlayer(player.abilities.isCreativeMode);
			shulkerTile.fillWithLoot(player);
        }

	      super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (stack.hasDisplayName())
        {
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof ShulkerStatueTileEntity)
			{
				ShulkerStatueTileEntity shulkerTile = (ShulkerStatueTileEntity)tile;
				shulkerTile.setCustomName(stack.getDisplayName());
            }
        }
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof ShulkerStatueTileEntity)
		{
			ShulkerStatueTileEntity shulkerTile = (ShulkerStatueTileEntity)worldIn.getTileEntity(pos);

            if (!shulkerTile.isCleared() && shulkerTile.shouldDrop())
            {
                ItemStack itemstack = new ItemStack(this.asItem());
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound.setTag("BlockEntityTag", (shulkerTile.saveToNbt(nbttagcompound1)));
                itemstack.setTag(nbttagcompound);

                if (shulkerTile.hasCustomName())
                {
                    itemstack.setDisplayName(shulkerTile.getName());
					shulkerTile.setCustomName(new TextComponentString(""));
                }

                spawnAsEntity(worldIn, pos, itemstack);
            }

            worldIn.updateComparatorOutputLevel(pos, state.getBlock());
        }
        
		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip,
			ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		if(this.TIER >= 3)
        {
			NBTTagCompound nbttagcompound = stack.getChildTag("BlockEntityTag");
			if (nbttagcompound != null) {
				if (nbttagcompound.contains("LootTable", 8)) {
					tooltip.add(new TextComponentString("???????"));
				}

				if (nbttagcompound.contains("Items", 9)) {
					NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
					ItemStackHelper.loadAllItems(nbttagcompound, nonnulllist);
					int i = 0;
					int j = 0;
					Iterator iterator = nonnulllist.iterator();

					while(iterator.hasNext()) {
						ItemStack itemstack = (ItemStack)iterator.next();
						if (!itemstack.isEmpty()) {
							++j;
							if (i <= 4) {
								++i;
								ITextComponent itextcomponent = itemstack.getDisplayName().createCopy();
								itextcomponent.appendText(" x").appendText(String.valueOf(itemstack.getCount()));
								tooltip.add(itextcomponent);
							}
						}
					}

					if (j - i > 0) {
						tooltip.add((new TextComponentTranslation("container.shulkerBox.more", new Object[]{j - i})).applyTextStyle(TextFormatting.ITALIC));
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
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos,
			EntityPlayer player) {

		if (player.abilities.isCreativeMode && GuiScreen.isCtrlKeyDown()) {
			ItemStack stack = new ItemStack(asItem(), 1);
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof ShulkerStatueTileEntity)
			{
				ShulkerStatueTileEntity tileShulker = (ShulkerStatueTileEntity)world.getTileEntity(pos);
				NBTTagCompound tagCompound = tileShulker.saveToNbt(new NBTTagCompound());
				if (!tagCompound.isEmpty()) {
					stack.setTagInfo("BlockEntityTag", tagCompound);
				}

				return stack;
			}
			else {
				return new ItemStack(asItem(), 1);
			}
		}
		else {
			return new ItemStack(asItem(), 1);
		}
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (te instanceof INameable && ((INameable)te).hasCustomName())
        {
            player.addStat(StatList.BLOCK_MINED.get(this));
            player.addExhaustion(0.005F);

            if (worldIn.isRemote)
            {
                return;
            }

            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            Item item = this.getItemDropped(state, worldIn, pos, i).asItem();

            if (item == Items.AIR)
            {
                return;
            }

            @SuppressWarnings("deprecation")
			ItemStack itemstack = new ItemStack(item, this.quantityDropped(state, worldIn.rand));
            itemstack.setDisplayName(((INameable)te).getName());
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }

	@SuppressWarnings("deprecation")
	@Override
	public void dropBlockAsItemWithChance(IBlockState state, World worldIn, BlockPos pos, float chancePerItem,
			int fortune) {
		if(this.TIER <= 2)
		{
			super.dropBlockAsItemWithChance(state, worldIn, pos, chancePerItem, fortune);
		}
	}
	
	public static class ShulkerInterface implements IInteractionObject {
	    private final World world;
	    private final BlockPos position;
	    private final Block block;
	      
        public ShulkerInterface(World worldIn, BlockPos pos, Block block) {
            this.world = worldIn;
            this.position = pos;
            this.block = block;
        }
        
		@Override
		public ITextComponent getName() {
	        return new TextComponentTranslation(block.getTranslationKey());
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Override
		public ITextComponent getCustomName() {
	        return null;
		}

		@Override
		public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
            return new ContainerShulkerStatue(playerInventory, (ShulkerStatueTileEntity) world.getTileEntity(position), playerIn);
		}

		@Override
		public String getGuiID() {
			return "statues:shulker_statue";
		}
		
	}
}
