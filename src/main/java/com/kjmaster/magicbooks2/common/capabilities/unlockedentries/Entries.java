package com.kjmaster.magicbooks2.common.capabilities.unlockedentries;

public class Entries implements IEntries {

    public static String[] entriesList = {"Intro"};
    private boolean isIntroUnlocked;

    @Override
    public void unlockEntry(String entry) {
        setEntry(entry, true);
    }

    @Override
    public void setEntry(String entry, Boolean value) {
        switch (entry) {
            case "Intro":
                this.isIntroUnlocked = value;
                break;
            default:
                break;
        }
    }

    @Override
    public Boolean isEntryUnlocked(String entry) {
        switch (entry) {
            case "Intro":
                return isIntroUnlocked;
            default:
                return false;
        }
    }
}
