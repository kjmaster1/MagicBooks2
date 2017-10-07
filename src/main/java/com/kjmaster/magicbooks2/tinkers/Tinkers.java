package com.kjmaster.magicbooks2.tinkers;

import gnu.trove.map.hash.THashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.TinkerTraits;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static slimeknights.tconstruct.library.utils.HarvestLevels.OBSIDIAN;

public class Tinkers {

    private static final Map<String, Material> materials = new THashMap<>();
    private static final Map<String, MaterialIntegration> materialIntegrations = new THashMap<>();
    private static final Map<String, CompletionStage<?>> materialIntegrationStages = new THashMap<>();

    public static void init(Side side) {
        if (Loader.isModLoaded("tconstruct")) {
            //Air
            Material airShardMaterial = new Material("shardAir", 0xFCE458);
            airShardMaterial.addTrait(TinkerTraits.lightweight);
            airShardMaterial.addTrait(TinkerTraits.magnetic2);
            airShardMaterial.addItem("shardAir", 1, Material.VALUE_Ingot);
            airShardMaterial.addItem("blockAir", 1, Material.VALUE_Ingot * 4);
            airShardMaterial.setCraftable(true).setCastable(false);
            setDispItem(airShardMaterial, "shardAir");
            if (side.isClient())
                registerTinkersRenderInfo(airShardMaterial, 0xFCE458);

            TinkerRegistry.addMaterialStats(airShardMaterial,
                    new HeadMaterialStats(40, 13f, 5, OBSIDIAN),
                    new HandleMaterialStats(0.8f, 20),
                    new ExtraMaterialStats(40),
                    new BowMaterialStats(3, 4.0f, 7));
            materials.put("AirMaterial", airShardMaterial);

            //Arcane
            Material arcaneShardMaterial = new Material("shardArcane", 0xA748A8);
            arcaneShardMaterial.addTrait(TinkerTraits.alien);
            arcaneShardMaterial.addItem("shardArcane", 1, Material.VALUE_Ingot);
            arcaneShardMaterial.addItem("blockArcane", 1, Material.VALUE_Ingot * 4);
            arcaneShardMaterial.setCraftable(true).setCastable(false);
            setDispItem(arcaneShardMaterial, "shardArcane");
            if (side.isClient())
                registerTinkersRenderInfo(arcaneShardMaterial,0xA748A8);

            TinkerRegistry.addMaterialStats(arcaneShardMaterial,
                    new HeadMaterialStats(660, 8f, 6.5f, OBSIDIAN),
                    new HandleMaterialStats(1.45f, 320),
                    new ExtraMaterialStats(120),
                    new BowMaterialStats(4, 1.3f, 5));
            materials.put("ArcaneMaterial", arcaneShardMaterial);

            //Earth
            Material earthShardMaterial = new Material("shardEarth", 0xA6CF02);
            earthShardMaterial.addTrait(TinkerTraits.dense);
            earthShardMaterial.addItem("shardEarth", 1, Material.VALUE_Ingot);
            earthShardMaterial.addItem("blockEarth", 1, Material.VALUE_Ingot * 4);
            earthShardMaterial.setCraftable(true).setCastable(false);
            setDispItem(earthShardMaterial, "shardEarth");
            if(side.isClient())
                registerTinkersRenderInfo(earthShardMaterial,0xA6CF02);

            TinkerRegistry.addMaterialStats(earthShardMaterial,
                    new HeadMaterialStats(1050, 4.5f, 5.5f, OBSIDIAN),
                    new HandleMaterialStats(1.0f, 640),
                    new ExtraMaterialStats(240),
                    new BowMaterialStats(3.5f, 1.2f, 6));
            materials.put("EarthMaterial", earthShardMaterial);

            //Fire
            Material fireShardMaterial = new Material("shardFire", 0xFF8C00);
            fireShardMaterial.addTrait(TinkerTraits.autosmelt);
            fireShardMaterial.addTrait(TinkerTraits.hellish);
            fireShardMaterial.addTrait(TinkerTraits.superheat);
            fireShardMaterial.addItem("shardFire", 1, Material.VALUE_Ingot);
            fireShardMaterial.addItem("blockFire", 1, Material.VALUE_Ingot * 4);
            fireShardMaterial.setCraftable(true).setCastable(false);
            setDispItem(fireShardMaterial, "shardFire");
            if (side.isClient())
                registerTinkersRenderInfo(fireShardMaterial,0xFF8C00);

            TinkerRegistry.addMaterialStats(fireShardMaterial,
                    new HeadMaterialStats(700, 7f, 8f, OBSIDIAN),
                    new HandleMaterialStats(1.2f, 320),
                    new ExtraMaterialStats(80),
                    new BowMaterialStats(2.0f, 3.0f, 6));
            materials.put("FireMaterial", fireShardMaterial);

            //Water
            Material waterShardMaterial = new Material("shardWater", 0x06E8A6);
            waterShardMaterial.addTrait(TinkerTraits.aquadynamic);
            waterShardMaterial.addItem("shardWater", 1, Material.VALUE_Ingot);
            waterShardMaterial.addItem("blockWater", 1, Material.VALUE_Ingot * 4);
            waterShardMaterial.setCraftable(true).setCastable(false);
            setDispItem(waterShardMaterial, "shardWater");
            if(side.isClient())
                registerTinkersRenderInfo(waterShardMaterial,0x06E8A6);

            TinkerRegistry.addMaterialStats(waterShardMaterial,
                    new HeadMaterialStats(700, 7f, 6f, OBSIDIAN),
                    new HandleMaterialStats(1.2f, 320),
                    new ExtraMaterialStats(80),
                    new BowMaterialStats(2.0f, 3.0f, 5));
            materials.put("WaterMaterial", waterShardMaterial);

            preIntegrate(materials, materialIntegrations, materialIntegrationStages);
        }
    }
    private static void setDispItem(Material mat, String ore) {
        List<ItemStack> ores = OreDictionary.getOres(ore);
        if (mat == null || ores.isEmpty()) return;
        mat.setRepresentativeItem(ores.get(0));
    }

    private static void preIntegrate(Map<String,Material> materials,
                                     Map<String,MaterialIntegration> materialIntegrations,
                                     Map<String, CompletionStage<?>> materialIntegrationStages) {
        materials.forEach((k, v) -> {
            if (!materialIntegrations.containsKey(k)) {
                materialIntegrationStages.getOrDefault(k, CompletableFuture.completedFuture(null)).thenRun(() -> {
                    MaterialIntegration mi;
                    if (v.getRepresentativeItem().getItem() == Items.EMERALD) {
                        mi = new MaterialIntegration(v, v.getFluid());
                    } else if (v.getFluid() != null) {
                        mi = new MaterialIntegration(v, v.getFluid(), StringUtils.capitalize(k)).toolforge();
                    } else {
                        mi = new MaterialIntegration(v);
                    }
                    TinkerRegistry.integrate(mi).preInit();
                    materialIntegrations.put(k, mi);
                });
            }
        });
    }

    @SideOnly(Side.CLIENT)
    private static void registerTinkersRenderInfo(Material material, int color) {
        material.setRenderInfo(color);
    }
}
