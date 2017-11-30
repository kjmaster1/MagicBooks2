package com.kjmaster.magicbooks2.common.blocks.tile.extractor;

import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.AirManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.ArcaneManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.EarthManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.FireManaStorage;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.WaterManaStorage;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class TileManaExtractor extends TileEntity implements ITickable {

    private final Capability[] capabilities = {CapabilityAirMana.AIRMANA, CapabilityArcaneMana.ARCANEMANA,
    CapabilityEarthMana.EARTHMANA, CapabilityFireMana.FIREMANA, CapabilityWaterMana.WATERMANA};
    private final List<Capability> capabilityList = Arrays.asList(capabilities);

    public AirManaStorage airManaStorage;
    public ArcaneManaStorage arcaneManaStorage;
    public EarthManaStorage earthManaStorage;
    public FireManaStorage fireManaStorage;
    public WaterManaStorage waterManaStorage;
    int cooldown;
    int ticks;

    private ItemStack stack = ItemStack.EMPTY;

    public TileManaExtractor() {
        this.airManaStorage = new AirManaStorage(10000, 0, 1000);
        this.arcaneManaStorage = new ArcaneManaStorage(10000, 0, 1000);
        this.earthManaStorage = new EarthManaStorage(10000, 0, 1000);
        this.fireManaStorage = new FireManaStorage(10000, 0, 1000);
        this.waterManaStorage = new WaterManaStorage(10000, 0, 1000);
        this.cooldown = 100;
        this.ticks = 1;
    }

    @Override
    public void update() {
        if (!getStack().isEmpty()) {
            if (getStack().getItem().equals(ModItems.Shard)) {
                if (ticks % cooldown == 0) {
                    setStack(ItemStack.EMPTY);
                    ticks = 0;
                }
                boolean success;
                if (!world.isRemote) {
                    switch (stack.getItemDamage()) {
                        case 0:
                            if (this.airManaStorage.getManaStored() <= this.airManaStorage.getMaxManaStored() - 50) {
                                this.airManaStorage.addMana(50);
                                success = true;
                            } else {
                                success = false;
                            }
                            break;
                        case 1:
                            if (this.earthManaStorage.getManaStored() <= this.earthManaStorage.getMaxManaStored() - 50) {
                                this.earthManaStorage.addMana(50);
                                success = true;
                            } else {
                                success = false;
                            }
                            break;
                        case 2:
                            if (this.fireManaStorage.getManaStored() <= this.fireManaStorage.getMaxManaStored() - 50) {
                                this.fireManaStorage.addMana(50);
                                success = true;
                            } else {
                                success = false;
                            }
                            break;
                        case 3:
                           if (this.waterManaStorage.getManaStored() <= this.waterManaStorage.getMaxManaStored() - 50) {
                               this.waterManaStorage.addMana(50);
                               success = true;
                           } else {
                               success = false;
                           }
                           break;
                        case 4:
                            if (this.arcaneManaStorage.getManaStored() <= this.arcaneManaStorage.getMaxManaStored() - 50) {
                                this.arcaneManaStorage.addMana(50);
                                success = true;
                            } else {
                                success = false;
                            }
                            break;
                        default:
                            success = false;
                            break;
                    }
                    sendAirMana();
                    sendArcaneMana();
                    sendEarthMana();
                    sendFireMana();
                    sendWaterMana();
                    if (success)
                        ticks++;
                }
            }
        }
    }

    private void sendAirMana() {
        int manaStored = this.airManaStorage.getManaStored();

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te != null && te.hasCapability(CapabilityAirMana.AIRMANA, facing.getOpposite())) {
                IMana airManaCap = te.getCapability(CapabilityAirMana.AIRMANA, facing.getOpposite());
                if (airManaCap != null && airManaCap.canReceiveMana()) {
                    int manaToGive = 2500 <= manaStored ? 2500 : manaStored;
                    int received = airManaCap.receiveMana(manaToGive, false);
                    manaStored -= this.airManaStorage.extractMana(received, false);
                    if (manaStored <= 0)
                        break;
                }
            }
        }
    }

    private void sendArcaneMana() {
        int manaStored = this.arcaneManaStorage.getManaStored();

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te != null && te.hasCapability(CapabilityArcaneMana.ARCANEMANA, facing.getOpposite())) {
                IMana arcaneManaCap = te.getCapability(CapabilityArcaneMana.ARCANEMANA, facing.getOpposite());
                if (arcaneManaCap != null && arcaneManaCap.canReceiveMana()) {
                    int manaToGive = 2500 <= manaStored ? 2500 : manaStored;
                    int received = arcaneManaCap.receiveMana(manaToGive, false);
                    manaStored -= this.arcaneManaStorage.extractMana(received, false);
                    if (manaStored <= 0)
                        break;
                }
            }
        }
    }

    private void sendEarthMana() {
        int manaStored = this.earthManaStorage.getManaStored();

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te != null && te.hasCapability(CapabilityEarthMana.EARTHMANA, facing.getOpposite())) {
                IMana earthManaCap = te.getCapability(CapabilityEarthMana.EARTHMANA, facing.getOpposite());
                if (earthManaCap != null && earthManaCap.canReceiveMana()) {
                    int manaToGive = 2500 <= manaStored ? 2500 : manaStored;
                    int received = earthManaCap.receiveMana(manaToGive, false);
                    manaStored -= this.earthManaStorage.extractMana(received, false);
                    if (manaStored <= 0)
                        break;
                }
            }
        }
    }

    private void sendFireMana() {
        int manaStored = this.fireManaStorage.getManaStored();

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te != null && te.hasCapability(CapabilityFireMana.FIREMANA, facing.getOpposite())) {
                IMana fireManaCap = te.getCapability(CapabilityFireMana.FIREMANA, facing.getOpposite());
                if (fireManaCap != null && fireManaCap.canReceiveMana()) {
                    int manaToGive = 2500 <= manaStored ? 2500 : manaStored;
                    int received = fireManaCap.receiveMana(manaToGive, false);
                    manaStored -= this.fireManaStorage.extractMana(received, false);
                    if (manaStored <= 0)
                        break;
                }
            }
        }
    }

    private void sendWaterMana() {
        int manaStored = this.waterManaStorage.getManaStored();

        for (EnumFacing facing : EnumFacing.VALUES) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te != null && te.hasCapability(CapabilityWaterMana.WATERMANA, facing.getOpposite())) {
                IMana waterManaCap = te.getCapability(CapabilityWaterMana.WATERMANA, facing.getOpposite());
                if (waterManaCap != null && waterManaCap.canReceiveMana()) {
                    int manaToGive = 2500 <= manaStored ? 2500 : manaStored;
                    int received = waterManaCap.receiveMana(manaToGive, false);
                    manaStored -= this.waterManaStorage.extractMana(received, false);
                    if (manaStored <= 0)
                        break;
                }
            }
        }
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        markDirty();
        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (!stack.isEmpty()) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);
            compound.setTag("item", tagCompound);
        }
        this.airManaStorage.writeToNBT(compound);
        this.arcaneManaStorage.writeToNBT(compound);
        this.earthManaStorage.writeToNBT(compound);
        this.fireManaStorage.writeToNBT(compound);
        this.waterManaStorage.writeToNBT(compound);
        compound.setInteger("Ticks", ticks);
        compound.setInteger("Cooldown", cooldown);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("item")) {
            stack = new ItemStack(compound.getCompoundTag("item"));
        } else {
            stack = ItemStack.EMPTY;
        }
        this.ticks = compound.getInteger("Ticks");
        this.cooldown = compound.getInteger("Cooldown");
        this.airManaStorage.readFromNBT(compound);
        this.arcaneManaStorage.readFromNBT(compound);
        this.earthManaStorage.readFromNBT(compound);
        this.fireManaStorage.readFromNBT(compound);
        this.waterManaStorage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new SPacketUpdateTileEntity(getPos(), 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capabilityList.contains(capability) || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityAirMana.AIRMANA)
            return (T) airManaStorage;
        else if (capability == CapabilityArcaneMana.ARCANEMANA)
            return (T) arcaneManaStorage;
        else if (capability == CapabilityEarthMana.EARTHMANA)
            return (T) earthManaStorage;
        else if (capability == CapabilityFireMana.FIREMANA)
            return (T) fireManaStorage;
        else if (capability == CapabilityWaterMana.WATERMANA)
            return (T) waterManaStorage;
        else
            return super.getCapability(capability, facing);
    }
}
