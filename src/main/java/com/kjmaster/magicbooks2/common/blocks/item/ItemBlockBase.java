package com.kjmaster.magicbooks2.common.blocks.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block);
        if (!(block instanceof IMetaBlockName)) {
            throw new IllegalArgumentException(String.format("The given Block %s is not an instance of IMetaBlockName!", block.getUnlocalizedName()));
        }
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + ((IMetaBlockName) this.block).getSpecialName(stack);
    }

    public int getMetadata(int damage) {
        return damage;
    }
}
