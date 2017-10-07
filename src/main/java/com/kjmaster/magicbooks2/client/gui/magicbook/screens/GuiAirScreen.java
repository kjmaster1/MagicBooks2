package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.gui.magicbook.elements.SpellIcon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiAirScreen extends GuiElementScreen {

    @Override
    public void initGui() {
        this.BACKGROUND = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/air/air.png");
        this.SPELL_ICONS = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/air/spell_icons.png");
        super.initGui();

        SpellIcon invisibility = new SpellIcon(0, guiLeft + 35, guiTop + 30, 16, 16, "",
                SPELL_ICONS, 0, 0);
        SpellIcon lightning = new SpellIcon(1, guiLeft + 144, guiTop + 138, 16, 16, "",
                SPELL_ICONS, 17, 0);

        this.buttonList.add(invisibility);
        this.buttonList.add(lightning);

    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 0:
                return "Unlock the spell of invisibility, costs 8 skill points and uses 1000 am/c";
            case 1:
                return "Unlock the lightning spell, costs 16 skill points and uses 500 am/c";
            default:
                return null;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int airPoints = pointsCap.getPoints("Air");
        MagicBooks2.LOGGER.info("Air Points: " + airPoints);
        switch (button.id) {
            case 0:
                tryUnlock("invisibility", airPoints);
                break;
            case 1:
                tryUnlock("lightning", airPoints);
                break;
        }
    }
}
