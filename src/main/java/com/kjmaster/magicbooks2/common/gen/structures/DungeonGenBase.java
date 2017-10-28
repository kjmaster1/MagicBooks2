package com.kjmaster.magicbooks2.common.gen.structures;

import com.kjmaster.magicbooks2.MagicBooks2;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;
import java.util.Random;

public class DungeonGenBase extends MapGenScatteredFeature {

    static ResourceLocation TOWER = new ResourceLocation(MagicBooks2.MODID, "dungeon_tower");
    static ResourceLocation DUNGEON = new ResourceLocation(MagicBooks2.MODID, "dungeon");
    static ResourceLocation DUNGEON_STAIRS = new ResourceLocation(MagicBooks2.MODID, "dungeon_stairs");

    @Override
    public synchronized boolean generateStructure(World worldIn, Random randomIn, ChunkPos chunkCoord) {
        if (canSpawnStructureAtCoords(chunkCoord.x, chunkCoord.z)) {
           new StructureDungeonStart(worldIn, chunkCoord.x, chunkCoord.z, rand);
           return true;
        }
        return false;
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        return rand.nextInt(100) == 0;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {

        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, 10000, 8, 14357617, false, 100, findUnexplored);
    }

    @Override
    public String getStructureName() {
        return MagicBooks2.MODID + ":dungeon";
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new StructureDungeonStart(world, rand, chunkX, chunkZ);
    }
}
