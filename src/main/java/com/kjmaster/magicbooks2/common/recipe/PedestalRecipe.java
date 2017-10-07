package com.kjmaster.magicbooks2.common.recipe;

import net.minecraft.item.ItemStack;

public class PedestalRecipe {

    public ItemStack input;
    public ItemStack output;

    public ItemStack airStack;
    public ItemStack earthStack;
    public ItemStack fireStack;
    public ItemStack waterStack;

    public int airManaCost;
    public int earthManaCost;
    public int fireManaCost;
    public int waterManaCost;
    public int time;

    public PedestalRecipe(ItemStack input, ItemStack output, ItemStack airStack, ItemStack earthStack, ItemStack fireStack, ItemStack waterStack, int airManaCost,
                          int earthManaCost, int fireManaCost, int waterManaCost, int time) {
        this.input = input;
        this.output = output;
        this.airStack = airStack;
        this.earthStack = earthStack;
        this.fireStack = fireStack;
        this.waterStack = waterStack;
        this.airManaCost = airManaCost;
        this.earthManaCost = earthManaCost;
        this.fireManaCost = fireManaCost;
        this.waterManaCost = waterManaCost;
        this.time = time;
    }
}
