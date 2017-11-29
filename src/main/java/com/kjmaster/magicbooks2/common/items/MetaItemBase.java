package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class MetaItemBase extends Item {

    public MetaItemBase(String unlocalizedName, CreativeTabs tab, int maxSize) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(MagicBooks2.MODID, unlocalizedName));
        this.setHasSubtypes(true);
        this.setCreativeTab(tab);
        this.setMaxStackSize(maxSize);
    }
}
