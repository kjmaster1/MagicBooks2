package com.kjmaster.magicbooks2.common.blocks.arcanecrafter;

import com.kjmaster.magicbooks2.common.init.ModBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileArcaneCrafter extends TileEntity implements ITickable {

    private ItemStackHandler handler;
    private ArcaneRecipe crystal;
    private Item[] items = {Items.REDSTONE, Items.REDSTONE, Items.REDSTONE,
                            Items.REDSTONE, Items.REDSTONE, Items.REDSTONE,
                            Items.REDSTONE, Items.REDSTONE, Items.REDSTONE};

    public TileArcaneCrafter() {
        this.handler = new ItemStackHandler(10);
        this.crystal = new ArcaneRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.crystal)),
                items, 0);
    }

    @Override
    public void update() {
        if(crystal.hasRecipe(handler)) {
            if (!handler.getStackInSlot(9).isEmpty())
                 if(handler.getStackInSlot(9).getItem().equals(Item.getItemFromBlock(ModBlocks.crystal)))
                     handler.getStackInSlot(9).setCount(handler.getStackInSlot(9).getCount() + 1);
            else
                handler.setStackInSlot(9, new ItemStack(Item.getItemFromBlock(ModBlocks.crystal)));
            for (int i = 0; i <= 8; i++)
                handler.getStackInSlot(i).shrink(1);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        handler.deserializeNBT(compound.getCompoundTag("ItemStackHandler"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("ItemStackHandler", handler.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) handler;
        return super.getCapability(capability, facing);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }
}
