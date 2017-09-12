package com.kjmaster.magicbooks2.common.handlers;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class CapabilityHandler {
    public static final ResourceLocation MANA_CAP = new ResourceLocation(MagicBooks2.MODID,
            "Mana");
    public static final ResourceLocation POINTS_CAP = new ResourceLocation(MagicBooks2.MODID,
            "SkillPoints");

    @SubscribeEvent
    public void attachEntityCap(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer)) return;
        event.addCapability(MANA_CAP, new ManaProvider());
        event.addCapability(POINTS_CAP, new SkillPointsProvider());
    }
}
