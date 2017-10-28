package com.kjmaster.magicbooks2.common.blocks.tile.container.runes;

import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileLumberRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLumberRune extends ContainerRune {

    private TileLumberRune lumberRune;
    private IItemHandler handler;
    private int cooldown;

    public ContainerLumberRune(IInventory playerInv, TileRune rune) {
        super(playerInv, rune);
        this.lumberRune = (TileLumberRune) rune;
        this.handler = lumberRune.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.addSlotToContainer(new SlotItemHandler(handler, 0, 36, 35));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = ItemStack.EMPTY;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (fromSlot < this.handler.getSlots()) {
                if (!this.mergeItemStack(current, handler.getSlots(), handler.getSlots() + 36, true))
                    return ItemStack.EMPTY;
            } else {
                if (!this.mergeItemStack(current, 0, handler.getSlots(), false))
                    return ItemStack.EMPTY;
            }

            if (current.getCount() == 0) //Use func_190916_E() instead of stackSize 1.11 only 1.11.2 use getCount()
                slot.putStack(ItemStack.EMPTY); //Use ItemStack.field_190927_a instead of (ItemStack)null for a blank item stack. In 1.11.2 use ItemStack.EMPTY
            else
                slot.onSlotChanged();

            if (current.getCount() == previous.getCount())
                return null;
            slot.onTake(playerIn, current);
        }
        return previous;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(int i = 0; i < this.listeners.size(); i++) {
            IContainerListener iContainerListener = this.listeners.get(i);

            if (this.cooldown != this.lumberRune.getField(1))
                iContainerListener.sendWindowProperty(this, 1, this.lumberRune.getField(1));
        }
        this.cooldown = this.lumberRune.getCooldown();
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        this.lumberRune.setField(id, data);
    }
}
