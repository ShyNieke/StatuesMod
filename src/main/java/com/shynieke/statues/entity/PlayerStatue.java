package com.shynieke.statues.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.shynieke.statues.client.ClientHandler;
import com.shynieke.statues.client.screen.PlayerStatueData;
import com.shynieke.statues.network.message.PlayerStatueScreenData;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.registry.StatueSerializers;
import net.minecraft.util.Util;
import net.minecraft.core.Rotations;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class PlayerStatue extends Avatar {
	public static final Rotations DEFAULT_HEAD_POSE = new Rotations(0.0F, 0.0F, 0.0F);
	public static final Rotations DEFAULT_BODY_POSE = new Rotations(0.0F, 0.0F, 0.0F);
	public static final Rotations DEFAULT_LEFT_ARM_POSE = new Rotations(-10.0F, 0.0F, -10.0F);
	public static final Rotations DEFAULT_RIGHT_ARM_POSE = new Rotations(-15.0F, 0.0F, 10.0F);
	public static final Rotations DEFAULT_LEFT_LEG_POSE = new Rotations(-1.0F, 0.0F, -1.0F);
	public static final Rotations DEFAULT_RIGHT_LEG_POSE = new Rotations(1.0F, 0.0F, 1.0F);

	private static final String DEFAULT_MODEL = PlayerStatueData.MODEL_TYPE.AUTO.name();
	private static final EntityDataAccessor<Optional<ResolvableProfile>> RESOLVABLE_PROFILE = SynchedEntityData.defineId(PlayerStatue.class, StatueSerializers.OPTIONAL_RESOLVABLE_PROFILE.get());
	public static final EntityDataAccessor<Byte> STATUS = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Float> Y_OFFSET = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Rotations> HEAD_ROTATION = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> BODY_ROTATION = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> LEFT_ARM_ROTATION = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> RIGHT_ARM_ROTATION = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> LEFT_LEG_ROTATION = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<Rotations> RIGHT_LEG_ROTATION = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.ROTATIONS);
	public static final EntityDataAccessor<String> MODEL_TYPE = SynchedEntityData.defineId(PlayerStatue.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Optional<UUID>> LOCKED_BY_UUID = SynchedEntityData.defineId(PlayerStatue.class, StatueSerializers.OPTIONAL_UUID.get());
	/**
	 * After punching the stand, the cooldown before you can punch it again without breaking it.
	 */
	public long punchCooldown;
	private int disabledSlots;
	private Rotations headRotation = DEFAULT_HEAD_POSE;
	private Rotations bodyRotation = DEFAULT_BODY_POSE;
	private Rotations leftArmRotation = DEFAULT_LEFT_ARM_POSE;
	private Rotations rightArmRotation = DEFAULT_RIGHT_ARM_POSE;
	private Rotations leftLegRotation = DEFAULT_LEFT_LEG_POSE;
	private Rotations rightLegRotation = DEFAULT_RIGHT_LEG_POSE;


	public PlayerStatue(EntityType<? extends PlayerStatue> entityType, Level level) {
		super(entityType, level);
	}

	public int clientLock = 0;

	public void setYRot(float yRot) {
		if (this.clientLock > 0) {
			return;
		}
		super.setYRot(yRot);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createLivingAttributes().add(Attributes.STEP_HEIGHT, 0.0);
	}

	public PlayerStatue(Level level, double posX, double posY, double posZ) {
		this(StatueRegistry.PLAYER_STATUE_ENTITY.get(), level);
		this.setPos(posX, posY, posZ);
	}

	@Override
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
		return super.isNoGravity();
	}

	/**
	 * Returns whether the entity is in a server level
	 */
	@Override
	public boolean isEffectiveAi() {
		return super.isEffectiveAi() && this.hasPhysics();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(RESOLVABLE_PROFILE, Optional.empty());
		builder.define(STATUS, (byte) 0);
		builder.define(Y_OFFSET, 0F);
		builder.define(HEAD_ROTATION, DEFAULT_HEAD_POSE);
		builder.define(BODY_ROTATION, DEFAULT_BODY_POSE);
		builder.define(LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_POSE);
		builder.define(RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_POSE);
		builder.define(LEFT_LEG_ROTATION, DEFAULT_LEFT_LEG_POSE);
		builder.define(RIGHT_LEG_ROTATION, DEFAULT_RIGHT_LEG_POSE);
		builder.define(LOCKED_BY_UUID, Optional.empty());
		builder.define(MODEL_TYPE, DEFAULT_MODEL);
	}

	public Optional<ResolvableProfile> getResolvableProfile() {
		return entityData.get(RESOLVABLE_PROFILE);
	}

	public void setResolvableProfile(ResolvableProfile profile) {
		entityData.set(RESOLVABLE_PROFILE, Optional.of(profile));
	}

	@Nullable
	public boolean isLocked() {
		return this.entityData.get(LOCKED_BY_UUID).isPresent();
	}

	@Nullable
	public UUID getLockedBy() {
		return this.entityData.get(LOCKED_BY_UUID).orElse(Util.NIL_UUID);
	}

	public String getModel() {
		return this.entityData.get(MODEL_TYPE);
	}

	public void setModel(String model) {
		this.entityData.set(MODEL_TYPE, model);
	}

	public boolean canOpenUI(Player player) {
		final UUID lockedBy = this.getLockedBy();
		return lockedBy.equals(Util.NIL_UUID) || (lockedBy != Util.NIL_UUID && lockedBy.equals(player.getUUID()));
	}

	public void setLockedBy(@Nullable UUID uuid) {
		if (uuid == null) {
			this.setUnlocked();
		} else {
			this.entityData.set(LOCKED_BY_UUID, Optional.of(uuid));
		}
	}

	public void setUnlocked() {
		this.entityData.set(LOCKED_BY_UUID, Optional.empty());
	}

	public void setYOffset(float yOffset) {
		entityData.set(Y_OFFSET, Mth.clamp(yOffset, -1, 1));
	}

	public float getYOffsetData() {
		return entityData.get(Y_OFFSET);
	}

	@Override
	public boolean canUseSlot(EquipmentSlot slot) {
		return slot != EquipmentSlot.BODY && !this.isDisabled(slot);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);

		output.putBoolean("profileExists", entityData.get(RESOLVABLE_PROFILE).isPresent());

		if (getResolvableProfile().isPresent()) {
			output.store("profile", ResolvableProfile.CODEC, getResolvableProfile().get());
		}

		output.putFloat("yOffset", getYOffsetData());
		output.putString("Model", getModel());

		output.putBoolean("Locked", this.isLocked());
		if (this.isLocked() && this.getLockedBy() != null) {
			output.store("LockedBy", UUIDUtil.CODEC, this.getLockedBy());
		}

		output.putBoolean("Small", this.isSmall());
		output.putInt("DisabledSlots", this.disabledSlots);

		output.store("Pose", StatuePose.CODEC, this.getStatuePose());
	}

	@Override
	public void load(ValueInput input) {
		super.load(input);

		Optional<ResolvableProfile> optionalProfile = input.read("profile", ResolvableProfile.CODEC);
		if (optionalProfile.isPresent()) {
			entityData.set(RESOLVABLE_PROFILE, optionalProfile);
		} else {
			entityData.set(RESOLVABLE_PROFILE, Optional.empty());
		}
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		if (this.clientLock > 0) return;
		super.readAdditionalSaveData(input);

		this.setYOffset(input.getFloatOr("yOffset", 0));
		this.setModel(input.getStringOr("Model", "DEFAULT"));

		if (input.getBooleanOr("Locked", false)) {
			UUID uuid;
			Optional<UUID> lockedBy = input.read("LockedBy", UUIDUtil.CODEC);
			if (lockedBy.isPresent()) {
				uuid = lockedBy.get();
			} else {
				String s = input.getStringOr("LockedBy", "");
				uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.level().getServer(), s);
			}

			if (uuid != null) {
				this.setLockedBy(uuid);
			}
		}

		this.setSmall(input.getBooleanOr("Small", false));
		this.disabledSlots = input.getIntOr("DisabledSlots", 0);
		this.noPhysics = !this.hasPhysics();
		input.read("Pose", StatuePose.CODEC).ifPresent(this::setStatuePose);
	}

	public void setStatuePose(StatuePose statuePose) {
		this.setHeadPose(statuePose.head());
		this.setBodyPose(statuePose.body());
		this.setLeftArmPose(statuePose.leftArm());
		this.setRightArmPose(statuePose.rightArm());
		this.setLeftLegPose(statuePose.leftLeg());
		this.setRightLegPose(statuePose.rightLeg());
	}

	public StatuePose getStatuePose() {
		return new StatuePose(
				this.getHeadPose(), this.getBodyPose(), this.getLeftArmPose(), this.getRightArmPose(), this.getLeftLegPose(), this.getRightLegPose()
		);
	}

	public static CompoundTag writeAllPoses(PlayerStatue playerStatue) {
		CompoundTag compoundTag = new CompoundTag();

		compoundTag.store("Head", Rotations.CODEC, playerStatue.getHeadPose());
		compoundTag.store("Body", Rotations.CODEC, playerStatue.getBodyPose());
		compoundTag.store("LeftArm", Rotations.CODEC, playerStatue.getLeftArmPose());
		compoundTag.store("RightArm", Rotations.CODEC, playerStatue.getRightArmPose());
		compoundTag.store("LeftLeg", Rotations.CODEC, playerStatue.getLeftLegPose());
		compoundTag.store("RightLeg", Rotations.CODEC, playerStatue.getRightLegPose());

		return compoundTag;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	public boolean isPushable() {
		return false;
	}

	protected void doPush(Entity entityIn) {

	}

	@Override
	public void setCustomName(@Nullable Component name) {
		if (name != null) {
			if (!isLocked()) {
				super.setCustomName(name);

				String username = name.getString().toLowerCase(Locale.ROOT);
				this.setResolvableProfile(ResolvableProfile.createUnresolved(username));
			}
		}
	}

	/**
	 * Applies the given player interaction to this Entity.
	 */
	public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (player.isShiftKeyDown()) {
			if (!this.level().isClientSide() && player != null && canOpenUI(player)) {
				((ServerPlayer) player).connection.send(new PlayerStatueScreenData(getId()));
			}
		} else {
			if (itemstack.getItem() != Items.NAME_TAG) {
				if (player.isSpectator()) {
					return InteractionResult.SUCCESS;
				} else if (player.level().isClientSide()) {
					return InteractionResult.CONSUME;
				} else {
					if (!isLocked()) {
						EquipmentSlot equipmentSlot = getEquipmentSlotForItem(itemstack);
						if (itemstack.isEmpty()) {
							EquipmentSlot equipmentslot1 = this.getClickedSlot(vec);
							EquipmentSlot equipmentslot2 = this.isDisabled(equipmentslot1) ? equipmentSlot : equipmentslot1;
							if (this.hasItemInSlot(equipmentslot2) && this.swapItem(player, equipmentslot2, itemstack, hand)) {
								return InteractionResult.SUCCESS;
							}
						} else {
							if (this.isDisabled(equipmentSlot)) {
								return InteractionResult.FAIL;
							}

							if (this.swapItem(player, equipmentSlot, itemstack, hand)) {
								return InteractionResult.SUCCESS;
							}
						}
					}

					return InteractionResult.PASS;
				}
			}
		}
		return InteractionResult.PASS;
	}

	private EquipmentSlot getClickedSlot(Vec3 clicked) {
		EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
		boolean flag = this.isSmall();
		double d0 = flag ? clicked.y * 2.0D : clicked.y;
		EquipmentSlot equipmentSlot1 = EquipmentSlot.FEET;
		if (d0 >= 0.1D && d0 < 0.1D + (flag ? 0.8D : 0.45D) && this.hasItemInSlot(equipmentSlot1)) {
			equipmentSlot = EquipmentSlot.FEET;
		} else if (d0 >= 0.9D + (flag ? 0.3D : 0.0D) && d0 < 0.9D + (flag ? 1.0D : 0.7D) && this.hasItemInSlot(EquipmentSlot.CHEST)) {
			equipmentSlot = EquipmentSlot.CHEST;
		} else if (d0 >= 0.4D && d0 < 0.4D + (flag ? 1.0D : 0.8D) && this.hasItemInSlot(EquipmentSlot.LEGS)) {
			equipmentSlot = EquipmentSlot.LEGS;
		} else if (d0 >= 1.6D && this.hasItemInSlot(EquipmentSlot.HEAD)) {
			equipmentSlot = EquipmentSlot.HEAD;
		} else if (!this.hasItemInSlot(EquipmentSlot.MAINHAND) && this.hasItemInSlot(EquipmentSlot.OFFHAND)) {
			equipmentSlot = EquipmentSlot.OFFHAND;
		}

		return equipmentSlot;
	}

	private boolean isDisabled(EquipmentSlot slotIn) {
		return (this.disabledSlots & 1 << slotIn.getFilterBit(0)) != 0;
	}

	private boolean swapItem(Player player, EquipmentSlot slot, ItemStack stack, InteractionHand hand) {
		ItemStack itemstack = this.getItemBySlot(slot);
		if (!itemstack.isEmpty() && (this.disabledSlots & 1 << slot.getFilterBit(8)) != 0) {
			return false;
		} else if (itemstack.isEmpty() && (this.disabledSlots & 1 << slot.getFilterBit(16)) != 0) {
			return false;
		} else if (player.hasInfiniteMaterials() && itemstack.isEmpty() && !stack.isEmpty()) {
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
	public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource source) {
		if (isLocked()) {
			return true;
		}

		return super.isInvulnerableTo(serverLevel, source);
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		if (this.isRemoved()) {
			return false;
		} else if (this.level() instanceof ServerLevel serverlevel) {
			if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
				this.remove(RemovalReason.DISCARDED);
				return false;
			} else if (!this.isInvulnerableTo(serverLevel, source)) {
				if (source.is(DamageTypeTags.IS_EXPLOSION)) {
					this.brokenByAnything(serverlevel, source);
					this.remove(RemovalReason.KILLED);
					return false;
				} else if (source.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
					if (this.isOnFire()) {
						this.causeDamage(serverlevel, source, 0.15F);
					} else {
						this.setRemainingFireTicks(5 * 20);
					}

					return false;
				} else if (source.is(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
					this.causeDamage(serverlevel, source, 4.0F);
					return false;
				} else {
					boolean flag = source.getDirectEntity() instanceof AbstractArrow;
					boolean flag1 = flag && ((AbstractArrow) source.getDirectEntity()).getPierceLevel() > 0;
					boolean flag2 = "player".equals(source.getMsgId());
					if (!flag2 && !flag) {
						return false;
					} else if (source.getEntity() instanceof Player && !((Player) source.getEntity()).getAbilities().mayBuild) {
						return false;
					} else if (source.isCreativePlayer()) {
						this.playBrokenSound();
						this.playParticles();
						this.remove(RemovalReason.KILLED);
						return flag1;
					} else {
						long i = this.level().getGameTime();
						if (i - this.punchCooldown > 5L && !flag) {
							this.level().broadcastEntityEvent(this, (byte) 32);
							this.punchCooldown = i;
						} else {
							this.breakPlayerStatue(serverlevel, source);
							this.playParticles();
							this.remove(RemovalReason.KILLED);
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

	public void handleEntityEvent(byte id) {
		if (id == 32) {
			if (this.level().isClientSide()) {
				this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
				this.punchCooldown = this.level().getGameTime();
			}
		} else {
			super.handleEntityEvent(id);
		}

	}

	/**
	 * Checks if the entity is in range to render.
	 */
	public boolean shouldRenderAtSqrDistance(double distance) {
		double d0 = this.getBoundingBox().getSize() * 4.0D;
		if (Double.isNaN(d0) || d0 == 0.0D) {
			d0 = 4.0D;
		}

		d0 = d0 * 64.0D;
		return distance < d0 * d0;
	}

	private void playParticles() {
		if (this.level() instanceof ServerLevel) {
			((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, StatueRegistry.PLAYER_STATUE.get().defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, (double) (this.getBbWidth() / 4.0F), (double) (this.getBbHeight() / 4.0F), (double) (this.getBbWidth() / 4.0F), 0.05D);
		}

	}

	private void causeDamage(ServerLevel serverLevel, DamageSource source, float p_213817_2_) {
		float f = this.getHealth();
		f = f - p_213817_2_;
		if (f <= 0.5F) {
			this.brokenByAnything(serverLevel, source);
			this.remove(RemovalReason.KILLED);
		} else {
			this.setHealth(f);
		}

	}

	private void breakPlayerStatue(ServerLevel serverLevel, DamageSource source) {
		ItemStack stack = new ItemStack(StatueRegistry.PLAYER_STATUE.get());
		if (getResolvableProfile().isPresent()) {
			ResolvableProfile resolvableProfile = getResolvableProfile().get();
			if (resolvableProfile != null) {
				stack.set(DataComponents.PROFILE, resolvableProfile);
				stack.set(DataComponents.CUSTOM_NAME, Component.literal(resolvableProfile.name().orElse("Steve")));
			}
		}

		Block.popResource(this.level(), this.blockPosition(), stack);
		Block.popResource(this.level(), this.blockPosition(), new ItemStack(StatueRegistry.STATUE_CORE.get()));
		this.brokenByAnything(serverLevel, source);
	}

//	@Override TODO: Re-implement?
//	public ItemStack getPickedResult(HitResult target) {
//		ItemStack stack = new ItemStack(StatueRegistry.PLAYER_STATUE.get());
//		if (getGameProfile().isPresent()) {
//			GameProfile profile = getGameProfile().get();
//			if (profile != null) {
//				CompoundTag stackTag = stack.getTag() != null ? stack.getTag() : new CompoundTag();
//				CompoundTag nbttagcompound = new CompoundTag();
//				NbtUtils.writeGameProfile(nbttagcompound, profile);
//				stackTag.put("PlayerProfile", nbttagcompound);
//				stack.setTag(stackTag);
//				stack.setHoverName(Component.literal(profile.getName()));
//			}
//		}
//
//		return stack;
//	}

	private void brokenByAnything(ServerLevel serverLevel, DamageSource source) {
		this.playBrokenSound();
		this.dropAllDeathLoot(serverLevel, source);

		for (EquipmentSlot equipmentslot : EquipmentSlot.VALUES) {
			ItemStack itemstack = this.equipment.set(equipmentslot, ItemStack.EMPTY);
			if (!itemstack.isEmpty()) {
				Block.popResource(this.level(), this.blockPosition().above(), itemstack);
			}
		}
	}

	private void playBrokenSound() {
		this.level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
	}

	protected float tickHeadTurn(float p_110146_1_, float p_110146_2_) {
		this.yBodyRotO = this.yRotO;
		this.yBodyRot = this.getYRot();
		return 0.0F;
	}

	protected float getStandingEdyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return sizeIn.height() * (this.isBaby() ? 0.5F : 0.9F);
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
//	@Override
//	protected float ridingOffset(Entity entity) {
//		return 0.1F + getYOffsetData(); //TODO: what does this do?
//	}
	public void travel(Vec3 travelVector) {
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

	@Override
	public HumanoidArm getMainArm() {
		return HumanoidArm.RIGHT;
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

		if (level().isClientSide() && getResolvableProfile().isPresent()) {
			ResolvableProfile resolvableProfile = getResolvableProfile().get();
			GameProfile profile = resolvableProfile.partialProfile();
			if (ClientHandler.TRANSLATORS.contains(profile.id())) {
				level().addParticle(ParticleTypes.ENCHANT,
						(double) getX(), (double) getEyeY() + 1, (double) getZ(),
						(double) ((float) (level().random.nextFloat() - 0.5) * 3 + random.nextFloat()) - 0.5D,
						(double) ((float) (level().random.nextFloat() - 0.5) * 3 - random.nextFloat() - 1.0F),
						(double) ((float) (level().random.nextFloat() - 0.5) * 3 + random.nextFloat()) - 0.5D);
			}
		}

		if (this.clientLock > 0) {
			this.clientLock--;
			return;
		}
		Rotations rotations = this.entityData.get(HEAD_ROTATION);
		if (!this.headRotation.equals(rotations)) {
			this.setHeadPose(rotations);
		}

		Rotations rotations1 = this.entityData.get(BODY_ROTATION);
		if (!this.bodyRotation.equals(rotations1)) {
			this.setBodyPose(rotations1);
		}

		Rotations rotations2 = this.entityData.get(LEFT_ARM_ROTATION);
		if (!this.leftArmRotation.equals(rotations2)) {
			this.setLeftArmPose(rotations2);
		}

		Rotations rotations3 = this.entityData.get(RIGHT_ARM_ROTATION);
		if (!this.rightArmRotation.equals(rotations3)) {
			this.setRightArmPose(rotations3);
		}

		Rotations rotations4 = this.entityData.get(LEFT_LEG_ROTATION);
		if (!this.leftLegRotation.equals(rotations4)) {
			this.setLeftLegPose(rotations4);
		}

		Rotations rotations5 = this.entityData.get(RIGHT_LEG_ROTATION);
		if (!this.rightLegRotation.equals(rotations5)) {
			this.setRightLegPose(rotations5);
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
		this.remove(RemovalReason.KILLED);
	}

	private void setSmall(boolean small) {
		this.entityData.set(STATUS, this.setBit(this.entityData.get(STATUS), 1, small));
	}

	public boolean isSmall() {
		return (this.entityData.get(STATUS) & 1) != 0;
	}

	private byte setBit(byte p_184797_1_, int p_184797_2_, boolean p_184797_3_) {
		if (p_184797_3_) {
			p_184797_1_ = (byte) (p_184797_1_ | p_184797_2_);
		} else {
			p_184797_1_ = (byte) (p_184797_1_ & ~p_184797_2_);
		}

		return p_184797_1_;
	}

	public void setHeadPose(Rotations vec) {
		this.headRotation = vec;
		this.entityData.set(HEAD_ROTATION, vec);
	}

	public void setBodyPose(Rotations vec) {
		this.bodyRotation = vec;
		this.entityData.set(BODY_ROTATION, vec);
	}

	public void setLeftArmPose(Rotations vec) {
		this.leftArmRotation = vec;
		this.entityData.set(LEFT_ARM_ROTATION, vec);
	}

	public void setRightArmPose(Rotations vec) {
		this.rightArmRotation = vec;
		this.entityData.set(RIGHT_ARM_ROTATION, vec);
	}

	public void setLeftLegPose(Rotations vec) {
		this.leftLegRotation = vec;
		this.entityData.set(LEFT_LEG_ROTATION, vec);
	}

	public void setRightLegPose(Rotations vec) {
		this.rightLegRotation = vec;
		this.entityData.set(RIGHT_LEG_ROTATION, vec);
	}

	public Rotations getHeadPose() {
		return this.headRotation;
	}

	public Rotations getBodyPose() {
		return this.bodyRotation;
	}


	public Rotations getLeftArmPose() {
		return this.leftArmRotation;
	}


	public Rotations getRightArmPose() {
		return this.rightArmRotation;
	}


	public Rotations getLeftLegPose() {
		return this.leftLegRotation;
	}


	public Rotations getRightLegPose() {
		return this.rightLegRotation;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean isPickable() {
		return true;
	}

	/**
	 * Called when a player attacks an entity. If this returns true the attack will not happen.
	 */
	public boolean skipAttackInteraction(Entity entityIn) {
		return entityIn instanceof Player && !this.level().mayInteract((Player) entityIn, this.blockPosition());
	}

	protected SoundEvent getFallDamageSound(int heightIn) {
		return SoundEvents.ARMOR_STAND_FALL;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ARMOR_STAND_HIT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.ARMOR_STAND_BREAK;
	}

	public void thunderHit(ServerLevel serverLevel, LightningBolt bolt) {
	}

	/**
	 * Returns false if the entity is an armor stand. Returns true for all other entity living bases.
	 */
	public boolean isAffectedByPotions() {
		return false;
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		if (STATUS.equals(key)) {
			this.refreshDimensions();
			this.blocksBuilding = !this.isRemoved();
		}

		super.onSyncedDataUpdated(key);
	}

	public boolean attackable() {
		return false;
	}

	public record StatuePose(Rotations head, Rotations body, Rotations leftArm, Rotations rightArm, Rotations leftLeg,
	                         Rotations rightLeg) {
		public static final PlayerStatue.StatuePose DEFAULT = new PlayerStatue.StatuePose(
				PlayerStatue.DEFAULT_HEAD_POSE,
				PlayerStatue.DEFAULT_BODY_POSE,
				PlayerStatue.DEFAULT_LEFT_ARM_POSE,
				PlayerStatue.DEFAULT_RIGHT_ARM_POSE,
				PlayerStatue.DEFAULT_LEFT_LEG_POSE,
				PlayerStatue.DEFAULT_RIGHT_LEG_POSE
		);
		public static final Codec<PlayerStatue.StatuePose> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								Rotations.CODEC.optionalFieldOf("Head", PlayerStatue.DEFAULT_HEAD_POSE).forGetter(PlayerStatue.StatuePose::head),
								Rotations.CODEC.optionalFieldOf("Body", PlayerStatue.DEFAULT_BODY_POSE).forGetter(PlayerStatue.StatuePose::body),
								Rotations.CODEC.optionalFieldOf("LeftArm", PlayerStatue.DEFAULT_LEFT_ARM_POSE).forGetter(PlayerStatue.StatuePose::leftArm),
								Rotations.CODEC.optionalFieldOf("RightArm", PlayerStatue.DEFAULT_RIGHT_ARM_POSE).forGetter(PlayerStatue.StatuePose::rightArm),
								Rotations.CODEC.optionalFieldOf("LeftLeg", PlayerStatue.DEFAULT_LEFT_LEG_POSE).forGetter(PlayerStatue.StatuePose::leftLeg),
								Rotations.CODEC.optionalFieldOf("RightLeg", PlayerStatue.DEFAULT_RIGHT_LEG_POSE).forGetter(PlayerStatue.StatuePose::rightLeg)
						)
						.apply(instance, PlayerStatue.StatuePose::new)
		);
	}
}
