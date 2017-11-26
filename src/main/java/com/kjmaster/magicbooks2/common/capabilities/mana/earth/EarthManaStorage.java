package com.kjmaster.magicbooks2.common.capabilities.mana.earth;

import com.kjmaster.magicbooks2.common.capabilities.mana.ManaStorage;

public class EarthManaStorage extends ManaStorage implements IEarthMana {

    public EarthManaStorage(int capacity) {
        super(capacity);
    }

    public EarthManaStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EarthManaStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EarthManaStorage(int capacity, int maxReceive, int maxExtract, int mana) {
        super(capacity, maxReceive, maxExtract, mana);
    }
}
