package com.svennieke.statues.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.svennieke.statues.compat.ct.loot.CTStatueLoot;
import com.svennieke.statues.recipes.LootInfo;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

@ZenRegister
@Name("mods.statues.Loot")
public class ChangeLootCT {
    @Method
    public static void changeLoot(String statueName, CTStatueLoot loot) {
        CraftTweakerAPI.apply(new ActionChangeLoot(statueName, loot));
    }

    @Method
    public static void changeLoot(IItemStack statue, CTStatueLoot loot) {
        CraftTweakerAPI.apply(new ActionChangeLoot(statue, loot));
    }

    @Method
    public static void removeLoot(String statueName) {
        CraftTweakerAPI.apply(new ActionChangeLoot(statueName, new CTStatueLoot(LootInfo.EMPTY)));
    }

    @Method
    public static void removeLoot(IItemStack statue) {
        CraftTweakerAPI.apply(new ActionChangeLoot(statue, new CTStatueLoot(LootInfo.EMPTY)));
    }
}
