package com.shynieke.statues.blockentities;

import com.shynieke.statues.blocks.AbstractStatueBase;
import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.util.LootHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatueBlockEntity extends AbstractStatueBlockEntity {

	protected LootRecipe cachedLootRecipe;

	public StatueBlockEntity(BlockPos pos, BlockState state) {
		this(StatueBlockEntities.STATUE.get(), pos, state);
	}

	public StatueBlockEntity(BlockEntityType<?> tileType, BlockPos pos, BlockState state) {
		super(tileType, pos, state);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, StatueBlockEntity blockEntity) {
		if (state.getValue(AbstractStatueBase.INTERACTIVE) && state.getBlock() instanceof AbstractStatueBase) {
			if (!blockEntity.isStatueInteractable()) {
				blockEntity.interactCooldown--;

				if (blockEntity.interactCooldown == 0) {
					blockEntity.interactCooldown = 200;
					blockEntity.setStatueInteractable(true);
				}
			}
			if (!blockEntity.isStatueAble()) {
				blockEntity.cooldown--;

				if (blockEntity.cooldown == 0) {
					blockEntity.cooldown = 200;
					blockEntity.setStatueAble(true);
				}
			} else {
				//Insert spawner behavior
				if (blockEntity.isSpawner()) {
					blockEntity.summonMob((ServerLevel) level);
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
				giveItem(player);
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

	public void giveItem(Player playerIn) {
		if (level != null) {
			LootRecipe loot;
			if (cachedLootRecipe != null) {
				loot = cachedLootRecipe;
			} else {
				loot = cachedLootRecipe = LootHelper.getMatchingLoot(level, new ItemStack(getBlockState().getBlock()));
			}

			double random = level.random.nextDouble();
			ItemStack stack1 = loot.getResultItem().copy();
			float chance1 = loot.getChance1();
			if (!stack1.isEmpty() && random <= chance1) {
				exportItem(playerIn, stack1);
			}

			ItemStack stack2 = loot.getResultItem2().copy();
			float chance2 = loot.getChance2();
			if (!stack2.isEmpty() && random <= chance2) {
				exportItem(playerIn, stack2);
			}

			ItemStack stack3 = loot.getResultItem3().copy();
			float chance3 = loot.getChance3();
			if (!stack3.isEmpty() && random <= chance3) {
				exportItem(playerIn, stack3);
			}
		}
	}

	private void exportItem(Player player, ItemStack stack) {
		if (canAutomate()) {
			List<BiggestInventory> inventoryList = new ArrayList<>();
			for (Direction dir : Direction.values()) {
				BlockPos offPos = worldPosition.relative(dir);
				if (this.level.isAreaLoaded(worldPosition, 1)) {
					BlockEntity foundTile = this.level.getBlockEntity(offPos);
					if (foundTile != null) {
						ResourceLocation typeLocation = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(foundTile.getType());
						boolean flag2 = typeLocation != null;
						if (flag2 && !foundTile.isRemoved() && foundTile.hasLevel() && foundTile.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
							IItemHandler itemHandler = foundTile.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).orElse(null);
							if (itemHandler != null) {
								inventoryList.add(new BiggestInventory(offPos, itemHandler.getSlots(), dir.getOpposite()));
							}
						}
					}
				}
			}
			inventoryList.sort(Collections.reverseOrder());
			if (inventoryList.isEmpty()) {
				player.drop(stack, true);
			} else {
				for (BiggestInventory inventory : inventoryList) {
					IItemHandler itemHandler = inventory.getIItemHandler(this.level);
					ItemStack rest = ItemHandlerHelper.insertItem(itemHandler, stack, false);
					if (rest.isEmpty()) {
						break;
					}
				}
			}
		} else {
			player.drop(stack, true);
		}
	}

	public void summonMob(ServerLevel serverLevel) {
		final BlockPos pos = getBlockPos();
		final int spawnerLevel = getSpawnerLevel() + 1;
		int spawnCount = serverLevel.random.nextInt(spawnerLevel) + 1;
		EntityType<?> entityType = getStatue().getEntity();

		for (int i = 0; i < spawnCount; i++) {
			double d0 = (double) pos.getX() + (serverLevel.random.nextDouble() - serverLevel.random.nextDouble()) * (double) 4 + 0.5D;
			double d1 = (double) (pos.getY() + serverLevel.random.nextInt(3) - 1);
			double d2 = (double) pos.getZ() + (serverLevel.random.nextDouble() - serverLevel.random.nextDouble()) * (double) 4 + 0.5D;
			if (serverLevel.noCollision(entityType.getAABB(d0, d1, d2))) {
				BlockPos blockpos = new BlockPos(d0, d1, d2);

				if (!SpawnPlacements.checkSpawnRules(entityType, serverLevel, MobSpawnType.SPAWNER, blockpos, serverLevel.getRandom())) {
					continue;
				}

				Entity entity = entityType.create(level);
				if (entity == null) {
					continue;
				}
				entity.moveTo(d0, d1, d2, entity.getYRot(), entity.getXRot());

				int k = serverLevel.getEntitiesOfClass(entity.getClass(), (new AABB((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (double)
						(pos.getX() + 1), (double) (pos.getY() + 1), (double) (pos.getZ() + 1))).inflate((double) 4)).size();
				if (k >= 6 + spawnerLevel) {
					continue;
				}

				entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), level.random.nextFloat() * 360.0F, 0.0F);
				if (entity instanceof Mob mob) {
					getStatue().adjustSpawnedEntity(mob);

					net.minecraftforge.eventbus.api.Event.Result res = net.minecraftforge.event.ForgeEventFactory.canEntitySpawn(mob, serverLevel,
							(float) entity.getX(), (float) entity.getY(), (float) entity.getZ(), null, MobSpawnType.SPAWNER);
					if (res == net.minecraftforge.eventbus.api.Event.Result.DENY) continue;
					if (res == net.minecraftforge.eventbus.api.Event.Result.DEFAULT)
						if (!mob.checkSpawnRules(serverLevel, MobSpawnType.SPAWNER) || !mob.checkSpawnObstruction(serverLevel)) {
							continue;
						}

					if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mob, (LevelAccessor) serverLevel,
							(float) entity.getX(), (float) entity.getY(), (float) entity.getZ(), null, MobSpawnType.SPAWNER))
						((Mob) entity).finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()),
								MobSpawnType.SPAWNER, (SpawnGroupData) null, (CompoundTag) null);
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
		if (hasSpecialInteraction() && level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			int random = level.random.nextInt(100);
			if (stack.getItem() == Items.BUCKET && !playerIn.getAbilities().instabuild) {
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
						getFirework(level.random));
				level.addFreshEntity(firework);
			}
		}
	}

	public void mooshroomBehavior(Player playerIn, BlockPos pos, InteractionHand hand) {
		if (hasSpecialInteraction() && level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			if (stack.getItem() == Items.BOWL && !playerIn.getAbilities().instabuild) {
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
		if (hasSpecialInteraction() && level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			if (stack.getItem() == Items.BUCKET && !playerIn.getAbilities().instabuild) {
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

	public void giveEffect(Player player, BlockPos pos, MobEffect effect) {
		if (hasSpecialInteraction() && level != null && !level.isClientSide) {
			if (level.random.nextDouble() <= 0.1F) {
				if (player.getEffect(effect) == null) {
					player.addEffect(new MobEffectInstance(effect, 20 * 20, 1, true, true));
				}
			}
		}
	}
}
