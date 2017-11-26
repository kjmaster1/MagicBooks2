package com.kjmaster.magicbooks2.common.blocks.tile.pipes;

import com.kjmaster.magicbooks2.common.capabilities.mana.water.CapabilityWaterMana;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileWaterPipe extends TilePipe {

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityWaterMana.WATERMANA || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityWaterMana.WATERMANA)
            return (T) this;
        return super.getCapability(capability, facing);
    }
}
