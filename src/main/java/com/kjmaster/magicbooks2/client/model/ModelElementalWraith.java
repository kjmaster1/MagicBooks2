package com.kjmaster.magicbooks2.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelElementalWraith extends ModelBiped {
    public ModelRenderer leg2;
    public ModelRenderer leg1;
    public ModelRenderer body;
    public ModelRenderer chest;
    public ModelRenderer arm1;
    public ModelRenderer head;
    public ModelRenderer arm2;

    public ModelElementalWraith() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.chest = new ModelRenderer(this, 30, 16);
        this.chest.setRotationPoint(0.0F, -5.2F, -0.0F);
        this.chest.addBox(-4.0F, -8.0F, -2.0F, 8, 10, 4, 0.0F);
        this.setRotateAngle(chest, 0.18203784098300857F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -8.0F, -0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.8F, 8, 8, 8, 0.0F);
        this.leg1 = new ModelRenderer(this, 56, 0);
        this.leg1.mirror = true;
        this.leg1.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.leg1.addBox(-1.0F, 0.0F, -1.0F, 2, 25, 2, 0.0F);
        this.arm1 = new ModelRenderer(this, 56, 0);
        this.arm1.mirror = true;
        this.arm1.setRotationPoint(5.0F, -6.3F, 0.0F);
        this.arm1.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, 0.0F);
        this.setRotateAngle(arm1, -0.3490658503988659F, 0.0F, -0.10000736613927509F);
        this.arm2 = new ModelRenderer(this, 56, 0);
        this.arm2.setRotationPoint(-5.0F, -6.3F, 0.0F);
        this.arm2.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, 0.0F);
        this.setRotateAngle(arm2, -0.3490658503988659F, 0.0F, 0.10000736613927509F);
        this.leg2 = new ModelRenderer(this, 56, 0);
        this.leg2.setRotationPoint(-2.0F, 0.0F, 0.0F);
        this.leg2.addBox(-1.0F, 0.0F, -1.0F, 2, 25, 2, 0.0F);
        this.body = new ModelRenderer(this, 3, 18);
        this.body.setRotationPoint(0.0F, -0.6F, -0.4F);
        this.body.addBox(-3.5F, -6.0F, -1.2F, 7, 8, 3, 0.0F);
        this.body.addChild(this.chest);
        this.chest.addChild(this.head);
        this.chest.addChild(this.arm1);
        this.chest.addChild(this.arm2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    }
}
