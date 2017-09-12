package com.kjmaster.magicbooks2.common.capabilities.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Created by pbill_000 on 12/09/2017.
 */
public class ManaStorage implements Capability.IStorage<IMana> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        for (String type: Mana.manaList) {
            compound.setInteger(type, instance.getMana(type));
        }
        return compound;
    }

    @Override
    public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        for (String type: Mana.manaList) {
            instance.setMana(compound.getInteger(type), type);
        }
    }
}
