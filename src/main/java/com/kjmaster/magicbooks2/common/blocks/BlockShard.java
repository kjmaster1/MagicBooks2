package com.kjmaster.magicbooks2.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockShard extends BlockElementBase {

    public BlockShard(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
    }
}
