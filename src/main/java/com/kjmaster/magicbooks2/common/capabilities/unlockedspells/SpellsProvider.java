package com.kjmaster.magicbooks2.common.capabilities.unlockedspells;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpellsProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ISpells.class)
    public static final Capability<ISpells> SPELLS_CAP = null;

    private ISpells instance = SPELLS_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == SPELLS_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == SPELLS_CAP ? SPELLS_CAP.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return SPELLS_CAP.getStorage().writeNBT(SPELLS_CAP, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        SPELLS_CAP.getStorage().readNBT(SPELLS_CAP, instance, null, nbt);
    }
}
