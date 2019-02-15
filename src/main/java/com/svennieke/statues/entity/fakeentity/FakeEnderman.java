package com.svennieke.statues.entity.fakeentity;

import javax.annotation.Nullable;

import com.svennieke.statues.init.StatuesBlocks;
import com.svennieke.statues.init.StatuesEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FakeEnderman extends EntityEnderman implements IFakeEntity{

	private int lifetime;

	public FakeEnderman(World worldIn) {
		super(worldIn);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
	}
	
	@Override
	public EntityType<?> getType() {
		return StatuesEntity.FAKE_ENDERMAN;
	}

	@Override
	@Nullable
    protected ResourceLocation getLootTable()
    {
        return null;
    }
	
	@Override
	protected boolean canDropLoot() {
		return false;
	}
	
	@Override
	public IBlockState func_195405_dq() {
		return StatuesBlocks.pebble.getDefaultState();
	}

	@Override
	public void livingTick()
    {
		if (!this.world.isRemote)
        {
            if (!this.isNoDespawnRequired())
            {
                ++this.lifetime;
            }

            if (this.lifetime >= 2400)
            {
                this.remove();
            }
        }
        
        super.livingTick();
    }

	@Override
	public void writeAdditional(NBTTagCompound compound)
    {
        super.writeAdditional(compound);
        compound.setInt("Lifetime", this.lifetime);
    }

	@Override
	public void readAdditional(NBTTagCompound compound)
    {
        super.readAdditional(compound);
        this.lifetime = compound.getInt("Lifetime");
    }
}
