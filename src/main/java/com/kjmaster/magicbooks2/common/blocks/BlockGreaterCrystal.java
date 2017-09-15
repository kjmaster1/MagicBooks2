package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.TileGreaterCrystal;
import com.kjmaster.magicbooks2.common.network.ModGuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class BlockGreaterCrystal extends BlockCrystal {

    public BlockGreaterCrystal(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileGreaterCrystal();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileGreaterCrystal();
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && !playerIn.isSneaking()) {
            playerIn.openGui(MagicBooks2.instance, ModGuiHandler.greaterCrystal, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
