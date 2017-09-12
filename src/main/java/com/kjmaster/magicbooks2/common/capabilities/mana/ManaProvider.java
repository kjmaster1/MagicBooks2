package com.kjmaster.magicbooks2.common.capabilities.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class ManaProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IMana.class)
    public static final Capability<IMana> MANA_CAP = null;

    private IMana manaInstance = MANA_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == MANA_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == MANA_CAP ? MANA_CAP.<T>cast(this.manaInstance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return MANA_CAP.getStorage().writeNBT(MANA_CAP, this.manaInstance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        MANA_CAP.getStorage().readNBT(MANA_CAP, this.manaInstance, null, nbt);
    }
}
