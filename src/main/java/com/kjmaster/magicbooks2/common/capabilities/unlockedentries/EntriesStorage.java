package com.kjmaster.magicbooks2.common.capabilities.unlockedentries;

import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPoints;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EntriesStorage implements Capability.IStorage<IEntries> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IEntries> capability, IEntries instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        for (String entry: Entries.entriesList) {
            compound.setBoolean(entry, instance.isEntryUnlocked(entry));
        }
        return compound;
    }

    @Override
    public void readNBT(Capability<IEntries> capability, IEntries instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        for (String entry: Entries.entriesList) {
            instance.setEntry(entry, compound.getBoolean(entry));
        }
    }
}
