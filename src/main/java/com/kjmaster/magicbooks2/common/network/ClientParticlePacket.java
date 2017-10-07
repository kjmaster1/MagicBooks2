package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ClientParticlePacket implements IMessage {

    public String particle;
    public float f1;
    public float f2;
    public float f3;
    public float f4;
    public float f5;

    public ClientParticlePacket() {}

    public ClientParticlePacket(String particle, float f1, float f2, float f3, float f4, float f5) {
        this.particle = particle;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
       particle = ByteBufUtils.readUTF8String(buf);
       f1 = buf.readFloat();
       f2 = buf.readFloat();
       f3 = buf.readFloat();
       f4 = buf.readFloat();
       f5 = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, particle);
        buf.writeFloat(f1);
        buf.writeFloat(f2);
        buf.writeFloat(f3);
        buf.writeFloat(f4);
        buf.writeFloat(f5);
    }
}
