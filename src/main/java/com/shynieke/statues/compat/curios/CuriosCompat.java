package com.shynieke.statues.compat.curios;

import com.shynieke.statues.Reference;
import com.shynieke.statues.items.StatueBlockItem;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CuriosCompat {
	public static void sendImc(InterModEnqueueEvent event) {
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("statue")
				.icon(new ResourceLocation(Reference.MOD_ID, "item/statuecore")).size(1).build());
	}

	public static boolean isStackInCuriosSlot(ItemStack stack, LivingEntity player) {
		return CuriosApi.getCuriosHelper().findEquippedCurio(stack.getItem(), player).isPresent();
	}

	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		if (!(event.getObject().getItem() instanceof StatueBlockItem)) {
			return;
		}
		if(ModList.get().isLoaded("curios")) {
			ICurio curio = new ICurio() {
				@Override
				public ItemStack getStack() {
					return event.getObject();
				}

				@Override
				public boolean canEquipFromUse(SlotContext ctx) {
					return true;
				}
			};
			ICapabilityProvider provider = new ICapabilityProvider() {
				private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
														 @Nullable Direction side) {
					return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
				}
			};
			event.addCapability(CuriosCapability.ID_ITEM, provider);
		}
	}
}
