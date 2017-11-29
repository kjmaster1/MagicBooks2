package com.kjmaster.magicbooks2.common.blocks.tile.vase;

import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.WaterManaStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileWaterManaVase extends TileManaVase implements ITickable {

    public TileWaterManaVase() {
        this.storage = new WaterManaStorage(25000, 2500);
    }

    @Override
    public void update() {
       if (world.isRemote)
           return;
       sendManaToAdjacents();
    }

    private void sendManaToAdjacents() {
        int manaStored = this.storage.getManaStored();

        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te != null && te.hasCapability(CapabilityWaterMana.WATERMANA, facing.getOpposite())) {
                IMana waterManaCap = te.getCapability(CapabilityWaterMana.WATERMANA, facing.getOpposite());
                if (waterManaCap != null && waterManaCap.canReceiveMana()) {
                    int manaToGive = 2500 <= manaStored ? 2500 : manaStored;
                    int received = waterManaCap.receiveMana(manaToGive, false);
                    manaStored -= storage.extractMana(received, false);
                    if (manaStored <= 0)
                        break;
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing != null && capability == CapabilityWaterMana.WATERMANA)
            if (facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.EAST) || facing.equals(EnumFacing.WEST)
                    || facing.equals(EnumFacing.SOUTH) || facing.equals(EnumFacing.NORTH))
                return true;
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityWaterMana.WATERMANA && facing != null && facing.equals(EnumFacing.UP))
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
