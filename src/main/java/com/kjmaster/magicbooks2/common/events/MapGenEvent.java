package com.kjmaster.magicbooks2.common.events;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.gen.structures.DungeonGenBase;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MapGenEvent {

    @SubscribeEvent()
    public void onMapGen(InitMapGenEvent event) {
        if(event.getType().equals(InitMapGenEvent.EventType.SCATTERED_FEATURE)) {
            DungeonGenBase dungeonGenBase = new DungeonGenBase();
            event.setNewGen(dungeonGenBase);
        }
    }
}
