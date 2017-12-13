package com.kjmaster.magicbooks2.common.blocks.tile.runes;


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

    String element;
    ManaStorage storage;
    private int manaUse;

    public TileRune(){}

    public TileRune(String element, int manaUse) {
        switch (element) {
            case "Air":
                this.storage = new AirManaStorage(1000, 500, 0);
            case "Arcane":
                this.storage = new ArcaneManaStorage(1000, 500, 0);
            case "Earth":
                this.storage = new EarthManaStorage(1000, 500, 0);
            case "Fire":
                this.storage = new FireManaStorage(1000, 500, 0);
            case "Water":
                this.storage = new WaterManaStorage(1000, 500, 0);
        }
        this.manaUse = manaUse;
        this.element = element;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        storage.readFromNBT(compound);
        setElement(compound.getString("Element"));
        setManaUse(compound.getInteger("ManaUse"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        storage.writeToNBT(compound);
        compound.setString("Element", getElement());
        compound.setInteger("ManaUse", getManaUse());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        switch (element) {
            case "Air":
                return capability == CapabilityAirMana.AIRMANA;
            case "Arcane":
                return capability == CapabilityArcaneMana.ARCANEMANA;
            case "Earth":
                return capability == CapabilityEarthMana.EARTHMANA;
            case "Fire":
                return capability == CapabilityFireMana.FIREMANA;
            case "Water":
                return capability == CapabilityWaterMana.WATERMANA;
            default:
                return super.hasCapability(capability, facing);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        switch (element) {
            case "Air":
                if (capability == CapabilityAirMana.AIRMANA)
                    return (T) storage;
                break;
            case "Arcane":
                if (capability == CapabilityArcaneMana.ARCANEMANA)
                    return (T) storage;
            case "Earth":
                if (capability == CapabilityEarthMana.EARTHMANA)
                    return (T) storage;
            case "Fire":
                if (capability == CapabilityFireMana.FIREMANA)
                    return (T) storage;
            case "Water":
                if (capability == CapabilityWaterMana.WATERMANA)
                    return (T) storage;
        }
        return super.getCapability(capability, facing);
    }

    public int getManaUse() {
        return manaUse;
    }

    public String getElement() {
        return element;
    }

    public ManaStorage getStorage() {
        return storage;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public void setManaUse(int manaUse) {
        this.manaUse = manaUse;
    }

    public void setStorage(ManaStorage storage) {
        this.storage = storage;
    }

    public int getField(int id) {
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
}
