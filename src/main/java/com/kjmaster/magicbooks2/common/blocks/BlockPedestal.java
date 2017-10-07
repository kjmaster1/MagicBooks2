package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.tile.TilePedestal;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPedestal extends BlockElementBase implements ITileEntityProvider {

    public BlockPedestal(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePedestal();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePedestal();
    }

    private TilePedestal getTE(World world, BlockPos pos) {
        return (TilePedestal) world.getTileEntity(pos);
    }

    //Thank you McJty for the great tutorial which can be found here https://wiki.mcjty.eu/modding/index.php/Render_Block_TESR_/_OBJ-1.9
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TilePedestal te = getTE(world, pos);
            if (te.getStack().isEmpty()) {
                if (!player.getHeldItem(hand).isEmpty()) {
                    // There is no item in the pedestal and the player is holding an item. We move that item
                    // to the pedestal
                    te.setStack(new ItemStack(player.getHeldItem(hand).getItem(), 1, player.getHeldItem(hand).getMetadata()));
                    player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
                    // Make sure the client knows about the changes in the player inventory
                    player.openContainer.detectAndSendChanges();
                }
            } else {
                // There is a stack in the pedestal. In this case we remove it and try to put it in the
                // players inventory if there is room

                ItemStack stack = te.getStack();
                te.setStack(ItemStack.EMPTY);
                if (!player.inventory.addItemStackToInventory(stack)) {
                    // Not possible. Throw item in the world
                    EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY()+1, pos.getZ(), stack);
                    world.spawnEntity(entityItem);
                } else {
                    player.openContainer.detectAndSendChanges();
                }
            }
        }

        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }
}

