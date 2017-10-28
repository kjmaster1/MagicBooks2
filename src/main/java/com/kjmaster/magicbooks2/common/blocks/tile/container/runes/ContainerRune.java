package com.kjmaster.magicbooks2.common.blocks.tile.container.runes;

import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerRune extends Container {

    private IInventory playerInv;
    private TileRune rune;
    private int mana;

    public ContainerRune(IInventory playerInv, TileRune rune) {
        this.rune = rune;
        this.playerInv = playerInv;

        // Player Inventory, Slot 9-35, Slot IDs 0-26
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs 27-35
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !playerIn.isSpectator();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for(int i = 0; i < this.listeners.size(); i++) {
            IContainerListener iContainerListener = this.listeners.get(i);

            if (this.mana != this.rune.getField(0))
                iContainerListener.sendWindowProperty(this, 0, this.rune.getField(0));
        }
        this.mana = this.rune.getManaStored();
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        this.rune.setField(id, data);
    }
}
