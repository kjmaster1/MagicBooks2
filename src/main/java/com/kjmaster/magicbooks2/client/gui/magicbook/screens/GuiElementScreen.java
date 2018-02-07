package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.kjlib.client.gui.GuiToolTipScreen;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.ServerPointsPacket;
import com.kjmaster.magicbooks2.common.network.ServerUnlockSpellPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiElementScreen extends GuiToolTipScreen {

    ResourceLocation SPELL_ICONS;
    ResourceLocation BACKGROUND;
    int guiLeft, guiTop;
    String text;
    ISpells spellsCap;
    ISkillPoints pointsCap;
    int count = 0;

    @Override
    public void setGuiSize(int w, int h) {
        super.setGuiSize(192, 192);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();
        mc.renderEngine.bindTexture(BACKGROUND);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 192, 192);
        if (text != null) {
            this.drawString(this.fontRenderer, text, guiLeft + (192 / 2) - getLengthForRender(text),
                    guiTop + 192, Color.WHITE.getRGB());
            count++;
            if (count % 250 == 0) {
                text = null;
                count = 0;
            }
        }
        super.drawScreen(mouseX, mouseY, f);
    }

    private int getLengthForRender(String msg) {
        return this.mc.fontRenderer.getStringWidth(msg) / 2;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void initGui() {
        guiLeft = (this.width - 192) / 2;
        guiTop = (this.height - 192) / 2;
        EntityPlayer player = Minecraft.getMinecraft().player;
        spellsCap = player.getCapability(SpellsProvider.SPELLS_CAP, null);
        pointsCap = player.getCapability(SkillPointsProvider.SKILL_POINTS_CAP, null);
        super.initGui();
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        return null;
    }

    void tryUnlock(String spellAsString, int points) {
        Spell spell = spellsCap.getSpell(spellAsString);
        Boolean isUnlocked = spellsCap.getIsUnlocked(spell);
        if (!isUnlocked) {
            if (points >= spell.getPointCost()) {
                spellsCap.setUnlocked(spell);
                PacketInstance.INSTANCE.sendToServer(new ServerUnlockSpellPacket(spell.getAsString()));
                spellUnlocked(spell);
                PacketInstance.INSTANCE.sendToServer(new ServerPointsPacket(spell.getPointCost(), spell.getElement(), "Consume"));
            } else {
                notEnoughPoints(spell);
            }
        } else {
            alreadyUnlocked(spell);
        }
    }

    private void alreadyUnlocked(Spell spell) {
        this.text = "You already have the " + spell.getAsString() + " spell";
    }

    private void notEnoughPoints(Spell spell) {
         this.text = "You do not have enough points for the " + spell.getAsString() + " spell";
    }

    private void spellUnlocked(Spell spell) {
        this.text = spell.getAsString() + " spell unlocked!";
    }
}
