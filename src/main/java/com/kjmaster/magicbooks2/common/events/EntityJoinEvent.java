package com.kjmaster.magicbooks2.common.events;

import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.network.ClientPointsPacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class EntityJoinEvent {
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (!player.world.isRemote) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            ISkillPoints skillPointsCap = playerMP.getCapability(SkillPointsProvider.SKILL_POINTS_CAP, null);
            int airPoints = skillPointsCap.getPoints("Air");
            int arcanePoints = skillPointsCap.getPoints("Arcane");
            int earthPoints = skillPointsCap.getPoints("Earth");
            int firePoints = skillPointsCap.getPoints("Fire");
            int waterPoints = skillPointsCap.getPoints("Water");
            PacketInstance.INSTANCE.sendTo(new ClientPointsPacket(airPoints, "Air"), playerMP);
            PacketInstance.INSTANCE.sendTo(new ClientPointsPacket(arcanePoints, "Arcane"), playerMP);
            PacketInstance.INSTANCE.sendTo(new ClientPointsPacket(earthPoints, "Earth"), playerMP);
            PacketInstance.INSTANCE.sendTo(new ClientPointsPacket(firePoints, "Fire"), playerMP);
            PacketInstance.INSTANCE.sendTo(new ClientPointsPacket(waterPoints, "Water"), playerMP);
        }
    }
}
