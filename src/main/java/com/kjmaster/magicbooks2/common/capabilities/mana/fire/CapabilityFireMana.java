package com.kjmaster.magicbooks2.common.capabilities.mana.fire;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityFireMana implements ICapabilityProvider {

    @CapabilityInject(IFireMana.class)
    public static Capability<IFireMana> FIREMANA = null;

    private IFireMana instance = FIREMANA.getDefaultInstance();

    public static void register() {

        CapabilityManager.INSTANCE.register(IFireMana.class, new Capability.IStorage<IFireMana>() {
                    @Nullable
                    @Override
                    public NBTBase writeNBT(Capability<IFireMana> capability, IFireMana instance, EnumFacing side) {
                        return new NBTTagInt(instance.getManaStored());
                    }

                    @Override
                    public void readNBT(Capability<IFireMana> capability, IFireMana instance, EnumFacing side, NBTBase nbt) {
                        if (!(instance instanceof FireManaStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't" +
                                    " the default implementation");
                        ((FireManaStorage)instance).mana = ((NBTTagInt)nbt).getInt();
                    }
                },
                () -> new FireManaStorage(1000));
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == FIREMANA ? FIREMANA.cast(this.instance) : null;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == FIREMANA;
    }
}
