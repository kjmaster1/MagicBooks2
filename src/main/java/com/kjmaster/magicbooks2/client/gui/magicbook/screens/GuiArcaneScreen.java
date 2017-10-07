package com.kjmaster.magicbooks2.client.gui.magicbook.screens;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.util.ResourceLocation;

public class GuiArcaneScreen extends GuiElementScreen {

    @Override
    public void initGui() {
        this.BACKGROUND = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/arcane/arcane.png");
        this.SPELL_ICONS = new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/magicbook/arcane/spell_icons.png");
        super.initGui();
    }

    @Override
    protected String GetButtonTooltip(int buttonId) {
        return null;
    }
}
