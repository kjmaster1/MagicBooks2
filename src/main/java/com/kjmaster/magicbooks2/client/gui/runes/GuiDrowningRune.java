package com.kjmaster.magicbooks2.client.gui.runes;

import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class GuiDrowningRune extends GuiRune {

    public GuiDrowningRune(IInventory playerInv, TileRune rune, World world) {
        super(playerInv, rune, world);
        x = 229;
    }
}
