package com.kjmaster.magicbooks2.client.render;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.model.ModelElementalWraith;
import com.kjmaster.magicbooks2.common.entities.EntityAirWraith;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderElementalWraithAir extends RenderLiving<EntityAirWraith> {

    private ResourceLocation airWraithTexture = new ResourceLocation(MagicBooks2.MODID + ":textures/entity/elemental_wraith_air.png");

    public RenderElementalWraithAir(RenderManager renderManager) {
        super(renderManager, new ModelElementalWraith(), 0.5F);

    }

    @Override
    public void doRender(EntityAirWraith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityAirWraith entity) {
        return airWraithTexture;
    }

    public static class Factory implements IRenderFactory<EntityAirWraith> {

        @Override
        public Render<? super EntityAirWraith> createRenderFor(RenderManager manager) {
            return new RenderElementalWraithAir(manager);
        }
    }
}