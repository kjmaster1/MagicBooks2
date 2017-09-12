package com.kjmaster.magicbooks2.common.capabilities.mana;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class Mana implements IMana {

    public static String[] manaList = {"Air", "Earth", "Fire", "Water"};
    private int airMana = 0;
    private int earthMana = 0;
    private int fireMana = 0;
    private int waterMana = 0;

    private int capacity = 0;

    @Override
    public void receiveMana(int mana, String type) {
        int stored = getMana(type);
        int accepted = Math.min(mana, capacity - stored);
        stored += accepted;
        this.setMana(stored, type);
    }

    @Override
    public void extractMana(int mana, String type) {
        int stored = getMana(type);
        int extracted = Math.min(mana, stored);
        stored -= extracted;
        this.setMana(stored, type);
    }

    @Override
    public void setMana(int mana, String type) {
        switch (type) {
            case "Air":
                this.airMana = mana;
                break;
            case "Earth":
                this.earthMana = mana;
                break;
            case "Fire":
                this.fireMana = mana;
                break;
            case "Water":
                this.waterMana = mana;
                break;
            default:
                break;
        }
    }

    @Override
    public int getMana(String type) {
        switch (type) {
            case "Air":
                return airMana;
            case "Earth":
                return earthMana;
            case "Fire":
                return fireMana;
            case "Water":
                return waterMana;
            default:
                return 0;
        }
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
