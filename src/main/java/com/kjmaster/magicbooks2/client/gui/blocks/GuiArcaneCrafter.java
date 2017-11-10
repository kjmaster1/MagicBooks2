package com.kjmaster.magicbooks2.client.gui.blocks;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.arcanecrafter.ContainerArcaneCrafter;
import com.kjmaster.magicbooks2.common.blocks.arcanecrafter.TileArcaneCrafter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiArcaneCrafter extends GuiContainer {

    private final ResourceLocation TEXTURE = new ResourceLocation(MagicBooks2.MODID, "textures/gui/container/arcane_crafter.png");

    private final TileArcaneCrafter te;
    private IInventory playerInv;
    private World world;

    public GuiArcaneCrafter(IInventory playerInv, TileArcaneCrafter te, World world) {
        super(new ContainerArcaneCrafter(playerInv, te));
        this.xSize = 176;
        this.ySize = 166;
        this.te = te;
        this.playerInv = playerInv;
        this.world = world;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format("container.arcane_crafter");
        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();
    }
}
