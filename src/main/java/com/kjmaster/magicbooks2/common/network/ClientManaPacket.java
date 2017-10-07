package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ClientManaPacket implements IMessage {

    public String element;
    public int mana;

    public ClientManaPacket() {}

    public ClientManaPacket(String message, int mana) {
        this.element = message;
        this.mana = mana;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        element = ByteBufUtils.readUTF8String(buf);
        mana = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, element);
        buf.writeInt(mana);
    }
}
