package com.kjmaster.magicbooks2.common.capabilities.skillpoints;

/**
 * Created by pbill_000 on 12/09/2017.
 */

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SkillPointsStorage implements Capability.IStorage<ISkillPoints> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ISkillPoints> capability, ISkillPoints instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        for (String type: SkillPoints.pointTypes) {
            compound.setInteger(type, instance.getPoints(type));
        }
        return compound;
    }

    @Override
    public void readNBT(Capability<ISkillPoints> capability, ISkillPoints instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        for (String type: SkillPoints.pointTypes) {
            instance.setPoints(compound.getInteger(type), type);
        }
    }
}
