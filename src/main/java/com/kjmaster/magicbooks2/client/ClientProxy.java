package com.kjmaster.magicbooks2.client;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.CommonProxy;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by pbill_000 on 11/09/2017.
 */
@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModelBakeryVariants() {
        ModelBakery.registerItemVariants(ModItems.Shard,
                new ResourceLocation(MagicBooks2.MODID, "shard_air"),
                new ResourceLocation(MagicBooks2.MODID, "shard_earth"),
                new ResourceLocation(MagicBooks2.MODID, "shard_fire"),
                new ResourceLocation(MagicBooks2.MODID, "shard_water")
        );
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModItems.registerModels();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx));
    }
    @SideOnly(Side.CLIENT)
    @Override
    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx));
    }
}
