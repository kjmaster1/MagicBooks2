package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ItemShardPickaxe extends ItemPickaxe {

    public ItemShardPickaxe(ToolMaterial material, String name, CreativeTabs tab, int maxSize) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(MagicBooks2.MODID, name));
        this.setCreativeTab(tab);
        this.setMaxStackSize(maxSize);
        this.setHasSubtypes(true);
        this.isFull3D();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for(int i = 0; i < EnumHandler.ShardTypes.values().length; i++) {
            if(stack.getItemDamage() == i) {
                return this.getUnlocalizedName() + "_" +
                        EnumHandler.ShardTypes.values()[i].getName();
            }
        }
        return this.getUnlocalizedName() + "_" + EnumHandler.ShardTypes.AIR.getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == ModCreativeTabs.tabMagicBooks2)  {
            for (int i = 0; i < 5; i++)
                items.add(new ItemStack(this, 1, i));
        }
    }
}
