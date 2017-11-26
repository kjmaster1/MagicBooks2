package com.kjmaster.magicbooks2.common.capabilities.mana;

import net.minecraft.nbt.NBTTagCompound;

public class ManaStorage {

    public int mana;
    int capacity;
    int maxReceive;
    int maxExtract;

    public ManaStorage(int capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public ManaStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public ManaStorage(int capacity, int maxReceive, int maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public ManaStorage(int capacity, int maxReceive, int maxExtract, int mana) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.mana = mana;
    }

    public int receiveMana(int maxReceive, boolean simulate) {
        if (!canReceiveMana())
            return 0;

        int manaReceived = Math.min(capacity - mana, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            mana += manaReceived;
        return manaReceived;
    }

    public int extractMana(int maxExtract, boolean simulate) {
        if (!canExtractMana())
            return 0;

        int manaExtracted = Math.min(mana, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            mana -= manaExtracted;
        return manaExtracted;
    }

    public int getManaStored() {
        return mana;
    }

    public int getMaxManaStored() {
        return capacity;
    }

    public boolean canExtractMana() {
        return this.maxExtract > 0;
    }

    public boolean canReceiveMana() {
        return this.maxReceive > 0;
    }

    public void consumeMana(int consume) {
        mana -= consume;
        if (mana < 0)
            mana = 0;
    }

    public void addMana(int add) {
        mana += add;
        if (mana > capacity)
            mana = capacity;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void readFromNBT(NBTTagCompound compound) {
        mana = compound.getInteger("Mana");
        if (mana > capacity)
            mana = capacity;
    }

    public void writeToNBT(NBTTagCompound compound) {
        if (mana < 0)
            mana = 0;
        compound.setInteger("Mana", mana);
    }
}
