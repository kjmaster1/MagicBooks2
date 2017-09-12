package com.kjmaster.magicbooks2.common.capabilities.mana;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public interface IMana {
    public void receiveMana(int mana, String type);
    public void extractMana(int mana, String type);
    public int getMana(String type);
    public void setMana(int mana, String type);
    public void setCapacity(int capacity);
}
