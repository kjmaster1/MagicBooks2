package com.kjmaster.magicbooks2.common.blocks.pipe;

import com.kjmaster.magicbooks2.common.init.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum  EnumPipeType implements IStringSerializable {
    AIR("air", "magicbooks2:blocks/pipes/air_pipe"),
    ARCANE("arcane", "magicbooks2:blocks/pipes/arcane_pipe"),
    EARTH("earth", "magicbooks2:blocks/pipes/earth_pipe"),
    FIRE("fire", "magicbooks2:blocks/pipes/fire_pipe"),
    WATER("water", "magicbooks2:blocks/pipes/water_pipe");

    public String textureName;
    private String friendlyName;


    EnumPipeType(String friendlyName, String textureName) {
        this.friendlyName = friendlyName;
        this.textureName = textureName;
    }


    @Override
    public String getName() {
        return friendlyName.toLowerCase();
    }

    public ItemStack getStack() {
        return new ItemStack(ModBlocks.pipe, 1, this.ordinal());
    }
}
