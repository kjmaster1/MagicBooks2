package com.kjmaster.magicbooks2.common.blocks;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.TileManaPad;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockManaPad extends BlockPressurePlate implements ITileEntityProvider {

    public BlockManaPad(Material materialIn, Sensitivity sensitivityIn) {
        super(materialIn, sensitivityIn);
        this.setUnlocalizedName("mana_pad");
        this.setRegistryName("mana_pad");
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
                        manaPad.storage.extractMana(airMana, false, "Air");
                    }
                    if(manaCap.getMana("Arcane") < manaCap.getCapacity()) {
                        int arcaneMana = manaPad.storage.getManaStored("Arcane");
                        manaCap.receiveMana(arcaneMana, "Arcane");
                        manaPad.storage.extractMana(arcaneMana, false, "Arcane");
                    }
                    if(manaCap.getMana("Earth") < manaCap.getCapacity()) {
                        int earthMana = manaPad.storage.getManaStored("Earth");
                        manaCap.receiveMana(earthMana, "Earth");
                        manaPad.storage.extractMana(earthMana, false, "Earth");
                    }
                    if(manaCap.getMana("Fire") < manaCap.getCapacity()) {
                        int fireMana = manaPad.storage.getManaStored("Fire");
                        manaCap.receiveMana(fireMana, "Fire");
                        manaPad.storage.extractMana(fireMana, false, "Fire");
                    }
                    if(manaCap.getMana("Water") < manaCap.getCapacity()) {
                        int waterMana = manaPad.storage.getManaStored("Water");
                        manaCap.receiveMana(waterMana, "Water");
                        manaPad.storage.extractMana(waterMana, false, "Water");
                    }

                    MagicBooks2.LOGGER.info("Player Air Mana: " + manaCap.getMana("Air"));
                    MagicBooks2.LOGGER.info("Player Arcane Mana: " + manaCap.getMana("Arcane"));
                    MagicBooks2.LOGGER.info("Player Earth Mana: " + manaCap.getMana("Earth"));
                    MagicBooks2.LOGGER.info("Player Fire Mana: " + manaCap.getMana("Fire"));
                    MagicBooks2.LOGGER.info("Player Water Mana: " + manaCap.getMana("Water"));

                }
            }
        }
    }
}
