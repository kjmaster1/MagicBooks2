package com.kjmaster.magicbooks2.common.blocks.tile.container;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.TileGreaterCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGreaterCrystal extends Container {
    private IInventory playerInv;
    private TileGreaterCrystal te;
    private IItemHandler handler;
    private int airMana;
    private int arcaneMana;
    private int earthMana;
    private int fireMana;
    private int waterMana;

    public ContainerGreaterCrystal(IInventory playerInv, TileGreaterCrystal te) {
        this.te = te;
        this.playerInv = playerInv;
        this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        this.addSlotToContainer(new SlotItemHandler(handler, 0, 80, 35));
        // Player Inventory, Slot 9-35, Slot IDs 1-27
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
            IContainerListener icontainerlistener = (IContainerListener) this.listeners.get(i);

            if(this.airMana != this.te.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0, this.te.getField(0));
            }
            if(this.earthMana != this.te.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1, this.te.getField(1));
            }
            if(this.fireMana != this.te.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2, this.te.getField(2));
            }
            if(this.waterMana != this.te.getField(3)) {
                icontainerlistener.sendWindowProperty(this, 3, this.te.getField(3));
            }
            if(this.arcaneMana != this.te.getField(4)) {
                icontainerlistener.sendWindowProperty(this, 4, this.te.getField(4));
            }
        }

        this.airMana = this.te.getMana("Air");
        this.arcaneMana = this.te.getMana("Arcane");
        this.earthMana = this.te.getMana("Earth");
        this.fireMana = this.te.getMana("Fire");
        this.waterMana = this.te.getMana("Water");
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        this.te.setField(id, data);
    }
}
