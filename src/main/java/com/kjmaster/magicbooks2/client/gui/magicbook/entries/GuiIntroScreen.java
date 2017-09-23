package com.kjmaster.magicbooks2.client.gui.magicbook.entries;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiIntroScreen extends GuiScreen {

    private ResourceLocation ENTRYTEXTURE = new ResourceLocation(MagicBooks2.MODID,
            "textures/gui/magicbook/entry.png");
    private int guiLeft, guiTop;

    @Override
    public void setGuiSize(int w, int h) {
        super.setGuiSize(192, 192);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ENTRYTEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 192, 192);
        String text = "Welcome to the Magic Book. By unlocking this entry you have received" +
                " an arcane crafter. This can be used to craft items with recipes gained from" +
                " unlocking entries.";
        this.fontRenderer.drawSplitString(text, guiLeft + 30, guiTop + 15,  130, Color.WHITE.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void initGui() {
        guiLeft = (this.width - 192) / 2;
        guiTop = (this.height - 192) / 2;
        super.initGui();
    }
}
