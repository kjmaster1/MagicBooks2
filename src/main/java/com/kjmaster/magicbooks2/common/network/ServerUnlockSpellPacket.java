package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ServerUnlockSpellPacket implements IMessage {

    public String spell;

    public ServerUnlockSpellPacket() {}

    public ServerUnlockSpellPacket(String spell) {
        this.spell = spell;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        spell = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, spell);
    }
}
