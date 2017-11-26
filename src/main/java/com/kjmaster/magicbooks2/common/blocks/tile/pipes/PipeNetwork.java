package com.kjmaster.magicbooks2.common.blocks.tile.pipes;

public class PipeNetwork {

    private int networkMana;
    private int networkCapacity;
    private int maxReceive;
    private int maxExtract;

    PipeNetwork(int networkCapacity, int maxReceive, int maxExtract) {
        this.networkCapacity = networkCapacity;
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
    }

    public int addManaToNetwork(int maxReceive, boolean simulate) {
        if (!canReceiveMana())
            return 0;

        int manaReceived = Math.min(networkCapacity - networkMana, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            networkMana += manaReceived;
        return manaReceived;
    }

    public int consumeManaFromNetwork(int maxExtract, boolean simulate) {
        if (!canExtractMana())
            return 0;

        int manaExtracted = Math.min(networkMana, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            networkMana -= manaExtracted;
        return manaExtracted;
    }

    public int getNetworkCapacity() {
        return networkCapacity;
    }

    public int getNetworkMana() {
        return networkMana;
    }

    public void setMana(int mana) {
        this.networkMana = mana;
    }

    public boolean canReceiveMana() {
        return true;
    }

    public boolean canExtractMana() {
        return true;
    }
}
