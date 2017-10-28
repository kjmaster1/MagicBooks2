package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.tile.TileEarthWall;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockEarthWall extends BlockBase implements ITileEntityProvider {

    public BlockEarthWall(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
        this.setTickRandomly(true);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEarthWall();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEarthWall();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
