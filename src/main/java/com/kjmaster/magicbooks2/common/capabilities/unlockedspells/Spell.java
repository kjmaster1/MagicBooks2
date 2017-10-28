package com.kjmaster.magicbooks2.common.capabilities.unlockedspells;

public class Spell {

    private int manaCost;
    private int pointCost;
    private String element;
    private String asString;
    private Boolean isUnlocked;
    private Boolean isBeingCast;

    public Spell(int manaCost, int pointCost, String element, String name) {
        this.manaCost = manaCost;
        this.pointCost = pointCost;
        this.element = element;
        this.asString = name;
        this.isUnlocked = false;
        this.isBeingCast = false;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getPointCost() { return  pointCost; }

    public String getElement() {
        return element;
    }

    public Boolean getIsUnlocked() {
        return isUnlocked;
    }

    public String getAsString() { return asString; }

    public void setValueUnlocked(Boolean value) {
        this.isUnlocked = value;
    }

    public void setUnlocked() {
        setValueUnlocked(true);
    }

    public boolean getIsBeingCast() { return isBeingCast; }

    public void setIsBeingCast(Boolean value) { this.isBeingCast = value; }
}
