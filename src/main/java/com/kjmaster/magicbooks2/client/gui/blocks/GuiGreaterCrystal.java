package com.kjmaster.magicbooks2.client.gui.blocks;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.gui.ProgressBar;
import com.kjmaster.magicbooks2.common.blocks.tile.TileGreaterCrystal;
import com.kjmaster.magicbooks2.common.blocks.tile.container.ContainerGreaterCrystal;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by pbill_000 on 15/09/2017.
 */
public class GuiGreaterCrystal extends GuiContainer {

    private final ResourceLocation TEXTURE = new ResourceLocation(MagicBooks2.MODID, "textures/gui/container/crystal_greater.png");

    private final TileGreaterCrystal te;
    private ProgressBar progressBar;
    private IInventory playerInv;
    private World world;
    private int meta;

    public GuiGreaterCrystal(IInventory playerInv, TileGreaterCrystal te, World world) {
        super(new ContainerGreaterCrystal(playerInv, te));
        this.xSize = 176;
        this.ySize = 166;
        this.te = te;
        this.playerInv = playerInv;
        this.world = world;
        this.meta = findMeta(te, world);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format("container.crystal_greater");
        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
        int mana = this.te.getField(meta);
        this.progressBar.setMin(mana).setMax(10000);
        this.progressBar.draw(this.mc);
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(MagicBooks2.MODID, "textures/gui/container/crystal_greater.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(MagicBooks2.MODID, "textures/gui/container/crystal_greater.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int mana = this.te.getField(meta);
        this.progressBar.setMin(mana).setMax(1000);
        this.progressBar.draw(this.mc);
    }

    private int findMeta(TileGreaterCrystal te, World world) {
        BlockPos pos = te.getPos();
        int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
        switch (meta) {
            case 0:
                this.progressBar = new ProgressBar(TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP,
                        12, 48, this.guiLeft + 37, this.guiTop + 17, 177, 3);
                break;
            case 1:
                this.progressBar = new ProgressBar(TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP,
                        12, 48, this.guiLeft + 37, this.guiTop + 17, 203, 3);
                break;
            case 2:
                this.progressBar = new ProgressBar(TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP,
                        12, 48, this.guiLeft + 37, this.guiTop + 17, 216, 3);
                break;
            case 3:
                this.progressBar = new ProgressBar(TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP,
                        12, 48, this.guiLeft + 37, this.guiTop + 17, 229, 3);
                break;
            case 4:
                this.progressBar = new ProgressBar(TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP,
                        12, 48, this.guiLeft + 37, this.guiTop + 17, 190, 3);
                break;

        }
        return meta;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();
    }
}
