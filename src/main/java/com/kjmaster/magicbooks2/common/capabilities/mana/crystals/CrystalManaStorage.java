package com.kjmaster.magicbooks2.common.capabilities.mana.crystals;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class CrystalManaStorage implements IManaStorage {

    protected int airMana = 0;
    protected int arcaneMana = 0;
    protected int earthMana = 0;
    protected int fireMana = 0;
    protected int waterMana = 0;
    public int capacity;
    private int maxReceive;
    private int maxExtract;

    public CrystalManaStorage(int capacity) { this(capacity, capacity, capacity, 0); }
    public CrystalManaStorage(int capacity, int maxTransfer) { this(capacity, maxTransfer, maxTransfer, 0); }
    public CrystalManaStorage(int capacity, int maxReceive, int maxExtract) {this(capacity, maxReceive, maxExtract, 0); }
    public CrystalManaStorage(int capacity, int maxReceive, int maxExtract, int mana) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.airMana = Math.max(0, Math.min(capacity, mana));
        this.arcaneMana = Math.max(0, Math.min(capacity, mana));
        this.earthMana = Math.max(0, Math.min(capacity, mana));
        this.fireMana = Math.max(0, Math.min(capacity, mana));
        this.waterMana = Math.max(0, Math.min(capacity, mana));
    }

    @Override
    public int receiveMana(int maxReceive, boolean simulate, String element) {
        switch (element) {
            case "Air":
                if(!canReceive("Air"))
                    return 0;
                int airManaReceived = Math.min(capacity - airMana, Math.min(this.maxReceive, maxReceive));
                if (!simulate)
                    airMana += airManaReceived;
                return airManaReceived;
            case "Arcane":
                if(!canReceive("Arcane"))
                    return 0;
                int arcaneManaReceived = Math.min(capacity - arcaneMana, Math.min(this.maxReceive, maxReceive));
                if (!simulate)
                    arcaneMana += arcaneManaReceived;
                return arcaneManaReceived;
            case "Earth":
                if(!canReceive("Earth"))
                    return 0;
                int earthManaReceived = Math.min(capacity - earthMana, Math.min(this.maxReceive, maxReceive));
                if (!simulate)
                    earthMana += earthManaReceived;
                return earthManaReceived;
            case "Fire":
                if(!canReceive("Fire"))
                    return 0;
                int fireManaReceived = Math.min(capacity - fireMana, Math.min(this.maxReceive, maxReceive));
                if (!simulate)
                    fireMana += fireManaReceived;
                return fireManaReceived;
            case "Water":
                if(!canReceive("Water"))
                    return 0;
                int waterManaReceived = Math.min(capacity - waterMana, Math.min(this.maxReceive, maxReceive));
                if (!simulate)
                    waterMana += waterManaReceived;
                return waterManaReceived;
            default:
                return 0;

        }
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate, String element) {
        switch (element) {
            case "Air":
                if (!canExtract("Air"))
                    return 0;

                int airManaExtracted = Math.min(airMana, Math.min(this.maxExtract, maxExtract));
                if (!simulate)
                    airMana -= airManaExtracted;
                return airManaExtracted;
            case "Arcane":
                if (!canExtract("Arcane"))
                    return 0;

                int arcaneManaExtracted = Math.min(arcaneMana, Math.min(this.maxExtract, maxExtract));
                if (!simulate)
                    arcaneMana -= arcaneManaExtracted;
                return arcaneManaExtracted;
            case "Earth":
                if (!canExtract("Earth"))
                    return 0;

                int earthManaExtracted = Math.min(earthMana, Math.min(this.maxExtract, maxExtract));
                if (!simulate)
                    earthMana -= earthManaExtracted;
                return earthManaExtracted;
            case "Fire":
                if (!canExtract("Fire"))
                    return 0;

                int fireManaExtracted = Math.min(fireMana, Math.min(this.maxExtract, maxExtract));
                if (!simulate)
                    fireMana -= fireManaExtracted;
                return fireManaExtracted;
            case "Water":
                if (!canExtract("Water"))
                    return 0;

                int waterManaExtracted = Math.min(waterMana, Math.min(this.maxExtract, maxExtract));
                if (!simulate)
                    waterMana -= waterManaExtracted;
                return waterManaExtracted;
            default:
                return 0;
        }
    }

    @Override
    public int getManaStored(String element) {
        switch (element) {
            case "Air":
                return airMana;
            case "Arcane":
                return arcaneMana;
            case "Earth":
                return earthMana;
            case "Fire":
                return fireMana;
            case "Water":
                return waterMana;
            default:
                return 0;
        }
    }

    @Override
    public boolean canExtract(String element) {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive(String element) {
        return this.maxReceive > 0;
    }

    @Override
    public boolean isFull(String element) {
        switch (element) {
            case "Air":
                return this.airMana == capacity;
            case "Arcane":
                return this.arcaneMana == capacity;
            case "Earth":
                return this.earthMana == capacity;
            case "Fire":
                return this.fireMana == capacity;
            case "Water":
                return this.waterMana == capacity;
            default:
                return true;
        }
    }

    public void readFromNBT(NBTTagCompound compound) {
        this.setMana(compound.getInteger("Air"), "Air");
        this.setMana(compound.getInteger("Arcane"), "Arcane");
        this.setMana(compound.getInteger("Earth"), "Earth");
        this.setMana(compound.getInteger("Fire"), "Fire");
        this.setMana(compound.getInteger("Water"), "Water");

    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Air", this.getManaStored("Air"));
        compound.setInteger("Arcane", this.getManaStored("Arcane"));
        compound.setInteger("Earth", this.getManaStored("Earth"));
        compound.setInteger("Fire", this.getManaStored("Fire"));
        compound.setInteger("Water", this.getManaStored("Water"));
    }

    public void setMana(int mana, String element) {
        switch (element) {
            case "Air":
                this.airMana = mana;
                break;
            case "Arcane":
                this.arcaneMana = mana;
                break;
            case "Earth":
                this.earthMana = mana;
                break;
            case "Fire":
                this.fireMana = mana;
                break;
            case "Water":
                this.waterMana = mana;
                break;
        }
    }
}
