package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.kjlib.common.items.MetaItemBase;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

public class ItemShard extends MetaItemBase {

    public static final Item.ToolMaterial shardMaterial = EnumHelper.addToolMaterial(MagicBooks2.MODID + ":shard",
            3, 1600, 7.0F, 2.0F, 22);

    public ItemShard(String unlocalizedName, CreativeTabs tab, int maxSize) {
        super(unlocalizedName, tab, maxSize);
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
