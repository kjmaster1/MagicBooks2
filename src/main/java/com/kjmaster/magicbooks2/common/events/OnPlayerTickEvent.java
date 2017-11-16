package com.kjmaster.magicbooks2.common.events;

import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OnPlayerTickEvent {

    private int ticks = 0;

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (!player.world.isRemote) {
            ISpells spellsCap = player.getCapability(SpellsProvider.SPELLS_CAP, null);
            Spell bubble = spellsCap.getSpell("bubble");
            ticks++;
            if (bubble.getIsBeingCast()) {
                if (ticks %100 ==  0) {
                    ticks = 0;
                    bubble.setIsBeingCast(false);
                }
            }
        }
    }
}
