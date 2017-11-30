package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.tile.vase.*;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockManaVase extends BlockElementBase {

    private static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.9F, 1.0F, 0.9F);

    public BlockManaVase(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileManaVase) {
            TileManaVase manaVase = (TileManaVase) tileEntity;
            int mana = manaVase.storage.getManaStored();
            int meta = world.getBlockState(pos).getBlock().getMetaFromState(state);
            ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock(), 1, meta);
            stack.setTagInfo("Mana", new NBTTagInt(mana));
            drops.add(stack);
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
       return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool)
    {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasTagCompound()) {
            if (stack.getTagCompound().hasKey("Mana")) {
                int mana = stack.getTagCompound().getInteger("Mana");
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if (tileEntity instanceof TileManaVase) {
                    TileManaVase manaVase = (TileManaVase) tileEntity;
                    manaVase.storage.setMana(mana);
                }
            }
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        for(int i = 0; i < EnumHandler.ShardTypes.values().length; i++) {
            String type = EnumHandler.ShardTypes.values()[i].getName();
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i,
                    new ModelResourceLocation(getRegistryName() + "_" + type, "inventory"));
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BLOCK_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BLOCK_AABB;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        switch (state.getValue(BlockElementBase.ELEMENT)) {
            case AIR:
                return new TileAirManaVase();
            case ARCANE:
                return new TileArcaneManaVase();
            case EARTH:
                return new TileEarthManaVase();
            case FIRE:
                return new TileFireManaVase();
            case WATER:
                return new TileWaterManaVase();
        }
        return new TileManaVase();
    }
}
