package com.kjmaster.magicbooks2.common.blocks.tile.runes;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.TileCrystal;
import com.kjmaster.magicbooks2.common.capabilities.mana.crystals.CrystalManaStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileRune extends TileEntity implements ITickable {

    public final CrystalManaStorage storage = new CrystalManaStorage(10000, 10000, 10000);
    String element;
    private BlockPos connectedToPos;
    private Boolean hasConnection;
    private TileCrystal connectedCrystal;
    private int MANA_USE;

    public TileRune(){
        this.connectedToPos = new BlockPos(0,0, 0);
        this.hasConnection = false;
        this.element = "";
    }

    public void setElement(String element) { this.element = element; }

    public void setMANA_USE(int MANA_USE) { this.MANA_USE = MANA_USE; }

    @Override
    public void update() {
        if (this.world != null) {
            if (!this.world.isRemote) {
                if (!this.hasConnection) {
                    if(findClosestCrystal() != null) {
                        TileCrystal crystal = findClosestCrystal();
                        this.connectedToPos = crystal.getPos();
                        this.hasConnection = true;
                        this.connectedCrystal = crystal;
                    }
                } else {
                    if (this.connectedCrystal == null && !this.connectedToPos.equals(new BlockPos(0, 0, 0))) {
                        TileEntity possibleCrystal = world.getTileEntity(this.connectedToPos);
                        if (possibleCrystal instanceof TileCrystal)
                            this.connectedCrystal = (TileCrystal) possibleCrystal;
                    }
                    if (this.connectedCrystal != null) {
                        int crystalMana = this.connectedCrystal.storage.getManaStored(this.element);
                        if (crystalMana >= MANA_USE && this.getManaStored() <= this.storage.capacity - MANA_USE) {
                            this.connectedCrystal.storage.extractMana(MANA_USE, false, this.element);
                            this.receiveMana(MANA_USE);
                        }
                    }
                }
                this.markDirty();
            }
        }
    }

    private TileCrystal findClosestCrystal() {
        int posX = this.pos.getX();
        int posY = this.pos.getY();
        int posZ = this.pos.getZ();
        int range = 10;
        for (int x = -range; x < range + 1; x++) {
            for (int z = -range; z < range + 1; z++) {
                for (int y = -range; y < range + 1; y++) {
                    BlockPos posCrystal = new BlockPos(posX + x, posY + y, posZ + z);
                    if (getCrystalAtPos(posCrystal, this.element) != null) {
                        return getCrystalAtPos(posCrystal, this.element);
                    }
                }
            }
        }
        return null;
    }

    private TileCrystal getCrystalAtPos(BlockPos pos, String expectedElement) {
        TileEntity entity = world.getTileEntity(pos);
        int meta;
        TileCrystal crystal;
        if (entity instanceof TileCrystal) {
            crystal = (TileCrystal) entity;
            meta = crystal.getBlockMetadata();
        } else
            return null;
        switch (expectedElement) {
            case "Air":
                if (meta == 0)
                    return crystal;
            case "Earth":
                if (meta == 1)
                    return crystal;
            case "Fire":
                if (meta == 2)
                    return crystal;
            case "Water":
                if (meta == 3)
                    return crystal;
            case "Arcane":
                if (meta == 4)
                    return crystal;
            default:
                return null;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.storage.writeToNBT(compound);
        compound.setBoolean("Connection", this.hasConnection);
        compound.setInteger("PosX", this.connectedToPos.getX());
        compound.setInteger("PosY", this.connectedToPos.getY());
        compound.setInteger("PosZ", this.connectedToPos.getZ());
        compound.setString("Element", this.element);
        compound.setInteger("MANA_USE", this.MANA_USE);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.storage.readFromNBT(compound);
        this.hasConnection = compound.getBoolean("Connection");
        this.connectedToPos = new BlockPos(compound.getInteger("PosX"), compound.getInteger("PosY"), compound.getInteger("PosZ"));
        this.element = compound.getString("Element");
        this.MANA_USE = compound.getInteger("MANA_USE");
        super.readFromNBT(compound);
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

    public void receiveMana(int mana) {
        this.storage.receiveMana(mana, false, this.element);
    }

    public void extractMana(int mana) {
        this.storage.extractMana(mana, false, this.element);
    }

    public boolean getCanReceive() {
        return this.storage.canReceive(this.element);
    }

    public int getManaStored() {
        return this.storage.getManaStored(this.element);
    }

    public void  setManaStored(int mana) { this.storage.setMana(mana, this.element);}

    public int getMANA_USE() { return this.MANA_USE; }

    public String getElement() { return this.element; }

    public int getField(int id) {
        switch (id)
        {
            case 0:
                return this.storage.getManaStored(this.element);
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id)
        {
            case 0:
                this.storage.setMana(value, this.element);
        }
    }
}
