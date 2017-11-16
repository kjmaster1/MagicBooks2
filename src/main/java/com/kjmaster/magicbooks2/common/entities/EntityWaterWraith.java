package com.kjmaster.magicbooks2.common.entities;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityWaterWraith extends EntityElementalWraith {

    public static final ResourceLocation LOOT = new ResourceLocation(MagicBooks2.MODID,
            "entities/elemental_wraith_water");

    public EntityWaterWraith(World world) {
        super(world);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

}
