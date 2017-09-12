package com.kjmaster.magicbooks2.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class ClientPointsPacket implements IMessage {

    public int points;
    public String element;

    public ClientPointsPacket() {}

    public ClientPointsPacket(int points, String element) {
        this.points = points;
        this.element = element;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        points = buf.readInt();
        element = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(points);
        ByteBufUtils.writeUTF8String(buf, element);
    }
}
