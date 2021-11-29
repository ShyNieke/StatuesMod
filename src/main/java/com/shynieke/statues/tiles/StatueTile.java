package com.shynieke.statues.tiles;

import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.init.StatueTiles;
import com.shynieke.statues.recipes.LootInfo;
import com.shynieke.statues.recipes.StatueLootList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class StatueTile extends AbstractStatueTile{

	public StatueTile() {
		this(StatueTiles.STATUE);
	}

	public StatueTile(TileEntityType<?> tileType) {
		super(tileType);
	}

	public void playSound(SoundEvent sound, BlockPos pos) {
		playSound(sound, pos, 1F);
	}

	public void playSound(SoundEvent sound, BlockPos pos, float pitch) {
		if(makesSounds()) {
			level.playSound(null, pos, sound, SoundCategory.NEUTRAL, 1F, pitch);
		}
	}

	public void giveItem(LootInfo loot, PlayerEntity playerIn) {
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
					if(random <= 50)
					{
						playerIn.drop(stack2, true);
					}
				}

				if(stack3 != null && stack3 != ItemStack.EMPTY){
					if(random <= 10)
					{
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
				if(entityIn instanceof RabbitEntity) {
					RabbitEntity rabbit = (RabbitEntity) entityIn;
					rabbit.setRabbitType(99);
					rabbit.teleportTo(worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ());

					level.addFreshEntity(rabbit);
				} else if(entityIn instanceof CreeperEntity) {
					CreeperEntity creeper = (CreeperEntity) entityIn;
					creeper.moveTo((double)worldPosition.getX() + 0.5D, (double)worldPosition.getY(), (double)worldPosition.getZ() + 0.5D, 0.0F, 0.0F);
					CompoundNBT tag = new CompoundNBT();
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

	public void floodBehavior(PlayerEntity playerIn, BlockPos pos, Hand hand, float hitX, float hitY, float hitZ) {
		if(level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			int random = level.random.nextInt(100);
			if (stack.getItem() == Items.BUCKET && !playerIn.abilities.instabuild) {
				level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundCategory.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				ItemStack floodbucket = StatueLootList.getFloodBucket();

				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, floodbucket);
				}
				else if (!playerIn.inventory.add(floodbucket)) {
					playerIn.drop(floodbucket, false);
				}
			}

			if (random < 50) {
				FireworkRocketEntity firework = new FireworkRocketEntity(level, (double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ), getFirework(level.random));
				level.addFreshEntity(firework);
			}
		}
	}

	public void mooshroomBehavior(PlayerEntity playerIn, BlockPos pos, Hand hand) {
		if(level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			if (stack.getItem() == Items.BOWL && !playerIn.abilities.instabuild) {
				level.playSound(null, pos, SoundEvents.COW_MILK, SoundCategory.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				ItemStack soupStack = new ItemStack(StatueRegistry.SOUP.get());
				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, soupStack);
				} else if (!playerIn.inventory.add(soupStack)) {
					playerIn.drop(soupStack, false);
				}
			}
		}
	}

	public void cowBehavior(PlayerEntity playerIn, BlockPos pos, Hand hand) {
		if(level != null && !level.isClientSide) {
			ItemStack stack = playerIn.getItemInHand(hand);
			if (stack.getItem() == Items.BUCKET && !playerIn.abilities.instabuild) {
				level.playSound(null, pos, SoundEvents.COW_MILK, SoundCategory.NEUTRAL, 1F, 1F);
				stack.shrink(1);

				if (stack.isEmpty()) {
					playerIn.setItemInHand(hand, new ItemStack(Items.MILK_BUCKET));
				} else if (!playerIn.inventory.add(new ItemStack(Items.MILK_BUCKET))) {
					playerIn.drop(new ItemStack(Items.MILK_BUCKET), false);
				}
			}
		}
	}

	public void giveEffect(BlockPos pos, PlayerEntity player, Effect effect) {
		if(isStatueAble() && level != null) {
			int random = level.random.nextInt(100);
			if(hasExternalUse()) {
				if(random < 10) {
					if (!level.isClientSide) {
						if (player.getEffect(effect) == null) {
							player.addEffect(new EffectInstance(effect, 20 * 20, 1, true, true));
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
		CompoundNBT nbt = new CompoundNBT();
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

		ListNBT explosions = new ListNBT();
		explosions.add(nbt);

		CompoundNBT fireworkTag = new CompoundNBT();
		fireworkTag.put("Explosions", explosions);
		fireworkTag.putByte("Flight", (byte) 1);
		CompoundNBT stackTag = firework.getOrCreateTag();
		stackTag.put("Fireworks", fireworkTag);
		firework.setTag(stackTag);

		return firework;
	}


}
