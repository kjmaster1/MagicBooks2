package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ClientSpellPacket implements IMessage {

    public String spell;
    public Boolean isUnlocked;

    public ClientSpellPacket() {}
    public ClientSpellPacket(String spell, Boolean isUnlocked) {
        this.spell = spell;
        this.isUnlocked = isUnlocked;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        spell = ByteBufUtils.readUTF8String(buf);
        isUnlocked = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, spell);
        buf.writeBoolean(isUnlocked);
    }
}
