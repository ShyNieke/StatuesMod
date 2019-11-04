package com.svennieke.statues.recipes;

import net.minecraft.item.ItemStack;

public class StatueLootInfo {
    private String statue;
    private LootInfo loot;

    public StatueLootInfo(String statue, LootInfo loot) {
        this.statue = statue;
        this.loot = loot;
    }

    public String getStatue() {
        return this.statue;
    }

    public LootInfo getLoot() {
        return this.loot;
    }

    public void setLoot(LootInfo stack1) {
        this.loot = stack1;
    }

    public ItemStack getStack1() {
        return loot.getStack1();
    }

    public void setStack1(ItemStack stack1) {
        this.loot.setStack1(stack1);;
    }

    public ItemStack getStack2() {
        return loot.getStack2();
    }

    public void setStack2(ItemStack stack2) {
        this.loot.setStack2(stack2);
    }

    public ItemStack getStack3() {
        return loot.getStack3();
    }

    public void setStack3(ItemStack stack3) {
        this.loot.setStack3(stack3);
    }

    public boolean hasLoot() {
        return !loot.hasLoot();
    }
}
