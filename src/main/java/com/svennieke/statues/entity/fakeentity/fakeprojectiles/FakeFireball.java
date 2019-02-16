package com.svennieke.statues.entity.fakeentity.fakeprojectiles;

import com.svennieke.statues.init.StatuesEntity;
import com.svennieke.statues.util.ParticleUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class FakeFireball extends EntitySmallFireball{

	public FakeFireball(World worldIn)
    {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
    }
	
	@Override
	public EntityType<?> getType() {
		return StatuesEntity.FAKE_FIREBALL;
	}

    public FakeFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    public FakeFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }
    
    @Override
    protected void onImpact(RayTraceResult result)
    {
    	if (!this.world.isRemote)
        {
            this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, this.getSoundCategory(), 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    		this.remove();
        }
        ParticleUtil.emitExplosionParticles(this.world, this.getPosition());
    }
}
