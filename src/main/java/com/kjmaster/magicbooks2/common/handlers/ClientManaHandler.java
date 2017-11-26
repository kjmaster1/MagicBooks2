package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
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
        IMana manaCap;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        switch (element) {
            case "Air":
                manaCap = player.getCapability(CapabilityAirMana.AIRMANA, null);
                break;
            case "Arcane":
                manaCap = player.getCapability(CapabilityArcaneMana.ARCANEMANA, null);
                break;
            case "Earth":
                manaCap = player.getCapability(CapabilityEarthMana.EARTHMANA, null);
                break;
            case "Fire":
                manaCap = player.getCapability(CapabilityFireMana.FIREMANA, null);
                break;
            case "Water":
                manaCap = player.getCapability(CapabilityWaterMana.WATERMANA, null);
                break;
            default:
                manaCap = null;
                break;
        }
        if (manaCap != null) {
            manaCap.setMana(mana);
        }
    }
}
