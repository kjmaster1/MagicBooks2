package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.kjlib.client.gui.GuiToolTipScreen;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.gui.magicbook.elements.EntryButton;
import com.kjmaster.magicbooks2.common.network.ModGuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiMagicBookScreen extends GuiToolTipScreen {

    private ResourceLocation BACKGROUNDTEXTURE = new ResourceLocation(MagicBooks2.MODID,
            "textures/gui/magicbook/background.png");
    private ResourceLocation BUTTTONTEXTURE = new ResourceLocation(MagicBooks2.MODID,
            "textures/gui/magicbook/buttons.png");
    private int guiLeft, guiTop;

    @Override
    public void setGuiSize(int w, int h) {
        super.setGuiSize(192, 192);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(BACKGROUNDTEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 192, 192);
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

        EntryButton air = new EntryButton(1, guiLeft + 7, guiTop + 7, "", BUTTTONTEXTURE,
                "Air", 0, 0, 36, 102, 62, 64, false, true);
        EntryButton earth = new EntryButton(2, guiLeft + 7, guiTop + 118, "", BUTTTONTEXTURE,
                "Earth", 63, 0, 36, 102,  62, 64, false, true);
        EntryButton fire = new EntryButton(3, guiLeft + 108, guiTop + 7, "", BUTTTONTEXTURE,
                "Fire", 189, 0, 36, 102, 62, 64, false, true);
        EntryButton water = new EntryButton(4, guiLeft + 108, guiTop + 118, "", BUTTTONTEXTURE,
                "Water", 126, 0, 36, 102, 62, 64, false, true);
        EntryButton arcane = new EntryButton(5, guiLeft + 63, guiTop + 69, "", BUTTTONTEXTURE,
                "Arcane", 0, 49, 167, 0, 48, 46, false, false);

        this.buttonList.add(air);
        this.buttonList.add(earth);
        this.buttonList.add(fire);
        this.buttonList.add(water);
        this.buttonList.add(arcane);

        super.initGui();
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 1:
                return "Click to open air page";
            case 2:
                return "Click to open earth page";
            case 3:
                return "Click to open fire page";
            case 4:
                return "Click to open water page";
            case 5:
                return "Click to open arcane page";

            default: return null;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        EntityPlayer player = Minecraft.getMinecraft().player;
        switch (button.id) {
            case 1:
                player.openGui(MagicBooks2.instance, ModGuiHandler.airBookPage, player.world,
                        (int) player.posX, (int) player.posY, (int) player.posZ);
                break;
            case 2:
                player.openGui(MagicBooks2.instance, ModGuiHandler.earthBookPage, player.world,
                        (int) player.posX, (int) player.posY, (int) player.posZ);
                break;
            case 3:
                player.openGui(MagicBooks2.instance, ModGuiHandler.fireBookPage, player.world,
                        (int) player.posX, (int) player.posY, (int) player.posZ);
                break;
            case 4:
                player.openGui(MagicBooks2.instance, ModGuiHandler.waterBookPage, player.world,
                        (int) player.posX, (int) player.posY, (int) player.posZ);
                break;
            case 5:
                player.openGui(MagicBooks2.instance, ModGuiHandler.arcaneBookPage, player.world,
                        (int) player.posX, (int) player.posY, (int) player.posZ);
                break;
            default:
                break;
        }
    }
}

