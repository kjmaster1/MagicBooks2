package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.IEntries;
import com.kjmaster.magicbooks2.common.network.ClientEntriesPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientEntriesHandler implements IMessageHandler<ClientEntriesPacket, IMessage> {

    @Override
    public IMessage onMessage(ClientEntriesPacket message, MessageContext ctx) {

        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ClientEntriesPacket message, MessageContext ctx) {
        String entry = message.entry;
        Boolean isEntryUnlocked = message.isEntryUnlocked;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        IEntries entriesCap = player.getCapability(EntriesProvider.ENTRIES_CAP, null);
        entriesCap.setEntry(entry, isEntryUnlocked);
    }
}
