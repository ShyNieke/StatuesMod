package com.shynieke.statues.compat.curios;

import com.shynieke.statues.Reference;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosCompat {
	public static void sendImc(InterModEnqueueEvent event) {
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("statue")
				.icon(new ResourceLocation(Reference.MOD_ID, "curios/core")).size(1).build());
	}

	public static boolean isStackInCuriosSlot(ItemStack stack, LivingEntity player) {
		return CuriosApi.getCuriosHelper().findEquippedCurio(stack.getItem(), player).isPresent();
	}

	public static ICapabilityProvider getCapability(ItemStack stack) {
		ICurio curio = new ICurio() {
			@Override
			public ItemStack getStack() {
				return stack;
			}

			@Override
			public boolean canEquipFromUse(SlotContext ctx) {
				return true;
			}
		};
		ICapabilityProvider provider = new ICapabilityProvider() {
			private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

			@NotNull
			@Override
			public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap,
													 @Nullable Direction side) {
				return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
			}
		};
		return provider;
	}
}
