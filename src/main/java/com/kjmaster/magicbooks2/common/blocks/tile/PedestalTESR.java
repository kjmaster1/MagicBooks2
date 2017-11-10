package com.kjmaster.magicbooks2.common.blocks.tile;

import com.kjmaster.magicbooks2.common.items.ItemShardAxe;
import com.kjmaster.magicbooks2.common.items.ItemShardPickaxe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

/** Thank you McJty for the great tutorial which can be found here https://wiki.mcjty.eu/modding/index.php/Render_Block_TESR_/_OBJ-1.9 */
public class PedestalTESR extends TileEntitySpecialRenderer<TilePedestal> {

    @Override
    public void render(TilePedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderItem(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderItem(TilePedestal te) {
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
