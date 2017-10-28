package com.kjmaster.magicbooks2.client.render;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.model.ModelElementalWraith;
import com.kjmaster.magicbooks2.common.entities.EntityFireWraith;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderElementalWraithFire extends RenderLiving<EntityFireWraith> {

    private ResourceLocation fireWraithTexture = new ResourceLocation(MagicBooks2.MODID + ":textures/entity/elemental_wraith_fire.png");

    public RenderElementalWraithFire(RenderManager renderManager) {
        super(renderManager, new ModelElementalWraith(), 0.5F);

    }

    @Override
    public void doRender(EntityFireWraith entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFireWraith entity) {
        return fireWraithTexture;
    }

    public static class Factory implements IRenderFactory<EntityFireWraith> {

        @Override
        public Render<? super EntityFireWraith> createRenderFor(RenderManager manager) {
            return new RenderElementalWraithFire(manager);
        }
    }
}
