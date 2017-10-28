package com.kjmaster.magicbooks2.common.events;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HurtEvent {

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            ISpells spellsCap = player.getCapability(SpellsProvider.SPELLS_CAP, null);
            Spell bubble = spellsCap.getSpell("bubble");
            MagicBooks2.LOGGER.info("Bubble2: " + bubble.getIsBeingCast());
            if (bubble.getIsBeingCast()) {
                event.setAmount(event.getAmount() / 2);
            }
        }
    }
}
