package com.kjmaster.magicbooks2.common.items;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.tile.TileCrystal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by pbill_000 on 11/09/2017.
 */
public class ItemCrystalLinker extends ItemBase {
    private BlockPos storedPos = null;
    public ItemCrystalLinker(String name, CreativeTabs tab, int maxSize) {
        super(name, tab, maxSize);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        MagicBooks2.LOGGER.info("TESTING");
        if (storedPos != null) {
            if(pos.equals(storedPos) && !worldIn.isRemote) {
                player.sendMessage(new TextComponentString("No Connecting To Self, TEST"));
                storedPos = null;
            }
            else if (!isInRange(pos, storedPos) && !worldIn.isRemote) {
                player.sendMessage(new TextComponentString("Out Of Range, TEST"));
                storedPos = null;
            }
            else if (worldIn.getTileEntity(pos) instanceof TileCrystal
                    && !worldIn.isRemote && worldIn.getTileEntity(storedPos) instanceof TileCrystal) {
                TileCrystal tileCrystal = (TileCrystal) worldIn.getTileEntity(pos);
                tileCrystal.setConnectedToPos(storedPos);
                tileCrystal.setHasConnection(true);
                storedPos = null;
            }
        } else if (worldIn.getTileEntity(pos) instanceof TileCrystal && !worldIn.isRemote) {
            storedPos = pos;
            player.sendMessage(new TextComponentString("Pos Saved, TEST"));
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    private boolean isInRange(BlockPos pos, BlockPos storedPos) {
        if(pos.getX() - storedPos.getX() > 10)
            return false;
        else if (pos.getY() - storedPos.getY() > 10)
            return false;
        else if (pos.getZ() - storedPos.getZ() > 10)
            return false;
        else if (storedPos.getX() - pos.getX() > 10)
            return false;
        else if (storedPos.getY() - pos.getY() > 10)
            return false;
        else if (storedPos.getZ() - pos.getZ() > 10)
            return false;
        else
            return true;
    }
}
