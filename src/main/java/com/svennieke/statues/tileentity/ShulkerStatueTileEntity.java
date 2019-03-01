package com.svennieke.statues.tileentity;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.Statues.BlockShulker_Statue;
import com.svennieke.statues.tileentity.container.ContainerShulkerStatue;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class ShulkerStatueTileEntity extends TileEntityLockableLoot implements ITickable, ISidedInventory, iStatueBehaviors{

	private static final int[] SLOTS = new int[18];
    private NonNullList<ItemStack> items;
    private boolean hasBeenCleared;
    private int openCount;
    private float progress;
    private float progressOld;
    private boolean destroyedByCreativePlayer;
	private int tier;
	private static FakePlayer fakeStatue = null;

    public ShulkerStatueTileEntity() {
        this.items = NonNullList.<ItemStack>withSize(18, ItemStack.EMPTY);
        this.tier = 3;
	}
    
    public void setTier(int tier) {
		this.tier = tier;
	}
    
    public int getTier() {
		return this.tier;
	}
    
    @Override
    public void openInventory(EntityPlayer player)
    {
        if (!player.isSpectator())
        {
            if (this.openCount < 0)
            {
                this.openCount = 0;
            }

            ++this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.openCount);

            if (this.openCount == 1 && this.tier == 3)
            {
                this.world.playSound((EntityPlayer)null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }
    }
    
    @Override
    public void closeInventory(EntityPlayer player) {
    	if (!player.isSpectator())
        {
            --this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.openCount);

            if (this.openCount <= 0 && this.tier == 3)
            {
                this.world.playSound((EntityPlayer)null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }
    }
    
	@Override
	public int getSizeInventory() {
        return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
	}

	@Override
	public int getInventoryStackLimit() {
        return 64;
	}

	@Override
	public String getName() {
        return this.hasCustomName() ? this.customName : "statues:container.shulkerStatue";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerShulkerStatue(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		 return "statues:shulker_statue";	
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
        return SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return !(Block.getBlockFromItem(itemStackIn.getItem()) instanceof BlockShulker_Statue);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
	}

	@Override
	public void update() {
		
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
        return this.items;
	}

	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
        this.loadFromNbt(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        return this.saveToNbt(compound);
	}
	
	public void loadFromNbt(NBTTagCompound compound)
    {
        this.items = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        tier = compound.getInteger("StatueTier");

        if (!this.checkLootAndRead(compound) && compound.hasKey("Items", 9))
        {
            ItemStackHelper.loadAllItems(compound, this.items);
        }

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
    }

    public NBTTagCompound saveToNbt(NBTTagCompound compound)
    {
        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.items, false);
        }
        
        compound.setInteger("StatueTier", tier);

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }

        if (!compound.hasKey("Lock") && this.isLocked())
        {
            this.getLockCode().toNBT(compound);
        }

        return compound;
    }
    
    @Override
    public void clear() {
        this.hasBeenCleared = true;
        super.clear();
    }
    
    public boolean isCleared()
    {
        return this.hasBeenCleared;
    }
    
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 10, this.getUpdateTag());
    }
    
    public boolean isDestroyedByCreativePlayer()
    {
        return this.destroyedByCreativePlayer;
    }
    
    public void setDestroyedByCreativePlayer(boolean p_190579_1_)
    {
        this.destroyedByCreativePlayer = p_190579_1_;
    }

    public boolean shouldDrop()
    {
        return !this.isDestroyedByCreativePlayer() || !this.isEmpty() || this.hasCustomName() || this.lootTable != null;
    }
    
    static
    {
        for (int i = 0; i < SLOTS.length; SLOTS[i] = i++)
        {
            ;
        }
    }
    
    @Override
    protected net.minecraftforge.items.IItemHandler createUnSidedHandler()
    {
        return new net.minecraftforge.items.wrapper.SidedInvWrapper(this, EnumFacing.UP);
    }
    
    public void PlaySound(SoundEvent Mobsound, BlockPos pos, World worldIn) {
		if(tier == 2 || tier == 3)
		{
			worldIn.playSound(null, pos, Mobsound, SoundCategory.NEUTRAL, 1F, 1F);
		}
	}
    
    public void holidayCheck(Entity entity, World worldIn, BlockPos pos, boolean isChild) {
		if(tier == 3 || tier == 4)
		{
			LocalDateTime now = LocalDateTime.now();
			if(now.getMonth() == Month.OCTOBER)
			{
				int random = world.rand.nextInt(100);

				if (random < 1)
				{
					if(isChild == true && entity instanceof EntityMob)
					{
						EntityMob mob = (EntityMob)entity;
						mob.isChild();
						mob.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
						worldIn.spawnEntity(mob);
					}
					else
					{
						entity.setPositionAndUpdate(pos.getX(), pos.getY() + 1, pos.getZ());
						worldIn.spawnEntity(entity);
					}
				}
			}
		}
	}
    
    public static FakePlayer getFakePlayer() {
        if (fakeStatue == null) {
        	fakeStatue = FakePlayerFactory.get(DimensionManager.getWorld(0), new GameProfile(new UUID(123, 132), "Shulker Statue"));
        }
        return fakeStatue;
	}
    
    public void ShootBullet(BlockPos pos, World worldIn, EntityPlayer entity, EnumFacing.Axis facing) {
    	EntityPlayer player = (EntityPlayer)entity;
		FakePlayer fakePlayer = getFakePlayer();
		
		int random = worldIn.rand.nextInt(100);
		if(tier == 3 || tier == 4)
		{
			if(random < 50)
			{
				if (!worldIn.isRemote) {
                    EntityShulkerBullet bullet = new EntityShulkerBullet(worldIn, fakePlayer, player, facing);
                    bullet.setPosition(pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ());

                    worldIn.spawnEntity(bullet);
					worldIn.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.NEUTRAL, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
				}
			}
		}
	}
}
