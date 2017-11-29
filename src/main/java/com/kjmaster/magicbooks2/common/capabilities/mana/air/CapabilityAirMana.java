package com.kjmaster.magicbooks2.common.capabilities.mana.air;

import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityAirMana implements ICapabilityProvider {

    @CapabilityInject(IAirMana.class)
    public static Capability<IAirMana> AIRMANA = null;

    private IAirMana instance = AIRMANA.getDefaultInstance();

    public static void register() {

        CapabilityManager.INSTANCE.register(IAirMana.class, new Capability.IStorage<IAirMana>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IAirMana> capability, IAirMana instance, EnumFacing side) {
                return new NBTTagInt(instance.getManaStored());
            }

            @Override
            public void readNBT(Capability<IAirMana> capability, IAirMana instance, EnumFacing side, NBTBase nbt) {
                if (!(instance instanceof AirManaStorage))
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't" +
                            " the default implementation");
                ((AirManaStorage)instance).mana = ((NBTTagInt)nbt).getInt();
            }
        },
                () -> new AirManaStorage(10000));
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == AIRMANA ? AIRMANA.cast(this.instance) : null;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == AIRMANA;
    }
}
