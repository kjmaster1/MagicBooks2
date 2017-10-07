package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.gui.magicbook.elements.SpellIcon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiEarthScreen extends GuiElementScreen {

    @Override
    public void initGui() {
        this.BACKGROUND = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/earth/earth.png");
        this.SPELL_ICONS = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/earth/spell_icons.png");
        super.initGui();

        SpellIcon grow = new SpellIcon(0, guiLeft + 71, guiTop + 104, 16, 16, "",
                SPELL_ICONS, 0, 0);
        SpellIcon walling = new SpellIcon(1, guiLeft + 28, guiTop + 141, 16, 16, "",
                SPELL_ICONS, 17, 0);

        this.buttonList.add(grow);
        this.buttonList.add(walling);
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        switch (buttonId) {
            case 0:
                return "Unlock the grow spell, costs 8 skill points and uses 10 em/c";
            case 1:
                return "Unlock the spell of walling, costs 16 skill points and uses 200 em/c";
        }
        return null;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int earthPoints = pointsCap.getPoints("Earth");
        switch (button.id) {
            case 0:
                tryUnlock("grow", earthPoints);
                break;
            case 1:
                tryUnlock("walling", earthPoints);
                break;
        }
    }
}
