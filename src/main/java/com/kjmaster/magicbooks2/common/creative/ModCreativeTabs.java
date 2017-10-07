package com.kjmaster.magicbooks2.common.creative;

import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by pbill_000 on 11/09/2017.
 */
public class ModCreativeTabs {
    public static final CreativeTabs tabMagicBooks2 = new CreativeTabs("MagicBooks2") {
        @Override
        public ItemStack getTabIconItem() {
            ItemStack icon = new ItemStack(ModItems.Shard);
            icon.setItemDamage(4);
            return icon;
        }
    };

    public static final CreativeTabs tabMagicBooks2Spells = new CreativeTabs("MagicBooks2Spells") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.earthSpell);
        }
    };
}
