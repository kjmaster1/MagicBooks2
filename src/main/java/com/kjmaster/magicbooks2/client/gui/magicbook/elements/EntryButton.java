package com.kjmaster.magicbooks2.client.gui.magicbook.elements;

import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.IEntries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class EntryButton extends GuiButton {
    private ResourceLocation texture;
    private EntityPlayer player;
    private IEntries entriesCap;
    private String entry;
    private int textureX, textureX2, textureY, textureY2;
    private Boolean requiresUnlock, isOnY;

    public EntryButton(int id, int x, int y, String text, ResourceLocation texture, String entry, int textureX, int textureX2,
                       int textureY, int textureY2, int width, int height, Boolean requiresUnlock, Boolean isOnY) {
        super(id, x, y, width, height,  text);
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.entry = entry;
        this.textureX = textureX;
        this.textureX2 = textureX2;
        this.textureY = textureY;
        this.textureY2 = textureY2;
        this.requiresUnlock = requiresUnlock;
        this.isOnY = isOnY;
        this.player = Minecraft.getMinecraft().player;
        this.entriesCap = player.getCapability(EntriesProvider.ENTRIES_CAP, null);

    }

    @Override
    public void drawButton(Minecraft mc, int par2, int par3, float partialTicks) {
        if(enabled) {
            hovered = par2 >= x && par3 >= y && par2 < x + width && par3 < y + height;
            int k = getHoverState(hovered);

            mc.renderEngine.bindTexture(texture);
            GlStateManager.color(1F, 1F, 1F, 1F);
            if(this.isOnY) {
                if(this.requiresUnlock)
                    drawTexturedModalRect(x, y, textureX, k == 2 || entriesCap.isEntryUnlocked(entry) ? textureY : textureY2, width, height);
                else
                    drawTexturedModalRect(x, y, textureX, k == 2 ? textureY : textureY2, width, height);
            } else {
                if(this.requiresUnlock)
                    drawTexturedModalRect(x, y, k == 2 || entriesCap.isEntryUnlocked(entry) ? textureX : textureX2, textureY , width, height);
                else
                    drawTexturedModalRect(x, y, k == 2 ? textureX : textureX2, textureY , width, height);
            }
        }
    }
}
