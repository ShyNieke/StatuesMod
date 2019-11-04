package com.svennieke.statues.handlers;

import com.svennieke.statues.Statues;
import com.svennieke.statues.blocks.AbstractStatueBase;
import com.svennieke.statues.items.StatueBlockItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class InventoryHandler {
    @SubscribeEvent
    public void onLivingDrop(LivingDeathEvent event) {
        Entity source = event.getSource().getTrueSource();

        if(source instanceof PlayerEntity && !(source instanceof FakePlayer)) {
            PlayerEntity player = (PlayerEntity) source;
            LivingEntity killedEntity = event.getEntityLiving();
            PlayerInventory inventory = player.inventory;
            for(int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                Statues.LOGGER.info(stack.getItem());
                if(stack.getItem() instanceof StatueBlockItem) {
                    AbstractStatueBase statue = (AbstractStatueBase) ((StatueBlockItem) stack.getItem()).getBlock();
                    Statues.LOGGER.info(EntityType.ZOMBIE.getRegistryName());
                    Statues.LOGGER.info(killedEntity.getType().getRegistryName());
                    if(killedEntity.getType().getRegistryName().equals(statue.getEntity())) {
                        Statues.LOGGER.info("Statue isChild: " + statue.isBaby());
                        Statues.LOGGER.info("isChild: " + killedEntity.isChild());
                        if(statue.isBaby() == killedEntity.isChild()) {
                            Statues.LOGGER.info("Has tag: " + stack.hasTag());
                            if(stack.hasTag()) {
                                CompoundNBT tag = stack.getTag();
                                Statues.LOGGER.info(tag.toString());
                                if(tag.get("Traits") == null) {
                                    ListNBT list = new ListNBT();
                                    CompoundNBT compoundnbt = new CompoundNBT();

                                    compoundnbt.put("Traits", list);
                                    stack.setTag(compoundnbt);
                                } else {
                                    ListNBT list = (ListNBT) tag.get("Traits");
                                    Statues.LOGGER.info(list.size() + " " + list.toString());
//                                    CompoundNBT compoundnbt = (CompoundNBT) list.get(0);
//                                    int killed = compoundnbt.getInt("mobsKilled");
//                                    int level = getLevel(killed);
//                                    compoundnbt.putInt("statueLevel", level);
//                                    compoundnbt.putInt("mobKilled", killed++);
//
//                                    list.add(compoundnbt);
//                                    tag.put("Traits", list);
//                                    stack.setTag(tag);
                                }
//                                Statues.LOGGER.info(stack.getTag().toString());
                            } else {

                            }
                        }
                    }
                }
            }
        }
    }

    public ItemStack getStackInSlot(List<NonNullList<ItemStack>> allInventories, int index) {
        List<ItemStack> list = null;

        for(NonNullList<ItemStack> nonnulllist : allInventories) {
            if (index < nonnulllist.size()) {
                list = nonnulllist;
                break;
            }

            index -= nonnulllist.size();
        }

        return list == null ? ItemStack.EMPTY : list.get(index);
    }

    public int getLevel(int killedMobs) {
        if(killedMobs >= 0 && killedMobs <=9) {
            return 1;
        } else if(killedMobs >= 10 && killedMobs <= 29) {
            return 2;
        } else if (killedMobs >= 30 && killedMobs <= 49) {
            return 3;
        } else {
            return 4;
        }
    }
}
