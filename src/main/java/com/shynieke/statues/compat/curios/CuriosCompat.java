package com.shynieke.statues.compat.curios;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CuriosCompat {
    public static void sendImc(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("statue"));
    }

    public static class StatueCurioCapabilityProvider implements ICapabilityProvider {

        private ItemStack stack;

        public StatueCurioCapabilityProvider(ItemStack stack) {
            this.stack = stack;
        }

        final LazyOptional<ICurio> curio = LazyOptional.of(() -> new StatueCurioCapability(stack));

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CuriosCapability.ITEM.orEmpty(cap, curio);
        }
    }

    public static boolean isStackInCuriosSlot(ItemStack stack, LivingEntity player) {
        return CuriosAPI.getCurioEquipped(stack.getItem(), player).isPresent();
    }
}
