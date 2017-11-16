package com.kjmaster.magicbooks2.common.blocks.tile;

import com.kjmaster.magicbooks2.common.blocks.arcanecrafter.TileArcaneCrafter;
import com.kjmaster.magicbooks2.common.capabilities.mana.crystals.CrystalManaStorage;
import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.common.items.ItemShard;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pbill_000 on 13/09/2017.
 */
public class TileGreaterCrystal extends TileEntity implements ITickable {
    private final CrystalManaStorage storage = new CrystalManaStorage(10000, 10000, 10000);
    private int connections;
    private List<BlockPos> connectedToPos;
    private List<BlockPos> connectedToCrafters;
    private int crafterConnections;
    private int searchCooldown;
    private int itemCooldown;
    private int meta = -1;
    private String element = "";
    private ItemStackHandler handler;

    public TileGreaterCrystal() {
        this.connections = 0;
        this.connectedToPos = new ArrayList<>(5);
        this.crafterConnections = 0;
        this.connectedToCrafters = new ArrayList<>(2);
        handler = new ItemStackHandler(1);
    }

    @Override
    public void update() {
        if(this.world != null) {
            if(!this.world.isRemote) {
                this.searchCooldown++;
                this.itemCooldown++;
                this.searchCooldown %= 100;
                if (this.searchCooldown == 0) {
                    searchForCrystals(world, pos);
                    searchForCrafters(world, pos);
                }
                if (this.meta == -1)
                    this.meta = findMeta();
                if (this.storage.getManaStored(element) < this.storage.capacity) {
                    switch (meta) {
                        case 0:
                            if(handler.getStackInSlot(0).getItem().equals(ModItems.Shard)) {
                                ItemShard shard = (ItemShard) handler.getStackInSlot(0).getItem();
                                int meta = shard.getMetadata(handler.getStackInSlot(0));
                                if (meta == 0) {
                                    this.storage.receiveMana(25, false, element);
                                    this.itemCooldown %= 20;
                                    if(itemCooldown == 0)
                                        handler.getStackInSlot(0).shrink(1);
                                }
                                break;
                            }
                        case 1:
                            if(handler.getStackInSlot(0).getItem().equals(ModItems.Shard)) {
                                ItemShard shard = (ItemShard) handler.getStackInSlot(0).getItem();
                                int meta = shard.getMetadata(handler.getStackInSlot(0));
                                if (meta == 1) {
                                    this.storage.receiveMana(25, false, element);
                                    this.itemCooldown %= 20;
                                    if (itemCooldown == 0)
                                        handler.getStackInSlot(0).shrink(1);
                                }
                                break;
                            }
                        case 2:
                            if(handler.getStackInSlot(0).getItem().equals(ModItems.Shard)) {
                                ItemShard shard = (ItemShard) handler.getStackInSlot(0).getItem();
                                int meta = shard.getMetadata(handler.getStackInSlot(0));
                                if (meta == 2) {
                                    this.storage.receiveMana(25, false, element);
                                    this.itemCooldown %= 20;
                                    if (itemCooldown == 0)
                                        handler.getStackInSlot(0).shrink(1);
                                }
                                break;
                            }
                        case 3:
                            if(handler.getStackInSlot(0).getItem().equals(ModItems.Shard)) {
                                ItemShard shard = (ItemShard) handler.getStackInSlot(0).getItem();
                                int meta = shard.getMetadata(handler.getStackInSlot(0));
                                if (meta == 3) {
                                    this.storage.receiveMana(25, false, element);
                                    this.itemCooldown %= 20;
                                    if (itemCooldown == 0)
                                        handler.getStackInSlot(0).shrink(1);
                                }
                                break;
                            }
                        case 4:
                            if(handler.getStackInSlot(0).getItem().equals(ModItems.Shard)) {
                                ItemShard shard = (ItemShard) handler.getStackInSlot(0).getItem();
                                int meta = shard.getMetadata(handler.getStackInSlot(0));
                                if (meta == 4) {
                                    this.storage.receiveMana(25, false, element);
                                    this.itemCooldown %= 20;
                                    if (itemCooldown == 0)
                                        handler.getStackInSlot(0).shrink(1);
                                }
                                break;
                            }
                    }
                }
                if (this.connections > 0) {
                    for(int i = 0; i < this.connectedToPos.size(); i++) {
                        TileEntity entity = world.getTileEntity(this.connectedToPos.get(i));
                        if(entity instanceof TileCrystal) {
                            TileCrystal crystal = (TileCrystal) entity;
                            if (crystal.storage.canExtract(element) && this.storage.canExtract(element)
                                    && !crystal.storage.isFull(element)) {
                                int toExtract = crystal.storage.receiveMana(this.storage.getManaStored(element) / (this.connectedToPos.size() + this.connectedToCrafters.size()),
                                        false, element);
                                this.storage.extractMana(toExtract, false, element);
                            }
                        }
                    }
                }
                if (this.crafterConnections > 0) {
                    for(int i = 0; i < this.connectedToCrafters.size(); i++) {
                        TileEntity entity = world.getTileEntity(this.connectedToCrafters.get(i));
                        if (entity instanceof TileArcaneCrafter) {
                            TileArcaneCrafter crafter = (TileArcaneCrafter) entity;
                            doCrafterMana(element, crafter);
                        }
                    }
                }
                this.markDirty();
            }
        }
    }

