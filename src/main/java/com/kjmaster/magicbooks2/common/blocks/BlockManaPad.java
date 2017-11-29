package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.common.blocks.tile.TileManaPad;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.air.CapabilityAirMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.fire.CapabilityFireMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
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
                IMana airManaCap = player.getCapability(CapabilityAirMana.AIRMANA, null);
                IMana arcaneManaCap = player.getCapability(CapabilityArcaneMana.ARCANEMANA, null);
                IMana earthManaCap = player.getCapability(CapabilityEarthMana.EARTHMANA, null);
                IMana fireManaCap = player.getCapability(CapabilityFireMana.FIREMANA, null);
                IMana waterManaCap = player.getCapability(CapabilityWaterMana.WATERMANA, null);
                int airMana = assignManaValue(airManaCap);
                int arcaneMana = assignManaValue(arcaneManaCap);
                int earthMana = assignManaValue(earthManaCap);
                int fireMana = assignManaValue(fireManaCap);
                int waterMana = assignManaValue(waterManaCap);
                TileManaPad tile = (TileManaPad) worldIn.getTileEntity(pos);
                IMana tileAirCap = tile.getCapability(CapabilityAirMana.AIRMANA, null);
                IMana tileArcaneCap = tile.getCapability(CapabilityArcaneMana.ARCANEMANA, null);
                IMana tileEarthCap = tile.getCapability(CapabilityEarthMana.EARTHMANA, null);
                IMana tileFireCap = tile.getCapability(CapabilityFireMana.FIREMANA, null);
                IMana tileWaterCap = tile.getCapability(CapabilityWaterMana.WATERMANA, null);
                receiveManaServer(airMana, airManaCap, tileAirCap, "Air", player);
                receiveManaServer(arcaneMana, arcaneManaCap, tileArcaneCap, "Arcane", player);
                receiveManaServer(earthMana, earthManaCap, tileEarthCap, "Earth", player);
                receiveManaServer(fireMana, fireManaCap, tileFireCap, "Fire", player);
                receiveManaServer(waterMana, waterManaCap, tileWaterCap, "Water", player);
            }
        }
    }

    private void receiveManaServer(int mana, IMana manaCap, IMana tileManaCap, String element, EntityPlayer player) {
        if (manaCap.getManaStored() < manaCap.getMaxManaStored()) {
            int received = manaCap.receiveMana(tileManaCap.getManaStored(), false);
            tileManaCap.extractMana(received, false);
            sendManaPacket(manaCap, element, (EntityPlayerMP) player);
        }
    }

    private void sendManaPacket(IMana manaCap, String element, EntityPlayerMP entityPlayerMP) {
        PacketInstance.INSTANCE.sendTo(new ClientManaPacket(element, manaCap.getManaStored()), entityPlayerMP);
    }

    private int assignManaValue (IMana manaCap) {
        if (manaCap != null) {
            return manaCap.getManaStored();
        } else {
            return 0;
        }
    }
}
