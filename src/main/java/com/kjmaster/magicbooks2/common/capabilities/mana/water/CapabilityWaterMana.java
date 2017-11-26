package com.kjmaster.magicbooks2.common.capabilities.mana.water;

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

public class CapabilityWaterMana implements ICapabilityProvider {

    @CapabilityInject(IWaterMana.class)
    public static Capability<IWaterMana> WATERMANA = null;

    private IWaterMana instance = WATERMANA.getDefaultInstance();

    public static void register() {

        CapabilityManager.INSTANCE.register(IWaterMana.class, new Capability.IStorage<IWaterMana>() {
                    @Nullable
                    @Override
                    public NBTBase writeNBT(Capability<IWaterMana> capability, IWaterMana instance, EnumFacing side) {
                        return new NBTTagInt(instance.getManaStored());
                    }

                    @Override
                    public void readNBT(Capability<IWaterMana> capability, IWaterMana instance, EnumFacing side, NBTBase nbt) {
                        if (!(instance instanceof WaterManaStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't" +
                                    " the default implementation");
                        ((WaterManaStorage)instance).mana = ((NBTTagInt)nbt).getInt();
                    }
                },
                () -> new WaterManaStorage(1000));
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == WATERMANA ? WATERMANA.cast(this.instance) : null;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == WATERMANA;
    }
}
