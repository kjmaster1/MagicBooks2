package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.ClientProxy;
import com.kjmaster.magicbooks2.common.network.ClientParticlePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import scala.Array;
import slimeknights.tconstruct.shared.client.ParticleEffect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class ClientParticleHandler implements IMessageHandler<ClientParticlePacket, IMessage> {

    @Override
    public IMessage onMessage(ClientParticlePacket message, MessageContext ctx) {
        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ClientParticlePacket message, MessageContext ctx) {
        String particle = message.particle;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        float f1 = message.f1;
        float f2 = message.f2;
        float f3 = message.f3;
        float f4 = message.f4;
        float f5 = message.f5;
        switch (particle) {
            case "AutoSmelt":
                for (int i = 0; i < 5; i++) {
                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f1 - f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);
                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.FLAME, f1 - f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);

                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f1 + f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);
                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.FLAME, f1 + f4, f2, f3 + f5, 0.0D, 0.0D, 0.0D);

                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f1 + f5, f2, f3 - f4, 0.0D, 0.0D, 0.0D);
                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.FLAME, f1 + f5, f2, f3 - f4, 0.0D, 0.0D, 0.0D);

                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f1 + f5, f2, f3 + f4, 0.0D, 0.0D, 0.0D);
                    Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.FLAME, f1 + f5, f2, f3 + f4, 0.0D, 0.0D, 0.0D);
                }
                break;
            case "Bubble":
                //Someone teach me how to make a sphere
                break;
            default:
                break;
        }
    }
}
