package com.kjmaster.magicbooks2.common.capabilities.mana.water;

import com.kjmaster.magicbooks2.common.capabilities.mana.ManaStorage;

public class WaterManaStorage extends ManaStorage implements IWaterMana {

    public WaterManaStorage(int capacity) {
        super(capacity);
    }

    public WaterManaStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public WaterManaStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public WaterManaStorage(int capacity, int maxReceive, int maxExtract, int mana) {
        super(capacity, maxReceive, maxExtract, mana);
    }

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        return super.receiveMana(maxReceive, simulate);
    }
}
