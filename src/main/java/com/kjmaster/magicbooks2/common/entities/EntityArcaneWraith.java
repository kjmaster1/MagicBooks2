package com.kjmaster.magicbooks2.common.entities;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityArcaneWraith extends EntityElementalWraith {

    public static final ResourceLocation LOOT = new ResourceLocation(MagicBooks2.MODID,
            "entities/elemental_wraith_arcane");

    public EntityArcaneWraith(World world) {
        super(world);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getAttackTarget() != null) {
            EntityLivingBase entity = this.getAttackTarget();
            float distance = this.getDistanceToEntity(entity);
            if (distance >= 2 && this.ticksExisted % 160 == 0) {
                entity.attemptTeleport(this.posX + rand.nextInt(2) - 1, this.posY, this.posZ + rand.nextInt(2) - 2);
            }
        }
    }
}
