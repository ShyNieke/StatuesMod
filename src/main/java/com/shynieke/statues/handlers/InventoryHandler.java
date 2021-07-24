package com.shynieke.statues.handlers;

public class InventoryHandler {
//    @SubscribeEvent
//    public void onLivingDrop(LivingDeathEvent event) {
//        Entity source = event.getSource().getEntity();
//
//        if(source instanceof Player && !(source instanceof FakePlayer)) {
//            Player player = (Player) source;
//            LivingEntity killedEntity = event.getEntityLiving();
//            Inventory inventory = player.getInventory();
//            for(int i = 0; i < inventory.getContainerSize(); i++) {
//                ItemStack stack = inventory.getItem(i);
//                Statues.LOGGER.info(stack.getItem());
//                if(stack.getItem() instanceof StatueBlockItem) {
//                    AbstractStatueBase statue = (AbstractStatueBase) ((StatueBlockItem) stack.getItem()).getBlock();
//                    Statues.LOGGER.info(EntityType.ZOMBIE.getRegistryName());
//                    Statues.LOGGER.info(killedEntity.getType().getRegistryName());
//                    if(killedEntity.getType().getRegistryName().equals(statue.getEntity().getRegistryName())) {
//                        Statues.LOGGER.info("Statue isChild: " + statue.isBaby());
//                        Statues.LOGGER.info("isChild: " + killedEntity.isBaby());
//                        if(statue.isBaby() == killedEntity.isBaby()) {
//                            Statues.LOGGER.info("Has tag: " + stack.hasTag());
//                            if(stack.hasTag()) {
//                                CompoundTag tag = stack.getTag();
//                                Statues.LOGGER.info(tag.toString());
//                                if(tag.get("Traits") == null) {
//                                    ListTag list = new ListTag();
//                                    CompoundTag compoundnbt = new CompoundTag();
//
//                                    compoundnbt.put("Traits", list);
//                                    stack.setTag(compoundnbt);
//                                } else {
//                                    ListTag list = (ListTag) tag.get("Traits");
//                                    Statues.LOGGER.info(list.size() + " " + list.toString());
////                                    CompoundTag compoundnbt = (CompoundTag) list.get(0);
////                                    int killed = compoundnbt.getInt("mobsKilled");
////                                    int level = getLevel(killed);
////                                    compoundnbt.putInt("statueLevel", level);
////                                    compoundnbt.putInt("mobKilled", killed++);
////
////                                    list.add(compoundnbt);
////                                    tag.put("Traits", list);
////                                    stack.setTag(tag);
//                                }
////                                Statues.LOGGER.info(stack.getTag().toString());
//                            } else {
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public ItemStack getStackInSlot(List<NonNullList<ItemStack>> allInventories, int index) {
//        List<ItemStack> list = null;
//
//        for(NonNullList<ItemStack> nonnulllist : allInventories) {
//            if (index < nonnulllist.size()) {
//                list = nonnulllist;
//                break;
//            }
//
//            index -= nonnulllist.size();
//        }
//
//        return list == null ? ItemStack.EMPTY : list.get(index);
//    }
//
//    public int getLevel(int killedMobs) {
//        if(killedMobs >= 0 && killedMobs <=9) {
//            return 1;
//        } else if(killedMobs >= 10 && killedMobs <= 29) {
//            return 2;
//        } else if (killedMobs >= 30 && killedMobs <= 49) {
//            return 3;
//        } else {
//            return 4;
//        }
//    }
}
