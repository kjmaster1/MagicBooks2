package com.kjmaster.magicbooks2.common.blocks.tile.pipes;

import com.kjmaster.magicbooks2.common.blocks.pipe.BasePipeBlock;
import com.kjmaster.magicbooks2.common.blocks.pipe.EnumPipeType;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TilePipe extends TileEntity implements ITickable, IMana {

    public int mana = 0;
    private int transferRate = 2500;
    private ArrayList<IMana> acceptors = new ArrayList<>();
    private ArrayList<TilePipe> pipes = new ArrayList<>();

    @Override
    public void update() {
        if (world != null) {
            if (world.isRemote)
                return;
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos offPos = getPos().offset(facing);
                TileEntity tile = getWorld().getTileEntity(offPos);
                if (tile == null) {
                    continue;
                } else if (tile instanceof TilePipe) {
                    TilePipe tilePipe = (TilePipe) tile;
                    if (this.getPipeType().equals(tilePipe.getPipeType())
                            && mana > tilePipe.mana) {
                        pipes.add(tilePipe);
                    }
                } else {
                    switch (getPipeType()) {
                        case AIR:
                            IMana airManaCap = addAirToAcceptors(tile, facing);
                            if (airManaCap != null)
                                acceptors.add(airManaCap);
                            break;
                        case ARCANE:
                            IMana arcaneManaCap = addArcaneToAcceptors(tile, facing);
                            if (arcaneManaCap != null)
                                acceptors.add(arcaneManaCap);
                            break;
                        case EARTH:
                            IMana earthManaCap = addEarthToAcceptors(tile, facing);
                            if (earthManaCap != null)
                                acceptors.add(earthManaCap);
                            break;
                        case FIRE:
                            IMana fireManaCap = addFireToAcceptors(tile, facing);
                            if (fireManaCap != null)
                                acceptors.add(fireManaCap);
                            break;
                        case WATER:
                            IMana waterManaCap = addWaterToAcceptors(tile, facing);
                            if (waterManaCap != null)
                                acceptors.add(waterManaCap);
                            break;
                        default:
                            break;
                    }
                }
            }

            if (pipes.size() > 0) {
                int drain = Math.min(mana, transferRate);
                int manaShare = drain / pipes.size();
                int remainingMana = drain;

                if (manaShare > 0) {
                    for (TilePipe pipe : pipes) {
                        int move = pipe.receiveMana(Math.min(manaShare, remainingMana), false);
                        if (move > 0) {
                            remainingMana -= move;
                        }
                    }
                    extractMana(drain - remainingMana, false);
                }
                pipes.clear();
            }

            if (acceptors.size() > 0) {
                int drain = Math.min(mana, transferRate);
                int manaShare = drain / acceptors.size();
                int remainingMana = drain;

                if (manaShare > 0) {
                    for (IMana tile : acceptors) {
                        int move = tile.receiveMana(Math.min(manaShare, remainingMana), false);
                        if (move > 0) {
                            remainingMana -= move;
                        }
                    }
                    extractMana(drain - remainingMana, false);
                }
                acceptors.clear();
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (mana > 0) {
            NBTTagCompound data = new NBTTagCompound();
            data.setInteger("Mana", mana);
            compound.setTag("TilePipe", data);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("TilePipe")) {
            this.mana = compound.getCompoundTag("TilePipe").getInteger("Mana");
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbttag = new NBTTagCompound();
        this.writeToNBT(nbttag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbttag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    public EnumPipeType getPipeType() {
        return world.getBlockState(getPos()).getValue(BasePipeBlock.TYPE);
    }

    private IMana addAirToAcceptors(TileEntity tile, EnumFacing face) {
        if (tile.hasCapability(CapabilityAirMana.AIRMANA, face.getOpposite())) {
            IMana airManaCap = tile.getCapability(CapabilityAirMana.AIRMANA, face.getOpposite());
            if (airManaCap.canReceiveMana()) {
                return airManaCap;
            }
        }
        return null;
    }

    private IMana addArcaneToAcceptors(TileEntity tile, EnumFacing face) {
        if (tile.hasCapability(CapabilityArcaneMana.ARCANEMANA, face.getOpposite())) {
            IMana arcaneManaCap = tile.getCapability(CapabilityArcaneMana.ARCANEMANA, face.getOpposite());
            if (arcaneManaCap.canReceiveMana()) {
                return arcaneManaCap;
            }
        }
        return null;
    }

    private IMana addEarthToAcceptors(TileEntity tile, EnumFacing face) {
        if (tile.hasCapability(CapabilityEarthMana.EARTHMANA, face.getOpposite())) {
            IMana earthManaCap = tile.getCapability(CapabilityEarthMana.EARTHMANA, face.getOpposite());
            if (earthManaCap.canReceiveMana()) {
                return earthManaCap;
            }
        }
        return null;
    }

    private IMana addFireToAcceptors(TileEntity tile, EnumFacing face) {
        if (tile.hasCapability(CapabilityFireMana.FIREMANA, face.getOpposite())) {
            IMana fireManaCap = tile.getCapability(CapabilityFireMana.FIREMANA, face.getOpposite());
            if (fireManaCap.canReceiveMana()) {
                return fireManaCap;
            }
        }
        return null;
    }

    private IMana addWaterToAcceptors(TileEntity tile, EnumFacing face) {
        if (tile.hasCapability(CapabilityWaterMana.WATERMANA, face.getOpposite())) {
            IMana waterManaCap = tile.getCapability(CapabilityWaterMana.WATERMANA, face.getOpposite());
            if (waterManaCap.canReceiveMana()) {
                return waterManaCap;
            }
        }
        return null;
    }

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        if (!canReceiveMana()) {
            return 0;
        }

        int manaReceived = Math.min(getMaxManaStored() - getManaStored(), Math.min(this.transferRate, maxReceive));
        if (!simulate)
            mana += manaReceived;
        return manaReceived;
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        if (!canExtractMana()) {
            return 0;
        }

        int manaExtracted = Math.min(getManaStored(), Math.min(this.transferRate, maxExtract));
        if (!simulate)
            mana -= manaExtracted;
        return manaExtracted;
    }

    @Override
    public int getManaStored() {
        return this.mana;
    }

    @Override
    public int getMaxManaStored() {
        return this.transferRate * 6;
    }

    @Override
    public boolean canExtractMana() {
        return true;
    }

    @Override
    public boolean canReceiveMana() {
        return true;
    }

    @Override
    public void consumeMana(int consume) {
        this.mana -= consume;
        if (this.mana < 0)
            this.mana = 0;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
        if (this.mana > this.getMaxManaStored())
            this.mana = this.getMaxManaStored();
    }
}
