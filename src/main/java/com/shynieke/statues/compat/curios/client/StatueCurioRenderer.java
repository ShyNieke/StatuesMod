package com.shynieke.statues.compat.curios.client;

import com.shynieke.statues.init.StatueRegistry;
import com.shynieke.statues.items.StatueBlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class StatueCurioRenderer {//implements ICurioRenderer {
	public static void setupRenderer(FMLClientSetupEvent event) {
		for(RegistryObject<Item> itemObject : StatueRegistry.ITEMS.getEntries()) {
			if(itemObject.isPresent() && itemObject.get() instanceof StatueBlockItem) {
//				CuriosRendererRegistry.register(itemObject.get(), () -> new StatueCurioRenderer());
			}
		}
	}

//	@Override
//	public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//		poseStack.pushPose();
//
//		Minecraft mc = Minecraft.getInstance();
//		PlayerRenderer playerrenderer = (PlayerRenderer)mc.getEntityRenderDispatcher().<AbstractClientPlayer>getRenderer(mc.player);
//		playerrenderer.getModel().getHead().translateAndRotate(poseStack);
//		poseStack.translate(0.0D, -0.25D, 0.0D);
//		poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
//		poseStack.scale(0.65F, -0.65F, -0.65F);
//
//		mc.getItemRenderer().renderStatic(stack, TransformType.HEAD, light, OverlayTexture.NO_OVERLAY, poseStack, renderTypeBuffer, 0);
//		poseStack.popPose();
//	}
}
