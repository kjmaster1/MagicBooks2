package com.kjmaster.magicbooks2.common.capabilities.mana.arcane;

import com.kjmaster.magicbooks2.common.capabilities.mana.ManaStorage;

public class ArcaneManaStorage extends ManaStorage implements IArcaneMana {

    public ArcaneManaStorage(int capacity) {
        super(capacity);
    }

    public ArcaneManaStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ArcaneManaStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public ArcaneManaStorage(int capacity, int maxReceive, int maxExtract, int mana) {
        super(capacity, maxReceive, maxExtract, mana);
    }
}
