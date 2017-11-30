package com.kjmaster.magicbooks2.client;

import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TooltipEvent {

    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();
        if(item.equals(ModItems.shardPickaxeAir))
            event.getToolTip().add("Doubles as a magnet!");
        else if (item.equals(ModItems.shardPickaxeArcane))
            event.getToolTip().add("Gotta get those shards!");
        else if (item.equals(ModItems.shardPickaxeWater))
            event.getToolTip().add("Handy for obsidian!");
        else if (item.equals(ModItems.shardPickaxeFire))
            event.getToolTip().add("All your auto-smelting needs!");
        else if (item.equals(ModItems.shardPickaxeEarth))
            event.getToolTip().add("Like a hammer but a bit better!");
        else if (item.equals(new ItemStack(ModItems.Wand, 1, 5).getItem()))
            event.getToolTip().add("WIP");
        else if (item.equals(Item.getItemFromBlock(ModBlocks.manaVase))) {
            ItemStack stack = event.getItemStack();
            if (stack.hasTagCompound()) {
                checkTagAndAddTooltip("Mana", "Mana: ", stack, event);
            }
        } else if (item.equals(Item.getItemFromBlock(ModBlocks.manaExtractor))) {
            ItemStack stack = event.getItemStack();
            if (stack.hasTagCompound()) {
                checkTagAndAddTooltip("AirMana", "Air Mana: ", stack, event);
                checkTagAndAddTooltip("ArcaneMana", "Arcane Mana: ", stack, event);
                checkTagAndAddTooltip("EarthMana", "Earth Mana: ", stack, event);
                checkTagAndAddTooltip("FireMana", "Fire Mana: ", stack, event);
                checkTagAndAddTooltip("WaterMana", "Water Mana: ", stack, event);
            }
        }
    }

    private void checkTagAndAddTooltip(String key, String tooltip, ItemStack stack, ItemTooltipEvent event) {
        if (stack.getTagCompound().hasKey(key)) {
            int mana = stack.getTagCompound().getInteger(key);
            event.getToolTip().add(tooltip + mana);
        }
    }
}
