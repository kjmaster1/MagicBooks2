package com.kjmaster.magicbooks2.common.blocks.tile.extractor;

import com.kjmaster.magicbooks2.common.items.ItemShardAxe;
import com.kjmaster.magicbooks2.common.items.ItemShardPickaxe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class ExtractorTESR extends TileEntitySpecialRenderer<TileManaExtractor> {

    @Override
    public void render(TileManaExtractor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderItem(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderItem(TileManaExtractor te) {
        ItemStack stack = te.getStack();
        if (!stack.isEmpty()) {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            if (stack.getItem() instanceof ItemShardPickaxe || stack.getItem() instanceof ItemShardAxe) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(.65, 1.05, .65);
                GlStateManager.scale(.3f, .3f, .3f);
                Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();
                GlStateManager.translate(.5, 1.05, .5);
                GlStateManager.scale(.3f, .3f, .3f);
                Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
                GlStateManager.popMatrix();
            }
        }
    }
}
