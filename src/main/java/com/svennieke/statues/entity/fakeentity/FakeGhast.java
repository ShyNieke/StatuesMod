package com.svennieke.statues.entity.fakeentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeLargeFireball;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FakeGhast extends EntityGhast{

    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(FakeGhast.class, DataSerializers.BOOLEAN);
	private int lifetime;
	
	public FakeGhast(World worldIn) {
		super(worldIn);
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
	protected void initEntityAI()
    {
        this.tasks.addTask(5, new FakeGhast.AIRandomFly(this));
        this.tasks.addTask(7, new FakeGhast.AILookAround(this));
        this.tasks.addTask(7, new FakeGhast.AIFireballAttack(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }

    static class AIFireballAttack extends EntityAIBase
    {
        private final EntityGhast parentEntity;
        public int attackTimer;

        public AIFireballAttack(EntityGhast ghast)
        {
            this.parentEntity = ghast;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return this.parentEntity.getAttackTarget() != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.attackTimer = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            this.parentEntity.setAttacking(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
            double d0 = 64.0D;

            if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D && this.parentEntity.canEntityBeSeen(entitylivingbase))
            {
                World world = this.parentEntity.world;
                ++this.attackTimer;

                if (this.attackTimer == 10)
                {
                    world.playEvent((EntityPlayer)null, 1015, new BlockPos(this.parentEntity), 0);
                }

                if (this.attackTimer == 20)
                {
                    double d1 = 4.0D;
                    Vec3d vec3d = this.parentEntity.getLook(1.0F);
                    double d2 = entitylivingbase.posX - (this.parentEntity.posX + vec3d.x * 4.0D);
                    double d3 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (0.5D + this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F));
                    double d4 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3d.z * 4.0D);
                    world.playEvent((EntityPlayer)null, 1016, new BlockPos(this.parentEntity), 0);
                    FakeLargeFireball entitylargefireball = new FakeLargeFireball(world, this.parentEntity, d2, d3, d4);
                    entitylargefireball.explosionPower = 0;
                    entitylargefireball.posX = this.parentEntity.posX + vec3d.x * 4.0D;
                    entitylargefireball.posY = this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F) + 0.5D;
                    entitylargefireball.posZ = this.parentEntity.posZ + vec3d.z * 4.0D;
                    world.spawnEntity(entitylargefireball);
                    this.attackTimer = -40;
                }
            }
            else if (this.attackTimer > 0)
            {
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }

    static class AILookAround extends EntityAIBase
    {
        private final EntityGhast parentEntity;

        public AILookAround(EntityGhast ghast)
        {
            this.parentEntity = ghast;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            if (this.parentEntity.getAttackTarget() == null)
            {
                this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float)Math.PI);
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
            }
            else
            {
                EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
                double d0 = 64.0D;

                if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D)
                {
                    double d1 = entitylivingbase.posX - this.parentEntity.posX;
                    double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
                    this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                }
            }
        }
    }

	static class AIRandomFly extends EntityAIBase
    {
        private final EntityGhast parentEntity;

        public AIRandomFly(EntityGhast ghast)
        {
            this.parentEntity = ghast;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

            if (!entitymovehelper.isUpdating())
            {
                return true;
            }
            else
            {
                double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        }
    }
	
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Lifetime", this.lifetime);
    }
	
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.lifetime = compound.getInteger("Lifetime");
    }
	
	@Override
	public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.world.isRemote)
        {
            if (!this.isNoDespawnRequired())
            {
                ++this.lifetime;
            }

            if (this.lifetime >= 2400)
            {
                this.setDead();
            }
        }
    }
}
