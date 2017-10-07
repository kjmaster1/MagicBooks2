package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.item.IMetaBlockName;
import com.kjmaster.magicbooks2.common.blocks.tile.TileCrystal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class BlockCrystal extends BlockElementBase implements IMetaBlockName, ITileEntityProvider {

    public BlockCrystal(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCrystal();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCrystal();
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
