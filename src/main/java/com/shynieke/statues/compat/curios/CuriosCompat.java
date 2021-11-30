package com.shynieke.statues.compat.curios;

import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class CuriosCompat {
    public static void sendImc(InterModEnqueueEvent event) {
//        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("statue").icon(new ResourceLocation(Reference.MOD_ID, "item/statuecore")).size(1).build());
    }

//    public static class StatueCurioCapabilityProvider implements ICapabilityProvider {
//
//        private ItemStack stack;
//
//        public StatueCurioCapabilityProvider(ItemStack stack) {
//            this.stack = stack;
//        }
//
//        final LazyOptional<ICurio> curio = LazyOptional.of(() -> new StatueCurioCapability(stack));
//
//        @Nonnull
//        @Override
//        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//            return CuriosCapability.ITEM.orEmpty(cap, curio);
//        }
//    }

//    public static boolean isStackInCuriosSlot(ItemStack stack, LivingEntity player) {
//        return CuriosApi.getCuriosHelper().findEquippedCurio(stack.getItem(), player).isPresent();
//    }
}
