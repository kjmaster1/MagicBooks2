package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.network.ClientSpellPacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.ServerUnlockSpellPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerSpellHandler implements IMessageHandler<ServerUnlockSpellPacket, IMessage> {

    @Override
    public IMessage onMessage(ServerUnlockSpellPacket message, MessageContext ctx) {
        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ServerUnlockSpellPacket message, MessageContext ctx) {
        String spellAsString = message.spell;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        ISpells spellsCap = player.getCapability(SpellsProvider.SPELLS_CAP, null);
        Spell spell = spellsCap.getSpell(spellAsString);
        spellsCap.setUnlocked(spell);
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        PacketInstance.INSTANCE.sendTo(new ClientSpellPacket(spellAsString, true), playerMP);
    }
}
