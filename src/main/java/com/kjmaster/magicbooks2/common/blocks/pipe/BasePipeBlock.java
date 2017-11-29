package com.kjmaster.magicbooks2.common.blocks.pipe;

import com.kjmaster.magicbooks2.common.blocks.tile.pipes.*;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;
import java.security.InvalidParameterException;

public class BasePipeBlock extends Block {

    static final IProperty<Boolean> DOWN = PropertyBool.create("down");
    static final IProperty<Boolean> UP = PropertyBool.create("up");
    static final IProperty<Boolean> NORTH = PropertyBool.create("north");
    static final IProperty<Boolean> SOUTH = PropertyBool.create("south");
    static final IProperty<Boolean> WEST = PropertyBool.create("west");
    static final IProperty<Boolean> EAST = PropertyBool.create("east");
    public static final IProperty<EnumPipeType> TYPE = PropertyEnum.create("type", EnumPipeType.class);
    protected AxisAlignedBB boundbox;
    protected int lookingSide;

    public BasePipeBlock(String name) {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHardness(1F);
        this.setResistance(8F);
        this.setHarvestLevel("pickaxe", 2);
        this.setCreativeTab(ModCreativeTabs.tabMagicBooks2);
        setDefaultState(getDefaultState().withProperty(EAST, false).withProperty(WEST, false)
        .withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(UP, false)
        .withProperty(DOWN, false).withProperty(TYPE, EnumPipeType.AIR));
    }

    public static ItemStack getPipeByName(String name, int count) {
        for (int i = 0; i < EnumPipeType.values().length; i++) {
            if (EnumPipeType.values()[i].getName().equalsIgnoreCase(name)) {
                return new ItemStack(ModBlocks.pipe, count, i);
            }
        }
        throw new InvalidParameterException("The pipe " + name + " could not be found");
    }

    public static ItemStack getPipeByName(String name) {
        return getPipeByName(name, 1);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return state.getValue(TYPE).getStack();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, EAST, WEST, NORTH, SOUTH, UP, DOWN, TYPE);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getStateFromMeta(placer.getHeldItem(hand).getItemDamage());
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        if (itemIn.equals(ModCreativeTabs.tabMagicBooks2)) {
            for (EnumPipeType pipeType: EnumPipeType.values()) {
                items.add(new ItemStack(this, 1, pipeType.ordinal()));
            }
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, EnumPipeType.values()[meta]);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = state.getActualState(source, pos);
        float minX = state.getValue(WEST) ? 0.0F : 0.3125F;
        float minY = state.getValue(DOWN) ? 0.0F : 0.3125F;
        float minZ = state.getValue(NORTH) ? 0.0F : 0.3125F;
        float maxX = state.getValue(EAST) ? 1.0F : 0.6875F;
        float maxY = state.getValue(UP) ? 1.0F : 0.6875F;
        float maxZ = state.getValue(SOUTH) ? 1.0F : 0.6875F;
        return new AxisAlignedBB((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY,
                (double) maxZ);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState actualState = state;
        EnumPipeType type = state.getValue(TYPE);
        for (EnumFacing facing: EnumFacing.VALUES) {
            TileEntity tileEntity = getTileEntitySafely(worldIn, pos.offset(facing));
            if (tileEntity != null) {
                actualState = getActualState(actualState, worldIn, facing, type, tileEntity);
            }
        }
        return actualState;
    }

    private IBlockState getActualState(IBlockState actualState, IBlockAccess world, EnumFacing facing,
                                       EnumPipeType pipeType, TileEntity tileEntity) {
        if (pipeType != null) {
            switch (pipeType) {
                case AIR:
                    return actualState.withProperty(getProperty(facing),
                            tileEntity.hasCapability(CapabilityAirMana.AIRMANA, facing.getOpposite()));
                case ARCANE:
                    return actualState.withProperty(getProperty(facing),
                            tileEntity.hasCapability(CapabilityArcaneMana.ARCANEMANA, facing.getOpposite()));
                case EARTH:
                    return actualState.withProperty(getProperty(facing),
                            tileEntity.hasCapability(CapabilityEarthMana.EARTHMANA, facing.getOpposite()));
                case FIRE:
                    return actualState.withProperty(getProperty(facing),
                            tileEntity.hasCapability(CapabilityFireMana.FIREMANA, facing.getOpposite()));
                case WATER:
                    return actualState.withProperty(getProperty(facing),
                            tileEntity.hasCapability(CapabilityWaterMana.WATERMANA, facing.getOpposite()));
            }
        }
        return actualState;
    }

    public TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
        if (blockAccess instanceof ChunkCache) {
            return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
        } else {
            return blockAccess.getTileEntity(pos);
        }
    }

    public IProperty<Boolean> getProperty(EnumFacing facing) {
        switch (facing) {
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            case NORTH:
                return NORTH;
            case SOUTH:
                return SOUTH;
            case DOWN:
                return DOWN;
            case UP:
                return UP;
            default:
                return EAST;
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        switch (state.getValue(TYPE)) {
            case AIR:
                return new TileAirPipe();
            case ARCANE:
                return new TileArcanePipe();
            case EARTH:
                return new TileEarthPipe();
            case FIRE:
                return new TileFirePipe();
            case WATER:
                return new TileWaterPipe();
        }
        return null;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}

