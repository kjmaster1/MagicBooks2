package com.kjmaster.magicbooks2.common.blocks.tile.pipes;

import com.kjmaster.magicbooks2.common.capabilities.mana.arcane.CapabilityArcaneMana;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileArcanePipe extends TilePipe {

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityArcaneMana.ARCANEMANA || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityArcaneMana.ARCANEMANA)
            return (T) this;
        return super.getCapability(capability, facing);
    }
}
