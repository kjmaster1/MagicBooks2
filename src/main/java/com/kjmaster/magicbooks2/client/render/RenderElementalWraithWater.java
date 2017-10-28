package com.kjmaster.magicbooks2.client.render;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.model.ModelElementalWraith;
import com.kjmaster.magicbooks2.common.entities.EntityWaterWraith;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderElementalWraithWater extends RenderLiving<EntityWaterWraith> {

    private ResourceLocation waterWraithTexture = new ResourceLocation(MagicBooks2.MODID + ":textures/entity/elemental_wraith_water.png");

    public RenderElementalWraithWater(RenderManager renderManager) {
        super(renderManager, new ModelElementalWraith(), 0.5F);

    }

    @Override
    public void doRender(EntityWaterWraith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityWaterWraith entity) {
        return waterWraithTexture;
    }

    public static class Factory implements IRenderFactory<EntityWaterWraith> {

        @Override
        public Render<? super EntityWaterWraith> createRenderFor(RenderManager manager) {
            return new RenderElementalWraithWater(manager);
        }
    }
}
