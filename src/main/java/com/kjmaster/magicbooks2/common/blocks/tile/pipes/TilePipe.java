package com.kjmaster.magicbooks2.common.blocks.tile.pipes;

import com.kjmaster.magicbooks2.MagicBooks2;
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
    public PipeNetwork network;
    private ArrayList<IMana> acceptors = new ArrayList<>();

    @Override
    public void update() {
        if (world != null) {
            if (world.isRemote)
                return;
            if (this.checkExistingNetwork() != null)
                this.network = checkExistingNetwork();
            if (this.checkExistingNetwork() == null && this.network != null && !hasSurroundingAcceptor()) {
                this.network = new PipeNetwork(25000, 2500, 2500);
            }
            if (this.network == null) {
                this.network = new PipeNetwork(25000, 2500, 2500);
                this.network.setMana(mana);
            }
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos offPos = getPos().offset(facing);
                TileEntity tile = getWorld().getTileEntity(offPos);
                if (tile == null) {
                    continue;
                } else if (!(tile instanceof TilePipe)) {
                    switch (getPipeType()) {
                        case AIR:
                            IMana airManaCap = addAirToAcceptors(tile, facing);
                            if (airManaCap != null)
                                this.acceptors.add(airManaCap);
                            break;
                        case ARCANE:
                            IMana arcaneManaCap = addArcaneToAcceptors(tile, facing);
                            if (arcaneManaCap != null)
                                this.acceptors.add(arcaneManaCap);
                            break;
                        case EARTH:
                            IMana earthManaCap = addEarthToAcceptors(tile, facing);
                            if (earthManaCap != null)
                                this.acceptors.add(earthManaCap);
                            break;
                        case FIRE:
                            IMana fireManaCap = addFireToAcceptors(tile, facing);
                            if (fireManaCap != null)
                                this.acceptors.add(fireManaCap);
                            break;
                        case WATER:
                            IMana waterManaCap = addWaterToAcceptors(tile, facing);
                            if (waterManaCap != null)
                                this.acceptors.add(waterManaCap);
                            break;
                        default:
                            break;
                    }
                }
            }

            if (this.acceptors.size() > 0) {
                int drain = Math.min(this.network.getNetworkMana(), 2500);
                int manaShare = drain / this.acceptors.size();
                int remainingMana = drain;

                if (manaShare > 0) {
                    for (IMana tile : this.acceptors) {
                        int move = tile.receiveMana(Math.min(manaShare, remainingMana), false);
                        if (move > 0) {
                            remainingMana -= move;
                        }
                    }
                    this.network.consumeManaFromNetwork(drain - remainingMana, false);
                }
            }
            this.mana = network.getNetworkMana();
            MagicBooks2.LOGGER.info("Network Mana: " + this.network.getNetworkMana());
        }
    }

    private boolean hasSurroundingAcceptor() {
        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offPos = getPos().offset(facing);
            TileEntity tile = getWorld().getTileEntity(offPos);
            if (tile != null) {
                switch (getPipeType()) {
                    case AIR:
                        if (tile.hasCapability(CapabilityAirMana.AIRMANA, facing.getOpposite()))
                            return true;
                    case ARCANE:
                        if (tile.hasCapability(CapabilityArcaneMana.ARCANEMANA, facing.getOpposite()))
                            return true;
                    case EARTH:
                        if (tile.hasCapability(CapabilityEarthMana.EARTHMANA, facing.getOpposite()))
                            return true;
                    case FIRE:
                        if (tile.hasCapability(CapabilityFireMana.FIREMANA, facing.getOpposite()))
                            return true;
                    case WATER:
                        if (tile.hasCapability(CapabilityWaterMana.WATERMANA, facing.getOpposite()))
                            return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    private PipeNetwork checkExistingNetwork() {
        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TilePipe) {
                TilePipe pipe = (TilePipe) te;
                if (pipe.getPipeType().equals(getPipeType())) {
                    if (pipe.getNetwork() != null) {
                        return pipe.getNetwork();
                    }
                }
            }
        }
        return null;
    }

    public PipeNetwork getNetwork() {
        return network;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (getManaStored() > 0) {
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

    private EnumPipeType getPipeType() {
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
        if (this.network != null)
            return this.network.addManaToNetwork(maxReceive, simulate);
        return 0;
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
         if (this.network != null)
             return this.network.consumeManaFromNetwork(maxExtract, simulate);
         return 0;
    }

    @Override
    public int getManaStored() {
        if (this.network != null) {
            return this.network.getNetworkMana();
        }
        return 0;
    }

    @Override
    public int getMaxManaStored() {
        if (this.network != null)
            return this.network.getNetworkCapacity();
        return 0;
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
        if (this.network != null)
            this.network.consumeManaFromNetwork(consume, false);
    }

    @Override
    public void setMana(int mana) {
        if (this.network != null)
            this.network.setMana(mana);
    }
}
