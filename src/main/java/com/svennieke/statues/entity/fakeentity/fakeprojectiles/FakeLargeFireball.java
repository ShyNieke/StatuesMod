package com.svennieke.statues.entity.fakeentity.fakeprojectiles;

import com.svennieke.statues.init.StatuesEntity;
import com.svennieke.statues.util.ParticleUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FakeLargeFireball extends EntityLargeFireball{

	public FakeLargeFireball(World worldIn)
    {
        super(worldIn);
    }
	
	@Override
	public EntityType<?> getType() {
		return StatuesEntity.FAKE_LARGE_FIREBALL;
	}

    @OnlyIn(Dist.CLIENT)
    public FakeLargeFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
    }

    public FakeLargeFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }
    
    @Override
    protected void onImpact(RayTraceResult result)
    {
    	if (!this.world.isRemote)
        {	
            this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, this.getSoundCategory(), 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    		this.remove();
        }
        ParticleUtil.emitExplosionParticles(this.world, this.getPosition());
    }
}
