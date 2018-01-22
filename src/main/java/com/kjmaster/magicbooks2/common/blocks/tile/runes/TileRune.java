package com.kjmaster.magicbooks2.common.blocks.tile.runes;


import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.AirManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.ArcaneManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.EarthManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.FireManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.WaterManaStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileRune extends TileEntity {

    ManaStorage storage;
    private int manaUse;

    public TileRune(){}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        storage.readFromNBT(compound);
        setManaUse(compound.getInteger("ManaUse"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        storage.writeToNBT(compound);
        compound.setInteger("ManaUse", getManaUse());
        return compound;
    }


    public int getManaUse() {
        return manaUse;
    }

    public ManaStorage getStorage() {
        return storage;
    }

    public void setManaUse(int manaUse) {
        this.manaUse = manaUse;
    }

    public void setStorage(ManaStorage storage) {
        this.storage = storage;
    }

    public int getField(int id) {

        if (storage == null)
            return 0;
        switch (id) {
            case 0:
                return this.storage.getManaStored();
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.storage.setMana(value);
                break;
        }
    }

    public static class TileAirRune extends TileRune {

        public TileAirRune() {}

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityAirMana.AIRMANA || super.hasCapability(capability, facing);
        }

        @SuppressWarnings("unchecked")
        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityAirMana.AIRMANA)
                return (T) storage;
            else
                return super.getCapability(capability, facing);
        }
    }

    public static class TileArcaneRune extends TileRune {

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityArcaneMana.ARCANEMANA || super.hasCapability(capability, facing);
        }

        @SuppressWarnings("unchecked")
        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityArcaneMana.ARCANEMANA)
                return (T) storage;
            else
                return super.getCapability(capability, facing);
        }
    }

    public static class TileEarthRune extends TileRune {

        public TileEarthRune() {}

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityEarthMana.EARTHMANA || super.hasCapability(capability, facing);
        }

        @SuppressWarnings("unchecked")
        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityEarthMana.EARTHMANA)
                return (T) storage;
            else
                return super.getCapability(capability, facing);
        }
    }

    public static class TileFireRune extends TileRune {

        public TileFireRune() {}

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityFireMana.FIREMANA || super.hasCapability(capability, facing);
        }

        @SuppressWarnings("unchecked")
        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityFireMana.FIREMANA)
                return (T) storage;
            return super.getCapability(capability, facing);
        }
    }

    public static class TileWaterRune extends TileRune {

        public TileWaterRune() {}

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityWaterMana.WATERMANA || super.hasCapability(capability, facing);
        }

        @SuppressWarnings("unchecked")
        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityWaterMana.WATERMANA)
                return (T) storage;
            return super.getCapability(capability, facing);
        }
    }
}
