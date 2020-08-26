package com.shynieke.statues.compat.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class StatueCurioCapability implements ICurio {
    private final ItemStack displayedItem;

    public StatueCurioCapability(ItemStack item) {
        this.displayedItem = item;
    }

    @Override
    public boolean canRightClickEquip() {
        return true;
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.push();

        translateToHeadLevel(livingEntity, matrixStack);
        translateToFace(matrixStack);
        matrixStack.translate(0.0F, 2.975F, 1.275F);
        matrixStack.scale(0.625F, 0.625F, 0.625F);

        Minecraft.getInstance().getItemRenderer().renderItem(this.displayedItem, ItemCameraTransforms.TransformType.HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer);
        matrixStack.pop();
    }

    public static void translateToHeadLevel(LivingEntity livingEntity, MatrixStack matrixStack) {
        matrixStack.translate(0, -livingEntity.getEyeHeight(), 0);
        if (livingEntity.isSneaking())
            matrixStack.translate(0.25F * MathHelper.sin(livingEntity.rotationPitch * (float) Math.PI / 180), 0.25F * MathHelper.cos(livingEntity.rotationPitch * (float) Math.PI / 180), 0F);
    }

    public static void translateToFace(MatrixStack matrixStack) {
        matrixStack.rotate(Vector3f.YP.rotation(90F));
        matrixStack.rotate(Vector3f.XP.rotation(180F));
        matrixStack.translate(0f, -4.35f, -1.27f);
    }
}
