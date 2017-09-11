package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Created by pbill_000 on 11/09/2017.
 */
public class ItemBook extends MetaItemBase {

    public ItemBook(String unlocalizedName, CreativeTabs tab, int maxSize) {
        super(unlocalizedName, tab, maxSize);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for(int i = 0; i < EnumHandler.BookTypes.values().length; i++) {
            if(stack.getItemDamage() == i) {
                return this.getUnlocalizedName() + "_" +
                        EnumHandler.BookTypes.values()[i].getName();
            } else
                continue;
        }
        return this.getUnlocalizedName() + "_" + EnumHandler.BookTypes.AIR.getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(this.isInCreativeTab(ModCreativeTabs.tabMagicBooks2)) {
            for (int i = 0; i < 5; i++)
                items.add(new ItemStack(ModItems.Book, 1, i));
        }
    }
}
