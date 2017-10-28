package com.kjmaster.magicbooks2.common.events;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.network.ClientParticlePacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
                    MagicBooks2.LOGGER.info("Bubble1: " + bubble.getIsBeingCast());
                }
            }
        }
    }
}
