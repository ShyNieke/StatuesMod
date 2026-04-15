package com.shynieke.statues.compat.curios.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.statues.items.StatueBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class StatueCurioRenderer implements ICurioRenderer {
	public static void setupRenderer() {
		for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
			if (item instanceof StatueBlockItem) {
				ICurioRenderer.register(item, StatueCurioRenderer::new);
			}
		}
	}

	private final ItemStackRenderState statueRenderState = new ItemStackRenderState();

	@Override
	public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(ItemStack stack,
	                                                                                         SlotContext slotContext,
	                                                                                         PoseStack poseStack,
	                                                                                         SubmitNodeCollector nodeCollector,
	                                                                                         int packedLight, S renderState,
	                                                                                         RenderLayerParent<S, M> renderLayerParent,
	                                                                                         EntityRendererProvider.Context context,
	                                                                                         float yRotation, float xRotation) {
		poseStack.pushPose();

		Minecraft mc = Minecraft.getInstance();
		AvatarRenderer<?> playerrenderer = (AvatarRenderer) mc.getEntityRenderDispatcher().<AbstractClientPlayer>getRenderer(mc.player);
		playerrenderer.getModel().getHead().translateAndRotate(poseStack);
		poseStack.translate(0.0D, -0.25D, 0.0D);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		poseStack.scale(0.65F, -0.65F, -0.65F);

		context.getItemModelResolver()
				.updateForTopItem(statueRenderState, stack, ItemDisplayContext.HEAD, null, null, 0);

		statueRenderState.submit(poseStack, nodeCollector, packedLight, OverlayTexture.NO_OVERLAY, renderState.outlineColor);

		poseStack.popPose();
	}
}
