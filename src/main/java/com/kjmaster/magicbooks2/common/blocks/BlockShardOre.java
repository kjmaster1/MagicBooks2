package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.item.IMetaBlockName;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.init.ModItems;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockShardOre extends BlockBase implements IMetaBlockName {

    public static final PropertyEnum<EnumHandler.ShardTypes> ELEMENT = PropertyEnum.create("element", EnumHandler.ShardTypes.class);

    public BlockShardOre(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
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

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        int meta = this.getMetaFromState(state);
        ItemStack shardStack = new ItemStack(ModItems.Shard);
        switch (meta) {
            case 0:
                shardStack.setItemDamage(0);
                return shardStack.getItem();
            case 1:
                shardStack.setItemDamage(1);
                return shardStack.getItem();
            case 2:
                shardStack.setItemDamage(2);
                return shardStack.getItem();
            case 3:
                shardStack.setItemDamage(3);
                return shardStack.getItem();
            case 4:
                shardStack.setItemDamage(4);
                return shardStack.getItem();
            default:
                return shardStack.getItem();

        }
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(2);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        if(this.getItemDropped(state, RANDOM, fortune) != Item.getItemFromBlock(this)) {
            return 1 + RANDOM.nextInt(5);
        }
        return 0;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        int meta = this.getMetaFromState(state);
        ItemStack shardStack = new ItemStack(ModBlocks.shardOre);
        switch (meta) {
            case 0:
                shardStack.setItemDamage(0);
                return shardStack;
            case 1:
                shardStack.setItemDamage(1);
                return shardStack;
            case 2:
                shardStack.setItemDamage(2);
                return shardStack;
            case 3:
                shardStack.setItemDamage(3);
                return shardStack;
            case 4:
                shardStack.setItemDamage(4);
                return shardStack;
            default:
                return shardStack;
        }
    }
}