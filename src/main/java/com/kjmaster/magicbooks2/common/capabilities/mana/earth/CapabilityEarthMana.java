package com.kjmaster.magicbooks2.common.capabilities.mana.earth;

import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.AirManaStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityEarthMana implements ICapabilityProvider {

    @CapabilityInject(IEarthMana.class)
    public static Capability<IEarthMana> EARTHMANA = null;

    private IEarthMana instance = EARTHMANA.getDefaultInstance();

    public static void register() {

        CapabilityManager.INSTANCE.register(IEarthMana.class, new Capability.IStorage<IEarthMana>() {
                    @Nullable
                    @Override
                    public NBTBase writeNBT(Capability<IEarthMana> capability, IEarthMana instance, EnumFacing side) {
                        return new NBTTagInt(instance.getManaStored());
                    }

                    @Override
                    public void readNBT(Capability<IEarthMana> capability, IEarthMana instance, EnumFacing side, NBTBase nbt) {
                        if (!(instance instanceof EarthManaStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't" +
                                    " the default implementation");
                        ((EarthManaStorage)instance).mana = ((NBTTagInt)nbt).getInt();
                    }
                },
                () -> new EarthManaStorage(1000));
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == EARTHMANA ? EARTHMANA.cast(this.instance) : null;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == EARTHMANA;
    }
}
