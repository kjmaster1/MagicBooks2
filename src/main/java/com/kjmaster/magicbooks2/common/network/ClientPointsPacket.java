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
    public boolean sendMessage;

    public ClientPointsPacket() {}

    public ClientPointsPacket(int points, String element, Boolean sendMessage) {
        this.points = points;
        this.element = element;
        this.sendMessage = sendMessage;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        points = buf.readInt();
        element = ByteBufUtils.readUTF8String(buf);
        sendMessage = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(points);
        ByteBufUtils.writeUTF8String(buf, element);
        buf.writeBoolean(sendMessage);
    }
}