    private void searchForCrystals(World world, BlockPos pos) {
        this.connectedToPos.clear();
        this.connections = 0;
        int thisX = pos.getX();
        int thisY = pos.getY();
        int thisZ = pos.getZ();
        int range = 10;
        for (int x = -range; x < range + 1; x++) {
            for (int z = -range; z < range + 1; z++) {
                for(int y = -range; y < range + 1; y++) {
                    BlockPos posOfCrystal = new BlockPos(thisX + x, thisY + y, thisZ + z);
                    TileEntity entity = world.getTileEntity(posOfCrystal);
                    int crystalMeta = world.getBlockState(posOfCrystal).getBlock().getMetaFromState(world.getBlockState(posOfCrystal));
                    if (entity instanceof TileCrystal && !this.connectedToPos.contains(posOfCrystal) && crystalMeta == meta) {
                        this.connectedToPos.add(posOfCrystal);
                        this.connections++;
                    }
                }
            }
        }
    }

    private void searchForCrafters(World world, BlockPos pos) {
        this.connectedToCrafters.clear();
        this.crafterConnections = 0;
        int thisX = pos.getX();
        int thisY = pos.getY();
        int thisZ = pos.getZ();
        int range = 10;
        for (int x = -range; x < range + 1; x++) {
            for (int z = -range; z < range + 1; z++) {
                for (int y = -range; y < range + 1; y++) {
                    BlockPos posOfTile = new BlockPos(thisX + x, thisY + y, thisZ + z);
                    TileEntity entity = world.getTileEntity(posOfTile);
                    if (entity instanceof TileArcaneCrafter && !this.connectedToCrafters.contains(posOfTile)) {
                        this.connectedToCrafters.add(posOfTile);
                        this.crafterConnections++;
                    }
                }
            }
        }
    }

    private void doCrafterMana(String element, TileArcaneCrafter crafter) {
        if (crafter.storage.canExtract(element) && this.storage.canExtract(element)
                && !crafter.storage.isFull(element)) {
            int toExtract = crafter.storage.receiveMana(this.storage.getManaStored(element) / (this.connectedToPos.size() + this.connectedToCrafters.size()),
                    false, element);
            this.storage.extractMana(toExtract, false, element);
        }

    }

