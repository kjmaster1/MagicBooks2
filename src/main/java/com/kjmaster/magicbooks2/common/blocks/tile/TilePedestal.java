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
public class TilePedestal extends TileEntity {

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

    private TilePedestal[] getPedestals() {
        TilePedestal pedestalAir = getPedestalAtPos(pos.add(3, 0, 0), "Air");
        TilePedestal pedestalEarth = getPedestalAtPos(pos.add(0, 0, 3), "Earth");
        TilePedestal pedestalFire = getPedestalAtPos(pos.add(-3, 0, 0), "Fire");
        TilePedestal pedestalWater = getPedestalAtPos(pos.add(0, 0, -3), "Water");
        TilePedestal[] pedestals = {pedestalAir, pedestalEarth, pedestalFire, pedestalWater};
        return pedestals;
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
