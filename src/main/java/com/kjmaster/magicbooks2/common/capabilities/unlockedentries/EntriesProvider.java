package com.kjmaster.magicbooks2.common.capabilities.unlockedentries;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntriesProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IEntries.class)
    public static final Capability<IEntries> ENTRIES_CAP = null;

    private IEntries entriesInstance = ENTRIES_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ENTRIES_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == ENTRIES_CAP ? ENTRIES_CAP.cast(this.entriesInstance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return ENTRIES_CAP.getStorage().writeNBT(ENTRIES_CAP, this.entriesInstance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        ENTRIES_CAP.getStorage().readNBT(ENTRIES_CAP, this.entriesInstance, null, nbt);
    }
}
