package com.kjmaster.magicbooks2.common.gen.structures;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.utils.InventoryUtils;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class DungeonGenerator implements IWorldGenerator {

    private static ResourceLocation TOWER = new ResourceLocation(MagicBooks2.MODID, "dungeon_tower");
    private static ResourceLocation DUNGEON = new ResourceLocation(MagicBooks2.MODID, "dungeon");
    private static ResourceLocation DUNGEON_STAIRS = new ResourceLocation(MagicBooks2.MODID, "dungeon_stairs");

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16 + random.nextInt(16) + 8;
        int z = chunkZ * 16 + random.nextInt(16) + 8;
        long seed = random.nextLong();
        generateDungeon(seed, world, x, z);
    }

    private void generateDungeon(Long seed, World world, int x, int z) {
        Random random = new Random(seed);
        int xy = x >> 4;
        int zy = z >> 4;
        int height = world.getChunkFromChunkCoords(xy, zy).getHeight(new BlockPos(x & 15, 0, z & 15));
        int testy;
        int y = 0;
        for (int i = 0; i < 60; i++) {
            testy = height - i;
            if (!world.isAirBlock(new BlockPos(x, testy, z))) {
                y = testy;
                break;
            }
        }
        boolean tower = checkTower(world, x, y, z);
        if (tower) {
            Template templateTower = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), TOWER);
            Template templateStairs = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), DUNGEON_STAIRS);
            Template templateDungeon = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), DUNGEON);
            y = y - 2;
            templateTower.addBlocksToWorld(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE));
            y = y - 11;
            x = x - 2;
            templateDungeon.addBlocksToWorld(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE));
            y = y + 7;
            x = x + 3;
            templateStairs.addBlocksToWorld(world, new BlockPos(x, y, z), new PlacementSettings().setRotation(Rotation.NONE));
            z = z + 9;
            x = x + 14;
            y = y - 4;
            addChest(world, x, y, z, random, Rotation.COUNTERCLOCKWISE_90);
            x = x - 1;
            z = z + 3;
            addChest(world, x, y, z, random, Rotation.NONE);
            z = z - 6;
            addChest(world, x, y, z, random, Rotation.CLOCKWISE_180);
        }
    }


    private void addChest(World world, int x, int y, int z, Random random, Rotation rotation) {
        world.setBlockState(new BlockPos(x, y, z), Blocks.CHEST.getDefaultState().withRotation(rotation));
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof TileEntityChest) {
            TileEntityChest tileChest = (TileEntityChest) tileEntity;
            tileChest.setLootTable(new ResourceLocation("chests/simple_dungeon"), random.nextLong());
            IItemHandler handler = InventoryUtils.tryGetHandler(tileChest, null);
            ItemHandlerHelper.insertItemStacked(handler, new ItemStack(ModItems.Shard, random.nextInt(3), random.nextInt(5)), false);
            ItemHandlerHelper.insertItemStacked(handler, new ItemStack(ModItems.Shard, random.nextInt(3), random.nextInt(5)), false);
            ItemHandlerHelper.insertItemStacked(handler, new ItemStack(ModItems.Shard, random.nextInt(3), random.nextInt(5)), false);
            ItemHandlerHelper.insertItemStacked(handler, new ItemStack(ModItems.Shard, random.nextInt(3), random.nextInt(5)), false);
        }
    }

    private boolean checkTower(World world, int x, int y, int z) {
        Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), TOWER);
        int xSize = template.getSize().getX();
        int zSize = template.getSize().getZ();
        for (int l = 0; l <= xSize; l++) {
            for (int m = 0; m <= zSize; m++) {
                BlockPos pos = new BlockPos(x + l, y, z + m);
                if (world.getBlockState(pos).getMaterial().equals(Material.WATER) || world.isAirBlock(pos)) {
                    return false;
                }
            }
        }
        y = y+1;
        for (int i = 0; i <= xSize; i++) {
            for (int j = 0; j <= 4; j++) {
                for (int k = 0; k <= zSize; k++) {
                    BlockPos pos = new BlockPos(x + i, y + j, z + k);
                    if (world.getBlockState(pos).getBlock().equals(Blocks.TALLGRASS)
                            || world.getBlockState(pos).getBlock() instanceof BlockFlower)
                        continue;
                    if (!world.isAirBlock(pos)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
