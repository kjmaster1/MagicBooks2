package com.kjmaster.magicbooks2.common.blocks.tile;

import com.kjmaster.magicbooks2.common.blocks.BlockElementBase;
import com.kjmaster.magicbooks2.common.blocks.tile.vase.*;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import com.kjmaster.magicbooks2.common.recipe.PedestalHandler;
import com.kjmaster.magicbooks2.common.recipe.PedestalRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/** Thank you McJty for the great tutorial which can be found here https://wiki.mcjty.eu/modding/index.php/Render_Block_TESR_/_OBJ-1.9 */
public class TilePedestal extends TileEntity implements ITickable {

    private ItemStack stack = ItemStack.EMPTY;
    private int processTime;

    @Override
    public void update() {
        if (world.isRemote)
            return;
        switch (this.world.getBlockState(getPos()).getValue(BlockElementBase.ELEMENT)) {
            case ARCANE:
                List<PedestalRecipe> recipes = getValidRecipesForStack(stack);
                if (!recipes.isEmpty()) {
                    ItemStack airStack = ItemStack.EMPTY;
                    ItemStack earthStack = ItemStack.EMPTY;
                    ItemStack fireStack = ItemStack.EMPTY;
                    ItemStack waterStack = ItemStack.EMPTY;
                    TilePedestal[] pedestals = getPedestals();
                    for (TilePedestal pedestal : pedestals) {
                        if (pedestal == null)
                            return;
                        else if (pedestal.getPedestalType().equals(EnumHandler.ShardTypes.AIR)) {
                            if (!pedestal.getStack().isEmpty())
                                airStack = pedestal.getStack();
                        } else if (pedestal.getPedestalType().equals(EnumHandler.ShardTypes.EARTH)) {
                            if (!pedestal.getStack().isEmpty())
                                earthStack = pedestal.getStack();
                        } else if (pedestal.getPedestalType().equals(EnumHandler.ShardTypes.FIRE)) {
                            if (!pedestal.getStack().isEmpty())
                                fireStack = pedestal.getStack();
                        } else if (pedestal.getPedestalType().equals(EnumHandler.ShardTypes.WATER)) {
                           if (!pedestal.getStack().isEmpty())
                               waterStack = pedestal.getStack();
                        }
                    }
                    TileManaVase airManaVase = getManaVaseAtPos(pos.add(3, 0, -3), "Air");
                    TileManaVase earthManaVase = getManaVaseAtPos(pos.add(3, 0, 3), "Earth");
                    TileManaVase fireManaVase = getManaVaseAtPos(pos.add(-3, 0, 3), "Fire");
                    TileManaVase waterManaVase = getManaVaseAtPos(pos.add(-3, 0, -3 ), "Water");
                    if (airManaVase != null && earthManaVase != null && fireManaVase != null && waterManaVase != null) {
                        int airMana = airManaVase.storage.getManaStored();
                        int earthMana = earthManaVase.storage.getManaStored();
                        int fireMana = fireManaVase.storage.getManaStored();
                        int waterMana = waterManaVase.storage.getManaStored();
                        for (PedestalRecipe recipe : recipes) {
                            if (recipe.airManaCost <= airMana
                                    && recipe.earthManaCost <= earthMana
                                    && recipe.fireManaCost <= fireMana
                                    && recipe.waterManaCost <= waterMana
                                    && ItemStack.areItemStacksEqual(airStack, recipe.airStack)
                                    && ItemStack.areItemStacksEqual(earthStack, recipe.earthStack)
                                    && ItemStack.areItemStacksEqual(fireStack, recipe.fireStack)
                                    && ItemStack.areItemStacksEqual(waterStack, recipe.waterStack)) {

                                this.processTime++;
                                boolean done = this.processTime >= recipe.time;

                                airManaVase.storage.extractMana(recipe.airManaCost / recipe.time, false);
                                earthManaVase.storage.extractMana(recipe.earthManaCost / recipe.time, false);
                                fireManaVase.storage.extractMana(recipe.fireManaCost / recipe.time, false);
                                waterManaVase.storage.extractMana(recipe.waterManaCost / recipe.time, false);
                                if (done) {
                                    for (TilePedestal pedestal : pedestals) {
                                        pedestal.setStack(ItemStack.EMPTY);
                                    }
                                    setStack(recipe.output.copy());
                                    this.markDirty();
                                    this.processTime = 0;
                                }
                                return;
                            }
                        }
                        recipes.clear();
                    }
                }
            default:
                break;
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

    public static List<PedestalRecipe> getValidRecipesForStack(ItemStack stack) {
        List<PedestalRecipe> validRecipes = new ArrayList<>();
        if (!stack.isEmpty()) {
            for (PedestalRecipe recipe: PedestalHandler.PEDESTAL_RECIPES) {
                if(!recipe.input.isEmpty() && recipe.input.isItemEqual(stack)) {
                    validRecipes.add(recipe);
                }
            }
        }
        return validRecipes;
    }

    private TilePedestal[] getPedestals() {
        TilePedestal pedestalAir = getPedestalAtPos(pos.add(3, 0, 0), "Air");
        TilePedestal pedestalEarth = getPedestalAtPos(pos.add(0, 0, 3), "Earth");
        TilePedestal pedestalFire = getPedestalAtPos(pos.add(-3, 0, 0), "Fire");
        TilePedestal pedestalWater = getPedestalAtPos(pos.add(0, 0, -3), "Water");
        TilePedestal[] pedestals = {pedestalAir, pedestalEarth, pedestalFire, pedestalWater};
        return pedestals;
    }

    private TileManaVase getManaVaseAtPos(BlockPos pos, String expectedElement) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof  TileManaVase) {
            TileManaVase manaVase = (TileManaVase) entity;
            EnumHandler.ShardTypes type = world.getBlockState(pos).getValue(BlockElementBase.ELEMENT);
            switch (expectedElement) {
                case "Air":
                    if (type.equals(EnumHandler.ShardTypes.AIR))
                        return manaVase;
                case "Earth":
                    if (type.equals(EnumHandler.ShardTypes.EARTH))
                        return manaVase;
                case "Fire":
                    if (type.equals(EnumHandler.ShardTypes.FIRE))
                        return manaVase;
                case "Water":
                    if (type.equals(EnumHandler.ShardTypes.WATER))
                        return manaVase;
                default:
                    return null;
            }
        }
        return null;
    }

    private TilePedestal getPedestalAtPos(BlockPos pos, String expectedElement) {
        TileEntity entity = world.getTileEntity(pos);
        int meta;
        TilePedestal pedestal;
        if (entity instanceof TilePedestal) {
            pedestal = (TilePedestal) entity;
            meta = pedestal.getBlockMetadata();
        } else
            return null;
        switch (expectedElement) {
            case "Air":
                if (meta == 0)
                    return pedestal;
            case "Earth":
                if (meta == 1)
                    return pedestal;
            case "Fire":
                if (meta == 2)
                    return pedestal;
            case "Water":
                if (meta == 3)
                    return pedestal;
            case "Arcane":
                if (meta == 4)
                    return pedestal;
            default:
                return null;
        }
    }

    private EnumHandler.ShardTypes getPedestalType () {
        return this.world.getBlockState(getPos()).getValue(BlockElementBase.ELEMENT);
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
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (!stack.isEmpty()) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);
            compound.setTag("item", tagCompound);
        }
        compound.setInteger("ProcessTime", processTime);
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
        processTime = compound.getInteger("ProcessTime");
    }
}
