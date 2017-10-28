package com.kjmaster.magicbooks2.client.render;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.model.ModelElementalWraith;
import com.kjmaster.magicbooks2.common.entities.EntityArcaneWraith;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderElementalWraithArcane extends RenderLiving<EntityArcaneWraith> {

    private ResourceLocation arcaneWraithTexture = new ResourceLocation(MagicBooks2.MODID + ":textures/entity/elemental_wraith_arcane.png");

    public RenderElementalWraithArcane(RenderManager renderManager) {
        super(renderManager, new ModelElementalWraith(), 0.5F);

    }

    @Override
    public void doRender(EntityArcaneWraith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityArcaneWraith entity) {
        return arcaneWraithTexture;
    }

    public static class Factory implements IRenderFactory<EntityArcaneWraith> {

        @Override
        public Render<? super EntityArcaneWraith> createRenderFor(RenderManager manager) {
            return new RenderElementalWraithArcane(manager);
        }
    }
}
