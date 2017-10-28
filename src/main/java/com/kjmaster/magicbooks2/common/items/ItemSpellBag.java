package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spells;
import com.kjmaster.magicbooks2.common.network.ModGuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import slimeknights.mantle.common.GuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemSpellBag extends ItemBase {

    private static final String TAG_ITEMS = "InvItems";

    public ItemSpellBag(String name, CreativeTabs tab, int maxSize) {
        super(name, tab, maxSize);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new InvProvider();
    }

    private static class InvProvider implements ICapabilitySerializable<NBTBase> {

        private final IItemHandler inv = new ItemStackHandler(54) {

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!stack.isEmpty() && Spells.validSpellItems.contains(stack.getItem()))
                    return super.insertItem(slot, stack, simulate);
                else return stack;
            }
        };

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
            else return null;
        }

        @Override
        public NBTBase serializeNBT() {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inv, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inv, null, nbt);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(stack.getTagCompound() != null && stack.getTagCompound().hasKey(TAG_ITEMS)) {
            NBTTagList oldData = stack.getTagCompound().getTagList(TAG_ITEMS, Constants.NBT.TAG_COMPOUND);
            IItemHandler newInv = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(newInv, null, oldData);

            stack.getTagCompound().removeTag(TAG_ITEMS);

            if(stack.getTagCompound().getSize() == 0)
                stack.setTagCompound(null);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.openGui(MagicBooks2.instance, ModGuiHandler.spellBag, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(),
                playerIn.getPosition().getZ());
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
