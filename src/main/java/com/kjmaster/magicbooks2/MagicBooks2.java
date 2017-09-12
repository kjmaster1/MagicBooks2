package com.kjmaster.magicbooks2;

import com.kjmaster.magicbooks2.common.CommonProxy;
import com.kjmaster.magicbooks2.common.events.EntityJoinEvent;
import com.kjmaster.magicbooks2.common.handlers.CapabilityHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MagicBooks2.MODID, version = MagicBooks2.VERSION)
public class MagicBooks2
{
    @SidedProxy(clientSide = MagicBooks2.CLIENT_PROXY, serverSide = MagicBooks2.COMMON_PROXY)
    public static CommonProxy proxy;
    public static final String MODID = "magicbooks2";
    public static final String VERSION = "1.0.0";
    public static final String CLIENT_PROXY = "com.kjmaster.magicbooks2.client.ClientProxy";
    public static final String COMMON_PROXY = "com.kjmaster.magicbooks2.common.CommonProxy";

    public static final Logger LOGGER = LogManager.getLogger(MagicBooks2.MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER.info("Starting Pre-Initialization");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("Starting Initialization");
        proxy.registerModelBakeryVariants();
        proxy.registerCaps();
        proxy.registerPackets();
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new EntityJoinEvent());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LOGGER.info("Starting Post-Initialization");
    }
}
