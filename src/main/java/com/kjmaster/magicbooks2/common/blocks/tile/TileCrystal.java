package com.kjmaster.magicbooks2.common.blocks.tile;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.crystals.CrystalManaStorage;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class TileCrystal extends TileEntity implements ITickable, ICapabilityProvider{
    private int MANA_USE = 400;
    private boolean hasConnection;
    private BlockPos connectedToPos;
    public final CrystalManaStorage storage = new CrystalManaStorage(10000, 10000, MANA_USE);
    public TileCrystal() {
        this.hasConnection = false;
        this.connectedToPos = new BlockPos(0,0,0);
    }

    @Override
    public void update() {
        if (this.world != null) {
            if (!this.world.isRemote) {
                IBlockState blockState = world.getBlockState(pos);
                Block block = blockState.getBlock();
                int meta = block.getMetaFromState(blockState);
                switch (meta) {
                    case 0:
                        if(getHasConnection()) {
                            doConnection(world, pos, "Air");
                        }
                        break;
                    case 1:
                        if(getHasConnection()) {
                            doConnection(world, pos, "Earth");
                        }
                        break;
                    case 2:
                        if(getHasConnection()) {
                            doConnection(world, pos, "Fire");
                        }
                        break;
                    case 3:
                        if(getHasConnection()) {
                            doConnection(world, pos, "Water");
                        }
                        break;
                    case 4:
                        if(getHasConnection()) {
                            doConnection(world, pos, "Arcane");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void doConnection(World world, BlockPos pos, String element) {
        BlockPos posToSendMana = getConnectedToPos();
        TileEntity entity = world.getTileEntity(posToSendMana);
        if(entity instanceof TileCrystal) {
            TileCrystal tileCrystal = (TileCrystal) entity;
            if(tileCrystal.storage.canReceive(element) && this.storage.canExtract(element)
                  && !tileCrystal.storage.isFull(element) && this.storage.getManaStored(element) > MANA_USE) {
                tileCrystal.storage.receiveMana(MANA_USE, false, element);
                this.storage.extractMana(MANA_USE, false, element);
                MagicBooks2.LOGGER.info("Mana: " + this.storage.getManaStored(element));
                MagicBooks2.LOGGER.info("Other Crystal Mana" + tileCrystal.storage.getManaStored(element));
            }
        }
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        setHasConnection(compound.getBoolean("HasConnection"));
        setConnectedToPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("HasConnection", getHasConnection());
        compound.setInteger("X", getConnectedToPos().getX());
        compound.setInteger("Y", getConnectedToPos().getY());
        compound.setInteger("Z", getConnectedToPos().getZ());
        return super.writeToNBT(compound);
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

    public boolean getHasConnection() {
        return hasConnection;
    }

    public void setHasConnection(Boolean connection) {
        this.hasConnection = connection;
    }

    public void setConnectedToPos(BlockPos connectedToPos) {
        this.connectedToPos = connectedToPos;
    }

    public BlockPos getConnectedToPos() {
        return connectedToPos;
    }

    public void setConnectedToPos(int X, int Y, int Z) {
        BlockPos pos = new BlockPos(X, Y, Z);
        this.setConnectedToPos(pos);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return super.getCapability(capability, facing);
    }
}
