package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockShardOre extends BlockElementBase {

    public BlockShardOre(String name, Material mat, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, mat, tab, hardness, resistance, tool, harvest);
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
