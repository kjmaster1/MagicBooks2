package com.kjmaster.magicbooks2.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import java.util.Iterator;
import java.util.List;

public class InventoryUtils {
    public static boolean areItemAndTagEqual(final ItemStack stackA, ItemStack stackB) {
        return stackA.isItemEqual(stackB) && ItemStack.areItemStackTagsEqual(stackA, stackB);
    }

    public static boolean areMergeCandidates(ItemStack source, ItemStack target) {
        return areItemAndTagEqual(source, target) && target.getCount() < target.getMaxStackSize();
    }

    public static ItemStack copyAndChange(ItemStack stack, int newSize) {
        ItemStack copy = stack.copy();
        copy.setCount(newSize);
        return copy;
    }

    public static List<ItemStack> getInventoryContents(IInventory inventory) {
        List<ItemStack> result = Lists.newArrayList();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (slot != null) result.add(slot);
        }
        return result;
    }

    public static boolean tryMergeStacks(ItemStack stackToMerge, ItemStack stackInSlot) {
        if (stackInSlot == null || !stackInSlot.isItemEqual(stackToMerge) || !ItemStack.areItemStackTagsEqual(stackToMerge, stackInSlot)) return false;

        int newStackSize = stackInSlot.getCount() + stackToMerge.getCount();

        final int maxStackSize = stackToMerge.getMaxStackSize();
        if (newStackSize <= maxStackSize) {
            stackToMerge.setCount(0);
            stackInSlot.setCount(newStackSize);
            return true;
        } else if (stackInSlot.getCount() < maxStackSize) {
            stackToMerge.setCount(stackToMerge.getCount() - (maxStackSize - stackInSlot.getCount()));
            stackInSlot.setCount(maxStackSize);
            return true;
        }

        return false;
    }

    public static ItemStack returnItem(ItemStack stack) {
        return (stack == null || stack.getCount() <= 0)? null : stack.copy();
    }

    protected static void isItemValid(IInventory inventory, int slot, ItemStack stack) {
        Preconditions.checkArgument(inventory.isItemValidForSlot(slot, stack), "Slot %s cannot accept item", slot);
    }

    public static Iterable<ItemStack> asIterable(final IInventory inv) {
        return new Iterable<ItemStack>() {
            @Override
            public Iterator<ItemStack> iterator() {
                return new AbstractIterator<ItemStack>() {
                    final int size = inv.getSizeInventory();
                    int slot = 0;

                    @Override
                    protected ItemStack computeNext() {
                        if (slot >= size) return endOfData();
                        return inv.getStackInSlot(slot++);
                    }
                };
            }
        };
    }

    public static IItemHandler tryGetHandler(World world, BlockPos pos, EnumFacing side) {
        if (!world.isBlockLoaded(pos)) return null;
        final TileEntity te = world.getTileEntity(pos);

        return tryGetHandler(te, side);
    }

    public static IItemHandler tryGetHandler(TileEntity te, EnumFacing side) {
        if (te == null) return null;

        if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side))
            return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);

        if (te instanceof ISidedInventory)
            return new SidedInvWrapper((ISidedInventory)te, side);

        if (te instanceof IInventory)
            return new InvWrapper((IInventory)te);

        return null;
    }

    public static boolean canInsertStack(IItemHandler handler, ItemStack stack) {
        final ItemStack toInsert = ItemHandlerHelper.insertItemStacked(handler, stack, true);
        return toInsert == null || toInsert.getCount() < stack.getCount();
    }
}
