package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.network.ClientPointsPacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.ServerPointsPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class ServerPointsHandler implements IMessageHandler<ServerPointsPacket, IMessage> {

    @Override
    public IMessage onMessage(ServerPointsPacket message, MessageContext ctx) {

        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                     processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ServerPointsPacket message, MessageContext ctx) {

        int points = message.points;
        String element = message.element;
        String method = message.method;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        ISkillPoints skillPointsCap = player.getCapability(SkillPointsProvider.SKILL_POINTS_CAP, null);

        switch (method) {
            case "Add":
                skillPointsCap.addPoints(points, element);
                break;
            case "Consume":
                skillPointsCap.consumePoints(points, element);
                break;
            case "Set":
                skillPointsCap.setPoints(points, element);
            default:
                break;
        }
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        PacketInstance.INSTANCE.sendTo(new ClientPointsPacket(skillPointsCap.getPoints(element),
                element, true), playerMP);
    }
}
