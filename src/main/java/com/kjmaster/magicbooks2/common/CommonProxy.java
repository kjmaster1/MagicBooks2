package com.kjmaster.magicbooks2.common;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.TileCrystal;
import com.kjmaster.magicbooks2.common.blocks.tile.TileGreaterCrystal;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.Mana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsStorage;
import com.kjmaster.magicbooks2.common.handlers.ClientPointsHandler;
import com.kjmaster.magicbooks2.common.handlers.ServerPointsHandler;
import com.kjmaster.magicbooks2.common.network.ClientPointsPacket;
import com.kjmaster.magicbooks2.common.network.ServerPointsPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static com.kjmaster.magicbooks2.common.network.PacketInstance.INSTANCE;
/**
 * Created by pbill_000 on 11/09/2017.
 */
public class CommonProxy {
    public void registerModelBakeryVariants() {}

    public void registerCaps() {
        CapabilityManager.INSTANCE.register(IMana.class,
                new ManaStorage(), Mana.class);
        CapabilityManager.INSTANCE.register(ISkillPoints.class,
                new SkillPointsStorage(), SkillPoints.class);
    }

    public void registerPackets() {
        INSTANCE.registerMessage(ServerPointsHandler.class, ServerPointsPacket.class, 1, Side.SERVER);

        INSTANCE.registerMessage(ClientPointsHandler.class, ClientPointsPacket.class, 2, Side.CLIENT);
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileCrystal.class, MagicBooks2.MODID + ":tile_crystal");
        GameRegistry.registerTileEntity(TileGreaterCrystal.class, MagicBooks2.MODID + ":tile_crystal_greater");
    }
    /**
     * Returns a side-appropriate EntityPlayer for use during message handling
     */
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().player;
    }

    /**
     * Returns the current thread based on side during message handling,
     * used for ensuring that the message is being handled by the main thread
     */
    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return ctx.getServerHandler().player.getServer();
    }
}
