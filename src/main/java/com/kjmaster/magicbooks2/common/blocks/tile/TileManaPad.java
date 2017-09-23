package com.kjmaster.magicbooks2.common.blocks.tile;

import com.kjmaster.magicbooks2.common.capabilities.mana.crystals.CrystalManaStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class TileManaPad extends TileEntity implements ITickable {

    public final CrystalManaStorage storage = new CrystalManaStorage(10000, 10000, 10000);

    private int connections;
    private int searchCooldown;
    private List<BlockPos> connectedToPos;

    public TileManaPad() {
        this.connections = 0;
        this.connectedToPos = new ArrayList<>(5);
    }



    @Override
    public void update() {
        if (world != null) {
            if(!world.isRemote) {
                this.searchCooldown++;
                this.searchCooldown %= 100;
                if (this.searchCooldown == 0) {
                    searchForCrystals(world, pos);
                }
                if(this.connections > 0) {
                    for (BlockPos pos: this.connectedToPos) {
                        TileEntity entity = world.getTileEntity(pos);
                        if (entity instanceof TileCrystal) {
                            TileCrystal crystal = (TileCrystal) entity;
                            if(this.storage.getManaStored("Air") < this.storage.capacity) {
                                int airMana = crystal.storage.getManaStored("Air");
                                crystal.storage.extractMana(airMana, false, "Air");
                                this.storage.receiveMana(airMana, false, "Air");
                            }
                            if(this.storage.getManaStored("Arcane") < this.storage.capacity) {
                                int arcaneMana = crystal.storage.getManaStored("Arcane");
                                crystal.storage.extractMana(arcaneMana, false, "Arcane");
                                this.storage.receiveMana(arcaneMana, false, "Arcane");
                            }
                            if(this.storage.getManaStored("Earth") < this.storage.capacity) {
                                int earthMana = crystal.storage.getManaStored("Earth");
                                crystal.storage.extractMana(earthMana, false, "Earth");
                                this.storage.receiveMana(earthMana, false, "Earth");
                            }
                            if(this.storage.getManaStored("Fire") < this.storage.capacity) {
                                int fireMana = crystal.storage.getManaStored("Fire");
                                crystal.storage.extractMana(fireMana, false, "Fire");
                                this.storage.receiveMana(fireMana, false, "Fire");
                            }
                            if(this.storage.getManaStored("Water") < this.storage.capacity) {
                                int waterMana = crystal.storage.getManaStored("Water");
                                crystal.storage.extractMana(waterMana, false, "Water");
                                this.storage.receiveMana(waterMana, false, "Water");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.storage.readFromNBT(compound);
        setConnections(compound.getInteger("Connections"));
        NBTTagList tagList = compound.getTagList("PosList", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(1);
            int X = tagCompound.getInteger("PosX" + i);
            int Y = tagCompound.getInteger("PosY" + i);
            int Z = tagCompound.getInteger("PosZ" + i);
            addConnectedToPos(X, Y, Z);
        }
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.storage.writeToNBT(compound);
        compound.setInteger("Connections", this.connections);
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
        return super.writeToNBT(compound);
    }

    private void setConnections(int connections) {this.connections = connections;}

    private void addConnectedToPos(int X, int Y, int Z) {
        BlockPos pos = new BlockPos(X, Y, Z);
        this.connectedToPos.add(pos);
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
                    if (entity instanceof TileCrystal && !this.connectedToPos.contains(posOfCrystal)) {
                        this.connectedToPos.add(posOfCrystal);
                        this.connections++;
                    }
                }
            }
        }
    }
}
