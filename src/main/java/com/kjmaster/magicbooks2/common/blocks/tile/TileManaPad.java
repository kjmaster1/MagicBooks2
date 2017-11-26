package com.kjmaster.magicbooks2.common.blocks.tile;

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
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class TileManaPad extends TileEntity {

    private Capability<?>[] capabilities = {CapabilityAirMana.AIRMANA, CapabilityArcaneMana.ARCANEMANA,
    CapabilityEarthMana.EARTHMANA, CapabilityFireMana.FIREMANA, CapabilityWaterMana.WATERMANA};
    private List capabilitiesList = Arrays.asList(capabilities);

    private int MAX_MANA = 10000;

    private final AirManaStorage airStorage =
            new AirManaStorage(MAX_MANA, MAX_MANA, 0);
    private final ArcaneManaStorage arcaneStorage =
            new ArcaneManaStorage(MAX_MANA, MAX_MANA, 0);
    private final EarthManaStorage earthStorage =
            new EarthManaStorage(MAX_MANA, MAX_MANA, 0);
    private final FireManaStorage fireStorage =
            new FireManaStorage(MAX_MANA, MAX_MANA, 0);
    private final WaterManaStorage waterStorage =
            new WaterManaStorage(MAX_MANA, MAX_MANA, 0);

    public TileManaPad() {this.markDirty();}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.airStorage.readFromNBT(compound);
        this.arcaneStorage.readFromNBT(compound);
        this.earthStorage.readFromNBT(compound);
        this.fireStorage.readFromNBT(compound);
        this.waterStorage.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.airStorage.writeToNBT(compound);
        this.arcaneStorage.writeToNBT(compound);
        this.fireStorage.writeToNBT(compound);
        this.waterStorage.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capabilitiesList.contains(capability) || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capabilitiesList.contains(capability))
            return (T) this;
        else
            return super.getCapability(capability, facing);
    }
}
