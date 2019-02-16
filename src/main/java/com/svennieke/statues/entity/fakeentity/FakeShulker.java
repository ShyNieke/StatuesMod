package com.svennieke.statues.entity.fakeentity;

import com.google.common.base.Predicate;
import com.svennieke.statues.entity.fakeentity.fakeprojectiles.FakeShulkerBullet;
import com.svennieke.statues.init.StatuesEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FakeShulker extends EntityShulker implements IFakeEntity {

	private int lifetime;
	
	public FakeShulker(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public EntityType<?> getType() {
		return StatuesEntity.FAKE_SHULKER;
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
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new FakeShulker.AIAttack());
        this.tasks.addTask(7, new FakeShulker.AIPeek());
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new FakeShulker.AIAttackNearest(this));
        this.targetTasks.addTask(3, new FakeShulker.AIDefenseAttack(this));
    }
	
	class AIAttack extends EntityAIBase
    {
        private int attackTime;

        public AIAttack()
        {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = FakeShulker.this.getAttackTarget();

            if (entitylivingbase != null && entitylivingbase.isAlive())
            {
                return FakeShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
            }
            else
            {
                return false;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.attackTime = 20;
            FakeShulker.this.updateArmorModifier(100);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            FakeShulker.this.updateArmorModifier(0);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick()
        {
            if (FakeShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL)
            {
                --this.attackTime;
                EntityLivingBase entitylivingbase = FakeShulker.this.getAttackTarget();
                FakeShulker.this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 180.0F, 180.0F);
                double d0 = FakeShulker.this.getDistanceSq(entitylivingbase);

                if (d0 < 400.0D)
                {
                    if (this.attackTime <= 0)
                    {
                        this.attackTime = 20 + FakeShulker.this.rand.nextInt(10) * 20 / 2;
                        FakeShulkerBullet FakeShulkerbullet = new FakeShulkerBullet(FakeShulker.this.world, FakeShulker.this, entitylivingbase, FakeShulker.this.getAttachmentFacing().getAxis());
                        FakeShulker.this.world.spawnEntity(FakeShulkerbullet);
                        FakeShulker.this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (FakeShulker.this.rand.nextFloat() - FakeShulker.this.rand.nextFloat()) * 0.2F + 1.0F);
                    }
                }
                else
                {
                    FakeShulker.this.setAttackTarget((EntityLivingBase)null);
                }

                super.tick();
            }
        }
    }

    class AIAttackNearest extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        public AIAttackNearest(FakeShulker shulker)
        {
            super(shulker, EntityPlayer.class, true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return FakeShulker.this.world.getDifficulty() == EnumDifficulty.PEACEFUL ? false : super.shouldExecute();
        }

        protected AxisAlignedBB getTargetableArea(double targetDistance)
        {
            EnumFacing enumfacing = ((FakeShulker)this.taskOwner).getAttachmentFacing();

            if (enumfacing.getAxis() == EnumFacing.Axis.X)
            {
                return this.taskOwner.getBoundingBox().grow(4.0D, targetDistance, targetDistance);
            }
            else
            {
                return enumfacing.getAxis() == EnumFacing.Axis.Z ? this.taskOwner.getBoundingBox().grow(targetDistance, targetDistance, 4.0D) : this.taskOwner.getBoundingBox().grow(targetDistance, 4.0D, targetDistance);
            }
        }
    }

    static class AIDefenseAttack extends EntityAINearestAttackableTarget<EntityLivingBase>
        {
            public AIDefenseAttack(FakeShulker shulker)
            {
                super(shulker, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>()
                {
                    public boolean apply(@Nullable EntityLivingBase p_apply_1_)
                    {
                        return p_apply_1_ instanceof IMob;
                    }
                });
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                return this.taskOwner.getTeam() == null ? false : super.shouldExecute();
            }

            protected AxisAlignedBB getTargetableArea(double targetDistance)
            {
                EnumFacing enumfacing = ((FakeShulker)this.taskOwner).getAttachmentFacing();

                if (enumfacing.getAxis() == EnumFacing.Axis.X)
                {
                    return this.taskOwner.getBoundingBox().grow(4.0D, targetDistance, targetDistance);
                }
                else
                {
                    return enumfacing.getAxis() == EnumFacing.Axis.Z ? this.taskOwner.getBoundingBox().grow(targetDistance, targetDistance, 4.0D) : this.taskOwner.getBoundingBox().grow(targetDistance, 4.0D, targetDistance);
                }
            }
        }

    class AIPeek extends EntityAIBase
    {
        private int peekTime;

        private AIPeek()
        {
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return FakeShulker.this.getAttackTarget() == null && FakeShulker.this.rand.nextInt(40) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return FakeShulker.this.getAttackTarget() == null && this.peekTime > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.peekTime = 20 * (1 + FakeShulker.this.rand.nextInt(3));
            FakeShulker.this.updateArmorModifier(30);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            if (FakeShulker.this.getAttackTarget() == null)
            {
                FakeShulker.this.updateArmorModifier(0);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.peekTime;
        }
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
