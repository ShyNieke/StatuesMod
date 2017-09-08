package com.svennieke.statues.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPlayerStatue extends ModelBase {

    //fields
    public final ModelRenderer e1;
    public final ModelRenderer e2;
    public final ModelRenderer e3;
    public final ModelRenderer e4;
    public final ModelRenderer e5;
    public final ModelRenderer e6;

    public ModelPlayerStatue()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        e1 = new ModelRenderer(this, 16, 8);
        e1.addBox(8F, 0F, 7F, 2, 6, 2);
        e1.setRotationPoint(8F, 0F, 7F);
        e1.mirror = false;
        e2 = new ModelRenderer(this, 24, 8);
        e2.addBox(6F, 0F, 7F, 2, 6, 2);
        e2.setRotationPoint(6F, 0F, 7F);
        e2.mirror = false;
        e3 = new ModelRenderer(this, 16, 0);
        e3.addBox(10F, 6F, 7F, 2, 6, 2);
        e3.setRotationPoint(10F, 6F, 7F);
        e3.mirror = false;
        e4 = new ModelRenderer(this, 24, 0);
        e4.addBox(4F, 6F, 7F, 2, 6, 2);
        e4.setRotationPoint(4F, 6F, 7F);
        e4.mirror = false;
        e5 = new ModelRenderer(this, 0, 8);
        e5.addBox(6F, 12F, 6F, 4, 4, 4);
        e5.setRotationPoint(6F, 12F, 6F);
        e5.mirror = false;
        e6 = new ModelRenderer(this, 0, 0);
        e6.addBox(6F, 6F, 7F, 4, 6, 2);
        e6.setRotationPoint(6F, 6F, 7F);
        e6.mirror = false;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        e1.render(f5);
        e2.render(f5);
        e3.render(f5);
        e4.render(f5);
        e5.render(f5);
        e6.render(f5);
    }
     
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
 
}