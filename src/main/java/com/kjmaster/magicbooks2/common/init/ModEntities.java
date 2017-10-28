package com.kjmaster.magicbooks2.common.init;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.client.render.*;
import com.kjmaster.magicbooks2.common.entities.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    public static void init() {
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocation(MagicBooks2.MODID, "air_wraith"),
                EntityAirWraith.class, "AirWraith", id++, MagicBooks2.instance, 64,
                3, true, 0xFEBA00, 0xD19E00);
        EntityRegistry.registerModEntity(new ResourceLocation(MagicBooks2.MODID, "arcane_wraith"),
                EntityArcaneWraith.class, "ArcaneWraith", id++, MagicBooks2.instance, 64,
                3, true, 0xA748A8, 0x6B3174);
        EntityRegistry.registerModEntity(new ResourceLocation(MagicBooks2.MODID, "earth_wraith"),
                EntityEarthWraith.class, "EarthWraith", id++, MagicBooks2.instance, 64,
                3, true, 0xA6CF02, 0x85AB02);
        EntityRegistry.registerModEntity(new ResourceLocation(MagicBooks2.MODID, "fire_wraith"),
                EntityFireWraith.class, "FireWraith", id++, MagicBooks2.instance, 64,
                3, true, 0xFF8C00, 0xCC4B00);
        EntityRegistry.registerModEntity(new ResourceLocation(MagicBooks2.MODID, "water_wraith"),
                EntityWaterWraith.class, "WaterWraith", id++, MagicBooks2.instance, 64,
                3, true, 0x06E8A6, 0x04A988);

        LootTableList.register(EntityAirWraith.LOOT);
        LootTableList.register(EntityArcaneWraith.LOOT);
        LootTableList.register(EntityEarthWraith.LOOT);
        LootTableList.register(EntityFireWraith.LOOT);
        LootTableList.register(EntityWaterWraith.LOOT);

    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityAirWraith.class, new RenderElementalWraithAir.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityArcaneWraith.class, new RenderElementalWraithArcane.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityEarthWraith.class, new RenderElementalWraithEarth.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityFireWraith.class, new RenderElementalWraithFire.Factory());
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterWraith.class, new RenderElementalWraithWater.Factory());
    }
}
