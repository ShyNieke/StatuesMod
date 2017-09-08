package com.svennieke.statues.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPlayerStatue extends ModelBase {

    //fields
    public ModelRenderer e1;
    public ModelRenderer e2;
    public ModelRenderer e3;
    public ModelRenderer e4;
    public ModelRenderer e5;
    public ModelRenderer e6;
    public ModelRenderer e7;
    public ModelRenderer e8;
    public ModelRenderer e9;
    public ModelRenderer e10;
    public ModelRenderer e11;
    public ModelRenderer e12;

    public ModelPlayerStatue()
    {
        textureWidth = 64;
        textureHeight = 64;

        e1 = new ModelRenderer(this, 12, 36);
        e1.addBox(8F, 0F, 7F, 2, 6, 2);
        e1.setRotationPoint(8F, 0F, 7F);
        e1.setTextureSize(64, 64);
        e1.mirror = false;
        setRotation(e1, 0F, 0F, 0F);
        e2 = new ModelRenderer(this, 20, 36);
        e2.addBox(6F, 0F, 7F, 2, 6, 2);
        e2.setRotationPoint(6F, 0F, 7F);
        e2.setTextureSize(64, 64);
        e2.mirror = false;
        setRotation(e2, 0F, 0F, 0F);
        e3 = new ModelRenderer(this, 0, 28);
        e3.addBox(10F, 6F, 7F, 2, 6, 2);
        e3.setRotationPoint(10F, 6F, 7F);
        e3.setTextureSize(64, 64);
        e3.mirror = false;
        setRotation(e3, 0F, 0F, 0F);
        e4 = new ModelRenderer(this, 8, 28);
        e4.addBox(4F, 6F, 7F, 2, 6, 2);
        e4.setRotationPoint(4F, 6F, 7F);
        e4.setTextureSize(64, 64);
        e4.mirror = false;
        setRotation(e4, 0F, 0F, 0F);
        e5 = new ModelRenderer(this, 16, 46);
        e5.addBox(6F, 12F, 6F, 4, 4, 4);
        e5.setRotationPoint(6F, 12F, 6F);
        e5.setTextureSize(64, 64);
        e5.mirror = false;
        setRotation(e5, 0F, 0F, 0F);
        e6 = new ModelRenderer(this, 0, 36);
        e6.addBox(6F, 6F, 7F, 4, 6, 2);
        e6.setRotationPoint(6F, 6F, 7F);
        e6.setTextureSize(64, 64);
        e6.mirror = false;
        setRotation(e6, 0F, 0F, 0F);
        e7 = new ModelRenderer(this, 0, 54);
        e7.addBox(5.75F, 11.75F, 5.75F, 5, 5, 5);
        e7.setRotationPoint(5.75F, 11.75F, 5.75F);
        e7.setTextureSize(64, 64);
        e7.mirror = false;
        setRotation(e7, 0F, 0F, 0F);
        e8 = new ModelRenderer(this, 20, 54);
        e8.addBox(7.75F, -0.25F, 6.5F, 3, 7, 3);
        e8.setRotationPoint(7.75F, -0.25F, 6.5F);
        e8.setTextureSize(64, 64);
        e8.mirror = false;
        setRotation(e8, 0F, 0F, 0F);
        e9 = new ModelRenderer(this, 20, 54);
        e9.addBox(5.75F, -0.25F, 6.75F, 3, 7, 3);
        e9.setRotationPoint(5.75F, -0.25F, 6.75F);
        e9.setTextureSize(64, 64);
        e9.mirror = false;
        setRotation(e9, 0F, 0F, 0F);
        e10 = new ModelRenderer(this, 0, 44);
        e10.addBox(5.75F, 5.5F, 6.75F, 5, 7, 3);
        e10.setRotationPoint(5.75F, 5.5F, 6.75F);
        e10.setTextureSize(64, 64);
        e10.mirror = false;
        setRotation(e10, 0F, 0F, 0F);
        e11 = new ModelRenderer(this, 20, 54);
        e11.addBox(9.75F, 5.75F, 6.5F, 3, 7, 3);
        e11.setRotationPoint(9.75F, 5.75F, 6.5F);
        e11.setTextureSize(64, 64);
        e11.mirror = false;
        setRotation(e11, 0F, 0F, 0F);
        e12 = new ModelRenderer(this, 20, 54);
        e12.addBox(3.75F, 5.75F, 6.5F, 3, 7, 3);
        e12.setRotationPoint(3.75F, 5.75F, 6.5F);
        e12.setTextureSize(64, 64);
        e12.mirror = false;
        setRotation(e12, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        e1.render(f5);
        e2.render(f5);
        e3.render(f5);
        e4.render(f5);
        e5.render(f5);
        e6.render(f5);
        e7.render(f5);
        e8.render(f5);
        e9.render(f5);
        e10.render(f5);
        e11.render(f5);
        e12.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
     
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
 
}