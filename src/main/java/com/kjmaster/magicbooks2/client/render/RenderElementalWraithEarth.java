package com.kjmaster.magicbooks2.client.render;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.model.ModelElementalWraith;
import com.kjmaster.magicbooks2.common.entities.EntityEarthWraith;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderElementalWraithEarth extends RenderLiving<EntityEarthWraith> {

    private ResourceLocation earthWraithTexture = new ResourceLocation(MagicBooks2.MODID + ":textures/entity/elemental_wraith_earth.png");

    public RenderElementalWraithEarth(RenderManager renderManager) {
        super(renderManager, new ModelElementalWraith(), 0.5F);

    }

    @Override
    public void doRender(EntityEarthWraith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityEarthWraith entity) {
        return earthWraithTexture;
    }

    public static class Factory implements IRenderFactory<EntityEarthWraith> {

        @Override
        public Render<? super EntityEarthWraith> createRenderFor(RenderManager manager) {
            return new RenderElementalWraithEarth(manager);
        }
    }
}
