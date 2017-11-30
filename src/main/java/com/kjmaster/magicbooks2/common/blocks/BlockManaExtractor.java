package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.tile.extractor.TileManaExtractor;
import com.kjmaster.magicbooks2.common.blocks.tile.vase.TileManaVase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockManaExtractor extends BlockBase {

    public BlockManaExtractor(String name, Material material, CreativeTabs tab, float hardness, float resistance, String tool, int harvest) {
        super(name, material, tab, hardness, resistance, tool, harvest);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileManaExtractor) {
            TileManaExtractor manaExtractor = (TileManaExtractor) tileEntity;
            int airMana = manaExtractor.airManaStorage.getManaStored();
            int arcaneMana = manaExtractor.arcaneManaStorage.getManaStored();
            int earthMana = manaExtractor.earthManaStorage.getManaStored();
            int fireMana = manaExtractor.fireManaStorage.getManaStored();
            int waterMana = manaExtractor.waterManaStorage.getManaStored();
            int meta = world.getBlockState(pos).getBlock().getMetaFromState(state);
            ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock(), 1, meta);
            stack.setTagInfo("AirMana", new NBTTagInt(airMana));
            stack.setTagInfo("ArcaneMana", new NBTTagInt(arcaneMana));
            stack.setTagInfo("EarthMana", new NBTTagInt(earthMana));
            stack.setTagInfo("FireMana", new NBTTagInt(fireMana));
            stack.setTagInfo("WaterMana", new NBTTagInt(waterMana));
            drops.add(stack);
        }
        else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool)
    {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileManaExtractor) {
            TileManaExtractor manaExtractor = (TileManaExtractor) tileEntity;
            int mana;
            if (stack.hasTagCompound()) {
                if (stack.getTagCompound().hasKey("AirMana")) {
                    mana = stack.getTagCompound().getInteger("AirMana");
                    manaExtractor.airManaStorage.setMana(mana);
                }
                if (stack.getTagCompound().hasKey("ArcaneMana")) {
                    mana = stack.getTagCompound().getInteger("ArcaneMana");
                    manaExtractor.arcaneManaStorage.setMana(mana);
                }
                if (stack.getTagCompound().hasKey("EarthMana")) {
                    mana = stack.getTagCompound().getInteger("EarthMana");
                    manaExtractor.earthManaStorage.setMana(mana);
                }
                if (stack.getTagCompound().hasKey("FireMana")) {
                    mana = stack.getTagCompound().getInteger("FireMana");
                    manaExtractor.fireManaStorage.setMana(mana);
                }
                if (stack.getTagCompound().hasKey("WaterMana")) {
                    mana = stack.getTagCompound().getInteger("WaterMana");
                    manaExtractor.waterManaStorage.setMana(mana);
                }
            }
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileManaExtractor();
    }

    private TileManaExtractor getTE(World world, BlockPos pos) {
        return (TileManaExtractor) world.getTileEntity(pos);
    }

    //Thank you McJty for the great tutorial which can be found here https://wiki.mcjty.eu/modding/index.php/Render_Block_TESR_/_OBJ-1.9
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileManaExtractor te = getTE(world, pos);
            if (te.getStack().isEmpty()) {
                if (!player.getHeldItem(hand).isEmpty()) {
                    // There is no item in the pedestal and the player is holding an item. We move that item
                    // to the pedestal
                    te.setStack(new ItemStack(player.getHeldItem(hand).getItem(), 1, player.getHeldItem(hand).getMetadata()));
                    player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
                    // Make sure the client knows about the changes in the player inventory
                    player.openContainer.detectAndSendChanges();
                }
            } else {
                // There is a stack in the pedestal. In this case we remove it and try to put it in the
                // players inventory if there is room

                ItemStack stack = te.getStack();
                te.setStack(ItemStack.EMPTY);
                if (!player.inventory.addItemStackToInventory(stack)) {
                    // Not possible. Throw item in the world
                    EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY()+1, pos.getZ(), stack);
                    world.spawnEntity(entityItem);
                } else {
                    player.openContainer.detectAndSendChanges();
                }
            }
        }

        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }
}
