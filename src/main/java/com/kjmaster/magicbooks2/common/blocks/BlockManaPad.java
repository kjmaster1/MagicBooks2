package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.tile.TileManaPad;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.network.ClientManaPacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockManaPad extends BlockPressurePlate implements ITileEntityProvider {

    private static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);

    public BlockManaPad(Material materialIn, Sensitivity sensitivityIn) {
        super(materialIn, sensitivityIn);
        this.setUnlocalizedName("mana_pad");
        this.setRegistryName("mana_pad");
        setCreativeTab(ModCreativeTabs.tabMagicBooks2);
    }

    @Override
    public boolean canSpawnInBlock() {
        return false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BLOCK_AABB;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BLOCK_AABB;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileManaPad();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileManaPad();
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            if (!worldIn.isRemote) {
                IMana manaCap = player.getCapability(ManaProvider.MANA_CAP, null);
                TileEntity entity = worldIn.getTileEntity(pos);
                if(entity instanceof TileManaPad) {
                    TileManaPad manaPad = (TileManaPad) entity;
                    if(manaCap.getMana("Air") < manaCap.getCapacity()) {
                        int airMana = manaPad.storage.getManaStored("Air");
                        manaCap.receiveMana(airMana, "Air");
                        sendManaPacket(manaCap, "Air", (EntityPlayerMP) player);
                        manaPad.storage.extractMana(airMana, false, "Air");
                    }
                    if(manaCap.getMana("Arcane") < manaCap.getCapacity()) {
                        int arcaneMana = manaPad.storage.getManaStored("Arcane");
                        manaCap.receiveMana(arcaneMana, "Arcane");
                        sendManaPacket(manaCap, "Arcane", (EntityPlayerMP) player);
                        manaPad.storage.extractMana(arcaneMana, false, "Arcane");
                    }
                    if(manaCap.getMana("Earth") < manaCap.getCapacity()) {
                        int earthMana = manaPad.storage.getManaStored("Earth");
                        manaCap.receiveMana(earthMana, "Earth");
                        sendManaPacket(manaCap, "Earth", (EntityPlayerMP) player);
                        manaPad.storage.extractMana(earthMana, false, "Earth");
                    }
                    if(manaCap.getMana("Fire") < manaCap.getCapacity()) {
                        int fireMana = manaPad.storage.getManaStored("Fire");
                        manaCap.receiveMana(fireMana, "Fire");
                        sendManaPacket(manaCap, "Fire", (EntityPlayerMP) player);
                        manaPad.storage.extractMana(fireMana, false, "Fire");
                    }
                    if(manaCap.getMana("Water") < manaCap.getCapacity()) {
                        int waterMana = manaPad.storage.getManaStored("Water");
                        manaCap.receiveMana(waterMana, "Water");
                        sendManaPacket(manaCap, "Water", (EntityPlayerMP) player);
                        manaPad.storage.extractMana(waterMana, false, "Water");
                    }
                }
            }
        }
    }

    private void sendManaPacket(IMana manaCap, String element, EntityPlayerMP entityPlayerMP) {
        PacketInstance.INSTANCE.sendTo(new ClientManaPacket(element, manaCap.getMana(element)), entityPlayerMP);
    }
}
