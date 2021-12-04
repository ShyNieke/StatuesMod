package com.shynieke.statues.blockentities;

import com.shynieke.statues.init.StatueBlockEntities;
import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.recipes.LootInfo;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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

import java.util.Random;

public class StatueBlockEntity extends AbstractStatueBlockEntity {

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
		if(makesSounds()) {
			level.playSound(null, pos, sound, SoundSource.NEUTRAL, 1F, pitch);
		}
	}

	public void giveItem(LootInfo loot, Player playerIn) {
		if(level != null && isStatueAble()) {
			int random = level.random.nextInt(100);
			if(!isDecorative() && loot.hasLoot()) {
				ItemStack stack1 = loot.getStack1();
				ItemStack stack2 = loot.getStack2();
				ItemStack stack3 = loot.getStack3();

				if (stack1 != null && stack1 != ItemStack.EMPTY) {
					playerIn.drop(stack1, true);
				}

				if(stack2 != null && stack2 != ItemStack.EMPTY){
					if(random <= 50) {
						playerIn.drop(stack2, true);
					}
				}

				if(stack3 != null && stack3 != ItemStack.EMPTY){
					if(random <= 10) {
						playerIn.drop(stack3, true);
					}
				}
				setStatueAble(false);
			}
		}
	}

	public void summonMob(LivingEntity entityIn) {
		if(level != null && canSpawnMobs()) {
			int random = level.random.nextInt(100);

			if (random < 1) {
				if(entityIn instanceof Rabbit) {
					Rabbit rabbit = (Rabbit) entityIn;
					rabbit.setRabbitType(99);
					rabbit.teleportTo(worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ());

					level.addFreshEntity(rabbit);
				} else if(entityIn instanceof Creeper) {
					Creeper creeper = (Creeper) entityIn;
					creeper.moveTo((double)worldPosition.getX() + 0.5D, (double)worldPosition.getY(), (double)worldPosition.getZ() + 0.5D, 0.0F, 0.0F);
					CompoundTag tag = new CompoundTag();
					creeper.addAdditionalSaveData(tag);

					tag.putShort("ExplosionRadius", (short)0);

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
		if(level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			int random = level.random.nextInt(100);
			if (stack.getItem() == Items.BUCKET && !playerIn.getAbilities().instabuild) {
				level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				ItemStack floodbucket = StatueLootList.getFloodBucket();

				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, floodbucket);
				}
				else if (!playerIn.getInventory().add(floodbucket)) {
					playerIn.drop(floodbucket, false);
				}
			}

			if (random < 50) {
				FireworkRocketEntity firework = new FireworkRocketEntity(level, (double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ), getFirework(level.random));
				level.addFreshEntity(firework);
			}
		}
	}

	public void mooshroomBehavior(Player playerIn, BlockPos pos, InteractionHand hand) {
		if(level != null && !level.isClientSide) {
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
		if(level != null && !level.isClientSide) {
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
		if(isStatueAble() && level != null) {
			int random = level.random.nextInt(100);
			if(hasExternalUse()) {
				if(random < 10) {
					if (!level.isClientSide) {
						if (player.getEffect(effect) == null) {
							player.addEffect(new MobEffectInstance(effect, 20 * 20, 1, true, true));
						}
					}
				}
			}
		}
	}

	public static final int[] DYE_COLORS = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

	public ItemStack getFirework(Random rand) {
		ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
		firework.getOrCreateTag();
		CompoundTag nbt = new CompoundTag();
		nbt.putBoolean("Flicker", true);
		nbt.putBoolean("Trail", true);

		int[] colors = new int[rand.nextInt(8) + 1];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = DYE_COLORS[rand.nextInt(16)];
		}
		nbt.putIntArray("Colors", colors);
		byte type = (byte) (rand.nextInt(3) + 1);
		type = type == 3 ? 4 : type;
		nbt.putByte("Type", type);

		ListTag explosions = new ListTag();
		explosions.add(nbt);

		CompoundTag fireworkTag = new CompoundTag();
		fireworkTag.put("Explosions", explosions);
		fireworkTag.putByte("Flight", (byte) 1);
		CompoundTag stackTag = firework.getOrCreateTag();
		stackTag.put("Fireworks", fireworkTag);
		firework.setTag(stackTag);

		return firework;
	}
}
