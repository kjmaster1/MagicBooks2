package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.ServerPointsPacket;
import com.kjmaster.magicbooks2.common.network.ServerUnlockSpellPacket;
import com.kjmaster.magicbooks2.utils.GuiToolTipScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiElementScreen extends GuiToolTipScreen {

    ResourceLocation SPELL_ICONS;
    ResourceLocation BACKGROUND;
    int guiLeft, guiTop;
    ISpells spellsCap;
    ISkillPoints pointsCap;

    @Override
    public void setGuiSize(int w, int h) {
        super.setGuiSize(192, 192);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();
        mc.renderEngine.bindTexture(BACKGROUND);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, 192, 192);
        super.drawScreen(mouseX, mouseY, f);
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
        int pointsCost = spell.getPointCost();
        MagicBooks2.LOGGER.info("Points Cost" + pointsCost);
        if (!isUnlocked) {
            if (points >= spell.getPointCost()) {
                spellsCap.setUnlocked(spell);
                PacketInstance.INSTANCE.sendToServer(new ServerUnlockSpellPacket(spell.getAsString()));
                spellUnlocked(spell);
                PacketInstance.INSTANCE.sendToServer(new ServerPointsPacket(spell.getPointCost(), spell.getElement(), "Consume"));
                MagicBooks2.LOGGER.info("TEST1");
            } else {
                notEnoughPoints(spell);
                MagicBooks2.LOGGER.info("TEST2");
            }
        } else {
            alreadyUnlocked(spell);
            MagicBooks2.LOGGER.info("TEST3");
        }
        if (spellsCap.getIsUnlocked(spell))
            MagicBooks2.LOGGER.info("IT WORKED JIMMY");
    }

    private void alreadyUnlocked(Spell spell) {
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage("You already have the " + spell.getAsString() + " spell", false);
    }

    private void notEnoughPoints(Spell spell) {
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage("You do not have enough points for the " + spell.getAsString() + " spell", false);
    }

    private void spellUnlocked(Spell spell) {
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(spell.getAsString() + " spell unlocked!", false);
    }
}
