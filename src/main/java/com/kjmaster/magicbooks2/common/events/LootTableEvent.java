package com.kjmaster.magicbooks2.common.events;


import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.utils.LootUtils;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootTableEvent {
    @SubscribeEvent
    public static void lootTableLoad(LootTableLoadEvent event) {

        if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
            LootTable loot = event.getTable();
            LootUtils.addItemToTable(loot, ModItems.MagicBook, 0, 15/127F, 22/100, 1, 1, 0, 0, ModItems.MagicBook.getUnlocalizedName());
        }
    }
}
