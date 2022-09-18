package com.shynieke.statues.compat.ct;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.statues")
@ZenRegister
public class ChangeLootCT {

	@ZenMethod
	public static void changeLoot(IItemStack stack, IItemStack stack2, IItemStack stack3, String statue) {
		CraftTweakerAPI.apply(new ChangeStatueLoot(stack, stack2, stack3, statue));
	}
}
