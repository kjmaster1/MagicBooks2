package com.kjmaster.magicbooks2.common.gen.structures;

import com.google.common.collect.Sets;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.entities.*;
import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.utils.InventoryUtils;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;
import java.util.Set;

import static com.kjmaster.magicbooks2.common.gen.structures.DungeonGenBase.DUNGEON;
import static com.kjmaster.magicbooks2.common.gen.structures.DungeonGenBase.DUNGEON_STAIRS;
import static com.kjmaster.magicbooks2.common.gen.structures.DungeonGenBase.TOWER;

public class StructureDungeonStart extends StructureStart {

        private Set<ChunkPos> processed = Sets.newHashSet();

    StructureDungeonStart(World world, Random random, int chunkX, int chunkZ) {
        super(chunkX, chunkZ);
        this.updateBoundingBox();
        this.boundingBox.offset(16, 20, 19);
        this.updateBoundingBox();
    }

    public StructureDungeonStart() {
        super();
    }

    public StructureDungeonStart(int chunkX, int chunkZ) {
        super(chunkX, chunkZ);
    }

    StructureDungeonStart(World world, int chunkX, int chunkZ, Random random) {
        int x = chunkX * 16 + random.nextInt(16) + 8;
        int z = chunkZ * 16 + random.nextInt(16) + 8;
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
        if (checkTower(world, x, y, z)) {
            Template templateTower = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), TOWER);
            Template templateStairs = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), DUNGEON_STAIRS);
            Template templateDungeon = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), DUNGEON);
            y = y - 2;
            MagicBooks2.LOGGER.info("Generated Tower At: " + new BlockPos(x, y, z).toString());
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
            z = z + 3;
            x = x - 9;
            addWraith(world, x, y, z, random);
            x = x - 1;
            addWraith(world, x, y, z, random);
            x = x - 1;
            addWraith(world, x, y, z, random);
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

    private void addWraith(World world, int x, int y, int z, Random random) {
        int randint = random.nextInt(4);
        switch (randint) {
            case 0:
                EntityAirWraith airWraith = new EntityAirWraith(world);
                airWraith.setPosition(x, y, z);
                world.spawnEntity(airWraith);
                break;
            case 1:
                EntityArcaneWraith arcaneWraith = new EntityArcaneWraith(world);
                arcaneWraith.setPosition(x, y, z);
                world.spawnEntity(arcaneWraith);
                break;
            case 2:
                EntityEarthWraith earthWraith = new EntityEarthWraith(world);
                earthWraith.setPosition(x, y, z);
                world.spawnEntity(earthWraith);
                break;
            case 3:
                EntityFireWraith fireWraith = new EntityFireWraith(world);
                fireWraith.setPosition(x, y, z);
                world.spawnEntity(fireWraith);
                break;
            case 4:
                EntityWaterWraith waterWraith = new EntityWaterWraith(world);
                waterWraith.setPosition(x, y, z);
                world.spawnEntity(waterWraith);
                break;
            default:
                EntityAirWraith airWraith2 = new EntityAirWraith(world);
                airWraith2.setPosition(x, y, z);
                world.spawnEntity(airWraith2);
                break;
        }
    }

    @Override
    public boolean isValidForPostProcess(ChunkPos pair) {
        return this.processed.contains(pair) ? false : super.isValidForPostProcess(pair);
    }

    @Override
    public void notifyPostProcessAt(ChunkPos pair) {
        super.notifyPostProcessAt(pair);
        this.processed.add(pair);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagList tagList = new NBTTagList();

        for(ChunkPos chunkPos : this.processed) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setInteger("X", chunkPos.x);
            nbtTagCompound.setInteger("Z", chunkPos.z);
            tagList.appendTag(nbtTagCompound);
        }

        tagCompound.setTag("Processed", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        if (tagCompound.hasKey("Processed", 9)) {
            NBTTagList nbtTagList = tagCompound.getTagList("Processed", 10);

            for (int i = 0; i < nbtTagList.tagCount(); i++) {
                NBTTagCompound nbtTagCompound = nbtTagList.getCompoundTagAt(i);
                this.processed.add(new ChunkPos(nbtTagCompound.getInteger("X"), nbtTagCompound.getInteger("Z")));
            }
        }
    }
}
