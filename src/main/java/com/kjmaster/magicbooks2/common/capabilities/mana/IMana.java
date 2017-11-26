package com.kjmaster.magicbooks2.common.capabilities.mana;

public interface IMana {

    int receiveMana(int maxReceive, boolean simulate);

    int extractMana(int maxExtract, boolean simulate);

    int getManaStored();

    int getMaxManaStored();

    boolean canExtractMana();

    boolean canReceiveMana();

    void consumeMana(int consume);

    void setMana(int mana);
}
