package com.kjmaster.magicbooks2.common.blocks.runes;

import com.kjmaster.kjlib.common.blocks.BlockBase;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileLumberRune;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.EarthManaStorage;
import com.kjmaster.magicbooks2.common.network.ModGuiHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockLumberRune extends BlockBase implements ITileEntityProvider {

    public BlockLumberRune(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity entity = worldIn.getTileEntity(pos);
        if (entity instanceof TileLumberRune) {
            TileLumberRune rune = (TileLumberRune) entity;
            rune.setStorage(new EarthManaStorage(1000, 250, 0));
            rune.setManaUse(100);
        }
    }



    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileLumberRune();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileLumberRune();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && !playerIn.isSneaking())
            playerIn.openGui(MagicBooks2.instance, ModGuiHandler.lumberRune, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
