package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.ISkillPoints;
import com.kjmaster.magicbooks2.common.capabilities.skillpoints.SkillPointsProvider;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.ServerPointsPacket;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

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
        if(tab == ModCreativeTabs.tabMagicBooks2) {
            for (int i = 0; i < 5; i++)
                items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemStack = playerIn.getHeldItem(handIn);
        int meta = itemStack.getMetadata();
        String element;
        switch (meta) {
            case 0:
                element = "Air";
                break;
            case 1:
                element = "Earth";
                break;
            case 2:
                element = "Fire";
                break;
            case 3:
                element = "Water";
                break;
            case 4:
                element = "Arcane";
                break;
            default:
                element = "Air";
        }
        if (playerIn.world.isRemote)
            PacketInstance.INSTANCE.sendToServer(new ServerPointsPacket(1, element, "Add"));

        ISkillPoints skillPointsCap = playerIn.getCapability(SkillPointsProvider.SKILL_POINTS_CAP, null);
        int points = skillPointsCap.getPoints(element);
        MagicBooks2.LOGGER.info(element + ": " + points);

        playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
