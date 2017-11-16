package com.kjmaster.magicbooks2.common.init;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.creative.ModCreativeTabs;
import com.kjmaster.magicbooks2.common.handlers.EnumHandler;
import com.kjmaster.magicbooks2.common.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

    public static final Item Wand = new ItemWand("wand",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardPickaxeAir = new ItemShardPickaxe(ItemShard.shardMaterial, "shard_pickaxe_air",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardPickaxeArcane = new ItemShardPickaxe(ItemShard.shardMaterial, "shard_pickaxe_arcane",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardPickaxeEarth = new ItemShardPickaxe(ItemShard.shardMaterial, "shard_pickaxe_earth",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardPickaxeFire = new ItemShardPickaxe(ItemShard.shardMaterial, "shard_pickaxe_fire",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardPickaxeWater = new ItemShardPickaxe(ItemShard.shardMaterial, "shard_pickaxe_water",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardAxeAir = new ItemShardAxe(ItemShard.shardMaterial, "shard_axe_air",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardAxeArcane = new ItemShardAxe(ItemShard.shardMaterial, "shard_axe_arcane",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardAxeEarth = new ItemShardAxe(ItemShard.shardMaterial, "shard_axe_earth",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardAxeFire = new ItemShardAxe(ItemShard.shardMaterial, "shard_axe_fire",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item shardAxeWater = new ItemShardAxe(ItemShard.shardMaterial, "shard_axe_water",
            ModCreativeTabs.tabMagicBooks2, 1);

    public static final Item earthSpell = new ItemSpellEarth("spell_earth",
            ModCreativeTabs.tabMagicBooks2Spells, 1);

    public static final Item airSpell = new ItemSpellAir("spell_air",
            ModCreativeTabs.tabMagicBooks2Spells, 1);

    public static final Item fireSpell = new ItemSpellFire("spell_fire",
            ModCreativeTabs.tabMagicBooks2Spells, 1);

    public static final Item waterSpell = new ItemSpellWater("spell_water",
            ModCreativeTabs.tabMagicBooks2Spells, 1);

    public static final Item spellBag = new ItemSpellBag("spell_bag",
            ModCreativeTabs.tabMagicBooks2Spells, 1);

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
                    Wand,
                    shardPickaxeAir,
                    shardPickaxeEarth,
                    shardPickaxeFire,
                    shardPickaxeWater,
                    shardPickaxeArcane,
                    shardAxeAir,
                    shardAxeArcane,
                    shardAxeEarth,
                    shardAxeFire,
                    shardAxeWater,
                    earthSpell,
                    airSpell,
                    fireSpell,
                    waterSpell,
                    spellBag,
            };
            final IForgeRegistry<Item> registry = event.getRegistry();
            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);

            }
            MagicBooks2.proxy.registerOreDicItems();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        final Item[] items = {
                MagicBook,
                CrystalLinker,
                shardPickaxeAir,
                shardPickaxeEarth,
                shardPickaxeFire,
                shardPickaxeWater,
                shardPickaxeArcane,
                shardAxeAir,
                shardAxeArcane,
                shardAxeEarth,
                shardAxeFire,
                shardAxeWater,
                spellBag,
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
        for(int i = 0; i < EnumHandler.WandTypes.values().length; i++) {
            registerRender(Wand, i,
                    "wand_" + EnumHandler.WandTypes.values()[i].getName());
        }
        for(int i = 0; i < EnumHandler.EarthSpellTypes.values().length; i++) {
            registerRender(earthSpell, i,
                    "spell_earth_" + EnumHandler.EarthSpellTypes.values()[i].getName());
        }
        for(int i = 0; i < EnumHandler.AirSpellTypes.values().length; i++) {
            registerRender(airSpell, i,
                    "spell_air_" + EnumHandler.AirSpellTypes.values()[i].getName());
        }
        for(int i = 0; i < EnumHandler.FireSpellTypes.values().length; i++) {
            registerRender(fireSpell, i,
                    "spell_fire_" + EnumHandler.FireSpellTypes.values()[i].getName());
        }
        for(int i = 0; i < EnumHandler.WaterSpellTypes.values().length; i++) {
            registerRender(waterSpell, i,
                    "spell_water_" + EnumHandler.WaterSpellTypes.values()[i].getName());
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
