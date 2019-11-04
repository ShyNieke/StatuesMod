package com.svennieke.statues.compat.ct;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.svennieke.statues.blocks.AbstractStatueBase;
import com.svennieke.statues.compat.ct.loot.CTStatueLoot;
import com.svennieke.statues.items.StatueBlockItem;
import com.svennieke.statues.recipes.StatueLootList;

public class ActionChangeLoot implements IUndoableAction {
    public final String statueName;
    public final CTStatueLoot loot;
    public final CTStatueLoot oldLoot;
    public IItemStack statueStack;

    public ActionChangeLoot(String statueName, CTStatueLoot loot) {
        this.statueName = statueName;
        this.oldLoot = new CTStatueLoot(StatueLootList.getLootInfo(statueName).getLoot());
        this.loot = loot;
    }

    public ActionChangeLoot(IItemStack statue, CTStatueLoot loot) {
        if(statue.getInternal().getItem() instanceof StatueBlockItem) {
            StatueBlockItem blockItem = (StatueBlockItem) statue.getInternal().getItem();
            AbstractStatueBase statueBlock = (AbstractStatueBase) blockItem.getBlock();
            this.statueName = statueBlock.getLootName();
        } else {
            this.statueStack = statue;
            this.statueName = "";
        }
        this.oldLoot = new CTStatueLoot(StatueLootList.getLootInfo(statueName).getLoot());
        this.loot = loot;
    }

    @Override
    public void undo() {
        StatueLootList.changeLoot(statueName, oldLoot.getInternal());
    }

    @Override
    public String describeUndo() {
        if(statueName.isEmpty()) {
            return String.format("Could not undo loot changes since the stack %s specified is not recognized as a statue, \n" +
                    "if you think this is a bug please report this to the author.", statueStack.toString());
        } else {
            return String.format("Undid loot changes to statue: " + statueName);
        }
    }

    @Override
    public void apply() {
        StatueLootList.changeLoot(statueName, loot.getInternal());
    }

    @Override
    public String describe() {
        if(statueName.isEmpty()) {
            return String.format("Could not change loot since the stack %s specified is not recognized as a statue, \n" +
                    "if you think this is a bug please report this to the author.", statueStack.toString());
        } else {
            return String.format("Applied loot changes to statue: " + statueName);
        }
    }
}
