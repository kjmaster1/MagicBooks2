package com.kjmaster.magicbooks2.common.network;

import com.kjmaster.magicbooks2.client.gui.blocks.GuiArcaneCrafter;
import com.kjmaster.magicbooks2.client.gui.blocks.GuiGreaterCrystal;
import com.kjmaster.magicbooks2.client.gui.magicbook.GuiMagicBookScreen;
import com.kjmaster.magicbooks2.client.gui.magicbook.entries.GuiIntroScreen;
import com.kjmaster.magicbooks2.common.blocks.arcanecrafter.ContainerArcaneCrafter;
import com.kjmaster.magicbooks2.common.blocks.arcanecrafter.TileArcaneCrafter;
import com.kjmaster.magicbooks2.common.blocks.tile.TileGreaterCrystal;
import com.kjmaster.magicbooks2.common.blocks.tile.container.ContainerGreaterCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler {

    public static final int greaterCrystal = 0;
    public static final int magicBook = 1;
    public static final int introEntry = 2;
    public static final int arcaneCrafter = 3;

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case greaterCrystal:
                return new GuiGreaterCrystal(player.inventory, (TileGreaterCrystal) world.getTileEntity(new BlockPos(x, y, z)), world);
            case introEntry:
                return new GuiIntroScreen();
            case magicBook:
                return new GuiMagicBookScreen();
            case arcaneCrafter:
                return new GuiArcaneCrafter(player.inventory, (TileArcaneCrafter) world.getTileEntity(new BlockPos(x,y,z)), world);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case greaterCrystal:
                return new ContainerGreaterCrystal(player.inventory, (TileGreaterCrystal) world.getTileEntity(new BlockPos(x, y, z)));
            case arcaneCrafter:
                return new ContainerArcaneCrafter(player.inventory, (TileArcaneCrafter) world.getTileEntity(new BlockPos(x,y,z)));
        }
        return null;
    }
}
