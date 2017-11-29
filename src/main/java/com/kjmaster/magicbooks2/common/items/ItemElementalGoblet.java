package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.BlockElementBase;
import com.kjmaster.magicbooks2.common.blocks.tile.vase.*;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.common.network.ClientManaPacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ItemElementalGoblet extends MetaItemBase {

    public ItemElementalGoblet(String unlocalizedName, CreativeTabs tab, int maxSize) {
        super(unlocalizedName, tab, maxSize);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        int meta = stack.getMetadata();
        if (meta == 0 && !worldIn.isRemote) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof TileManaVase) {
                switch (worldIn.getBlockState(pos).getValue(BlockElementBase.ELEMENT)) {
                    case AIR:
                        TileAirManaVase airManaVase = (TileAirManaVase) worldIn.getTileEntity(pos);
                        IMana airManaCap = airManaVase.getCapability(CapabilityAirMana.AIRMANA, EnumFacing.UP);
                        ItemStack airGoblet = new ItemStack(ModItems.elementalGoblet, 1, 1);
                        IMana airManaCapStack = airGoblet.getCapability(CapabilityAirMana.AIRMANA, null);
                        if (airManaCap != null && airManaCapStack != null) {
                            int airMana = airManaCap.getManaStored();
                            int received = airManaCapStack.receiveMana(airMana, false);
                            airManaCap.extractMana(received, false);
                            player.setHeldItem(hand, airGoblet);
                        }
                        break;
                    case ARCANE:
                        TileArcaneManaVase arcaneManaVase = (TileArcaneManaVase) worldIn.getTileEntity(pos);
                        IMana arcaneManaCap = arcaneManaVase.getCapability(CapabilityArcaneMana.ARCANEMANA, EnumFacing.UP);
                        ItemStack arcaneGoblet = new ItemStack(ModItems.elementalGoblet, 1, 5);
                        IMana arcaneManaCapStack = arcaneGoblet.getCapability(CapabilityArcaneMana.ARCANEMANA, null);
                        if (arcaneManaCap != null && arcaneManaCapStack != null) {
                            int arcaneMana = arcaneManaCap.getManaStored();
                            int received = arcaneManaCapStack.receiveMana(arcaneMana, false);
                            arcaneManaCap.extractMana(received, false);
                            player.setHeldItem(hand, arcaneGoblet);
                        }
                        break;
                    case EARTH:
                        TileEarthManaVase earthManaVase = (TileEarthManaVase) worldIn.getTileEntity(pos);
                        IMana earthManaCap = earthManaVase.getCapability(CapabilityEarthMana.EARTHMANA, EnumFacing.UP);
                        ItemStack earthGoblet = new ItemStack(ModItems.elementalGoblet, 1, 2);
                        IMana earthManaCapStack = earthGoblet.getCapability(CapabilityEarthMana.EARTHMANA, null);
                        if (earthManaCap != null && earthManaCapStack != null) {
                            int earthMana = earthManaCap.getManaStored();
                            int received = earthManaCapStack.receiveMana(earthMana, false);
                            earthManaCap.extractMana(received, false);
                            player.setHeldItem(hand, earthGoblet);
                        }
                        break;
                    case FIRE:
                        TileFireManaVase fireManaVase = (TileFireManaVase) worldIn.getTileEntity(pos);
                        IMana fireManaCap = fireManaVase.getCapability(CapabilityFireMana.FIREMANA, EnumFacing.UP);
                        ItemStack fireGoblet = new ItemStack(ModItems.elementalGoblet, 1, 3);
                        IMana fireManaCapStack = fireGoblet.getCapability(CapabilityFireMana.FIREMANA, null);
                        if (fireManaCap != null && fireManaCapStack != null) {
                            int fireMana = fireManaCap.getManaStored();
                            int received = fireManaCapStack.receiveMana(fireMana, false);
                            fireManaCap.extractMana(received, false);
                            player.setHeldItem(hand, fireGoblet);
                        }
                    case WATER:
                        TileWaterManaVase waterManaVase = (TileWaterManaVase) worldIn.getTileEntity(pos);
                        IMana waterManaCap = waterManaVase.getCapability(CapabilityWaterMana.WATERMANA, EnumFacing.UP);
                        ItemStack waterGoblet = new ItemStack(ModItems.elementalGoblet, 1, 4);
                        IMana waterManaCapStack = waterGoblet.getCapability(CapabilityWaterMana.WATERMANA, null);
                        if (waterManaCap != null && waterManaCapStack != null) {
                            int waterMana = waterManaCap.getManaStored();
                            int received = waterManaCapStack.receiveMana(waterMana, false);
                            waterManaCap.extractMana(received, false);
                            player.setHeldItem(hand, waterGoblet);
                        }
                }
            }

        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {

        if (!worldIn.isRemote) {
            if (entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLiving;
                switch (stack.getMetadata()) {
                    case 0:
                        break;
                    case 1:
                        IMana airManaCap = player.getCapability(CapabilityAirMana.AIRMANA, null);
                        IMana airManaCapStack = stack.getCapability(CapabilityAirMana.AIRMANA, null);
                        if (airManaCap != null && airManaCapStack != null) {
                            int airMana = airManaCapStack.getManaStored();
                            MagicBooks2.LOGGER.info("Air Mana: " + airMana);
                            int received = airManaCap.receiveMana(airMana, false);
                            MagicBooks2.LOGGER.info("Received: " + received);
                            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Air", airManaCap.getManaStored()),
                                    (EntityPlayerMP) player);
                            checkCreative(player, stack);
                        }
                    case 2:
                        IMana earthManaCap = player.getCapability(CapabilityEarthMana.EARTHMANA, null);
                        IMana earthManaCapStack = stack.getCapability(CapabilityEarthMana.EARTHMANA, null);
                        if (earthManaCap != null && earthManaCapStack != null) {
                            int earthMana = earthManaCapStack.getManaStored();
                            earthManaCap.receiveMana(earthMana, false);
                            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Earth", earthManaCap.getManaStored()),
                                    (EntityPlayerMP) player);
                            checkCreative(player, stack);
                        }
                    case 3:
                        IMana fireManaCap = player.getCapability(CapabilityFireMana.FIREMANA, null);
                        IMana fireManaCapStack = stack.getCapability(CapabilityFireMana.FIREMANA, null);
                        if (fireManaCap != null && fireManaCapStack != null) {
                            int fireMana = fireManaCapStack.getManaStored();
                            fireManaCap.receiveMana(fireMana, false);
                            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Fire", fireManaCap.getManaStored()),
                                    (EntityPlayerMP) player);
                            checkCreative(player, stack);
                        }
                    case 4:
                        IMana waterManaCap = player.getCapability(CapabilityWaterMana.WATERMANA, null);
                        IMana waterManaCapStack = stack.getCapability(CapabilityWaterMana.WATERMANA, null);
                        if (waterManaCap != null && waterManaCapStack != null) {
                           int waterMana = waterManaCapStack.getManaStored();
                           waterManaCap.receiveMana(waterMana, false);
                            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Water", waterManaCap.getManaStored()),
                                    (EntityPlayerMP) player);
                            checkCreative(player, stack);
                        }
                    case 5:
                        IMana arcaneManaCap = player.getCapability(CapabilityArcaneMana.ARCANEMANA, null);
                        IMana arcaneManaCapStack = stack.getCapability(CapabilityArcaneMana.ARCANEMANA, null);
                        if (arcaneManaCap != null && arcaneManaCapStack != null) {
                            int arcaneMana = arcaneManaCapStack.getManaStored();
                            arcaneManaCap.receiveMana(arcaneMana, false);
                            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Arcane", arcaneManaCap.getManaStored()),
                                    (EntityPlayerMP) player);
                            checkCreative(player, stack);
                        }
                }
            }
        }

        return stack.isEmpty() ? new ItemStack(ModItems.elementalGoblet, 1, 0) : stack;
    }

    private void checkCreative(EntityPlayer player, ItemStack stack) {
        if (!player.capabilities.isCreativeMode)
            stack.shrink(1);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta != 0)
            return 32;
        else return super.getMaxItemUseDuration(stack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        if (stack.getMetadata() != 0)
            return EnumAction.DRINK;
        else
            return super.getItemUseAction(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        int meta = playerIn.getHeldItem(handIn).getMetadata();
        if (meta != 0) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        else
            return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        switch (stack.getMetadata()) {
            case 0:
                return null;
            case 1:
                return new CapabilityAirMana();
            case 2:
                return new CapabilityEarthMana();
            case 3:
                return new CapabilityFireMana();
            case 4:
                return new CapabilityWaterMana();
            case 5:
                return new CapabilityArcaneMana();
            default:
                return null;
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == ModCreativeTabs.tabMagicBooks2)
            items.add(new ItemStack(this, 1, 0));
    }
}
