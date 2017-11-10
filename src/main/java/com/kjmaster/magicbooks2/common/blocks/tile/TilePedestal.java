package com.kjmaster.magicbooks2.common.blocks.tile;

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

    @Override
    public void update() {
        if(!this.world.isRemote) {
            int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
            if (meta == 4) {
                List<PedestalRecipe> recipes = getValidRecipesForStack(stack);
                if(!recipes.isEmpty()) {
                    for (PedestalRecipe recipe: recipes) {
                        TileCrystal[] crystals = this.getCrystals();
                        int airMana = 0;
                        int earthMana = 0;
                        int waterMana = 0;
                        int fireMana = 0;
                        if (crystals[0] != null)
                            airMana = crystals[0].storage.getManaStored("Air");
                        if (crystals[1] != null)
                            earthMana = crystals[1].storage.getManaStored("Earth");
                        if (crystals[2] != null)
                            fireMana = crystals[2].storage.getManaStored("Fire");
                        if (crystals[3] != null)
                            waterMana = crystals[3].storage.getManaStored("Water");
                        TilePedestal[] pedestals = this.getPedestals();
                        ItemStack airStack = ItemStack.EMPTY;
                        ItemStack earthStack = ItemStack.EMPTY;
                        ItemStack fireStack = ItemStack.EMPTY;
                        ItemStack waterStack = ItemStack.EMPTY;
                        if (pedestals[0] != null)
                            airStack = pedestals[0].stack;
                        if (pedestals[1] != null)
                            earthStack = pedestals[1].stack;
                        if (pedestals[2] != null)
                            fireStack = pedestals[2].stack;
                        if (pedestals[3] != null)
                            waterStack = pedestals[3].stack;
                        if (airMana >= recipe.airManaCost && earthMana >= recipe.earthManaCost
                                && fireMana >= recipe.fireManaCost && waterMana >= recipe.waterManaCost
                                && ItemStack.areItemStacksEqual(airStack, recipe.airStack) && ItemStack.areItemStacksEqual(earthStack, recipe.earthStack)
                                && ItemStack.areItemStacksEqual(fireStack, recipe.fireStack) && ItemStack.areItemStacksEqual(waterStack, recipe.waterStack)) {
                            setStack(new ItemStack(recipe.output.getItem(), 1, recipe.output.getMetadata()));
                            pedestals[0].setStack(ItemStack.EMPTY);
                            pedestals[1].setStack(ItemStack.EMPTY);
                            pedestals[2].setStack(ItemStack.EMPTY);
                            pedestals[3].setStack(ItemStack.EMPTY);
                            crystals[0].storage.extractMana(recipe.airManaCost, false, "Air");
                            crystals[1].storage.extractMana(recipe.earthManaCost, false, "Earth");
                            crystals[2].storage.extractMana(recipe.fireManaCost, false, "Fire");
                            crystals[3].storage.extractMana(recipe.waterManaCost, false, "Water");
                        }
                    }
                    recipes.clear();
                }
            }
            this.markDirty();
        }
    }

    private TileCrystal[] getCrystals() {
        TileCrystal crystalAir = getCrystalAtPos(pos.add(3, 0, -3), "Air");
        TileCrystal crystalEarth = getCrystalAtPos(pos.add(3, 0, 3), "Earth");
        TileCrystal crystalFire = getCrystalAtPos(pos.add(-3, 0, 3), "Fire");
        TileCrystal crystalWater = getCrystalAtPos(pos.add(-3, 0, -3), "Water");
        TileCrystal[] crystals = {crystalAir, crystalEarth, crystalFire, crystalWater};
        return crystals;
    }

    private TilePedestal[] getPedestals() {
        TilePedestal pedestalAir = getPedestalAtPos(pos.add(3, 0, 0), "Air");
        TilePedestal pedestalEarth = getPedestalAtPos(pos.add(0, 0, 3), "Earth");
        TilePedestal pedestalFire = getPedestalAtPos(pos.add(-3, 0, 0), "Fire");
        TilePedestal pedestalWater = getPedestalAtPos(pos.add(0, 0, -3), "Water");
        TilePedestal[] pedestals = {pedestalAir, pedestalEarth, pedestalFire, pedestalWater};
        return pedestals;
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
    }
}
