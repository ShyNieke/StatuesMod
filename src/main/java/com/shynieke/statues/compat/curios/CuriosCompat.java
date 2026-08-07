package com.shynieke.statues.compat.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;

public class CuriosCompat {
	/**
	 * Get the Statue curios stack from the player
	 *
	 * @param player The player
	 * @return The curio ItemStack (EMPTY if not present)
	 */
	public static ItemStack getCuriosStack(Player player) {
		Optional<ICuriosItemHandler> optionalItemHandler = CuriosApi.getCuriosInventory(player);
		if (optionalItemHandler.isPresent()) {
			ICuriosItemHandler inventory = optionalItemHandler.get();
			List<SlotResult> curios = inventory.findCurios("statue");
			for (SlotResult result : curios) {
				if (!result.stack().isEmpty()) {
					return result.stack();
				}
			}
		}
		return ItemStack.EMPTY;
	}
}
