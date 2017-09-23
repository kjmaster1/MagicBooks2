package com.kjmaster.magicbooks2.common.capabilities.mana.crystals;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by pbill_000 on 13/09/2017.
 */
public class CrystalManaCapability implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IManaStorage.class)
    public static Capability<IManaStorage> MANA = null;
    private IManaStorage instance = MANA.getDefaultInstance();
    public  static int capacity = 0;
    public CrystalManaCapability(int capacity) { this.capacity = capacity; }
    public static void register() {
        CapabilityManager.INSTANCE.register(IManaStorage.class, new IStorage<IManaStorage>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IManaStorage> capability, IManaStorage instance, EnumFacing side) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger("Air", instance.getManaStored("Air"));
                compound.setInteger("Arcane", instance.getManaStored("Arcane"));
                compound.setInteger("Earth", instance.getManaStored("Earth"));
                compound.setInteger("Fire", instance.getManaStored("Fire"));
                compound.setInteger("Water", instance.getManaStored("Water"));
                return compound;
            }

            @Override
            public void readNBT(Capability<IManaStorage> capability, IManaStorage instance, EnumFacing side, NBTBase nbt) {
                if(!(instance instanceof CrystalManaStorage))
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                NBTTagCompound compound = (NBTTagCompound) nbt;
                ((CrystalManaStorage) instance).airMana = compound.getInteger("Air");
                ((CrystalManaStorage) instance).arcaneMana = compound.getInteger("Arcane");
                ((CrystalManaStorage) instance).earthMana = compound.getInteger("Earth");
                ((CrystalManaStorage) instance).fireMana = compound.getInteger("Fire");
                ((CrystalManaStorage) instance).waterMana = compound.getInteger("Water");
            }
        },
                () -> new CrystalManaStorage(capacity));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == MANA;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == MANA ? MANA.<T>cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return MANA.getStorage().writeNBT(MANA, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        MANA.getStorage().readNBT(MANA, this.instance, null, nbt);
    }
}
