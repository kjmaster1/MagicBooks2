package com.kjmaster.magicbooks2.common.capabilities.mana.crystals;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public interface IManaStorage {

    int receiveMana(int maxReceive, boolean simulate, String element);

    int extractMana(int maxExtract, boolean simulate, String element);

    int getManaStored(String element);

    boolean canExtract(String element);

    boolean canReceive(String element);

    boolean isFull(String element);
}
