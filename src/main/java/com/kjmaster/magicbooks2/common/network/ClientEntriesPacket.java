package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ClientEntriesPacket implements IMessage {

    public String entry;
    public Boolean isEntryUnlocked;

    public ClientEntriesPacket() {}

    public ClientEntriesPacket(String entry, Boolean isEntryUnlocked) {
        this.entry = entry;
        this.isEntryUnlocked = isEntryUnlocked;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entry = ByteBufUtils.readUTF8String(buf);
        isEntryUnlocked = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, entry);
        buf.writeBoolean(isEntryUnlocked);
    }
}
