package com.shynieke.statues.blockentities;

import com.mojang.datafixers.util.Either;
import com.shynieke.statues.Statues;
import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.fakeplayer.StatueFakePlayer;
import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.util.LootHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.extensions.IOwnedSpawner;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatueBlockEntity extends AbstractStatueBlockEntity implements IOwnedSpawner {
	private AABB hitbox;
	protected RecipeHolder<LootRecipe> cachedLootRecipe;

	public StatueBlockEntity(BlockPos pos, BlockState state) {
		this(StatueBlockEntities.STATUE.get(), pos, state);
	}

	public StatueBlockEntity(BlockEntityType<?> tileType, BlockPos pos, BlockState state) {
		super(tileType, pos, state);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, StatueBlockEntity blockEntity) {
		if (state.getValue(AbstractStatueBase.INTERACTIVE) && state.getBlock() instanceof AbstractStatueBase) {
			if (level.getGameTime() % level.tickRateManager().tickrate() == 0) {
				blockEntity.drainPower(blockEntity.getPassiveDrain());
			}
			if (!blockEntity.isStatueInteractable()) {
				blockEntity.interactCooldown--;

				if (blockEntity.interactCooldown <= 0) {
					blockEntity.interactCooldown = blockEntity.getInteractCooldown();
					blockEntity.setStatueInteractable(true);
				}
			}
			if (!blockEntity.isStatueAble()) {
				blockEntity.cooldown--;

				if (blockEntity.cooldown <= 0) {
					blockEntity.cooldown = blockEntity.getCooldown();
					blockEntity.setStatueAble(true);
					if (blockEntity.canAutomate()) {
						if (blockEntity.canDropLoot()) {
							blockEntity.giveItem();
							blockEntity.setStatueInteractable(false);
						}
					}
				}
			} else {
				if (!level.hasNeighborSignal(pos)) {
					//Insert spawner behavior
					if (blockEntity.isSpawner()) {
						blockEntity.summonMob((ServerLevel) level);
					}
					if (blockEntity.isKiller()) {
						blockEntity.killMob((ServerLevel) level);
					}

					blockEntity.setStatueAble(false);
				}
			}

			blockEntity.setChanged();
		}
	}

	@Override
	public InteractionResult interact(Level level, BlockPos pos, BlockState state, Player player, InteractionHand handIn, BlockHitResult result) {
		AbstractStatueBase statueBase = getStatue();
		if (statueBase == null)
			return InteractionResult.PASS;

		if (makesSounds()) {
			playSound(statueBase.getSound(state), pos);
		}

		if (isStatueInteractable()) {
			if (canDropLoot()) {
				giveItem();
			}

			setStatueInteractable(false);
		}

		if (hasSpecialInteraction()) {
			onSpecialInteract(level, pos, state, player, handIn, result);
		}
		return InteractionResult.CONSUME;
	}

	public void onSpecialInteract(Level level, BlockPos pos, BlockState state, Player player, InteractionHand handIn, BlockHitResult result) {
		Vec3 hitPos = result.getLocation();
		if (state.is(StatueRegistry.FLOOD_STATUE.get())) {
			floodBehavior(player, pos, handIn, (float) hitPos.x, (float) hitPos.y, (float) hitPos.z);
		} else if (state.is(StatueRegistry.MOOSHROOM_STATUE.get()) || state.is(StatueRegistry.BROWN_MOOSHROOM_STATUE.get())) {
			mooshroomBehavior(player, pos, handIn);
		} else if (state.is(StatueRegistry.COW_STATUE.get())) {
			cowBehavior(player, pos, handIn);
		} else if (state.is(StatueRegistry.SPIDER_STATUE.get())) {
			giveEffect(player, pos, MobEffects.POISON);
		}
	}

	public void giveItem() {
		if (level != null && !level.isClientSide()) {
			if (!drainPower(getItemPowerUsage())) return;
			LootRecipe loot;
			if (cachedLootRecipe != null) {
				loot = cachedLootRecipe.value();
			} else {
				loot = (cachedLootRecipe = LootHelper.getMatchingLoot((ServerLevel) level, new ItemStack(getBlockState().getBlock()))).value();
			}
			if (loot == null) {
				Statues.LOGGER.error("No loot found for statue {}, please report this to the Statues issue tracker", getBlockState());
				return;
			}

			final int looting = getLooting();
			final RegistryAccess access = level.registryAccess();
			ItemStack stack1 = loot.getResultItem().copy();
			float chance1 = loot.getChance1() + (looting * 0.1F);
			if (!stack1.isEmpty() && level.getRandom().nextDouble() <= chance1) {
				exportItem(stack1);
			}

			ItemStack stack2 = loot.getResultItem2().copy();
			float chance2 = loot.getChance2() + (looting * 0.1F);
			if (!stack2.isEmpty() && level.getRandom().nextDouble() <= chance2) {
				exportItem(stack2);
			}

			ItemStack stack3 = loot.getResultItem3().copy();
			float chance3 = loot.getChance3() + (looting * 0.1F);
			if (!stack3.isEmpty() && level.getRandom().nextDouble() <= chance3) {
				exportItem(stack3);
			}
		}
	}

	private void exportItem(ItemStack stack) {
		if (level.isClientSide()) return;

		ServerLevel serverLevel = (ServerLevel) this.level;
		if (canAutomate()) {
			List<BiggestInventory> inventoryList = new ArrayList<>();
			for (Direction dir : Direction.values()) {
				BlockPos offPos = worldPosition.relative(dir);
				if (this.level.isAreaLoaded(worldPosition, 1)) {
					BlockEntity foundTile = this.level.getBlockEntity(offPos);
					if (foundTile != null) {
						Identifier typeLocation = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(foundTile.getType());
						boolean flag2 = typeLocation != null;
						ResourceHandler<ItemResource> handler = this.level.getCapability(Capabilities.Item.BLOCK, offPos, null);
						if (flag2 && !foundTile.isRemoved() && foundTile.hasLevel() && handler != null) {
							ResourceHandler<ItemResource> itemHandler = this.level.getCapability(Capabilities.Item.BLOCK, offPos, dir.getOpposite());
							if (itemHandler != null) {
								inventoryList.add(new BiggestInventory(offPos, itemHandler.size(), dir.getOpposite()));
							}
						}
					}
				}
			}
			inventoryList.sort(Collections.reverseOrder());
			if (inventoryList.isEmpty()) {
				level.addFreshEntity(new ItemEntity(level, worldPosition.getX(), worldPosition.getY() + 0.5, worldPosition.getZ(), stack));
			} else {
				for (BiggestInventory inventory : inventoryList) {
					ResourceHandler<ItemResource> itemHandler = inventory.getResourceHandler(serverLevel);
					try (var tx = Transaction.openRoot()) {
						if (itemHandler.insert(ItemResource.of(stack), stack.getCount(), tx) != stack.getCount()) {
							continue;
						}
						tx.commit();
						break;
					}
				}
			}
		} else {
			level.addFreshEntity(new ItemEntity(level, worldPosition.getX(), worldPosition.getY() + 0.5, worldPosition.getZ(), stack));
		}
	}

	public void killMob(ServerLevel serverLevel) {
		BlockPos pos = getBlockPos();
		if (hitbox == null) {
			hitbox = new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f,
					pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f)
					.inflate(-12, -12, -12).inflate(12, 12, 12);
		}
		List<LivingEntity> targetList = level.getEntitiesOfClass(LivingEntity.class, hitbox).stream()
				.filter(entity -> entity.getType().equals(getStatue().getEntity()) && !(entity instanceof FakePlayer)).toList();

		final int killerLevel = getUpgradeLevel("mob_killer");

		for (LivingEntity target : targetList) {
			if (killerLevel > 1) {
				ItemStack tempSword = new ItemStack(Items.DIAMOND_SWORD, 1);
				StatueFakePlayer.useFakePlayer(serverLevel, (fakePlayer -> {
					fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, tempSword);
					if (!drainPower(getKillPowerUsage())) return false;
					target.hurt(serverLevel.damageSources().playerAttack(fakePlayer), (float) fakePlayer.getAttributeValue(Attributes.ATTACK_DAMAGE));
					return true;
				}));
			} else {
				target.hurt(serverLevel.damageSources().generic(), 6);
			}
		}
	}

	public void summonMob(ServerLevel serverLevel) {
		final BlockPos pos = getBlockPos();
		final int spawnerLevel = getSpawnerLevel() + 1;
		final boolean screwTheRulesIHasMoney = spawnerLevel > 3;
		int spawnCount = serverLevel.getRandom().nextInt(spawnerLevel) + 1;
		EntityType<?> entityType = getStatue().getEntity();

		for (int i = 0; i < spawnCount; i++) {
			double d0 = (double) pos.getX() + (serverLevel.getRandom().nextDouble() - serverLevel.getRandom().nextDouble()) * (double) 4 + 0.5D;
			double d1 = (double) (pos.getY() + serverLevel.getRandom().nextInt(3) - 1);
			double d2 = (double) pos.getZ() + (serverLevel.getRandom().nextDouble() - serverLevel.getRandom().nextDouble()) * (double) 4 + 0.5D;
			if (serverLevel.noCollision(entityType.getSpawnAABB(d0, d1, d2))) {
				BlockPos blockpos = BlockPos.containing(d0, d1, d2);
				if (!serverLevel.isAreaLoaded(blockpos, 1)) continue;

				if (!screwTheRulesIHasMoney && !SpawnPlacements.checkSpawnRules(entityType, serverLevel, EntitySpawnReason.SPAWNER, blockpos, serverLevel.getRandom())) {
					continue;
				}

				if (!drainPower(getSummonPowerUsage())) return;
				Entity entity = entityType.create(serverLevel, EntitySpawnReason.SPAWNER);
				if (entity == null) {
					continue;
				}
				entity.snapTo(d0, d1, d2, entity.getYRot(), entity.getXRot());

				int k = serverLevel.getEntitiesOfClass(entity.getClass(), (new AABB((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (double)
						(pos.getX() + 1), (double) (pos.getY() + 1), (double) (pos.getZ() + 1))).inflate((double) 4)).size();
				if (k >= 6 + spawnerLevel) {
					continue;
				}

				entity.snapTo(entity.getX(), entity.getY(), entity.getZ(), level.getRandom().nextFloat() * 360.0F, 0.0F);
				if (entity instanceof Mob mob) {
					if (!screwTheRulesIHasMoney && !mob.checkSpawnRules(serverLevel, EntitySpawnReason.SPAWNER) || !mob.checkSpawnObstruction(serverLevel)) {
						continue;
					}

					net.neoforged.neoforge.event.EventHooks.finalizeMobSpawnSpawner(mob, serverLevel,
							serverLevel.getCurrentDifficultyAt(entity.blockPosition()), EntitySpawnReason.SPAWNER, null, this, true);
				}

				if (!serverLevel.tryAddFreshEntityWithPassengers(entity)) {
					continue;
				}

				serverLevel.levelEvent(2004, pos, 0);
				serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, blockpos);
				if (entity instanceof Mob) {
					((Mob) entity).spawnAnim();
				}
			}
		}
	}

	public void floodBehavior(Player playerIn, BlockPos pos, InteractionHand hand, float hitX, float hitY, float hitZ) {
		if (hasSpecialInteraction() && level != null && !level.isClientSide()) {
			ItemStack stack = playerIn.getItemInHand(hand);
			int random = level.getRandom().nextInt(100);
			if (stack.getItem() == Items.BUCKET && !playerIn.hasInfiniteMaterials()) {
				level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				ItemStack floodBucket = LootHelper.getFloodBucket();

				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, floodBucket);
				} else if (!playerIn.getInventory().add(floodBucket)) {
					playerIn.drop(floodBucket, false);
				}
			}

			if (random < 50) {
				FireworkRocketEntity firework = new FireworkRocketEntity(level, (double) ((float) pos.getX() + hitX), (double) ((float) pos.getY() + hitY), (double) ((float) pos.getZ() + hitZ),
						getFirework(level.getRandom()));
				level.addFreshEntity(firework);
			}
		}
	}

	public void mooshroomBehavior(Player playerIn, BlockPos pos, InteractionHand hand) {
		if (hasSpecialInteraction() && level != null && !level.isClientSide()) {
			ItemStack stack = playerIn.getItemInHand(hand);
			if (stack.getItem() == Items.BOWL && !playerIn.hasInfiniteMaterials()) {
				level.playSound(null, pos, SoundEvents.COW_MILK, SoundSource.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				ItemStack soupStack = new ItemStack(StatueRegistry.SOUP.get());
				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, soupStack);
				} else if (!playerIn.getInventory().add(soupStack)) {
					playerIn.drop(soupStack, false);
				}
			}
		}
	}

	public void cowBehavior(Player playerIn, BlockPos pos, InteractionHand hand) {
		if (hasSpecialInteraction() && level != null && !level.isClientSide()) {
			ItemStack stack = playerIn.getItemInHand(hand);
			if (stack.getItem() == Items.BUCKET && !playerIn.hasInfiniteMaterials()) {
				level.playSound(null, pos, SoundEvents.COW_MILK, SoundSource.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, new ItemStack(Items.MILK_BUCKET));
				} else if (!playerIn.getInventory().add(new ItemStack(Items.MILK_BUCKET))) {
					playerIn.drop(new ItemStack(Items.MILK_BUCKET), false);
				}
			}
		}
	}

	public void giveEffect(Player player, BlockPos pos, Holder<MobEffect> effectHolder) {
		if (hasSpecialInteraction() && level != null && !level.isClientSide()) {
			if (level.getRandom().nextDouble() <= 0.1F) {
				if (player.getEffect(effectHolder) == null) {
					player.addEffect(new MobEffectInstance(effectHolder, 20 * 20, 1, true, true));
				}
			}
		}
	}

	@Override
	@Nullable
	public Either<BlockEntity, Entity> getOwner() {
		if (this.isSpawner()) {
			return com.mojang.datafixers.util.Either.left(this);
		}
		return null;
	}
}
