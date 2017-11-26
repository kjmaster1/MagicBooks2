package com.kjmaster.magicbooks2.common.capabilities.mana.air;

import com.kjmaster.magicbooks2.common.capabilities.mana.ManaStorage;

public class AirManaStorage extends ManaStorage implements IAirMana {

    public AirManaStorage(int capacity) {
        super(capacity);
    }

    public AirManaStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public AirManaStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public AirManaStorage(int capacity, int maxReceive, int maxExtract, int mana) {
        super(capacity, maxReceive, maxExtract, mana);
    }
}
