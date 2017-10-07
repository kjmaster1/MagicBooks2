package com.kjmaster.magicbooks2.common.capabilities.unlockedspells;

import net.minecraft.entity.player.EntityPlayer;

public interface ISpells {
    int getManaCost(Spell spell);
    String getElement(Spell spell);
    Boolean getIsUnlocked(Spell spell);
    void setValueUnlocked(Spell spell, Boolean value);
    void setUnlocked(Spell spell);
    Spell getSpell(String spell);
    Spell[] getSpellList();
    void castSpell(EntityPlayer player, String spell);
}
