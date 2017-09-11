package com.kjmaster.magicbooks2;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MagicBooks2.MODID, version = MagicBooks2.VERSION)
public class MagicBooks2
{
    public static final String MODID = "magicbooks2";
    public static final String VERSION = "1.0.0";

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
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LOGGER.info("Starting Post-Initialization");
    }
}
