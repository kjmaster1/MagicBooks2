package com.kjmaster.magicbooks2.common.capabilities.skillpoints;

import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class SkillPointsProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(ISkillPoints.class)
    public static final Capability<ISkillPoints> SKILL_POINTS_CAP = null;

    private ISkillPoints pointsInstance = SKILL_POINTS_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == SKILL_POINTS_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == SKILL_POINTS_CAP ? SKILL_POINTS_CAP.<T>cast(this.pointsInstance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return SKILL_POINTS_CAP.getStorage().writeNBT(SKILL_POINTS_CAP, this.pointsInstance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        SKILL_POINTS_CAP.getStorage().readNBT(SKILL_POINTS_CAP, this.pointsInstance, null, nbt);
    }
}
