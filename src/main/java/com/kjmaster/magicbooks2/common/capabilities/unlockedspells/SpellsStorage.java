package com.kjmaster.magicbooks2.common.capabilities.unlockedspells;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SpellsStorage implements Capability.IStorage<ISpells> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ISpells> capability, ISpells instance, EnumFacing side) {
        NBTTagList tagList = new NBTTagList();
        Spell[] spells = instance.getSpellList();
        for(Spell spell: spells) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setBoolean("Unlocked", instance.getIsUnlocked(spell));
            tagList.appendTag(tagCompound);
        }
        return tagList;

    }

    @Override
    public void readNBT(Capability<ISpells> capability, ISpells instance, EnumFacing side, NBTBase nbt) {
        NBTTagList tagList = (NBTTagList) nbt;
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            Boolean isUnlocked = tagCompound.getBoolean("Unlocked");
            Spell[] spells = instance.getSpellList();
            Spell spell = spells[i];
            instance.setValueUnlocked(spell, isUnlocked);
        }
    }
}
