package com.kjmaster.magicbooks2.common.network;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class PacketInstance {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MagicBooks2.MODID);
}