    private int findMeta() {
        int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
        switch (meta) {
            case 0:
                this.element = "Air";
                break;
            case 1:
                this.element = "Earth";
                break;
            case 2:
                this.element = "Fire";
                break;
            case 3:
                this.element = "Water";
                break;
            case 4:
                this.element = "Arcane";
                break;
            default:
                break;
        }
        return meta;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.storage.readFromNBT(compound);
        setConnections(compound.getInteger("Connections"));
        setCrafterConnections(compound.getInteger("CrafterConnections"));
        searchCooldown = compound.getInteger("SearchCooldown");
        meta = compound.getInteger("Meta");
        element = compound.getString("Element");
        itemCooldown = compound.getInteger("ItemCooldown");
        NBTTagList tagList = compound.getTagList("PosList", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            int X = tagCompound.getInteger("PosX" + i);
            int Y = tagCompound.getInteger("PosY" + i);
            int Z = tagCompound.getInteger("PosZ" + i);
            addConnectedToPos(X, Y, Z);
        }
        NBTTagList tagList2 = compound.getTagList("CrafterPosList", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < tagList2.tagCount(); i++) {
            NBTTagCompound tagCompound2 = tagList2.getCompoundTagAt(i);
            int X = tagCompound2.getInteger("PosX" + i);
            int Y = tagCompound2.getInteger("PosY" + i);
            int Z = tagCompound2.getInteger("PosZ" + i);
            addCrafterConnection(X, Y, Z);

        }
        handler.deserializeNBT(compound.getCompoundTag("ItemStackHandler"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.storage.writeToNBT(compound);
        compound.setInteger("Connections", this.connections);
        compound.setInteger("CrafterConnections", this.crafterConnections);
        compound.setInteger("SearchCooldown", this.searchCooldown);
        compound.setInteger("Meta", this.meta);
        compound.setString("Element", this.element);
        compound.setInteger("ItemCooldown", this.itemCooldown);
        NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < connectedToPos.size(); i++) {
            BlockPos pos = connectedToPos.get(i);
            if(pos != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setInteger("PosX" + i, pos.getX());
                tagCompound.setInteger("PosY" + i, pos.getY());
                tagCompound.setInteger("PosZ" + i, pos.getZ());
                tagList.appendTag(tagCompound);
            }
        }
        NBTTagList tagList2 = new NBTTagList();
        for(int i = 0; i < connectedToCrafters.size(); i++) {
            BlockPos pos = connectedToCrafters.get(i);
            if(pos != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setInteger("PosX" + i, pos.getX());
                tagCompound.setInteger("PosY" + i, pos.getY());
                tagCompound.setInteger("PosZ" + i, pos.getZ());
                tagList2.appendTag(tagCompound);
            }
        }
        compound.setTag("ItemStackHandler", handler.serializeNBT());
        return super.writeToNBT(compound);
    }

    private void setConnections(int connections) {this.connections = connections;}

    private void addConnectedToPos(int X, int Y, int Z) {
        BlockPos pos = new BlockPos(X, Y, Z);
        this.connectedToPos.add(pos);
    }

    private void setCrafterConnections(int crafterConnections) {this.crafterConnections = crafterConnections;}

    private void addCrafterConnection(int X, int Y, int Z) {
        BlockPos pos = new BlockPos(X, Y, Z);
        this.connectedToCrafters.add(pos);
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

    public int getField(int id) {
        switch (id) {
            case 0:
                return this.storage.getManaStored("Air");
            case 1:
                return this.storage.getManaStored("Earth");
            case 2:
                return this.storage.getManaStored("Fire");
            case 3:
                return this.storage.getManaStored("Water");
            case 4:
                return this.storage.getManaStored("Arcane");
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.storage.setMana(value, "Air");
                break;
            case 1:
                this.storage.setMana(value, "Earth");
                break;
            case 2:
                this.storage.setMana(value, "Fire");
                break;
            case 3:
                this.storage.setMana(value, "Water");
                break;
            case 4:
                this.storage.setMana(value, "Arcane");
                break;

        }
    }

    public int getMana(String element) {
        return this.storage.getManaStored(element);
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
}
