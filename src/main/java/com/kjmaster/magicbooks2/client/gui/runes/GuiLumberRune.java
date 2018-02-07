package com.kjmaster.magicbooks2.client.gui.runes;

import com.kjmaster.kjlib.client.gui.ProgressBar;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.container.runes.ContainerLumberRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileLumberRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiLumberRune extends GuiContainer {

    private final ResourceLocation TEXTURE = new ResourceLocation(MagicBooks2.MODID,
            "textures/gui/container/rune_lumber.png");

    private TileLumberRune lumberRune;
    private ProgressBar progressBar;
    private IInventory playerInv;
    private World world;
    private String element;

    public GuiLumberRune(IInventory playerInv, TileRune rune, World world) {
        super(new ContainerLumberRune(playerInv, rune));
        this.lumberRune = (TileLumberRune) rune;
        this.playerInv = playerInv;
        this.world = world;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format("container.lumber_rune");
        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
        int mana = this.lumberRune.getField(0);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int l = this.getCooldownProgressScaled().intValue();
        this.drawTexturedModalRect(this.guiLeft + 102, this.guiTop + 35, 176, 53, l, 16);
    }

    private Double getCooldownProgressScaled() {
        int cooldown = lumberRune.getField(1);
        return cooldown != 0 ? cooldown * 0.16 : 0;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int l = this.getCooldownProgressScaled().intValue();
        this.drawTexturedModalRect(this.guiLeft + 102, this.guiTop + 35, 176, 53, l + 1, 16);
    }
}
