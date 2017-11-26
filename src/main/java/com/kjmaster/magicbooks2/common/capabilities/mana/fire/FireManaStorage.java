package com.kjmaster.magicbooks2.common.capabilities.mana.fire;

import com.kjmaster.magicbooks2.common.capabilities.mana.ManaStorage;

public class FireManaStorage extends ManaStorage implements IFireMana {

    public FireManaStorage(int capacity) {
        super(capacity);
    }

    public FireManaStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public FireManaStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public FireManaStorage(int capacity, int maxReceive, int maxExtract, int mana) {
        super(capacity, maxReceive, maxExtract, mana);
    }
}
