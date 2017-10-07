package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.item.IMetaBlockName;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockElementBase extends BlockBase implements IMetaBlockName{

    public static final PropertyEnum<EnumHandler.ShardTypes> ELEMENT = PropertyEnum.create("element", EnumHandler.ShardTypes.class);

    public BlockElementBase(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ELEMENT, EnumHandler.ShardTypes.AIR));
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return EnumHandler.ShardTypes.values()[stack.getItemDamage()].getName();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {ELEMENT});

    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumHandler.ShardTypes type = state.getValue(ELEMENT);
        return type.getID();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ELEMENT, EnumHandler.ShardTypes.values()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (int i = 0; i < EnumHandler.ShardTypes.values().length; i++) {
            items.add(new ItemStack(Item.getItemFromBlock(this), 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
}
