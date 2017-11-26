package com.kjmaster.magicbooks2.common.blocks.tile.vase;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.AirManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileAirManaVase extends TileManaVase implements ITickable {

    public TileAirManaVase() {
        this.storage = new AirManaStorage(25000, 2500);
    }

    @Override
    public void update() {
        if (world.isRemote)
            return;
        sendManaToAdjacents();
        MagicBooks2.LOGGER.info("Air Mana Stored: " + this.storage.getManaStored());
    }

    private void sendManaToAdjacents() {
        int manaStored = this.storage.getManaStored();

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te != null && te.hasCapability(CapabilityAirMana.AIRMANA, facing.getOpposite())) {
                IMana airManaCap = te.getCapability(CapabilityAirMana.AIRMANA, facing.getOpposite());
                if (airManaCap != null && airManaCap.canReceiveMana()) {
                    int manaToGive = 2500 <= manaStored ? 2500 : manaStored;
                    int received = airManaCap.receiveMana(manaToGive, false);
                    manaStored -= storage.extractMana(received, false);
                    if (manaStored <= 0)
                        break;
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing != null && capability == CapabilityAirMana.AIRMANA)
            if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)
                    || facing.equals(EnumFacing.SOUTH) || facing.equals(EnumFacing.NORTH))
                return true;
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityAirMana.AIRMANA && facing != null && facing.equals(EnumFacing.UP))
            return (T) this.storage;
        else if (facing != null) {
            if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.WEST) || facing.equals(EnumFacing.EAST)
                    || facing.equals(EnumFacing.SOUTH)) {
                return (T) new IMana() {
                    @Override
                    public int receiveMana(int maxReceive, boolean simulate) {
                        return 0;
                    }

                    @Override
                    public int extractMana(int maxExtract, boolean simulate) {
                        return 0;
                    }

                    @Override
                    public int getManaStored() {
                        return 0;
                    }

                    @Override
                    public int getMaxManaStored() {
                        return 0;
                    }

                    @Override
                    public boolean canExtractMana() {
                        return false;
                    }

                    @Override
                    public boolean canReceiveMana() {
                        return false;
                    }

                    @Override
                    public void consumeMana(int consume) {

                    }

                    @Override
                    public void setMana(int mana) {

                    }
                };
            }
        }
            return super.getCapability(capability, facing);
    }
}
