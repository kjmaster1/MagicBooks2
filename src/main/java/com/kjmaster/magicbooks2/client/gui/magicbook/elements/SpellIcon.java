package com.kjmaster.magicbooks2.client.gui.magicbook.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SpellIcon extends GuiButton {
    private ResourceLocation texture;
    private int textureX, textureY;

    public SpellIcon(int id, int x, int y, int width, int height, String text,
                     ResourceLocation texture, int textureX, int textureY) {
        super(id, x, y, width, height, text);
        this.texture = texture;
        this.textureX = textureX;
        this.textureY = textureY;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(texture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(x, y, textureX, textureY, width, height);
    }
}
