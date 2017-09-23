package com.kjmaster.magicbooks2.common.capabilities.unlockedentries;

public interface IEntries {
    void unlockEntry(String entry);
    void setEntry(String entry, Boolean value);
    Boolean isEntryUnlocked(String entry);
}
