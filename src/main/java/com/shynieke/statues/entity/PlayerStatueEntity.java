package com.shynieke.statues.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.shynieke.statues.Statues;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueSerializers;
import com.shynieke.statues.packets.PlayerStatueScreenMessage;
import com.shynieke.statues.tiles.PlayerTile;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class PlayerStatueEntity extends LivingEntity {
    private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
    private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
    private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
    private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
    private static final DataParameter<Optional<GameProfile>> GAMEPROFILE = EntityDataManager.defineId(PlayerStatueEntity.class, StatueSerializers.OPTIONAL_GAME_PROFILE);
    public static final DataParameter<Byte> STATUS = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Float> Y_OFFSET = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Rotations> HEAD_ROTATION = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> BODY_ROTATION = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> LEFT_ARM_ROTATION = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> RIGHT_ARM_ROTATION = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> LEFT_LEG_ROTATION = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> RIGHT_LEG_ROTATION = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Optional<UUID>> LOCKED_BY_UUID = EntityDataManager.defineId(PlayerStatueEntity.class, DataSerializers.OPTIONAL_UUID);

    private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
    private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
    /** After punching the stand, the cooldown before you can punch it again without breaking it. */
    public long punchCooldown;
    private int disabledSlots;
    private boolean isSlim = false;
    private Rotations headRotation = DEFAULT_HEAD_ROTATION;
    private Rotations bodyRotation = DEFAULT_BODY_ROTATION;
    private Rotations leftArmRotation = DEFAULT_LEFTARM_ROTATION;
    private Rotations rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
    private Rotations leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
    private Rotations rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;

    public PlayerStatueEntity(EntityType<? extends PlayerStatueEntity> entityType, World world) {
        super(entityType, world);
        this.maxUpStep = 0.0F;
    }

    public PlayerStatueEntity(World worldIn, double posX, double posY, double posZ) {
        this(StatueRegistry.PLAYER_STATUE_ENTITY.get(), worldIn);
        this.setPos(posX, posY, posZ);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    private boolean hasPhysics() {
        return !this.isNoGravity();
    }

    @Override
    public boolean isNoGravity() {
        return this.tickCount > 200 && super.isNoGravity();
    }

    /**
     * Returns whether the entity is in a server world
     */
    public boolean isEffectiveAi() {
        return super.isEffectiveAi() && this.hasPhysics();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GAMEPROFILE, Optional.empty());
        this.entityData.define(STATUS, (byte)0);
        this.entityData.define(Y_OFFSET, 0F);
        this.entityData.define(HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        this.entityData.define(BODY_ROTATION, DEFAULT_BODY_ROTATION);
        this.entityData.define(LEFT_ARM_ROTATION, DEFAULT_LEFTARM_ROTATION);
        this.entityData.define(RIGHT_ARM_ROTATION, DEFAULT_RIGHTARM_ROTATION);
        this.entityData.define(LEFT_LEG_ROTATION, DEFAULT_LEFTLEG_ROTATION);
        this.entityData.define(RIGHT_LEG_ROTATION, DEFAULT_RIGHTLEG_ROTATION);
        this.entityData.define(LOCKED_BY_UUID, Optional.empty());
    }

    public Optional<GameProfile> getGameProfile() {
        return entityData.get(GAMEPROFILE);
    }

    public void setGameProfile(GameProfile playerProfile) {
        GameProfile profile = PlayerTile.updateGameProfile(playerProfile);
        entityData.set(GAMEPROFILE, Optional.of(profile));
    }

    public boolean isLocked() {
        return this.entityData.get(LOCKED_BY_UUID).isPresent();
    }

    @Nullable
    public UUID getLockedBy() {
        return this.entityData.get(LOCKED_BY_UUID).orElse((UUID)null);
    }

    public void setLockedBy(@Nullable UUID uuid) {
        this.entityData.set(LOCKED_BY_UUID, Optional.ofNullable(uuid));
    }

    public void setUnlocked() {
        this.entityData.set(LOCKED_BY_UUID, Optional.empty());
    }

    public void setSlim(boolean slim) {
        this.isSlim = slim;
    }

    public boolean isSlim() {
        return this.isSlim;
    }

    public void setYOffset(float yOffset) {
        entityData.set(Y_OFFSET, MathHelper.clamp(yOffset, -1, 1));
    }

    public float getYOffsetData() {
        return entityData.get(Y_OFFSET);
    }

    @Override
    @Nonnull
    public Iterable<ItemStack> getHandSlots() {
        return this.handItems;
    }

    @Override
    @Nonnull
    public Iterable<ItemStack> getArmorSlots() {
        return this.armorItems;
    }

    @Override
    @Nonnull
    public ItemStack getItemBySlot(EquipmentSlotType slotIn) {
        switch(slotIn.getType()) {
            case HAND:
                return this.handItems.get(slotIn.getIndex());
            case ARMOR:
                return this.armorItems.get(slotIn.getIndex());
            default:
                return ItemStack.EMPTY;
        }
    }

    public void setItemSlot(EquipmentSlotType slotIn, ItemStack stack) {
        switch(slotIn.getType()) {
            case HAND:
                this.playEquipSound(stack);
                this.handItems.set(slotIn.getIndex(), stack);
                break;
            case ARMOR:
                this.playEquipSound(stack);
                this.armorItems.set(slotIn.getIndex(), stack);
        }

    }

    public boolean setSlot(int inventorySlot, ItemStack itemStackIn) {
        EquipmentSlotType equipmentslottype;
        if (inventorySlot == 98) {
            equipmentslottype = EquipmentSlotType.MAINHAND;
        } else if (inventorySlot == 99) {
            equipmentslottype = EquipmentSlotType.OFFHAND;
        } else if (inventorySlot == 100 + EquipmentSlotType.HEAD.getIndex()) {
            equipmentslottype = EquipmentSlotType.HEAD;
        } else if (inventorySlot == 100 + EquipmentSlotType.CHEST.getIndex()) {
            equipmentslottype = EquipmentSlotType.CHEST;
        } else if (inventorySlot == 100 + EquipmentSlotType.LEGS.getIndex()) {
            equipmentslottype = EquipmentSlotType.LEGS;
        } else {
            if (inventorySlot != 100 + EquipmentSlotType.FEET.getIndex()) {
                return false;
            }

            equipmentslottype = EquipmentSlotType.FEET;
        }

        if (!itemStackIn.isEmpty() && !MobEntity.isValidSlotForItem(equipmentslottype, itemStackIn) && equipmentslottype != EquipmentSlotType.HEAD) {
            return false;
        } else {
            this.setItemSlot(equipmentslottype, itemStackIn);
            return true;
        }
    }

    @Override
    public boolean canTakeItem(ItemStack itemstackIn) {
        EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstackIn);
        return this.getItemBySlot(equipmentslottype).isEmpty() && !this.isDisabled(equipmentslottype);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("gameProfileExists", entityData.get(GAMEPROFILE).isPresent());
        if (getGameProfile().isPresent()) {
            compound.put("gameProfile", NBTUtil.writeGameProfile(new CompoundNBT(), entityData.get(GAMEPROFILE).get()));
        }

        compound.putFloat("yOffset", getYOffsetData());

        ListNBT listnbt = new ListNBT();

        for(ItemStack itemstack : this.armorItems) {
            CompoundNBT compoundnbt = new CompoundNBT();
            if (!itemstack.isEmpty()) {
                itemstack.save(compoundnbt);
            }

            listnbt.add(compoundnbt);
        }

        compound.put("ArmorItems", listnbt);
        ListNBT listnbt1 = new ListNBT();

        for(ItemStack itemstack1 : this.handItems) {
            CompoundNBT compoundnbt1 = new CompoundNBT();
            if (!itemstack1.isEmpty()) {
                itemstack1.save(compoundnbt1);
            }

            listnbt1.add(compoundnbt1);
        }

        compound.putBoolean("Locked", this.isLocked());
        if (this.isLocked() && this.getLockedBy() != null) {
            compound.putUUID("LockedBy", this.getLockedBy());
        }

        compound.put("HandItems", listnbt1);
        compound.putBoolean("Small", this.isSmall());
        compound.putInt("DisabledSlots", this.disabledSlots);

        compound.put("Pose", this.writePose());
    }

    @Override
    public CompoundNBT saveWithoutId(CompoundNBT compound) {
        return super.saveWithoutId(compound);
    }

    @Override
    public void load(CompoundNBT compound) {
        super.load(compound);
        entityData.set(GAMEPROFILE, !compound.getBoolean("gameProfileExists") ? Optional.empty() : Optional.ofNullable(NBTUtil.readGameProfile(compound.getCompound("gameProfile"))));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setYOffset(compound.getFloat("yOffset"));
        if (compound.contains("ArmorItems", 9)) {
            ListNBT listnbt = compound.getList("ArmorItems", 10);

            for(int i = 0; i < this.armorItems.size(); ++i) {
                this.armorItems.set(i, ItemStack.of(listnbt.getCompound(i)));
            }
        }

        if(compound.getBoolean("Locked")) {
            UUID uuid;
            if (compound.hasUUID("LockedBy")) {
                uuid = compound.getUUID("LockedBy");
            } else {
                String s = compound.getString("LockedBy");
                uuid = PreYggdrasilConverter.convertMobOwnerIfNecessary(this.getServer(), s);
            }

            if (uuid != null) {
                this.setLockedBy(uuid);
            }
        }

        if (compound.contains("HandItems", 9)) {
            ListNBT listnbt1 = compound.getList("HandItems", 10);

            for(int j = 0; j < this.handItems.size(); ++j) {
                this.handItems.set(j, ItemStack.of(listnbt1.getCompound(j)));
            }
        }

        this.setSmall(compound.getBoolean("Small"));
        this.disabledSlots = compound.getInt("DisabledSlots");
        this.noPhysics = !this.hasPhysics();
        CompoundNBT compoundnbt = compound.getCompound("Pose");
        this.readPose(compoundnbt);
    }

    private void readPose(CompoundNBT tagCompound) {
        ListNBT listnbt = tagCompound.getList("Head", 5);
        this.setHeadRotation(listnbt.isEmpty() ? DEFAULT_HEAD_ROTATION : new Rotations(listnbt));
        ListNBT listnbt1 = tagCompound.getList("Body", 5);
        this.setBodyRotation(listnbt1.isEmpty() ? DEFAULT_BODY_ROTATION : new Rotations(listnbt1));
        ListNBT listnbt2 = tagCompound.getList("LeftArm", 5);
        this.setLeftArmRotation(listnbt2.isEmpty() ? DEFAULT_LEFTARM_ROTATION : new Rotations(listnbt2));
        ListNBT listnbt3 = tagCompound.getList("RightArm", 5);
        this.setRightArmRotation(listnbt3.isEmpty() ? DEFAULT_RIGHTARM_ROTATION : new Rotations(listnbt3));
        ListNBT listnbt4 = tagCompound.getList("LeftLeg", 5);
        this.setLeftLegRotation(listnbt4.isEmpty() ? DEFAULT_LEFTLEG_ROTATION : new Rotations(listnbt4));
        ListNBT listnbt5 = tagCompound.getList("RightLeg", 5);
        this.setRightLegRotation(listnbt5.isEmpty() ? DEFAULT_RIGHTLEG_ROTATION : new Rotations(listnbt5));
    }

    private CompoundNBT writePose() {
        CompoundNBT compoundnbt = new CompoundNBT();
        if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            compoundnbt.put("Head", this.headRotation.save());
        }

        if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            compoundnbt.put("Body", this.bodyRotation.save());
        }

        if (!DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation)) {
            compoundnbt.put("LeftArm", this.leftArmRotation.save());
        }

        if (!DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation)) {
            compoundnbt.put("RightArm", this.rightArmRotation.save());
        }

        if (!DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation)) {
            compoundnbt.put("LeftLeg", this.leftLegRotation.save());
        }

        if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
            compoundnbt.put("RightLeg", this.rightLegRotation.save());
        }

        return compoundnbt;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entityIn) {

    }

    @Override
    public void setCustomName(@Nullable ITextComponent name) {
        if(name != null) {
            if(!isLocked()) {
                super.setCustomName(name);

                this.setGameProfile(new GameProfile((UUID)null, name.getContents().toLowerCase(Locale.ROOT)));
            }
        }
    }

    /**
     * Applies the given player interaction to this Entity.
     */
    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(player.isShiftKeyDown()) {
            if(!level.isClientSide && player != null) {
                if(isLocked() && getLockedBy() != null) {
                    if(player.getUUID().equals(getLockedBy())) {
                        Statues.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerStatueScreenMessage(getId()));
                    }
                } else {
                    Statues.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerStatueScreenMessage(getId()));
                }
            }
        } else {
            if (itemstack.getItem() != Items.NAME_TAG) {
                if (player.isSpectator()) {
                    return ActionResultType.SUCCESS;
                } else if (player.level.isClientSide) {
                    return ActionResultType.CONSUME;
                } else {
                    if(!isLocked()) {
                        EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstack);
                        if (itemstack.isEmpty()) {
                            EquipmentSlotType equipmentslottype1 = this.getClickedSlot(vec);
                            EquipmentSlotType equipmentslottype2 = this.isDisabled(equipmentslottype1) ? equipmentslottype : equipmentslottype1;
                            if (this.hasItemInSlot(equipmentslottype2) && this.equipOrSwap(player, equipmentslottype2, itemstack, hand)) {
                                return ActionResultType.SUCCESS;
                            }
                        } else {
                            if (this.isDisabled(equipmentslottype)) {
                                return ActionResultType.FAIL;
                            }

                            if (this.equipOrSwap(player, equipmentslottype, itemstack, hand)) {
                                return ActionResultType.SUCCESS;
                            }
                        }
                    }

                    return ActionResultType.PASS;
                }
            }
        }
        return ActionResultType.PASS;
    }

    private EquipmentSlotType getClickedSlot(Vector3d p_190772_1_) {
        EquipmentSlotType equipmentslottype = EquipmentSlotType.MAINHAND;
        boolean flag = this.isSmall();
        double d0 = flag ? p_190772_1_.y * 2.0D : p_190772_1_.y;
        EquipmentSlotType equipmentslottype1 = EquipmentSlotType.FEET;
        if (d0 >= 0.1D && d0 < 0.1D + (flag ? 0.8D : 0.45D) && this.hasItemInSlot(equipmentslottype1)) {
            equipmentslottype = EquipmentSlotType.FEET;
        } else if (d0 >= 0.9D + (flag ? 0.3D : 0.0D) && d0 < 0.9D + (flag ? 1.0D : 0.7D) && this.hasItemInSlot(EquipmentSlotType.CHEST)) {
            equipmentslottype = EquipmentSlotType.CHEST;
        } else if (d0 >= 0.4D && d0 < 0.4D + (flag ? 1.0D : 0.8D) && this.hasItemInSlot(EquipmentSlotType.LEGS)) {
            equipmentslottype = EquipmentSlotType.LEGS;
        } else if (d0 >= 1.6D && this.hasItemInSlot(EquipmentSlotType.HEAD)) {
            equipmentslottype = EquipmentSlotType.HEAD;
        } else if (!this.hasItemInSlot(EquipmentSlotType.MAINHAND) && this.hasItemInSlot(EquipmentSlotType.OFFHAND)) {
            equipmentslottype = EquipmentSlotType.OFFHAND;
        }

        return equipmentslottype;
    }

    private boolean isDisabled(EquipmentSlotType slotIn) {
        return (this.disabledSlots & 1 << slotIn.getFilterFlag()) != 0;
    }

    private boolean equipOrSwap(PlayerEntity player, EquipmentSlotType slot, ItemStack stack, Hand hand) {
        ItemStack itemstack = this.getItemBySlot(slot);
        if (!itemstack.isEmpty() && (this.disabledSlots & 1 << slot.getFilterFlag() + 8) != 0) {
            return false;
        } else if (itemstack.isEmpty() && (this.disabledSlots & 1 << slot.getFilterFlag() + 16) != 0) {
            return false;
        } else if (player.abilities.instabuild && itemstack.isEmpty() && !stack.isEmpty()) {
            ItemStack itemstack2 = stack.copy();
            itemstack2.setCount(1);
            this.setItemSlot(slot, itemstack2);
            return true;
        } else if (!stack.isEmpty() && stack.getCount() > 1) {
            if (!itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = stack.copy();
                itemstack1.setCount(1);
                this.setItemSlot(slot, itemstack1);
                stack.shrink(1);
                return true;
            }
        } else {
            this.setItemSlot(slot, stack);
            player.setItemInHand(hand, itemstack);
            return true;
        }
    }

    @Override
    public boolean isInvulnerable() {
        return isLocked() || super.isInvulnerable();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if(isLocked()) {
            return true;
        }

        return super.isInvulnerableTo(source);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level.isClientSide && !this.removed) {
            if (DamageSource.OUT_OF_WORLD.equals(source)) {
                this.remove();
                return false;
            } else if (!this.isInvulnerableTo(source)) {
                if (source.isExplosion()) {
                    this.brokenByAnything(source);
                    this.remove();
                    return false;
                } else if (DamageSource.IN_FIRE.equals(source)) {
                    if (this.isOnFire()) {
                        this.damageArmorStand(source, 0.15F);
                    } else {
                        this.setSecondsOnFire(5);
                    }

                    return false;
                } else if (DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5F) {
                    this.damageArmorStand(source, 4.0F);
                    return false;
                } else {
                    boolean flag = source.getDirectEntity() instanceof AbstractArrowEntity;
                    boolean flag1 = flag && ((AbstractArrowEntity)source.getDirectEntity()).getPierceLevel() > 0;
                    boolean flag2 = "player".equals(source.getMsgId());
                    if (!flag2 && !flag) {
                        return false;
                    } else if (source.getEntity() instanceof PlayerEntity && !((PlayerEntity)source.getEntity()).abilities.mayBuild) {
                        return false;
                    } else if (source.isCreativePlayer()) {
                        this.playBrokenSound();
                        this.playParticles();
                        this.remove();
                        return flag1;
                    } else {
                        long i = this.level.getGameTime();
                        if (i - this.punchCooldown > 5L && !flag) {
                            this.level.broadcastEntityEvent(this, (byte)32);
                            this.punchCooldown = i;
                        } else {
                            this.breakPlayerStatue(source);
                            this.playParticles();
                            this.remove();
                        }

                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 32) {
            if (this.level.isClientSide) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
                this.punchCooldown = this.level.getGameTime();
            }
        } else {
            super.handleEntityEvent(id);
        }

    }

    /**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0) || d0 == 0.0D) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    private void playParticles() {
        if (this.level instanceof ServerWorld) {
            ((ServerWorld)this.level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, StatueRegistry.PLAYER_STATUE.get().defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05D);
        }

    }

    private void damageArmorStand(DamageSource source, float p_213817_2_) {
        float f = this.getHealth();
        f = f - p_213817_2_;
        if (f <= 0.5F) {
            this.brokenByAnything(source);
            this.remove();
        } else {
            this.setHealth(f);
        }

    }

    private void breakPlayerStatue(DamageSource source) {
        ItemStack stack = new ItemStack(StatueRegistry.PLAYER_STATUE.get());
        if(getGameProfile().isPresent()) {
            GameProfile profile = getGameProfile().get();
            if (profile != null) {
                CompoundNBT stackTag = stack.getTag() != null ? stack.getTag() : new CompoundNBT();
                CompoundNBT nbttagcompound = new CompoundNBT();
                NBTUtil.writeGameProfile(nbttagcompound, profile);
                stackTag.put("PlayerProfile", nbttagcompound);
                stack.setTag(stackTag);
                stack.setHoverName(new StringTextComponent(profile.getName()));
            }
        }

        Block.popResource(this.level, this.blockPosition(), stack);
        this.brokenByAnything(source);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        ItemStack stack = new ItemStack(StatueRegistry.PLAYER_STATUE.get());
        if(getGameProfile().isPresent()) {
            GameProfile profile = getGameProfile().get();
            if (profile != null) {
                CompoundNBT stackTag = stack.getTag() != null ? stack.getTag() : new CompoundNBT();
                CompoundNBT nbttagcompound = new CompoundNBT();
                NBTUtil.writeGameProfile(nbttagcompound, profile);
                stackTag.put("PlayerProfile", nbttagcompound);
                stack.setTag(stackTag);
                stack.setHoverName(new StringTextComponent(profile.getName()));
            }
        }

        return stack;
    }

    private void brokenByAnything(DamageSource source) {
        this.playBrokenSound();
        this.dropAllDeathLoot(source);

        for(int i = 0; i < this.handItems.size(); ++i) {
            ItemStack itemstack = this.handItems.get(i);
            if (!itemstack.isEmpty()) {
                Block.popResource(this.level, this.blockPosition().above(), itemstack);
                this.handItems.set(i, ItemStack.EMPTY);
            }
        }

        for(int j = 0; j < this.armorItems.size(); ++j) {
            ItemStack itemstack1 = this.armorItems.get(j);
            if (!itemstack1.isEmpty()) {
                Block.popResource(this.level, this.blockPosition().above(), itemstack1);
                this.armorItems.set(j, ItemStack.EMPTY);
            }
        }

    }

    private void playBrokenSound() {
        this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    protected float tickHeadTurn(float p_110146_1_, float p_110146_2_) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.yRot;
        return 0.0F;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * (this.isBaby() ? 0.5F : 0.9F);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getMyRidingOffset() {
        return (double)0.1F + getYOffsetData(); //TODO: what does this do?
    }

    public void travel(Vector3d travelVector) {
        if (this.hasPhysics()) {
            super.travel(travelVector);
        }
    }

    /**
     * Set the render yaw offset
     */
    public void setYBodyRot(float offset) {
        this.yBodyRotO = this.yRotO = offset;
        this.yHeadRotO = this.yHeadRot = offset;
    }

    /**
     * Sets the head's yaw rotation of the entity.
     */
    public void setYHeadRot(float rotation) {
        this.yBodyRotO = this.yRotO = rotation;
        this.yHeadRotO = this.yHeadRot = rotation;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        Rotations rotations = this.entityData.get(HEAD_ROTATION);
        if (!this.headRotation.equals(rotations)) {
            this.setHeadRotation(rotations);
        }

        Rotations rotations1 = this.entityData.get(BODY_ROTATION);
        if (!this.bodyRotation.equals(rotations1)) {
            this.setBodyRotation(rotations1);
        }

        Rotations rotations2 = this.entityData.get(LEFT_ARM_ROTATION);
        if (!this.leftArmRotation.equals(rotations2)) {
            this.setLeftArmRotation(rotations2);
        }

        Rotations rotations3 = this.entityData.get(RIGHT_ARM_ROTATION);
        if (!this.rightArmRotation.equals(rotations3)) {
            this.setRightArmRotation(rotations3);
        }

        Rotations rotations4 = this.entityData.get(LEFT_LEG_ROTATION);
        if (!this.leftLegRotation.equals(rotations4)) {
            this.setLeftLegRotation(rotations4);
        }

        Rotations rotations5 = this.entityData.get(RIGHT_LEG_ROTATION);
        if (!this.rightLegRotation.equals(rotations5)) {
            this.setRightLegRotation(rotations5);
        }
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isBaby() {
        return this.isSmall();
    }

    /**
     * Called by the /kill command.
     */
    public void kill() {
        this.remove();
    }

    private void setSmall(boolean small) {
        this.entityData.set(STATUS, this.setBit(this.entityData.get(STATUS), 1, small));
    }

    public boolean isSmall() {
        return (this.entityData.get(STATUS) & 1) != 0;
    }

    private byte setBit(byte p_184797_1_, int p_184797_2_, boolean p_184797_3_) {
        if (p_184797_3_) {
            p_184797_1_ = (byte)(p_184797_1_ | p_184797_2_);
        } else {
            p_184797_1_ = (byte)(p_184797_1_ & ~p_184797_2_);
        }

        return p_184797_1_;
    }

    public void setHeadRotation(Rotations vec) {
        this.headRotation = vec;
        this.entityData.set(HEAD_ROTATION, vec);
    }

    public void setBodyRotation(Rotations vec) {
        this.bodyRotation = vec;
        this.entityData.set(BODY_ROTATION, vec);
    }

    public void setLeftArmRotation(Rotations vec) {
        this.leftArmRotation = vec;
        this.entityData.set(LEFT_ARM_ROTATION, vec);
    }

    public void setRightArmRotation(Rotations vec) {
        this.rightArmRotation = vec;
        this.entityData.set(RIGHT_ARM_ROTATION, vec);
    }

    public void setLeftLegRotation(Rotations vec) {
        this.leftLegRotation = vec;
        this.entityData.set(LEFT_LEG_ROTATION, vec);
    }

    public void setRightLegRotation(Rotations vec) {
        this.rightLegRotation = vec;
        this.entityData.set(RIGHT_LEG_ROTATION, vec);
    }

    public Rotations getHeadRotation() {
        return this.headRotation;
    }

    public Rotations getBodyRotation() {
        return this.bodyRotation;
    }

    @OnlyIn(Dist.CLIENT)
    public Rotations getLeftArmRotation() {
        return this.leftArmRotation;
    }

    @OnlyIn(Dist.CLIENT)
    public Rotations getRightArmRotation() {
        return this.rightArmRotation;
    }

    @OnlyIn(Dist.CLIENT)
    public Rotations getLeftLegRotation() {
        return this.leftLegRotation;
    }

    @OnlyIn(Dist.CLIENT)
    public Rotations getRightLegRotation() {
        return this.rightLegRotation;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean isPickable() {
        return true;
    }

    /**
     * Called when a player attacks an entity. If this returns true the attack will not happen.
     */
    @Override
    public boolean skipAttackInteraction(Entity entityIn) {
        return entityIn instanceof PlayerEntity && !this.level.mayInteract((PlayerEntity)entityIn, this.blockPosition());
    }

    @Override
    @Nonnull
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    @Override
    @Nonnull
    protected SoundEvent getFallDamageSound(int heightIn) {
        return SoundEvents.ARMOR_STAND_FALL;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ARMOR_STAND_HIT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ARMOR_STAND_BREAK;
    }

    public void thunderHit(ServerWorld world, LightningBoltEntity lightning) {
    }

    /**
     * Returns false if the entity is an armor stand. Returns true for all other entity living bases.
     */
    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (STATUS.equals(key)) {
            this.refreshDimensions();
            this.blocksBuilding = !this.removed;
        } else if(GAMEPROFILE.equals(key)) {
            if(this.level.isClientSide()) {
                GameProfile gameprofile = this.getGameProfile().get();
                if(gameprofile != null) {
                    Minecraft.getInstance().getSkinManager().registerSkins(gameprofile, (textureType, textureLocation, profileTexture) -> {
                        if (textureType.equals(MinecraftProfileTexture.Type.SKIN))  {
                            String metadata = profileTexture.getMetadata("model");
                            this.setSlim(metadata != null && metadata.equals("slim"));
                        }
                    }, true);
                }
            }
        }

        super.onSyncedDataUpdated(key);
    }

    public boolean attackable() {
        return false;
    }
}
