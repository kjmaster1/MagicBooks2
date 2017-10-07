package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemWand extends MetaItemBase {

    public ItemWand(String unlocalizedName, CreativeTabs tab, int maxSize) {
        super(unlocalizedName, tab, maxSize);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for(int i = 0; i < EnumHandler.WandTypes.values().length; i++) {
            if(stack.getItemDamage() == i) {
                return this.getUnlocalizedName() + "_" +
                        EnumHandler.WandTypes.values()[i].getName();
            }
        }
        return this.getUnlocalizedName() + "_" + EnumHandler.WandTypes.AIR.getName();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == ModCreativeTabs.tabMagicBooks2)  {
            for (int i = 0; i < EnumHandler.WandTypes.values().length; i++)
                items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        int wandMeta = playerIn.getHeldItem(handIn).getMetadata();
        ISpells spellsCap = playerIn.getCapability(SpellsProvider.SPELLS_CAP, null);
        InventoryPlayer inventory = playerIn.inventory;
        int leftIndex;
        if (inventory.currentItem == 0) {
            leftIndex = 8;
        }
        else if (inventory.currentItem == 8) {
            leftIndex = inventory.currentItem - 1;
        }
        else {
            leftIndex = inventory.currentItem - 1;
        }
        ItemStack stackOnLeft = playerIn.inventory.getStackInSlot(leftIndex);
        Item itemOnLeft = stackOnLeft.getItem();
        if (stackOnLeft.equals(ItemStack.EMPTY))
            return super.onItemRightClick(worldIn, playerIn, handIn);
        switch (wandMeta) {
            case 0:
                if (itemOnLeft == ModItems.airSpell) {
                    airSpell(playerIn, worldIn, stackOnLeft, spellsCap);
                    return super.onItemRightClick(worldIn, playerIn, handIn);
                }
                break;
            case 1:
                if (itemOnLeft == ModItems.earthSpell) {
                    earthSpell(playerIn, worldIn, stackOnLeft, spellsCap);
                    return super.onItemRightClick(worldIn, playerIn, handIn);
                }
                break;
            case 2:
                if (itemOnLeft == ModItems.fireSpell) {
                    fireSpell(playerIn, worldIn, stackOnLeft, spellsCap);
                    return super.onItemRightClick(worldIn, playerIn, handIn);
                }
                break;
            default:
                break;
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private void earthSpell(EntityPlayer player, World world, ItemStack stack, ISpells spellsCap) {
        int meta = stack.getMetadata();
        switch (meta) {
            case 0:
                if (world.isRemote) {
                    spellsCap.castSpell(player, "grow");
                    break;
                }
            case 1:
                if (world.isRemote) {
                    spellsCap.castSpell(player, "walling");
                    break;
                }
            default:
                break;
        }
    }

    private void airSpell(EntityPlayer player, World world, ItemStack stack, ISpells spellsCap) {
        int meta = stack.getMetadata();
        switch (meta) {
            case 0:
                if(world.isRemote) {
                    spellsCap.castSpell(player, "lightning");
                    break;
                }
            case 1:
                if(world.isRemote) {
                    spellsCap.castSpell(player, "invisibility");
                    break;
                }
            default:
                break;
        }
    }

    private void fireSpell(EntityPlayer player, World world, ItemStack stack, ISpells spellsCap) {
        int meta = stack.getMetadata();
        switch (meta) {
            case 0:
                if(world.isRemote) {
                    spellsCap.castSpell(player, "fireblast");
                }
                break;
            default:
                break;
        }
    }
}
