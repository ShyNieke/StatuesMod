package com.svennieke.statues.tileentity;

import com.mojang.authlib.GameProfile;
import com.svennieke.statues.blocks.Statues.BlockShulker_Statue;
import com.svennieke.statues.init.StatuesTileTypes;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;
import java.util.stream.IntStream;

public class ShulkerStatueTileEntity extends TileEntityLockableLoot implements ITickable, ISidedInventory, iStatueBehaviors{

	private static final int[] SLOTS = IntStream.range(0, 18).toArray();
	private NonNullList<ItemStack> items = NonNullList.withSize(18, ItemStack.EMPTY);
    private boolean hasBeenCleared;
    private int openCount;
    private boolean destroyedByCreativePlayer;
	private int tier;
	private static FakePlayer fakeStatue = null;

    public ShulkerStatueTileEntity() {
    	super(StatuesTileTypes.SHULKER_STATUE);
        this.tier = 3;
	}

	public ShulkerStatueTileEntity(int tier) {
		super(StatuesTileTypes.SHULKER_STATUE);
		this.tier = tier;
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
		if (!player.isSpectator()) {
			if (this.openCount < 0) {
				this.openCount = 0;
			}

			++this.openCount;
			this.world.addBlockEvent(this.pos, this.getBlockState().getBlock(), 1, this.openCount);
			if (this.openCount == 1) {
				this.world.playSound((EntityPlayer)null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
    }
    
    @Override
    public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator()) {
			--this.openCount;
			this.world.addBlockEvent(this.pos, this.getBlockState().getBlock(), 1, this.openCount);
			if (this.openCount <= 0) {
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
		for (ItemStack itemstack : this.items){
            if (!itemstack.isEmpty()){
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
	public ITextComponent getName() {
		ITextComponent itextcomponent = this.getCustomName();
		return (ITextComponent)(itextcomponent != null ? itextcomponent : new TextComponentTranslation("statues:container.shulkerStatue", new Object[0]));
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
	public void tick() { }

	@Override
	protected NonNullList<ItemStack> getItems() {
        return this.items;
	}

	
	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);
        this.loadFromNbt(compound);
	}
	
	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		super.write(compound);
        return this.saveToNbt(compound);
	}
	
	public void loadFromNbt(NBTTagCompound compound)
    {
        this.items = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        tier = compound.getInt("StatueTier");

        if (!this.checkLootAndRead(compound) && compound.hasKey("Items"))
        {
            ItemStackHelper.loadAllItems(compound, this.items);
        }

        if (compound.contains("CustomName", 8)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    public NBTTagCompound saveToNbt(NBTTagCompound compound)
    {
    	if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items, false);
        }
        
        compound.setInt("StatueTier", tier);

        ITextComponent itextcomponent = this.getCustomName();
        if (itextcomponent != null) {
           compound.setString("CustomName", ITextComponent.Serializer.toJson(itextcomponent));
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
    
    public static FakePlayer getFakePlayer(World worldIn) {
        if (fakeStatue == null) {
        	fakeStatue = FakePlayerFactory.get(worldIn.getServer().getWorld(DimensionType.OVERWORLD), new GameProfile(new UUID(123, 132), "Shulker Statue"));
        }
        return fakeStatue;
	}
    
    public void ShootBullet(BlockPos pos, World worldIn, EntityPlayer entity, EnumFacing.Axis facing) {
    	EntityPlayer player = (EntityPlayer)entity;
		FakePlayer fakePlayer = getFakePlayer(worldIn);
		
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

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;
	}
}
