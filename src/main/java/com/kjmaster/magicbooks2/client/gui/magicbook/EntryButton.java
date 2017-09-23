package com.kjmaster.magicbooks2.client.gui.magicbook;

import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.IEntries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class EntryButton extends GuiButton {
    private ResourceLocation texture;
    private EntityPlayer player;
    private IEntries entriesCap;
    private String entry;
    private int textureX, textureY, textureY2;

    public EntryButton(int id, int x, int y, String text, ResourceLocation texture, String entry, int textureX,
                       int textureY, int textureY2 ) {
        super(id, x, y, 20, 20,  text);
        this.texture = texture;
        this.width = 20;
        this.height = 20;
        this.entry = entry;
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureY2 = textureY2;
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
            drawTexturedModalRect(x, y, textureX, k == 2 || entriesCap.isEntryUnlocked(entry) ? textureY : textureY2, 20, 20);
        }
    }
}
