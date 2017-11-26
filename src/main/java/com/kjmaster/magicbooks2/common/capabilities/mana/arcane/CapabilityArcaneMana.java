package com.kjmaster.magicbooks2.common.capabilities.mana.arcane;

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

public class CapabilityArcaneMana implements ICapabilityProvider {

    @CapabilityInject(IArcaneMana.class)
    public static Capability<IArcaneMana> ARCANEMANA = null;

    private IArcaneMana instance = ARCANEMANA.getDefaultInstance();

    public static void register() {

        CapabilityManager.INSTANCE.register(IArcaneMana.class, new Capability.IStorage<IArcaneMana>() {
                    @Nullable
                    @Override
                    public NBTBase writeNBT(Capability<IArcaneMana> capability, IArcaneMana instance, EnumFacing side) {
                        return new NBTTagInt(instance.getManaStored());
                    }

                    @Override
                    public void readNBT(Capability<IArcaneMana> capability, IArcaneMana instance, EnumFacing side, NBTBase nbt) {
                        if (!(instance instanceof ArcaneManaStorage))
                            throw new IllegalArgumentException("Can not deserialize to an instance that isn't" +
                                    " the default implementation");
                        ((ArcaneManaStorage)instance).mana = ((NBTTagInt)nbt).getInt();
                    }
                },
                () -> new ArcaneManaStorage(1000));
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == ARCANEMANA ? ARCANEMANA.cast(this.instance) : null;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ARCANEMANA;
    }
}
