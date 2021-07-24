package com.shynieke.statues.compat.curios;

public class StatueCurioCapability { //implements ICurio {
//    private final ItemStack displayedItem;
//
//    public StatueCurioCapability(ItemStack item) {
//        this.displayedItem = item;
//    }
//
//    @Override
//    public boolean canRightClickEquip() {
//        return true;
//    }
//
//    @Override
//    public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
//        return true;
//    }
//
//    @Override
//    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        matrixStack.pushPose();
//
//        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get("default").getModel().getHead().translateAndRotate(matrixStack);
//        matrixStack.translate(0.0D, -0.25D, 0.0D);
//        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
//        matrixStack.scale(0.65F, -0.65F, -0.65F);
//
//        Minecraft.getInstance().getItemRenderer().renderStatic(this.displayedItem, ItemCameraTransforms.TransformType.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer);
//        matrixStack.popPose();
//    }
}
