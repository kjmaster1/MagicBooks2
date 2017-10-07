package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.network.ClientPointsPacket;
import com.kjmaster.magicbooks2.common.network.ClientSpellPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientSpellHandler implements IMessageHandler<ClientSpellPacket, IMessage> {

    @Override
    public IMessage onMessage(ClientSpellPacket message, MessageContext ctx) {

        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ClientSpellPacket message, MessageContext ctx) {
        String spellAsString = message.spell;
        Boolean isUnlocked = message.isUnlocked;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        ISpells spellsCap = player.getCapability(SpellsProvider.SPELLS_CAP, null);
        Spell spell = spellsCap.getSpell(spellAsString);
        spellsCap.setValueUnlocked(spell, isUnlocked);
    }
}
