package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.util.ResourceLocation;

public class GuiWaterScreen extends GuiElementScreen {

    @Override
    public void initGui() {
        this.BACKGROUND = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/water/water.png");
        this.SPELL_ICONS = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/water/spell_icons.png");
        super.initGui();
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        return null;
    }
}
