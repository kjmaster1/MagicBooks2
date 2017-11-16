package com.kjmaster.magicbooks2.common.recipe;

import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;

import java.util.ArrayList;
import java.util.List;

public class PedestalHandler {

    public static final List<PedestalRecipe> PEDESTAL_RECIPES = new ArrayList<>();

    public static void init() {

        //Add All Picks
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(ModItems.shardPickaxeAir, 1, 0),
             new ItemStack(ModBlocks.shardBlock, 1, 0), ItemStack.EMPTY, ItemStack.EMPTY,
             ItemStack.EMPTY, 1000, 0, 0, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(ModItems.shardPickaxeEarth, 1, 0),
                ItemStack.EMPTY, new ItemStack(ModBlocks.shardBlock, 1, 1), ItemStack.EMPTY,
                ItemStack.EMPTY, 0, 1000, 0, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(ModItems.shardPickaxeFire, 1, 0),
                ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ModBlocks.shardBlock, 1, 2),
                ItemStack.EMPTY, 0, 0, 1000, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(ModItems.shardPickaxeWater, 1, 0),
                ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                new ItemStack(ModBlocks.shardBlock, 1, 3), 0, 0, 0, 1000, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(ModItems.shardPickaxeArcane, 1, 0),
                new ItemStack(ModItems.Shard, 1, 4), new ItemStack(ModItems.Shard, 1, 4),
                new ItemStack(ModItems.Shard, 1, 4), new ItemStack(ModItems.Shard, 1, 4),
                250, 250, 250, 250, 50));
        //Add All Axes
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_AXE), new ItemStack(ModItems.shardAxeAir, 1, 0),
                new ItemStack(ModBlocks.shardBlock, 1, 0), ItemStack.EMPTY, ItemStack.EMPTY,
                ItemStack.EMPTY, 1000, 0, 0, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_AXE), new ItemStack(ModItems.shardAxeEarth, 1, 0),
                ItemStack.EMPTY, new ItemStack(ModBlocks.shardBlock, 1, 1), ItemStack.EMPTY,
                ItemStack.EMPTY, 0, 1000, 0, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_AXE), new ItemStack(ModItems.shardAxeFire, 1, 0),
                ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ModBlocks.shardBlock, 1, 2),
                ItemStack.EMPTY, 0, 0, 1000, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_AXE), new ItemStack(ModItems.shardAxeWater, 1, 0),
                ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                new ItemStack(ModBlocks.shardBlock, 1, 3), 0, 0, 0, 1000, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.DIAMOND_AXE), new ItemStack(ModItems.shardAxeArcane, 1, 0),
                new ItemStack(ModItems.Shard, 1, 4), new ItemStack(ModItems.Shard, 1, 4),
                new ItemStack(ModItems.Shard, 1, 4), new ItemStack(ModItems.Shard, 1, 4),
                250, 250, 250, 250, 50));
        //Add All Books
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.BOOK), new ItemStack(ModItems.Book, 1, 0),
             new ItemStack(ModItems.Shard, 1, 0), ItemStack.EMPTY, ItemStack.EMPTY,
             ItemStack.EMPTY, 100, 0, 0, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.BOOK), new ItemStack(ModItems.Book, 1, 1),
            ItemStack.EMPTY, new ItemStack(ModItems.Shard, 1, 1), ItemStack.EMPTY,
            ItemStack.EMPTY, 0, 100, 0, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.BOOK), new ItemStack(ModItems.Book, 1, 2),
            ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(ModItems.Shard, 1, 2),
            ItemStack.EMPTY, 0, 0, 100, 0, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.BOOK), new ItemStack(ModItems.Book, 1, 3),
                ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                new ItemStack(ModItems.Shard, 1, 3), 0, 0, 0, 100, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.BOOK), new ItemStack(ModItems.Book, 1, 4),
                new ItemStack(ModItems.Shard, 1, 0), new ItemStack(ModItems.Shard, 1, 1),
                new ItemStack(ModItems.Shard, 1, 2), new ItemStack(ModItems.Shard, 1, 3),
                100, 100, 100, 100, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Items.BOOK), new ItemStack(ModItems.MagicBook, 1, 0),
                new ItemStack(ModItems.Shard, 1, 4), new ItemStack(ModItems.Shard, 1, 4),
                new ItemStack(ModItems.Shard, 1, 4), new ItemStack(ModItems.Shard, 1, 4),
                100, 100, 100, 100, 50));
        //Add All Air Spells
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.WOOL, 1, 15), new ItemStack(ModItems.airSpell, 1, 0),
                new ItemStack(ModBlocks.shardBlock, 1, 0), new ItemStack(Blocks.GOLD_BLOCK, 1, 0),
                new ItemStack(Blocks.GLOWSTONE, 1, 0), ItemStack.EMPTY,
                1000, 200, 200, 0, 50));

        ItemStack invisPotionStack = new ItemStack(Items.POTIONITEM, 1, 0);
        PotionUtils.addPotionToItemStack(invisPotionStack, PotionTypes.INVISIBILITY);

        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.WOOL, 1, 15), new ItemStack(ModItems.airSpell, 1, 1),
                invisPotionStack, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                        1500, 0, 0, 0, 50));
        //Add All Arcane Spells
        //Add All Earth Spells
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.WOOL, 1, 15), new ItemStack(ModItems.earthSpell, 1, 0),
                ItemStack.EMPTY, new ItemStack(Blocks.BONE_BLOCK, 1, 0), ItemStack.EMPTY, new ItemStack(Items.WATER_BUCKET, 1, 0),
                0, 1000, 0, 200, 50));
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.WOOL, 1, 15), new ItemStack(ModItems.earthSpell, 1, 1),
                ItemStack.EMPTY, new ItemStack(ModBlocks.shardBlock, 1, 1), ItemStack.EMPTY, ItemStack.EMPTY,
                0, 500, 0, 0, 50));
        //Add All Fire Spells
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.WOOL, 1, 15), new ItemStack(ModItems.fireSpell, 1, 0),
                ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Blocks.MAGMA, 1, 0), ItemStack.EMPTY,
                0, 0, 1000, 0, 50));
        //Add All Water Spells
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.WOOL, 1, 15), new ItemStack(ModItems.waterSpell, 1, 0),
                ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.WATER_BUCKET, 1, 0),
                0, 0, 0, 1000, 50));
        //Add All Air Runes
        //Add All Arcane Runes
        //Add All Earth Runes
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.STONE, 1, 0), new ItemStack(ModBlocks.lumberRune, 1, 0),
                ItemStack.EMPTY, new ItemStack(Items.DIAMOND_AXE, 1), ItemStack.EMPTY, ItemStack.EMPTY,
                0, 1000, 0, 0, 50));
        //Add All Fire Runes
        //Add All Water Runes
        PEDESTAL_RECIPES.add(new PedestalRecipe(new ItemStack(Blocks.STONE, 1, 0), new ItemStack(ModBlocks.drowningRune, 1, 0),
                ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.WATER_BUCKET, 1, 0),
                0, 0, 0, 500, 50));
    }
}

