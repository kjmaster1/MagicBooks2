package com.kjmaster.magicbooks2.common.capabilities.mana;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public interface IMana {
    void receiveMana(int mana, String type);
    void extractMana(int mana, String type);
    int getMana(String type);
    void setMana(int mana, String type);
    void setCapacity(int capacity);
    int getCapacity();
}
