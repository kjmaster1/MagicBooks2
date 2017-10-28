package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.gui.magicbook.elements.SpellIcon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiWaterScreen extends GuiElementScreen {

    @Override
    public void initGui() {
        this.BACKGROUND = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/water/water.png");
        this.SPELL_ICONS = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/water/spell_icons.png");
        super.initGui();

        SpellIcon bubble = new SpellIcon(0, guiLeft + 82, guiTop + 82, 16, 16, "",
                SPELL_ICONS, 0, 0);

        this.buttonList.add(bubble);
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 0:
                return "Unlock the bubble spell, costs 16 skill points and uses 500 am/c";
        }
        return null;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int waterPoints = pointsCap.getPoints("Water");
        switch (button.id) {
            case 0:
                tryUnlock("bubble", waterPoints);
                break;
            default:
                break;
        }
    }
}
