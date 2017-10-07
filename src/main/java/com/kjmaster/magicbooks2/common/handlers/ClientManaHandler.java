package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import com.kjmaster.magicbooks2.common.network.ClientManaPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientManaHandler implements IMessageHandler<ClientManaPacket, IMessage> {

    @Override
    public IMessage onMessage(ClientManaPacket message, MessageContext ctx) {
        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ClientManaPacket message, MessageContext ctx) {
        String element = message.element;
        int mana = message.mana;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        IMana manaCap = player.getCapability(ManaProvider.MANA_CAP, null);
        manaCap.setMana(mana, element);
    }
}
