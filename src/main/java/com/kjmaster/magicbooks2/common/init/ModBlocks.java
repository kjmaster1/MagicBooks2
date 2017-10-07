package com.kjmaster.magicbooks2.common.init;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.*;
import com.kjmaster.magicbooks2.common.blocks.arcanecrafter.BlockArcaneCrafter;
import com.kjmaster.magicbooks2.common.blocks.item.ItemBlockBase;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import jline.internal.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class ModBlocks {

    public static Block crystal = new BlockCrystal("crystal", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10F, "pickaxe", 2);

    public static Block greaterCrystal = new BlockGreaterCrystal("crystal_greater", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10.0F, "pickaxe", 2);
    public static Block manaPad = new BlockManaPad(Material.IRON, BlockPressurePlate.Sensitivity.EVERYTHING);

    public static Block arcaneCrafter = new BlockArcaneCrafter("arcane_crafter", Material.WOOD, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10.0F, "axe", 0);

    public static Block shardOre = new BlockShardOre("shard_ore", Material.ROCK, ModCreativeTabs.tabMagicBooks2,
            3.0F, 15.0F, "pickaxe", 2);

    public static Block shardBlock = new BlockShard("shard_block", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            5.0F, 10.0F, "pickaxe", 2);

    public static Block earthWall = new BlockEarthWall("earth_wall", Material.GROUND, ModCreativeTabs.tabMagicBooks2,
            0.01F, 0.01F, "pickaxe", 0);

    public static Block pedestal = new BlockPedestal("pedestal_block", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10F, "pickaxe", 2);

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();
            final Block[] blocks = {
                    crystal,
                    greaterCrystal,
                    pedestal,
                    manaPad,
                    arcaneCrafter,
                    shardOre,
                    shardBlock,
                    earthWall,
            };
            registry.registerAll(blocks);
        }

        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final ItemBlock[] items = {
                    new ItemBlock(manaPad),
                    new ItemBlock(arcaneCrafter),
                    new ItemBlock(earthWall),
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final ItemBlock item : items) {
                final Block block = item.getBlock();
                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName());
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
            }
            registry.register(new ItemBlockBase(crystal).setRegistryName(crystal.getRegistryName()));
            registry.register(new ItemBlockBase(greaterCrystal).setRegistryName(greaterCrystal.getRegistryName()));
            registry.register(new ItemBlockBase(shardOre).setRegistryName(shardOre.getRegistryName()));
            registry.register(new ItemBlockBase(shardBlock).setRegistryName(shardBlock.getRegistryName()));
            registry.register(new ItemBlockBase(pedestal).setRegistryName(pedestal.getRegistryName()));
            MagicBooks2.proxy.registerOreDicBlocks();
        }
    }
    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        final Block[] blocks = {
                manaPad,
                arcaneCrafter,
        };
        for(final Block block: blocks) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(MagicBooks2.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));;
        }
        final ItemBlock[] items = {
                new ItemBlock(manaPad),
                new ItemBlock(arcaneCrafter),
        };
        for(final ItemBlock item : items) {
            final Block block = item.getBlock();
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(MagicBooks2.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
        }
        for(int i = 0; i < EnumHandler.ShardTypes.values().length; i++) {
            registerMetaRender(crystal, i, "crystal_" + EnumHandler.ShardTypes.values()[i].getName() );
            registerMetaRender(greaterCrystal, i, "crystal_greater_" + EnumHandler.ShardTypes.values()[i].getName());
            registerMetaRender(shardOre, i, "shard_ore_" + EnumHandler.ShardTypes.values()[i].getName());
            registerMetaRender(shardBlock, i, "shard_block_" + EnumHandler.ShardTypes.values()[i].getName());
            registerMetaRender(pedestal, i, "pedestal_block_" + EnumHandler.ShardTypes.values()[i].getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerMetaRender(Block block, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(MagicBooks2.MODID, fileName), "inventory"));
    }
}

