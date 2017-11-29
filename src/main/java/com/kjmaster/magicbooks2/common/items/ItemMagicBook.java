package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.network.ModGuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMagicBook extends ItemBase {
    public ItemMagicBook(String unlocalizedName, CreativeTabs tab, int maxSize) {
        super(unlocalizedName, tab, maxSize);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(playerIn.world.isRemote && !playerIn.isSneaking()) {
            playerIn.openGui(MagicBooks2.instance, ModGuiHandler.magicBook, worldIn,
                    (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posX);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
