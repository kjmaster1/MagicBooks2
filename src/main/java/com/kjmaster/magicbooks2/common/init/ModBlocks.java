package com.kjmaster.magicbooks2.common.init;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.kjmaster.kjlib.common.blocks.item.ItemBlockBase;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.blocks.*;
import com.kjmaster.magicbooks2.common.blocks.item.ItemBlockPipe;
import com.kjmaster.magicbooks2.common.blocks.pipe.BasePipeBlock;
import com.kjmaster.magicbooks2.common.blocks.pipe.EnumPipeType;
import com.kjmaster.magicbooks2.common.blocks.runes.BlockDrowningRune;
import com.kjmaster.magicbooks2.common.blocks.runes.BlockLumberRune;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
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
import java.util.Map;
import java.util.Set;

public class ModBlocks {

    public static Block manaPad = new BlockManaPad(Material.IRON, BlockPressurePlate.Sensitivity.EVERYTHING);

    public static Block shardOre = new BlockShardOre("shard_ore", Material.ROCK, ModCreativeTabs.tabMagicBooks2,
            3.0F, 15.0F, "pickaxe", 2);

    public static Block shardBlock = new BlockShard("shard_block", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            5.0F, 10.0F, "pickaxe", 2);

    public static Block earthWall = new BlockEarthWall("earth_wall", Material.GROUND, ModCreativeTabs.tabMagicBooks2,
            0.01F, 0.01F, "pickaxe", 0);

    public static Block pedestal = new BlockPedestal("pedestal_block", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10F, "pickaxe", 2);

    public static Block drowningRune = new BlockDrowningRune("rune_drowning", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10F, "pickaxe", 2);

    public static Block lumberRune = new BlockLumberRune("rune_lumber", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10F, "pickaxe", 2);

    public static BlockManaVase manaVase = new BlockManaVase("mana_vase", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10F, "pickaxe", 2);

    public static BasePipeBlock pipe = new BasePipeBlock("pipe");

    public static Block manaExtractor = new BlockManaExtractor("mana_extractor", Material.IRON, ModCreativeTabs.tabMagicBooks2,
            3.0F, 10F, "pickaxe", 2);

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();
            final Block[] blocks = {
                    manaVase,
                    pedestal,
                    manaPad,
                    shardOre,
                    shardBlock,
                    earthWall,
                    drowningRune,
                    lumberRune,
                    pipe,
                    manaExtractor,
            };
            registry.registerAll(blocks);
        }

        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final ItemBlock[] items = {
                    new ItemBlock(manaPad),
                    new ItemBlock(earthWall),
                    new ItemBlock(drowningRune),
                    new ItemBlock(lumberRune),
                    new ItemBlock(manaExtractor),
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final ItemBlock item : items) {
                final Block block = item.getBlock();
                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName());
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
            }
            registry.register(new ItemBlockBase(manaVase).setRegistryName(manaVase.getRegistryName()));
            registry.register(new ItemBlockBase(shardOre).setRegistryName(shardOre.getRegistryName()));
            registry.register(new ItemBlockBase(shardBlock).setRegistryName(shardBlock.getRegistryName()));
            registry.register(new ItemBlockBase(pedestal).setRegistryName(pedestal.getRegistryName()));
            registry.register(new ItemBlockPipe(pipe).setRegistryName(pipe.getRegistryName()));
            MagicBooks2.proxy.registerOreDicBlocks();
        }
    }
    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        final Block[] blocks = {
                manaPad,
                earthWall,
                drowningRune,
                lumberRune,
                manaExtractor,
        };
        for(final Block block: blocks) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));;
        }
        final ItemBlock[] items = {
                new ItemBlock(earthWall),
                new ItemBlock(drowningRune),
                new ItemBlock(lumberRune),
        };
        for(final ItemBlock item : items) {
            final Block block = item.getBlock();
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
        }
        for(int i = 0; i < EnumHandler.ShardTypes.values().length; i++) {
            registerMetaRender(shardOre, i, "shard_ore_" + EnumHandler.ShardTypes.values()[i].getName());
            registerMetaRender(shardBlock, i, "shard_block_" + EnumHandler.ShardTypes.values()[i].getName());
            registerMetaRender(pedestal, i, "pedestal_block_" + EnumHandler.ShardTypes.values()[i].getName());
        }
        for (EnumPipeType pipeType: EnumPipeType.values()) {
            registerBlockstateMultiItem(Item.getItemFromBlock(ModBlocks.pipe), pipeType.ordinal(),
                    pipeType.getName().toLowerCase(), "pipe_inv");
        }

        ModelLoader.setCustomStateMapper(ModBlocks.pipe, new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                return new ModelResourceLocation(new ResourceLocation(ModBlocks.pipe.getRegistryName().getResourceDomain(),
                        ModBlocks.pipe.getRegistryName().getResourcePath()), this.getPropertyString(map));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public static void registerMetaRender(Block block, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(MagicBooks2.MODID, fileName), "inventory"));
    }

    private static void registerBlockstateMultiItem(Item item, int meta, String variantName, String path) {
        ResourceLocation loc = new ResourceLocation(MagicBooks2.MODID, path);
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(loc, "type=" + variantName));
    }
}

