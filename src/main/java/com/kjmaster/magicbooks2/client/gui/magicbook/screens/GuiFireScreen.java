package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.gui.magicbook.elements.SpellIcon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiFireScreen extends GuiElementScreen {

    @Override
    public void initGui() {
        this.BACKGROUND = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/fire/fire.png");
        this.SPELL_ICONS = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/fire/spell_icons.png");
        super.initGui();

        SpellIcon fireblast = new SpellIcon(0, guiLeft + 82, guiTop + 82, 16, 16, "",
                SPELL_ICONS, 0, 0);

        this.buttonList.add(fireblast);
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 0:
                return "Unlock the fireblast spell, costs 8 skill points and uses 250 fm/mob";
        }
        return null;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int firePoints = pointsCap.getPoints("Fire");
        switch (button.id) {
            case 0:
                tryUnlock("fireblast", firePoints);
                break;
            default:
                break;
        }
    }
}
