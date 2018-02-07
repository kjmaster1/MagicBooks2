package com.kjmaster.magicbooks2.common.blocks.runes;

import com.kjmaster.kjlib.common.blocks.BlockBase;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileDrowningRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune.TileWaterRune;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.WaterManaStorage;
import com.kjmaster.magicbooks2.common.network.ModGuiHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDrowningRune extends BlockBase implements ITileEntityProvider {

    public BlockDrowningRune(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity entity = worldIn.getTileEntity(pos);
        if (entity instanceof TileWaterRune) {
            MagicBooks2.LOGGER.info("I get called!");
            TileWaterRune rune = (TileWaterRune) entity;
            rune.setStorage(new WaterManaStorage(1000, 250, 0));
            MagicBooks2.LOGGER.info(rune.getStorage());
            rune.setManaUse(100);
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn instanceof EntityMob) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileRune) {
                TileRune rune = (TileRune) tile;
                if (rune.getStorage().getManaStored() >= 10) {
                    ((EntityMob) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 9000));
                    entityIn.attackEntityFrom(DamageSource.DROWN, 1.0F);
                    rune.getStorage().extractMana(10, false);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && !playerIn.isSneaking())
            playerIn.openGui(MagicBooks2.instance, ModGuiHandler.drowningRune, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileDrowningRune();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDrowningRune();
    }
}
