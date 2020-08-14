package com.shynieke.statues.compat.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
        Minecraft.getInstance().getRenderManager().getSkinMap().get("default").getEntityModel().getModelHead().translateRotate(matrixStack);
        matrixStack.translate(0.0D, -0.25D, 0.0D);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
        matrixStack.scale(0.65F, -0.65F, -0.65F);

        Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(livingEntity, this.displayedItem, ItemCameraTransforms.TransformType.HEAD, false, matrixStack, renderTypeBuffer, light);
        matrixStack.pop();
    }
}
