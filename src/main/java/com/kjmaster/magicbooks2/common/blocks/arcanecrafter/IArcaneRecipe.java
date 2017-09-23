package com.kjmaster.magicbooks2.common.blocks.arcanecrafter;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public interface IArcaneRecipe {

    ItemStack getOutput();
    Boolean hasRecipe(ItemStackHandler handler);
    int getManaCost();
}
