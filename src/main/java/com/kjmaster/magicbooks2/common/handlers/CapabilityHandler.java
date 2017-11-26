package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class CapabilityHandler {
    public static final ResourceLocation AIR_CAP = new ResourceLocation(MagicBooks2.MODID,
            "AirMana");
    public static final ResourceLocation ARCANE_CAP = new ResourceLocation(MagicBooks2.MODID,
            "ArcaneMana");
    public static final ResourceLocation EARTH_CAP = new ResourceLocation(MagicBooks2.MODID,
            "EarthMana");
    public static final ResourceLocation FIRE_CAP = new ResourceLocation(MagicBooks2.MODID,
            "FireMana");
    public static final ResourceLocation WATER_CAP = new ResourceLocation(MagicBooks2.MODID,
            "WaterMana");
    public static final ResourceLocation POINTS_CAP = new ResourceLocation(MagicBooks2.MODID,
            "SkillPoints");
    public static final ResourceLocation ENTRIES_CAP = new ResourceLocation(MagicBooks2.MODID,
            "Entries");
    public static final ResourceLocation SPELLS_CAP = new ResourceLocation(MagicBooks2.MODID,
            "Spells");


    @SubscribeEvent
    public void attachEntityCap(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer)) return;
        event.addCapability(POINTS_CAP, new SkillPointsProvider());
        event.addCapability(AIR_CAP, new CapabilityAirMana());
        event.addCapability(ARCANE_CAP, new CapabilityArcaneMana());
        event.addCapability(EARTH_CAP, new CapabilityEarthMana());
        event.addCapability(FIRE_CAP, new CapabilityFireMana());
        event.addCapability(WATER_CAP, new CapabilityWaterMana());
        event.addCapability(ENTRIES_CAP, new EntriesProvider());
        event.addCapability(SPELLS_CAP, new SpellsProvider());
    }
}
