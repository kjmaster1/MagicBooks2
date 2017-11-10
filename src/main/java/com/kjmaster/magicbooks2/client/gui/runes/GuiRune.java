package com.kjmaster.magicbooks2.client.gui.runes;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.gui.ProgressBar;
import com.kjmaster.magicbooks2.common.blocks.tile.container.runes.ContainerRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import com.kjmaster.magicbooks2.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiRune extends GuiContainer {

    private final ResourceLocation TEXTURE = new ResourceLocation(MagicBooks2.MODID,
            "textures/gui/container/rune.png");

    TileRune rune;
    private ProgressBar progressBar;
    private IInventory playerInv;
    private World world;
    private String element;

    public GuiRune(IInventory playerInv, TileRune rune, World world) {
        super(new ContainerRune(playerInv, rune));
        this.xSize = 176;
        this.ySize = 166;
        this.rune = rune;
        this.element = rune.getElement();
        this.playerInv = playerInv;
        this.world = world;
        int textureX;
        switch (element) {
            case "Air":
                textureX = 177;
                break;
            case "Arcane":
                textureX = 190;
                break;
            case "Earth":
                textureX = 203;
                break;
            case "Fire":
                textureX = 216;
                break;
            case "Water":
                textureX = 229;
                break;
            default:
                textureX = 177;
                break;
        }
        this.progressBar = new ProgressBar(TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP,
                12, 48, this.guiLeft + 83, this.guiTop + 17, textureX, 3);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format(this.findName(world, rune.getPos()));
        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
        int mana = this.rune.getField(0);
        this.progressBar.setMin(mana).setMax(10000);
        this.progressBar.draw(this.mc);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    private String findName(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block.equals(ModBlocks.drowningRune)) {
            return "container.rune_drowning";
        }
        else
            return "container.rune";
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
        int mana = this.rune.getField(0);
        this.progressBar.setMin(mana).setMax(1000);
        this.progressBar.draw(this.mc);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();
    }
}
