package com.kjmaster.magicbooks2.common.blocks.tile.runes;

import com.google.common.collect.Lists;
import com.kjmaster.magicbooks2.common.capabilities.mana.earth.CapabilityEarthMana;
import com.kjmaster.magicbooks2.common.events.LumberRuneEvent;
import com.kjmaster.magicbooks2.utils.InventoryUtils;
import com.mojang.authlib.GameProfile;
import gnu.trove.set.hash.THashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class TileLumberRune extends TileRune {

    private ItemStackHandler handler = new ItemStackHandler(1);
    private int cooldown;
    private int blocksbroken;

    public TileLumberRune() {}

    public TileLumberRune(String element, int manaUse) {
        super(element, manaUse);
    }

    public void update() {
        if (this.world != null) {
            if(!this.world.isRemote) {
                TileLumberRune lumberRune = (TileLumberRune) world.getTileEntity(pos);
                IItemHandler handler = lumberRune.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                ItemStack itemStack = handler.getStackInSlot(0);
                Item item = itemStack.getItem();
                if (item.getDamage(itemStack) >= item.getMaxDamage(itemStack) && item instanceof ItemAxe)
                    itemStack.shrink(1);
                if (item instanceof ItemAxe && !this.world.isRemote) {
                    this.cooldown++;
                    this.cooldown %= 150;
                    if (this.cooldown == 0) {
                        EntityPlayer player = new EntityPlayer(world, new GameProfile(null, "LumberRune")) {
                            @Override
                            public boolean isSpectator() {
                                return true;
                            }

                            @Override
                            public boolean isCreative() {
                                return false;
                            }
                        };
                        coolDownDone(pos, world, lumberRune, player, handler, blocksbroken);
                    }
                }
                this.markDirty();
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.setCooldown(compound.getInteger("Cooldown"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("Cooldown", this.getCooldown());
        return super.writeToNBT(compound);
    }

    private void coolDownDone(BlockPos pos, World world, TileLumberRune tile, EntityPlayer player, IItemHandler handler, int blocksbroken) {
        BlockPos blockPosNorth = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
        BlockPos blockPosSouth = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
        BlockPos blockPosEast = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
        BlockPos blockPosWest = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
        BlockPos[] blocksPos = {blockPosNorth, blockPosSouth, blockPosEast, blockPosWest};
        for (BlockPos pos2: blocksPos) {
            //if (world.getBlockState(pos2).getBlock() instanceof BlockLog && tile.getManaStored() >= this.getMANA_USE()) {
                if(detectTree(world, pos2))
                    fellTree(pos2, player, handler, pos, tile);
            //}
        }
    }

    private boolean fellTree(BlockPos start, EntityPlayer player, IItemHandler handler, BlockPos runePos, TileLumberRune rune) {
        if (player.getEntityWorld().isRemote)
            return true;
        LumberRuneEvent.ExtraBlockBreak event = LumberRuneEvent.ExtraBlockBreak.fireEvent(player, player.getEntityWorld().getBlockState(start),
                3, 3, 3, -1);
        int speed = Math.round((event.width * event.height * event.depth) / 27f);
        if (event.distance > 0)
            speed = event.distance + 1;
        MinecraftForge.EVENT_BUS.register(new TreeChopTask(start, player, speed, handler, runePos, rune));
        return true;
    }

    private boolean detectTree(World world, BlockPos origin) {
        BlockPos pos = null;
        Stack<BlockPos> candidates = new Stack<>();
        candidates.add(origin);

        while (!candidates.isEmpty()) {
            BlockPos candidate = candidates.pop();
            if ((pos == null || candidate.getY() > pos.getY()) && isLog(world, candidate)) {
                pos = candidate.up();
                // go up
                while (isLog(world, pos)) {
                    pos = pos.up();
                }
                // check if we still have a way diagonally up
                candidates.add(pos.north());
                candidates.add(pos.east());
                candidates.add(pos.south());
                candidates.add(pos.west());
            }
        }

        if (pos == null)
            return false;
        return true;
    }

    private boolean isLog(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isWood(world, pos)
                || world.getBlockState(pos).getBlock()
                .isLeaves(world.getBlockState(pos), world, pos);
    }

    private class TreeChopTask {

        private final World world;
        private final EntityPlayer player;
        private final int blocksPerTick;
        private IItemHandler handler;
        private BlockPos runePos;
        private Queue<BlockPos> blocks = Lists.newLinkedList();
        private Set<BlockPos> visited = new THashSet<>();
        private TileLumberRune rune;

        TreeChopTask(BlockPos start, EntityPlayer player, int blocksPerTick,
                            IItemHandler handler, BlockPos runePos, TileLumberRune rune) {
            this.world = player.getEntityWorld();
            this.player = player;
            this.blocksPerTick = blocksPerTick;
            this.handler = handler;
            this.runePos = runePos;
            this.rune = rune;
            this.blocks.add(start);
        }

        @SubscribeEvent
        public void chopChop(TickEvent.WorldTickEvent event) {
            if (event.side.isClient()) {
                finish();
                return;
            }
            if (event.world.provider.getDimension() != world.provider.getDimension())
                return;
            int left = blocksPerTick;
            BlockPos pos;
            while (left > 0) {
                if (blocks.isEmpty()) {
                    finish();
                    return;
                }

                pos = blocks.remove();
                if (!visited.add(pos))
                    continue;

                if (!isLog(world, pos))
                    continue;

                for (EnumFacing facing : new EnumFacing[] {EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST}) {
                    BlockPos pos2 = pos.offset(facing);
                    if (!visited.contains(pos2)) {
                        blocks.add(pos2);
                    }
                }

                for (int x = 0; x < 3; x++) {
                    for (int z = 0; z < 3; z++) {
                        BlockPos pos2 = pos.add(-1 + x, 1, -1 + z);
                        if (!visited.contains(pos2))
                            blocks.add(pos2);
                    }
                }

                BlockPos blockPosUp = new BlockPos(runePos.getX(), runePos.getY() + 1, runePos.getZ());
                BlockPos blockPosDown = new BlockPos(runePos.getX(), runePos.getY() - 1, runePos.getZ());
                BlockPos blockPosNorth = new BlockPos(runePos.getX(), runePos.getY() + 1, runePos.getZ() - 1);
                BlockPos blockPosSouth = new BlockPos(runePos.getX(), runePos.getY() + 1, runePos.getZ() + 1);
                BlockPos blockPosEast = new BlockPos(runePos.getX() + 1, runePos.getY() + 1, runePos.getZ());
                BlockPos blockPosWest = new BlockPos(runePos.getX() - 1, runePos.getY() + 1, runePos.getZ());
                List<ItemStack> drops = world.getBlockState(pos).getBlock().getDrops(world, pos, world.getBlockState(pos), 0);
                BlockPos[] blockPos = {blockPosUp, blockPosDown, blockPosNorth, blockPosSouth, blockPosEast, blockPosWest};
                IItemHandler handler = null;
                for (BlockPos pos2: blockPos) {
                    if (InventoryUtils.tryGetHandler(world, pos2, null) != null)
                        handler = InventoryUtils.tryGetHandler(world, pos2, null);
                }
                for (ItemStack drop: drops) {
                    if (handler != null) {
                        if (InventoryUtils.canInsertStack(handler, drop)) {
                            ItemHandlerHelper.insertItemStacked(handler, drop, false);
                        } else {
                            InventoryHelper.spawnItemStack(world, blockPosUp.getX(), blockPosUp.getY(),
                                    blockPosUp.getZ(), drop);
                        }
                    } else {
                        InventoryHelper.spawnItemStack(world, blockPosUp.getX(), blockPosUp.getY(),
                                blockPosUp.getZ(), drop);
                    }
                }
                world.destroyBlock(pos, false);
                blocksbroken = blocksbroken + 1;
                left--;
            }
        }

        private void finish() {
            MinecraftForge.EVENT_BUS.unregister(this);
            int damage = handler.getStackInSlot(0).getItemDamage();
            handler.getStackInSlot(0).setItemDamage(damage + blocksbroken);
            //if (!world.isRemote)
                //rune.extractMana(rune.getMANA_USE());
            blocksbroken = 0;
        }
    }

    public int getCooldown() { return this.cooldown; }

    public void setCooldown(int cooldown) { this.cooldown = cooldown; }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) handler;
        if (capability == CapabilityEarthMana.EARTHMANA)
            return (T) storage;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
                capability == CapabilityEarthMana.EARTHMANA || super.hasCapability(capability, facing);
    }

    public int getField(int id) {
        switch (id)
        {
            case 0:
                //return this.storage.getManaStored(this.element);
                return 0;
            case 1:
                return this.getCooldown();
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id)
        {
            case 0:
                //this.storage.setMana(value, this.element);
                break;
            case 1:
                this.setCooldown(value);
                break;
        }
    }
}
