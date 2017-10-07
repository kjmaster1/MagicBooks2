package com.kjmaster.magicbooks2.tinkers;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class ShardFluid extends Fluid {

    public static ResourceLocation airStill = new ResourceLocation(MagicBooks2.MODID, "textures/fluids/air_still.png");
    public static ResourceLocation airFlowing = new ResourceLocation(MagicBooks2.MODID, "textures/fluids/air_flowing.png");
    private int color;


    public ShardFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, int color) {
        super(fluidName, still, flowing);
        this.color = color;
    }

    @Override
    public int getColor() {
        return this.color;
    }
}
