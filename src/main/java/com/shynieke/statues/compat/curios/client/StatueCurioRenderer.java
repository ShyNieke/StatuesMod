package com.shynieke.statues.compat.curios.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.statues.items.StatueBlockItem;
import com.shynieke.statues.registry.StatueRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class StatueCurioRenderer implements ICurioRenderer {
	public static void setupRenderer(FMLClientSetupEvent event) {
		for (RegistryObject<Item> itemObject : StatueRegistry.ITEMS.getEntries()) {
			if (itemObject.isPresent() && itemObject.get() instanceof StatueBlockItem) {
				CuriosRendererRegistry.register(itemObject.get(), () -> new StatueCurioRenderer());
			}
		}
	}

	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack,
																		  RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource,
																		  int light, float limbSwing, float limbSwingAmount, float partialTicks,
																		  float ageInTicks, float netHeadYaw, float headPitch) {
		poseStack.pushPose();

		Minecraft mc = Minecraft.getInstance();
		PlayerRenderer playerrenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().<AbstractClientPlayer>getRenderer(mc.player);
		playerrenderer.getModel().getHead().translateAndRotate(poseStack);
		poseStack.translate(0.0D, -0.25D, 0.0D);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		poseStack.scale(0.65F, -0.65F, -0.65F);

		mc.getItemRenderer().renderStatic(stack, TransformType.HEAD, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 0);
		poseStack.popPose();
	}
}
