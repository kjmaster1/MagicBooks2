package com.kjmaster.magicbooks2.common.handlers;

import com.google.common.base.Predicate;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.ISpells;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.Spell;
import com.kjmaster.magicbooks2.common.capabilities.unlockedspells.SpellsProvider;
import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.network.ClientManaPacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import com.kjmaster.magicbooks2.common.network.RayTraceSpellPacket;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

public class RayTraceSpellHandler implements IMessageHandler<RayTraceSpellPacket, IMessage> {

    @Override
    public IMessage onMessage(RayTraceSpellPacket message, MessageContext ctx) {
        MagicBooks2.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(RayTraceSpellPacket message, MessageContext ctx) {
        int x = message.x;
        int y = message.y;
        int z = message.z;
        String spellAsString = message.spell;
        EntityPlayer player = MagicBooks2.proxy.getPlayerEntity(ctx);
        World world = player.world;
        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        IMana manaCap = player.getCapability(ManaProvider.MANA_CAP, null);
        ISpells spellsCap = player.getCapability(SpellsProvider.SPELLS_CAP, null);
        Spell spell = spellsCap.getSpell(spellAsString);
        switch (spell.getAsString()) {
            case "grow":
                castGrow(player, world, spell, block, manaCap, state, pos);
                break;
            case "walling":
                castWalling(player, world, spell, manaCap, pos);
                break;
            case "invisibility":
                castInvisibility(player, spell, manaCap);
                break;
            case "lightning":
                castLightning(player, world, spell, manaCap, pos);
                break;
            case "fireblast":
                castFireBlast(player, world, spell, manaCap);
            default:
                break;
        }
    }


    private void castGrow(EntityPlayer player, World world, Spell spell, Block block, IMana manaCap, IBlockState state, BlockPos pos) {
        if (spell.getIsUnlocked()) {
            if (manaCap.getMana("Earth") >= spell.getManaCost()) {
                if (block instanceof IGrowable || block instanceof IPlantable) {
                    for (int i = 0; i < 21; i++)
                        block.updateTick(world, pos, state, world.rand);
                    manaCap.extractMana(spell.getManaCost(), "Earth");
                    PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Earth", manaCap.getMana("Earth")), (EntityPlayerMP) player);
                }
            }
        }
    }

    private void castWalling(EntityPlayer player, World world, Spell spell, IMana manaCap, BlockPos pos) {
        if (spell.getIsUnlocked()) {
            BlockPos posAbove = pos.add(0, 1, 0);
            int x = posAbove.getX();
            int y = posAbove.getY();
            int z = posAbove.getZ();
            int xRange;
            int zRange;
            if (player.getHorizontalFacing().getOpposite().equals(EnumFacing.NORTH) ||
                    player.getHorizontalFacing().getOpposite().equals(EnumFacing.SOUTH)) {
                xRange = 1;
                zRange = 0;
            }
            else if (player.getHorizontalFacing().getOpposite().equals(EnumFacing.EAST) ||
                    player.getHorizontalFacing().getOpposite().equals(EnumFacing.WEST)) {
                zRange = 1;
                xRange = 0;
            }
            else
                return;
            for (int i = -xRange; i <= xRange; i++) {
                for (int j = 0; j <= 2; j++) {
                    for (int k = -zRange; k <= zRange; k++) {
                        BlockPos posTargetBlock = new BlockPos(x + i, y + j, z + k);
                        IBlockState stateTargetBlock = world.getBlockState(posTargetBlock);
                        if (stateTargetBlock.getMaterial().equals(Material.AIR)) {
                            if (manaCap.getMana("Earth") >= spell.getManaCost()) {
                                world.setBlockState(posTargetBlock, ModBlocks.earthWall.getDefaultState());
                                manaCap.extractMana(spell.getManaCost(), "Earth");
                                PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Earth", manaCap.getMana("Earth")), (EntityPlayerMP) player);
                            }
                        }
                    }
                }
            }
        }
    }

    private void castInvisibility(EntityPlayer player, Spell spell, IMana manaCap) {
        if (player.isInvisible()) {
            player.setInvisible(false);
        } else {
            if (manaCap.getMana("Air") >= spell.getManaCost() && spell.getIsUnlocked()) {
                player.setInvisible(true);
                manaCap.extractMana(spell.getManaCost(), "Air");
                PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Air", manaCap.getMana("Air")), (EntityPlayerMP) player);
            }
        }
    }

    private void castLightning(EntityPlayer player, World world, Spell spell, IMana manaCap, BlockPos pos) {
        if (manaCap.getMana("Air") >= spell.getManaCost() && spell.getIsUnlocked()) {
            if (!world.getBlockState(pos).getMaterial().equals(Material.AIR)) {
                world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
                manaCap.extractMana(spell.getManaCost(), "Air");
                PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Air", manaCap.getMana("Air")), (EntityPlayerMP) player);
            }
        }
    }

    private void castFireBlast(EntityPlayer player, World world, Spell spell, IMana manaCap) {
        if (manaCap.getMana("Fire") >= spell.getManaCost() && spell.getIsUnlocked()) {
            int mobs = 0;
            for(EntityMob e : world.getEntities(EntityMob.class, new Predicate<EntityMob>() {
                @Override
                public boolean apply(@Nullable EntityMob input) {
                    return (input.getDistanceToEntity(player) <= 8);
                }
            })) {
                e.setFire(20);
                e.attackEntityFrom(new DamageSource("magic"), 2F);
                mobs++;
            }
            manaCap.extractMana(spell.getManaCost() * mobs, "Fire");
            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Fire", manaCap.getMana("Fire")), (EntityPlayerMP) player);
        }
    }
}
