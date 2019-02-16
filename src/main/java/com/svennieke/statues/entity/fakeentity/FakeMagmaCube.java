package com.svennieke.statues.entity.fakeentity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class FakeMagmaCube extends EntityMagmaCube implements IFakeEntity{

	private int lifetime;
	
	public FakeMagmaCube(World worldIn) {
		super(worldIn);
        this.isImmuneToFire = true;
	}
	
	@Override
	public EntityType<? extends EntitySlime> getType() {
		return super.getType();
		//return StatuesEntity.FAKE_MAGMA_CUBE;
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
	
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean canSpawn(IWorld worldIn, boolean value) {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    public boolean isNotColliding(IWorldReaderBase worldIn) {
        return worldIn.checkNoEntityCollision(this, this.getBoundingBox()) && worldIn.isCollisionBoxesEmpty(this, this.getBoundingBox()) && !worldIn.containsAnyLiquid(this.getBoundingBox());
    }

    protected void setSlimeSize(int size, boolean resetHealth)
    {
        super.setSlimeSize(size, resetHealth);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((double)(size * 3));
    }

    @OnlyIn(Dist.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 1.0F;
    }

    protected IParticleData func_195404_m() {
        return Particles.FLAME;
    }
    
    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return false;
    }

    /**
     * Gets the amount of time the slime needs to wait between jumps.
     */
    protected int getJumpDelay()
    {
        return super.getJumpDelay() * 4;
    }

    protected void alterSquishAmount()
    {
        this.squishAmount *= 0.9F;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump()
    {
        this.motionY = (double)(0.42F + (float)this.getSlimeSize() * 0.1F);
        this.isAirBorne = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
    }

    protected void handleJumpLava()
    {
        this.motionY = (double)(0.22F + (float)this.getSlimeSize() * 0.05F);
        this.isAirBorne = true;
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    protected boolean canDamagePlayer()
    {
        return false;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    protected int getAttackStrength()
    {
        return super.getAttackStrength() + 2;
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
	public void livingTick()
    {
        super.livingTick();

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
    }
}
