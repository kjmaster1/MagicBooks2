package com.kjmaster.magicbooks2.common;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.*;
import com.kjmaster.magicbooks2.common.blocks.tile.extractor.TileManaExtractor;
import com.kjmaster.magicbooks2.common.blocks.tile.pipes.*;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileDrowningRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileLumberRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune.*;
import com.kjmaster.magicbooks2.common.blocks.tile.vase.*;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsStorage;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.Entries;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesStorage;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.IEntries;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsStorage;
import com.kjmaster.magicbooks2.common.handlers.*;
import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.common.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

import static com.kjmaster.magicbooks2.common.network.PacketInstance.INSTANCE;

public class CommonProxy {
    public void registerModelBakeryVariants() {}

    public void registerCaps() {
        CapabilityManager.INSTANCE.register(ISkillPoints.class,
                new SkillPointsStorage(), SkillPoints.class);
        CapabilityManager.INSTANCE.register(IEntries.class,
                new EntriesStorage(), Entries.class);
        CapabilityManager.INSTANCE.register(ISpells.class,
                new SpellsStorage(), Spells.class);
        CapabilityAirMana.register();
        CapabilityArcaneMana.register();
        CapabilityEarthMana.register();
        CapabilityFireMana.register();
        CapabilityWaterMana.register();
    }

    public void registerPackets() {
        INSTANCE.registerMessage(ServerPointsHandler.class, ServerPointsPacket.class, 1, Side.SERVER);
        INSTANCE.registerMessage(ServerEntriesHandler.class, ServerEntriesPacket.class, 3, Side.SERVER);
        INSTANCE.registerMessage(ServerSpellHandler.class, ServerUnlockSpellPacket.class, 5, Side.SERVER);
        INSTANCE.registerMessage(RayTraceSpellHandler.class, RayTraceSpellPacket.class, 9, Side.SERVER);

        INSTANCE.registerMessage(ClientPointsHandler.class, ClientPointsPacket.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(ClientEntriesHandler.class, ClientEntriesPacket.class, 4, Side.CLIENT);
        INSTANCE.registerMessage(ClientSpellHandler.class, ClientSpellPacket.class, 6, Side.CLIENT);
        INSTANCE.registerMessage(ClientParticleHandler.class, ClientParticlePacket.class, 7, Side.CLIENT);
        INSTANCE.registerMessage(ClientManaHandler.class, ClientManaPacket.class, 8, Side.CLIENT);
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileManaPad.class, MagicBooks2.MODID + ":tile_mana_pad");
        GameRegistry.registerTileEntity(TilePipe.class, MagicBooks2.MODID + ":tile_pipe");
        GameRegistry.registerTileEntity(TileAirPipe.class, MagicBooks2.MODID + ":tile_air_pipe");
        GameRegistry.registerTileEntity(TileArcanePipe.class, MagicBooks2.MODID + ":tile_arcane_pipe");
        GameRegistry.registerTileEntity(TileEarthPipe.class, MagicBooks2.MODID + ":tile_earth_pipe");
        GameRegistry.registerTileEntity(TileFirePipe.class, MagicBooks2.MODID + ":tile_fire_pipe");
        GameRegistry.registerTileEntity(TileWaterPipe.class, MagicBooks2.MODID + ":tile_water_pipe");
        GameRegistry.registerTileEntity(TileEarthWall.class, MagicBooks2.MODID + ":tile_earth_wall");
        GameRegistry.registerTileEntity(TilePedestal.class, MagicBooks2.MODID + ":tile_pedestal_block");
        GameRegistry.registerTileEntity(TileDrowningRune.class, MagicBooks2.MODID + ":tile:rune_drowning");
        GameRegistry.registerTileEntity(TileLumberRune.class, MagicBooks2.MODID + ":tile:rune_lumber");
        GameRegistry.registerTileEntity(TileManaExtractor.class, MagicBooks2.MODID + ":tile:mana_extractor");
        GameRegistry.registerTileEntity(TileManaVase.class, MagicBooks2.MODID + ":tile:mana_vase");
        GameRegistry.registerTileEntity(TileAirManaVase.class, MagicBooks2.MODID + ":tile:air_mana_vase");
        GameRegistry.registerTileEntity(TileArcaneManaVase.class, MagicBooks2.MODID + ":tile_arcane_mana_vase");
        GameRegistry.registerTileEntity(TileEarthManaVase.class, MagicBooks2.MODID + ":tile_earth_mana_vase");
        GameRegistry.registerTileEntity(TileFireManaVase.class, MagicBooks2.MODID + ":tile_fire_mana_vase");
        GameRegistry.registerTileEntity(TileWaterManaVase.class, MagicBooks2.MODID + "tile_water_mana_vase");
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

    public void init(FMLInitializationEvent event) {}

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed) {}

    public void registerOreDicItems() {
        OreDictionary.registerOre("shardAir", new ItemStack(ModItems.Shard, 1, 0));
        OreDictionary.registerOre("shardArcane", new ItemStack(ModItems.Shard, 1, 4));
        OreDictionary.registerOre("shardEarth", new ItemStack(ModItems.Shard, 1, 1));
        OreDictionary.registerOre("shardFire", new ItemStack(ModItems.Shard, 1, 2));
        OreDictionary.registerOre("shardWater", new ItemStack(ModItems.Shard, 1, 3));

    }

    public void registerOreDicBlocks() {
        OreDictionary.registerOre("oreAir", new ItemStack(ModBlocks.shardOre, 1, 0));
        OreDictionary.registerOre("oreArcane", new ItemStack(ModBlocks.shardOre, 1, 4));
        OreDictionary.registerOre("oreEarth", new ItemStack(ModBlocks.shardOre, 1, 1));
        OreDictionary.registerOre("oreFire", new ItemStack(ModBlocks.shardOre, 1, 2));
        OreDictionary.registerOre("oreWater", new ItemStack(ModBlocks.shardOre, 1, 3));
        OreDictionary.registerOre("blockAir", new ItemStack(ModBlocks.shardBlock, 1, 0));
        OreDictionary.registerOre("blockArcane", new ItemStack(ModBlocks.shardBlock, 1, 4));
        OreDictionary.registerOre("blockEarth", new ItemStack(ModBlocks.shardBlock, 1, 1));
        OreDictionary.registerOre("blockFire", new ItemStack(ModBlocks.shardBlock, 1, 2));
        OreDictionary.registerOre("blockWater", new ItemStack(ModBlocks.shardBlock, 1, 3));
    }
}
