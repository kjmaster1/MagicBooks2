package com.kjmaster.magicbooks2.common.blocks.arcanecrafter;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ArcaneRecipe implements IArcaneRecipe {

    private int manaCost;
    private ItemStack output;
    private Item[] items;

    public ArcaneRecipe(ItemStack output, Item[] items, int manaCost) {
        this.output = output;
        this.manaCost = manaCost;
        this.items = items;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Boolean hasRecipe(ItemStackHandler handler) {
        int correctSlots = 0;
        if (handler.getSlots() < 10)
            return false;
        for (int i = 0; i <= 8; i++) {
            if (handler.getStackInSlot(i).isEmpty()) {}
            else if (handler.getStackInSlot(i).getItem().equals(items[i]))
                correctSlots++;
        }
        return correctSlots == 9;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }
}
