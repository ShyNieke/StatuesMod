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
    private static final DataParameter<Optional<GameProfile>> GAMEPROFILE = EntityDataManager.createKey(PlayerStatueEntity.class, StatueSerializers.OPTIONAL_GAME_PROFILE);
    public static final DataParameter<Byte> STATUS = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.BYTE);
    public static final DataParameter<Float> Y_OFFSET = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Rotations> HEAD_ROTATION = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> BODY_ROTATION = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> LEFT_ARM_ROTATION = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> RIGHT_ARM_ROTATION = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> LEFT_LEG_ROTATION = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Rotations> RIGHT_LEG_ROTATION = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.ROTATIONS);
    public static final DataParameter<Optional<UUID>> LOCKED_BY_UUID = EntityDataManager.createKey(PlayerStatueEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);

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
        this.stepHeight = 0.0F;
    }

    public PlayerStatueEntity(World worldIn, double posX, double posY, double posZ) {
        this(StatueRegistry.PLAYER_STATUE_ENTITY.get(), worldIn);
        this.setPosition(posX, posY, posZ);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void recalculateSize() {
        double d0 = this.getPosX();
        double d1 = this.getPosY();
        double d2 = this.getPosZ();
        super.recalculateSize();
        this.setPosition(d0, d1, d2);
    }

    private boolean func_213814_A() {
        return !this.hasNoGravity();
    }

    @Override
    public boolean hasNoGravity() {
        return this.ticksExisted > 200 && super.hasNoGravity();
    }

    /**
     * Returns whether the entity is in a server world
     */
    public boolean isServerWorld() {
        return super.isServerWorld() && this.func_213814_A();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(GAMEPROFILE, Optional.empty());
        this.dataManager.register(STATUS, (byte)0);
        this.dataManager.register(Y_OFFSET, 0F);
        this.dataManager.register(HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        this.dataManager.register(BODY_ROTATION, DEFAULT_BODY_ROTATION);
        this.dataManager.register(LEFT_ARM_ROTATION, DEFAULT_LEFTARM_ROTATION);
        this.dataManager.register(RIGHT_ARM_ROTATION, DEFAULT_RIGHTARM_ROTATION);
        this.dataManager.register(LEFT_LEG_ROTATION, DEFAULT_LEFTLEG_ROTATION);
        this.dataManager.register(RIGHT_LEG_ROTATION, DEFAULT_RIGHTLEG_ROTATION);
        this.dataManager.register(LOCKED_BY_UUID, Optional.empty());
    }

    public Optional<GameProfile> getGameProfile() {
        return dataManager.get(GAMEPROFILE);
    }

    public void setGameProfile(GameProfile playerProfile) {
        GameProfile profile = PlayerTile.updateGameProfile(playerProfile);
        dataManager.set(GAMEPROFILE, Optional.of(profile));
    }

    public boolean isLocked() {
        return this.dataManager.get(LOCKED_BY_UUID).isPresent();
    }

    @Nullable
    public UUID getLockedBy() {
        return this.dataManager.get(LOCKED_BY_UUID).orElse((UUID)null);
    }

    public void setLockedBy(@Nullable UUID uuid) {
        this.dataManager.set(LOCKED_BY_UUID, Optional.ofNullable(uuid));
    }

    public void setUnlocked() {
        this.dataManager.set(LOCKED_BY_UUID, Optional.empty());
    }

    public void setSlim(boolean slim) {
        this.isSlim = slim;
    }

    public boolean isSlim() {
        return this.isSlim;
    }

    public void setYOffset(float yOffset) {
        dataManager.set(Y_OFFSET, MathHelper.clamp(yOffset, -1, 1));
    }

    public float getYOffsetData() {
        return dataManager.get(Y_OFFSET);
    }

    @Override
    @Nonnull
    public Iterable<ItemStack> getHeldEquipment() {
        return this.handItems;
    }

    @Override
    @Nonnull
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.armorItems;
    }

    @Override
    @Nonnull
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        switch(slotIn.getSlotType()) {
            case HAND:
                return this.handItems.get(slotIn.getIndex());
            case ARMOR:
                return this.armorItems.get(slotIn.getIndex());
            default:
                return ItemStack.EMPTY;
        }
    }

    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
        switch(slotIn.getSlotType()) {
            case HAND:
                this.playEquipSound(stack);
                this.handItems.set(slotIn.getIndex(), stack);
                break;
            case ARMOR:
                this.playEquipSound(stack);
                this.armorItems.set(slotIn.getIndex(), stack);
        }

    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
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

        if (!itemStackIn.isEmpty() && !MobEntity.isItemStackInSlot(equipmentslottype, itemStackIn) && equipmentslottype != EquipmentSlotType.HEAD) {
            return false;
        } else {
            this.setItemStackToSlot(equipmentslottype, itemStackIn);
            return true;
        }
    }

    @Override
    public boolean canPickUpItem(ItemStack itemstackIn) {
        EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstackIn);
        return this.getItemStackFromSlot(equipmentslottype).isEmpty() && !this.isDisabled(equipmentslottype);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("gameProfileExists", dataManager.get(GAMEPROFILE).isPresent());
        if (getGameProfile().isPresent()) {
            compound.put("gameProfile", NBTUtil.writeGameProfile(new CompoundNBT(), dataManager.get(GAMEPROFILE).get()));
        }

        compound.putFloat("yOffset", getYOffsetData());

        ListNBT listnbt = new ListNBT();

        for(ItemStack itemstack : this.armorItems) {
            CompoundNBT compoundnbt = new CompoundNBT();
            if (!itemstack.isEmpty()) {
                itemstack.write(compoundnbt);
            }

            listnbt.add(compoundnbt);
        }

        compound.put("ArmorItems", listnbt);
        ListNBT listnbt1 = new ListNBT();

        for(ItemStack itemstack1 : this.handItems) {
            CompoundNBT compoundnbt1 = new CompoundNBT();
            if (!itemstack1.isEmpty()) {
                itemstack1.write(compoundnbt1);
            }

            listnbt1.add(compoundnbt1);
        }

        compound.putBoolean("Locked", this.isLocked());
        if (this.isLocked() && this.getLockedBy() != null) {
            compound.putUniqueId("LockedBy", this.getLockedBy());
        }

        compound.put("HandItems", listnbt1);
        compound.putBoolean("Small", this.isSmall());
        compound.putInt("DisabledSlots", this.disabledSlots);

        compound.put("Pose", this.writePose());
    }

    @Override
    public CompoundNBT writeWithoutTypeId(CompoundNBT compound) {
        return super.writeWithoutTypeId(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        dataManager.set(GAMEPROFILE, !compound.getBoolean("gameProfileExists") ? Optional.empty() : Optional.ofNullable(NBTUtil.readGameProfile(compound.getCompound("gameProfile"))));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setYOffset(compound.getFloat("yOffset"));
        if (compound.contains("ArmorItems", 9)) {
            ListNBT listnbt = compound.getList("ArmorItems", 10);

            for(int i = 0; i < this.armorItems.size(); ++i) {
                this.armorItems.set(i, ItemStack.read(listnbt.getCompound(i)));
            }
        }

        if(compound.getBoolean("Locked")) {
            UUID uuid;
            if (compound.hasUniqueId("LockedBy")) {
                uuid = compound.getUniqueId("LockedBy");
            } else {
                String s = compound.getString("LockedBy");
                uuid = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s);
            }

            if (uuid != null) {
                this.setLockedBy(uuid);
            }
        }

        if (compound.contains("HandItems", 9)) {
            ListNBT listnbt1 = compound.getList("HandItems", 10);

            for(int j = 0; j < this.handItems.size(); ++j) {
                this.handItems.set(j, ItemStack.read(listnbt1.getCompound(j)));
            }
        }

        this.setSmall(compound.getBoolean("Small"));
        this.disabledSlots = compound.getInt("DisabledSlots");
        this.noClip = !this.func_213814_A();
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
            compoundnbt.put("Head", this.headRotation.writeToNBT());
        }

        if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            compoundnbt.put("Body", this.bodyRotation.writeToNBT());
        }

        if (!DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation)) {
            compoundnbt.put("LeftArm", this.leftArmRotation.writeToNBT());
        }

        if (!DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation)) {
            compoundnbt.put("RightArm", this.rightArmRotation.writeToNBT());
        }

        if (!DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation)) {
            compoundnbt.put("LeftLeg", this.leftLegRotation.writeToNBT());
        }

        if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
            compoundnbt.put("RightLeg", this.rightLegRotation.writeToNBT());
        }

        return compoundnbt;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {

    }

    @Override
    public void setCustomName(@Nullable ITextComponent name) {
        if(name != null) {
            if(!isLocked()) {
                super.setCustomName(name);

                this.setGameProfile(new GameProfile((UUID)null, name.getUnformattedComponentText().toLowerCase(Locale.ROOT)));
            }
        }
    }

    /**
     * Applies the given player interaction to this Entity.
     */
    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if(player.isSneaking()) {
            if(!world.isRemote && player != null) {
                if(isLocked() && getLockedBy() != null) {
                    if(player.getUniqueID().equals(getLockedBy())) {
                        Statues.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerStatueScreenMessage(getEntityId()));
                    }
                } else {
                    Statues.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerStatueScreenMessage(getEntityId()));
                }
            }
        } else {
            if (itemstack.getItem() != Items.NAME_TAG) {
                if (player.isSpectator()) {
                    return ActionResultType.SUCCESS;
                } else if (player.world.isRemote) {
                    return ActionResultType.CONSUME;
                } else {
                    if(!isLocked()) {
                        EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstack);
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
        return (this.disabledSlots & 1 << slotIn.getSlotIndex()) != 0;
    }

    private boolean equipOrSwap(PlayerEntity player, EquipmentSlotType slot, ItemStack stack, Hand hand) {
        ItemStack itemstack = this.getItemStackFromSlot(slot);
        if (!itemstack.isEmpty() && (this.disabledSlots & 1 << slot.getSlotIndex() + 8) != 0) {
            return false;
        } else if (itemstack.isEmpty() && (this.disabledSlots & 1 << slot.getSlotIndex() + 16) != 0) {
            return false;
        } else if (player.abilities.isCreativeMode && itemstack.isEmpty() && !stack.isEmpty()) {
            ItemStack itemstack2 = stack.copy();
            itemstack2.setCount(1);
            this.setItemStackToSlot(slot, itemstack2);
            return true;
        } else if (!stack.isEmpty() && stack.getCount() > 1) {
            if (!itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = stack.copy();
                itemstack1.setCount(1);
                this.setItemStackToSlot(slot, itemstack1);
                stack.shrink(1);
                return true;
            }
        } else {
            this.setItemStackToSlot(slot, stack);
            player.setHeldItem(hand, itemstack);
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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.world.isRemote && !this.removed) {
            if (DamageSource.OUT_OF_WORLD.equals(source)) {
                this.remove();
                return false;
            } else if (!this.isInvulnerableTo(source)) {
                if (source.isExplosion()) {
                    this.func_213816_g(source);
                    this.remove();
                    return false;
                } else if (DamageSource.IN_FIRE.equals(source)) {
                    if (this.isBurning()) {
                        this.damageArmorStand(source, 0.15F);
                    } else {
                        this.setFire(5);
                    }

                    return false;
                } else if (DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5F) {
                    this.damageArmorStand(source, 4.0F);
                    return false;
                } else {
                    boolean flag = source.getImmediateSource() instanceof AbstractArrowEntity;
                    boolean flag1 = flag && ((AbstractArrowEntity)source.getImmediateSource()).getPierceLevel() > 0;
                    boolean flag2 = "player".equals(source.getDamageType());
                    if (!flag2 && !flag) {
                        return false;
                    } else if (source.getTrueSource() instanceof PlayerEntity && !((PlayerEntity)source.getTrueSource()).abilities.allowEdit) {
                        return false;
                    } else if (source.isCreativePlayer()) {
                        this.playBrokenSound();
                        this.playParticles();
                        this.remove();
                        return flag1;
                    } else {
                        long i = this.world.getGameTime();
                        if (i - this.punchCooldown > 5L && !flag) {
                            this.world.setEntityState(this, (byte)32);
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
    public void handleStatusUpdate(byte id) {
        if (id == 32) {
            if (this.world.isRemote) {
                this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
                this.punchCooldown = this.world.getGameTime();
            }
        } else {
            super.handleStatusUpdate(id);
        }

    }

    /**
     * Checks if the entity is in range to render.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 4.0D;
        if (Double.isNaN(d0) || d0 == 0.0D) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    private void playParticles() {
        if (this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, StatueRegistry.PLAYER_STATUE.get().getDefaultState()), this.getPosX(), this.getPosYHeight(0.6666666666666666D), this.getPosZ(), 10, (double)(this.getWidth() / 4.0F), (double)(this.getHeight() / 4.0F), (double)(this.getWidth() / 4.0F), 0.05D);
        }

    }

    private void damageArmorStand(DamageSource source, float p_213817_2_) {
        float f = this.getHealth();
        f = f - p_213817_2_;
        if (f <= 0.5F) {
            this.func_213816_g(source);
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
                stack.setDisplayName(new StringTextComponent(profile.getName()));
            }
        }

        Block.spawnAsEntity(this.world, this.getPosition(), stack);
        this.func_213816_g(source);
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
                stack.setDisplayName(new StringTextComponent(profile.getName()));
            }
        }

        return stack;
    }

    private void func_213816_g(DamageSource source) {
        this.playBrokenSound();
        this.spawnDrops(source);

        for(int i = 0; i < this.handItems.size(); ++i) {
            ItemStack itemstack = this.handItems.get(i);
            if (!itemstack.isEmpty()) {
                Block.spawnAsEntity(this.world, this.getPosition().up(), itemstack);
                this.handItems.set(i, ItemStack.EMPTY);
            }
        }

        for(int j = 0; j < this.armorItems.size(); ++j) {
            ItemStack itemstack1 = this.armorItems.get(j);
            if (!itemstack1.isEmpty()) {
                Block.spawnAsEntity(this.world, this.getPosition().up(), itemstack1);
                this.armorItems.set(j, ItemStack.EMPTY);
            }
        }

    }

    private void playBrokenSound() {
        this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
    }

    protected float updateDistance(float p_110146_1_, float p_110146_2_) {
        this.prevRenderYawOffset = this.prevRotationYaw;
        this.renderYawOffset = this.rotationYaw;
        return 0.0F;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * (this.isChild() ? 0.5F : 0.9F);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset() {
        return (double)0.1F + getYOffsetData(); //TODO: what does this do?
    }

    public void travel(Vector3d travelVector) {
        if (this.func_213814_A()) {
            super.travel(travelVector);
        }
    }

    /**
     * Set the render yaw offset
     */
    public void setRenderYawOffset(float offset) {
        this.prevRenderYawOffset = this.prevRotationYaw = offset;
        this.prevRotationYawHead = this.rotationYawHead = offset;
    }

    /**
     * Sets the head's yaw rotation of the entity.
     */
    public void setRotationYawHead(float rotation) {
        this.prevRenderYawOffset = this.prevRotationYaw = rotation;
        this.prevRotationYawHead = this.rotationYawHead = rotation;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        Rotations rotations = this.dataManager.get(HEAD_ROTATION);
        if (!this.headRotation.equals(rotations)) {
            this.setHeadRotation(rotations);
        }

        Rotations rotations1 = this.dataManager.get(BODY_ROTATION);
        if (!this.bodyRotation.equals(rotations1)) {
            this.setBodyRotation(rotations1);
        }

        Rotations rotations2 = this.dataManager.get(LEFT_ARM_ROTATION);
        if (!this.leftArmRotation.equals(rotations2)) {
            this.setLeftArmRotation(rotations2);
        }

        Rotations rotations3 = this.dataManager.get(RIGHT_ARM_ROTATION);
        if (!this.rightArmRotation.equals(rotations3)) {
            this.setRightArmRotation(rotations3);
        }

        Rotations rotations4 = this.dataManager.get(LEFT_LEG_ROTATION);
        if (!this.leftLegRotation.equals(rotations4)) {
            this.setLeftLegRotation(rotations4);
        }

        Rotations rotations5 = this.dataManager.get(RIGHT_LEG_ROTATION);
        if (!this.rightLegRotation.equals(rotations5)) {
            this.setRightLegRotation(rotations5);
        }
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    public boolean isChild() {
        return this.isSmall();
    }

    /**
     * Called by the /kill command.
     */
    public void onKillCommand() {
        this.remove();
    }

    private void setSmall(boolean small) {
        this.dataManager.set(STATUS, this.setBit(this.dataManager.get(STATUS), 1, small));
    }

    public boolean isSmall() {
        return (this.dataManager.get(STATUS) & 1) != 0;
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
        this.dataManager.set(HEAD_ROTATION, vec);
    }

    public void setBodyRotation(Rotations vec) {
        this.bodyRotation = vec;
        this.dataManager.set(BODY_ROTATION, vec);
    }

    public void setLeftArmRotation(Rotations vec) {
        this.leftArmRotation = vec;
        this.dataManager.set(LEFT_ARM_ROTATION, vec);
    }

    public void setRightArmRotation(Rotations vec) {
        this.rightArmRotation = vec;
        this.dataManager.set(RIGHT_ARM_ROTATION, vec);
    }

    public void setLeftLegRotation(Rotations vec) {
        this.leftLegRotation = vec;
        this.dataManager.set(LEFT_LEG_ROTATION, vec);
    }

    public void setRightLegRotation(Rotations vec) {
        this.rightLegRotation = vec;
        this.dataManager.set(RIGHT_LEG_ROTATION, vec);
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
    public boolean canBeCollidedWith() {
        return true;
    }

    /**
     * Called when a player attacks an entity. If this returns true the attack will not happen.
     */
    @Override
    public boolean hitByEntity(Entity entityIn) {
        return entityIn instanceof PlayerEntity && !this.world.isBlockModifiable((PlayerEntity)entityIn, this.getPosition());
    }

    @Override
    @Nonnull
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }

    @Override
    @Nonnull
    protected SoundEvent getFallSound(int heightIn) {
        return SoundEvents.ENTITY_ARMOR_STAND_FALL;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ARMOR_STAND_HIT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
    }

    public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
    }

    /**
     * Returns false if the entity is an armor stand. Returns true for all other entity living bases.
     */
    @Override
    public boolean canBeHitWithPotion() {
        return false;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (STATUS.equals(key)) {
            this.recalculateSize();
            this.preventEntitySpawning = !this.removed;
        } else if(GAMEPROFILE.equals(key)) {
            if(this.world.isRemote()) {
                GameProfile gameprofile = this.getGameProfile().get();
                if(gameprofile != null) {
                    Minecraft.getInstance().getSkinManager().loadProfileTextures(gameprofile, (textureType, textureLocation, profileTexture) -> {
                        if (textureType.equals(MinecraftProfileTexture.Type.SKIN))  {
                            String metadata = profileTexture.getMetadata("model");
                            this.setSlim(metadata != null && metadata.equals("slim"));
                        }
                    }, true);
                }
            }
        }

        super.notifyDataManagerChange(key);
    }

    public boolean attackable() {
        return false;
    }
}
