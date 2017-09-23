package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ServerEntriesPacket implements IMessage {

    public String entry;

    public ServerEntriesPacket() {}

    public ServerEntriesPacket(String entry) {
        this.entry = entry;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entry = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, entry);
    }
}
