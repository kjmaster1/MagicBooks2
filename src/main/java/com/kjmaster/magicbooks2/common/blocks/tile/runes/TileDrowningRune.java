package com.kjmaster.magicbooks2.common.blocks.tile.runes;

import com.kjmaster.magicbooks2.common.capabilities.mana.water.WaterManaStorage;

public class TileDrowningRune extends TileRune.TileWaterRune {

    public TileDrowningRune() {
        setStorage(new WaterManaStorage(1000, 250, 0));
        setManaUse(100);
    }
}
