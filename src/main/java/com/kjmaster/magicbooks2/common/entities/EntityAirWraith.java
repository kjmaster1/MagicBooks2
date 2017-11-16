package com.kjmaster.magicbooks2.common.entities;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityAirWraith extends EntityElementalWraith {

    public static final ResourceLocation LOOT = new ResourceLocation(MagicBooks2.MODID,
            "entities/elemental_wraith_air");

    public EntityAirWraith(World world) {
        super(world);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.getAttackTarget() != null) {
            EntityLivingBase entity = this.getAttackTarget();
            float distance = this.getDistanceToEntity(entity);
            if (distance >= 8 && this.ticksExisted % 60 == 0) {
                world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ, false));
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof EntityLivingBase) {
                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200));
            }
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LOOT;
    }
}
