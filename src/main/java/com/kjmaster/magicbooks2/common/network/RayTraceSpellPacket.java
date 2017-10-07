package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class RayTraceSpellPacket implements IMessage {

    public int x;
    public int y;
    public int z;
    public String spell;

    public RayTraceSpellPacket() {}

    public RayTraceSpellPacket(int x, int y, int z, String spell) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.spell = spell;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        spell = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        ByteBufUtils.writeUTF8String(buf, spell);
    }
}
