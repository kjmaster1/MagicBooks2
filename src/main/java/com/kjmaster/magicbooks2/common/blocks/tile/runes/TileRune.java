package com.kjmaster.magicbooks2.common.blocks.tile.runes;


import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileRune extends TileEntity {

    String element;
    private BlockPos connectedToPos;
    private Boolean hasConnection;
    private int MANA_USE;

    public TileRune(){
        this.connectedToPos = new BlockPos(0,0, 0);
        this.hasConnection = false;
        this.element = "";
    }

    public void setElement(String element) { this.element = element; }

    public void setMANA_USE(int MANA_USE) { this.MANA_USE = MANA_USE; }
}
