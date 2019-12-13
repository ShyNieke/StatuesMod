package com.shynieke.statues.compat.ct;

import com.shynieke.statues.compat.list.StatueLootList;
import com.shynieke.statues.config.StatuesConfigGen;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;

public class ChangeStatueLoot implements IAction{

	private final ItemStack stack1;
	private final ItemStack stack2;
	private final ItemStack stack3;
	private final String statue;

	public ChangeStatueLoot(IItemStack stack1, IItemStack stack2, IItemStack stack3, String statue) {
		this.stack1 = CraftTweakerMC.getItemStack(stack1);
		this.stack2 = CraftTweakerMC.getItemStack(stack2);
		this.stack3 = CraftTweakerMC.getItemStack(stack3);
		this.statue = statue;
	}
	
	@Override
	public void apply()
	{
		if(!StatuesConfigGen.general.nonFunctional) {
			StatueLootList.changeLoot(statue, stack1, stack2, stack3);
		}
	}

	@Override
	public String describe() {
		return String.format(this.statue + "'s loot has been changed");	
	}

}
