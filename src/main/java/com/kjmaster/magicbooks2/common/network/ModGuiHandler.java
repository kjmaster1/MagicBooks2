package com.kjmaster.magicbooks2.common.network;

import com.kjmaster.magicbooks2.client.gui.bag.ContainerSpellBag;
import com.kjmaster.magicbooks2.client.gui.bag.GuiSpellBag;
import com.kjmaster.magicbooks2.client.gui.bag.InventorySpellBag;
import com.kjmaster.magicbooks2.client.gui.magicbook.screens.*;
import com.kjmaster.magicbooks2.client.gui.magicbook.entries.GuiIntroScreen;
import com.kjmaster.magicbooks2.client.gui.runes.GuiLumberRune;
import com.kjmaster.magicbooks2.client.gui.runes.GuiRune;
import com.kjmaster.magicbooks2.common.blocks.tile.container.runes.ContainerLumberRune;
import com.kjmaster.magicbooks2.common.blocks.tile.container.runes.ContainerRune;
import com.kjmaster.magicbooks2.common.blocks.tile.runes.TileRune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler {

    public static final int magicBook = 0;
    public static final int introEntry = 1;
    public static final int airBookPage = 2;
    public static final int arcaneBookPage = 3;
    public static final int earthBookPage = 4;
    public static final int fireBookPage = 5;
    public static final int waterBookPage = 6;
    public static final int spellBag = 7;
    public static final int rune = 8;
    public static final int lumberRune = 9;

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case introEntry:
                return new GuiIntroScreen();
            case magicBook:
                return new GuiMagicBookScreen();
            case airBookPage:
                return new GuiAirScreen();
            case arcaneBookPage:
                return new GuiArcaneScreen();
            case earthBookPage:
                return new GuiEarthScreen();
            case fireBookPage:
                return new GuiFireScreen();
            case waterBookPage:
                return new GuiWaterScreen();
            case spellBag:
                return new GuiSpellBag(player.inventory, new InventorySpellBag(player.getHeldItem(EnumHand.MAIN_HAND)));
            case rune:
                return new GuiRune(player.inventory, (TileRune) world.getTileEntity(new BlockPos(x, y, z)), world);
            case lumberRune:
                return new GuiLumberRune(player.inventory, (TileRune) world.getTileEntity(new BlockPos(x, y, z)), world);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case spellBag:
                return new ContainerSpellBag(player.inventory, new InventorySpellBag(player.getHeldItem(EnumHand.MAIN_HAND)));
            case rune:
                return new ContainerRune(player.inventory, (TileRune) world.getTileEntity(new BlockPos(x, y, z)));
            case lumberRune:
                return new ContainerLumberRune(player.inventory, (TileRune) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
