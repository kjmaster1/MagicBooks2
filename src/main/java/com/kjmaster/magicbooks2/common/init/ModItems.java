package com.kjmaster.magicbooks2.common.init;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import com.kjmaster.magicbooks2.common.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
 * Created by pbill_000 on 11/09/2017.
 */
public class ModItems {
    public static final Item Shard = new ItemShard("shard",
            ModCreativeTabs.tabMagicBooks2, 64);

    public static final Item Book = new ItemBook("book",
            ModCreativeTabs.tabMagicBooks2, 64);

    public static final Item MagicBook = new ItemMagicBook("book_magic",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item CrystalLinker = new ItemCrystalLinker("crystal_linker",
            ModCreativeTabs.tabMagicBooks2, 1);
    public static final Item shardPickaxe = new ItemShardPickaxe(ItemShard.shardMaterial, "shard_pick",
            ModCreativeTabs.tabMagicBooks2, 1);

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final Item[] items = {
                    Shard,
                    Book,
                    MagicBook,
                    CrystalLinker,
                    shardPickaxe,
            };
            final IForgeRegistry<Item> registry = event.getRegistry();
            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);

            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        final Item[] items = {
                MagicBook,
                CrystalLinker,
        };
        for(final Item item : items) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(MagicBooks2.MODID + ":" + item.getUnlocalizedName().substring(5)
                            , "inventory"));
        }
        for(int i = 0; i < EnumHandler.ShardTypes.values().length; i++) {
            registerRender(Shard, i,
                    "shard_" + EnumHandler.ShardTypes.values()[i].getName());
        }
        for(int i = 0; i < EnumHandler.BookTypes.values().length; i++) {
            registerRender(Book, i,
                    "book_" + EnumHandler.BookTypes.values()[i].getName());
        }

    }
    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item, int meta, String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(
                        new ResourceLocation(MagicBooks2.MODID, fileName),
                        "inventory"));
    }
}
