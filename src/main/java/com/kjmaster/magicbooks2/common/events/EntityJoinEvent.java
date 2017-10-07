package com.kjmaster.magicbooks2.common.events;

import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.EntriesProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedentries.IEntries;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.network.*;
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
            String[] elements = {"Air", "Arcane", "Earth", "Fire", "Water"};
            //Entries sync
            IEntries entriesCap = playerMP.getCapability(EntriesProvider.ENTRIES_CAP, null);
            Boolean isIntroUnlocked = entriesCap.isEntryUnlocked("Intro");
            PacketInstance.INSTANCE.sendTo(new ClientEntriesPacket("Intro", isIntroUnlocked), playerMP);
            //Spells sync
            ISpells spellsCap = playerMP.getCapability(SpellsProvider.SPELLS_CAP, null);
            Spell[] spells = spellsCap.getSpellList();
            for (Spell spell: spells) {
                Boolean isUnlocked = spellsCap.getIsUnlocked(spell);
                PacketInstance.INSTANCE.sendTo(new ClientSpellPacket(spell.getAsString(), isUnlocked), playerMP);
            }
            //Mana + SkillPoints Sync
            IMana manaCap = playerMP.getCapability(ManaProvider.MANA_CAP, null);
            ISkillPoints skillPointsCap = playerMP.getCapability(SkillPointsProvider.SKILL_POINTS_CAP, null);
            for (String element: elements) {
                int mana = manaCap.getMana(element);
                PacketInstance.INSTANCE.sendTo(new ClientManaPacket(element, mana), playerMP);
                int skillPoints = skillPointsCap.getPoints(element);
                PacketInstance.INSTANCE.sendTo(new ClientPointsPacket(skillPoints, element, false), playerMP);
            }
        }
    }
}
