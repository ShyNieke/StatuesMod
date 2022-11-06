package com.shynieke.statues.blockentities;

import com.shynieke.statues.recipe.LootRecipe;
import com.shynieke.statues.registry.StatueBlockEntities;
import com.shynieke.statues.registry.StatueRegistry;
import com.shynieke.statues.util.LootHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class StatueBlockEntity extends AbstractStatueBlockEntity {

	protected LootRecipe cachedLootRecipe;

	public StatueBlockEntity(BlockPos pos, BlockState state) {
		this(StatueBlockEntities.STATUE.get(), pos, state);
	}

	public StatueBlockEntity(BlockEntityType<?> tileType, BlockPos pos, BlockState state) {
		super(tileType, pos, state);
	}

	public void playSound(SoundEvent sound, BlockPos pos) {
		playSound(sound, pos, 1F);
	}

	public void playSound(SoundEvent sound, BlockPos pos, float pitch) {
		if (makesSounds()) {
			level.playSound(null, pos, sound, SoundSource.NEUTRAL, 1F, pitch);
		}
	}

	public void giveItem(Player playerIn) {
		if (level != null && isStatueAble() && canDropLoot()) {
			LootRecipe loot;
			if (cachedLootRecipe != null) {
				loot = cachedLootRecipe;
			} else {
				loot = cachedLootRecipe = LootHelper.getMatchingLoot(level, getBlockState().getBlock());
			}

			double random = level.random.nextDouble();
			if (!isDecorative()) {
				ItemStack stack1 = loot.getResultItem();
				float chance1 = loot.getChance1();
				ItemStack stack2 = loot.getResultItem2();
				float chance2 = loot.getChance2();
				ItemStack stack3 = loot.getResultItem3();
				float chance3 = loot.getChance3();

				if (stack1 != null && stack1 != ItemStack.EMPTY) {
					if (random <= chance1) {
						playerIn.drop(stack1, true);
					}
				}

				if (stack2 != null && stack2 != ItemStack.EMPTY) {
					if (random <= chance2) {
						playerIn.drop(stack2, true);
					}
				}

				if (stack3 != null && stack3 != ItemStack.EMPTY) {
					if (random <= chance3) {
						playerIn.drop(stack3, true);
					}
				}
				setStatueAble(false);
			}
		}
	}

	public void summonMob(LivingEntity entityIn) {
		if (level != null && isSpawner()) {
			int random = level.random.nextInt(100);

			if (random < 1) {
				if (entityIn instanceof Rabbit) {
					Rabbit rabbit = (Rabbit) entityIn;
					rabbit.setRabbitType(99);
					rabbit.teleportTo(worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ());

					level.addFreshEntity(rabbit);
				} else if (entityIn instanceof Creeper) {
					Creeper creeper = (Creeper) entityIn;
					creeper.moveTo((double) worldPosition.getX() + 0.5D, (double) worldPosition.getY(), (double) worldPosition.getZ() + 0.5D, 0.0F, 0.0F);
					CompoundTag tag = new CompoundTag();
					creeper.addAdditionalSaveData(tag);

					tag.putShort("ExplosionRadius", (short) 0);

					creeper.readAdditionalSaveData(tag);
					level.addFreshEntity(creeper);
					creeper.spawnAnim();
				} else {
					entityIn.teleportTo(worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ());
					level.addFreshEntity(entityIn);
				}
			}
			setStatueAble(false);
		}
	}

	public void floodBehavior(Player playerIn, BlockPos pos, InteractionHand hand, float hitX, float hitY, float hitZ) {
		if (hasSpecialInteraction() && level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			int random = level.random.nextInt(100);
			if (stack.getItem() == Items.BUCKET && !playerIn.getAbilities().instabuild) {
				level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				ItemStack floodbucket = LootHelper.getFloodBucket();

				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, floodbucket);
				} else if (!playerIn.getInventory().add(floodbucket)) {
					playerIn.drop(floodbucket, false);
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

	public void giveEffect(BlockPos pos, Player player, MobEffect effect) {
		if (hasSpecialInteraction() && isStatueAble() && level != null && !level.isClientSide) {
			if (level.random.nextDouble() <= 0.1F) {
				if (player.getEffect(effect) == null) {
					player.addEffect(new MobEffectInstance(effect, 20 * 20, 1, true, true));
				}
			}
		}
	}
}
