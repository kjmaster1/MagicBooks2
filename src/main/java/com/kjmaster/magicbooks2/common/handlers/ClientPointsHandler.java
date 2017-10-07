package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.network.ClientPointsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class ClientPointsHandler implements IMessageHandler<ClientPointsPacket, IMessage> {

    @Override
    public IMessage onMessage(ClientPointsPacket message, MessageContext ctx) {

        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ClientPointsPacket message, MessageContext ctx) {
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        int points = message.points;
        String element = message.element;
        ISkillPoints skillPointsCap = player.getCapability(SkillPointsProvider.SKILL_POINTS_CAP, null);
        skillPointsCap.setPoints(points, element);
        points = skillPointsCap.getPoints(element);
        if (message.sendMessage)
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("You now have " + points + " " + element + " skill points", false);
    }
}
