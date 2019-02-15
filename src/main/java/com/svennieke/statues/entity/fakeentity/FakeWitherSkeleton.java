package com.svennieke.statues.entity.fakeentity;

import javax.annotation.Nullable;

import com.svennieke.statues.init.StatuesEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class FakeWitherSkeleton extends EntityWitherSkeleton implements IFakeEntity{
	
	private int lifetime;

	public FakeWitherSkeleton(World worldIn) {
		super(worldIn);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
	}
	
	@Override
	public EntityType<?> getType() {
		return StatuesEntity.FAKE_WITHER_SKELETON;
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
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData entityLivingData,
			NBTTagCompound itemNbt) {
		IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, entityLivingData, itemNbt);
        this.setCombatTask();
        return ientitylivingdata;
	}
}
