package com.kjmaster.magicbooks2.common.capabilities.unlockedspells;

import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.RayTraceSpellPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class Spells implements ISpells {
    //Air
    private Spell lightning = new Spell(500, 16,"Air", "lightning");
    private Spell invisibility = new Spell(1000, 8,"Air", "invisibility");
    //Arcane
    //Earth
    private Spell grow = new Spell(10, 8, "Earth", "grow");
    private Spell walling = new Spell(200, 16, "Earth", "walling");
    //Fire
    private Spell fireblast = new Spell(250, 8, "Fire", "fireblast");

    private Spell[] spellsList = {lightning, invisibility, grow, walling, fireblast};

    @Override
    public int getManaCost(Spell spell) {
        return spell.getManaCost();
    }

    @Override
    public String getElement(Spell spell) {
        return spell.getElement();
    }

    @Override
    public Boolean getIsUnlocked(Spell spell) {
        return spell.getIsUnlocked();
    }

    @Override
    public void setValueUnlocked(Spell spell, Boolean value) {
        spell.setValueUnlocked(value);
    }

    @Override
    public void setUnlocked(Spell spell) {
        spell.setUnlocked();
    }

    @Override
    public Spell getSpell(String spell) {
        for (Spell spell2: spellsList)
            if(spell2.getAsString().equals(spell))
                return spell2;
        return null;
    }

    @Override
    public Spell[] getSpellList() {
        return spellsList;
    }

    @Override
    public void castSpell(EntityPlayer player, String spell) {
        RayTraceResult lastPos = player.rayTrace(10, 1.0F);
        BlockPos pos = lastPos.getBlockPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        PacketInstance.INSTANCE.sendToServer(new RayTraceSpellPacket(x, y, z, spell));
    }
}
