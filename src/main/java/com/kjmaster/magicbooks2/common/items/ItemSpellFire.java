package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemSpellFire extends MetaItemBase {

    public ItemSpellFire(String unlocalizedName, CreativeTabs tab, int maxSize) {
        super(unlocalizedName, tab, maxSize);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for(int i = 0; i < EnumHandler.FireSpellTypes.values().length; i++) {
            if(stack.getItemDamage() == i) {
                return this.getUnlocalizedName() + "_" +
                        EnumHandler.FireSpellTypes.values()[i].getName();
            }
        }
        return this.getUnlocalizedName() + "_" + EnumHandler.FireSpellTypes.FIREBLAST.getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == ModCreativeTabs.tabMagicBooks2Spells)  {
            for (int i = 0; i < EnumHandler.FireSpellTypes.values().length; i++)
                items.add(new ItemStack(this, 1, i));
        }
    }
}
