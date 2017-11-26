package com.kjmaster.magicbooks2.common.blocks.item;

import com.kjmaster.magicbooks2.common.blocks.pipe.EnumPipeType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPipe extends ItemBlock {

    public ItemBlockPipe(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + EnumPipeType.values()[stack.getItemDamage()].getName();
    }
}
