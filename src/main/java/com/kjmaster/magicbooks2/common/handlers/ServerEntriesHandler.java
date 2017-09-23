package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.IEntries;
import com.kjmaster.magicbooks2.common.network.ServerEntriesPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerEntriesHandler implements IMessageHandler<ServerEntriesPacket, IMessage> {

    @Override
    public IMessage onMessage(ServerEntriesPacket message, MessageContext ctx) {
        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ServerEntriesPacket message, MessageContext ctx) {
        String entry = message.entry;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        IEntries entriesCap = player.getCapability(EntriesProvider.ENTRIES_CAP, null);
        entriesCap.unlockEntry(entry);
    }
}
