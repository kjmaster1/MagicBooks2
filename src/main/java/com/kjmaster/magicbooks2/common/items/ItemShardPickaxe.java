package com.kjmaster.magicbooks2.common.items;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Sets;
import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import com.kjmaster.magicbooks2.common.init.ModItems;
import com.kjmaster.magicbooks2.common.network.ClientManaPacket;
import com.kjmaster.magicbooks2.common.network.ClientParticlePacket;
import com.kjmaster.magicbooks2.common.network.PacketInstance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ItemShardPickaxe extends ItemPickaxe {

    Random random = new Random();

    public ItemShardPickaxe(ToolMaterial material, String name, CreativeTabs tab, int maxSize) {
        super(material);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(MagicBooks2.MODID, name));
        this.setCreativeTab(tab);
        this.setMaxStackSize(maxSize);
        this.setFull3D();
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        Item item = stack.getItem();
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            IMana manaCap = player.getCapability(ManaProvider.MANA_CAP, null);
            if(worldIn.rand.nextInt() % 900 == 0) {
                if (!worldIn.isRemote) {
                    if(item == ModItems.shardPickaxeAir)
                        player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 0));
                    if(item == ModItems.shardPickaxeEarth)
                        player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 1));
                    if(item == ModItems.shardPickaxeFire)
                        player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 2));
                    if(item == ModItems.shardPickaxeWater)
                        player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 3));
                }
            }
            if(worldIn.rand.nextInt() % 300 == 0) {
                if (item == ModItems.shardPickaxeArcane) {
                    player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 0));
                    player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 1));
                    player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 2));
                    player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 3));
                    player.addItemStackToInventory(new ItemStack(ModItems.Shard, 1, 4));
                }
            }
            if (item == ModItems.shardPickaxeWater)
                if (!worldIn.isRemote)
                    doObsidianCheck(worldIn, state, pos, manaCap, (EntityPlayerMP) player, stack);
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    private void doObsidianCheck(World world, IBlockState state, BlockPos pos, IMana manaCap, EntityPlayerMP playerMP, ItemStack stack) {
        if (state.getBlock().equals(Blocks.OBSIDIAN)) {
            if (manaCap.getMana("Water") >= 100) {
                world.setBlockState(pos, Blocks.WATER.getDefaultState());
                    manaCap.extractMana(100, "Water");
                    PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Water", manaCap.getMana("Water")), playerMP);
            }
        }
    }


    private void doMagnet(World world, EntityPlayer player, IMana manaCap) {
        int range = 5;
        ArrayList<EntityItem> itemsInArea = (ArrayList<EntityItem>)world.getEntitiesWithinAABB(EntityItem.class,
                new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range,
                        player.posX + range, player.posY + range, player.posZ + range));
        if (!itemsInArea.isEmpty()) {
            for(EntityItem item: itemsInArea) {
                if(!item.isDead && !item.cannotPickup()) {
                    int manaForItem = 10 * item.getItem().getCount();
                    if (manaCap.getMana("Air") >= manaForItem) {
                        item.onCollideWithPlayer(player);
                        if (!world.isRemote) {
                            manaCap.extractMana(manaForItem, "Air");
                            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Air", manaCap.getMana("Air")), (EntityPlayerMP) player);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        World world = player.world;
        Item item = itemstack.getItem();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        int meta = block.getMetaFromState(world.getBlockState(pos));
        IMana manaCap = player.getCapability(ManaProvider.MANA_CAP, null);
        int fireMana = manaCap.getMana("Fire");
        if (item == ModItems.shardPickaxeFire) {
            if (block.quantityDropped(world.getBlockState(pos), 0, random) != 0) {
                int amount = block.quantityDropped(random);
                Item itemDropped = block.getItemDropped(world.getBlockState(pos), random, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), itemstack));
                ItemStack result = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(itemDropped, amount, meta));
                if (result != ItemStack.EMPTY && fireMana >= 100) {
                    world.setBlockToAir(pos);
                    this.onBlockDestroyed(itemstack, world, world.getBlockState(pos), pos, player);
                    if (!world.isRemote) {
                        ItemStack spawnStack = new ItemStack(result.getItem(), amount * result.getCount(), result.getItemDamage());
                        if (result.hasTagCompound())
                            spawnStack.setTagCompound(result.getTagCompound());
                        if (!(result.getItem() instanceof ItemBlock)) {
                            int loot = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), itemstack);
                            if (loot > 0) {
                                spawnStack.setCount(random.nextInt(loot + 1) + 1);
                            }
                        }
                        EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, spawnStack);

                        entityItem.setPickupDelay(10);
                        world.spawnEntity(entityItem);
                        world.playBroadcastSound(2001, pos, Block.getIdFromBlock(block) + (meta << 12));
                        manaCap.extractMana(100, "Fire");
                        PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Fire", manaCap.getMana("Fire")), (EntityPlayerMP) player);
                        int i = spawnStack.getCount();
                        float f = FurnaceRecipes.instance().getSmeltingExperience(spawnStack);
                        int j;

                        if (f == 0.0F)
                        {
                            i = 0;
                        }
                        else if (f < 1.0F)
                        {
                            j = MathHelper.floor((float) i * f);

                            if (j < MathHelper.ceil((float) i * f)  && (float) Math.random() < (float) i * f - (float) j)
                            {
                                ++j;
                            }

                            i = j;
                        }

                        while (i > 0)
                        {
                            j = EntityXPOrb.getXPSplit(i);
                            i -= j;
                            world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY() + 0.5, pos.getZ(), j));
                        }
                    }

                    float f1 = (float) pos.getX() + random.nextFloat();
                    float f2 = (float) pos.getY() + random.nextFloat();
                    float f3 = (float) pos.getZ() + random.nextFloat();
                    float f4 = 0.52F;
                    float f5 = random.nextFloat() * 0.6F - 0.3F;
                    NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20);
                    PacketInstance.INSTANCE.sendToAllAround(new ClientParticlePacket("AutoSmelt", f1, f2, f3, f4, f5), point);

                    int damage = itemstack.getItemDamage();
                    itemstack.setItemDamage(damage + 1);
                    damage = itemstack.getItemDamage();
                    if (damage > itemstack.getMaxDamage())
                        itemstack.shrink(1);

                    return true;
                }
            }
            return false;
        }
        if (item == ModItems.shardPickaxeEarth) {
            if (manaCap.getMana("Earth") >= 100)
               mineArea(itemstack, world, player, manaCap, pos);
        }

        return super.onBlockStartBreak(itemstack, pos, player);
    }

    private void mineArea(ItemStack stack, World world, EntityPlayer player, IMana manaCap, BlockPos pos) {
        if (world.isRemote)
            return;

        boolean silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;
        int fortuneLvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        int range = 1;

        HashMultiset<ItemStack> drops = HashMultiset.create();

        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                for (int k = -range; k <= range; k++) {
                    BlockPos blockPos = pos.add(i, j, k);
                    Block block = world.getBlockState(blockPos).getBlock();

                    if (block.isAir(world.getBlockState(blockPos), world, blockPos))
                        continue;
                    if (world.getBlockState(blockPos).getMaterial() != Material.ROCK && !EFFECTIVE_ON.contains(block))
                        continue;

                    BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, blockPos, world.getBlockState(blockPos), player);
                    if (MinecraftForge.EVENT_BUS.post(event) || event.getResult() == Event.Result.DENY)
                        continue;

                    if (block.getBlockHardness(world.getBlockState(blockPos), world, blockPos) != -1) {
                        float strengthVsBlock = getStrVsBlock(stack, world.getBlockState(blockPos));

                        if (strengthVsBlock > 1.1F && world.canMineBlockBody(player, blockPos)) {
                            if (silkTouch && block.canSilkHarvest(world, blockPos, world.getBlockState(blockPos), player))
                                drops.add(new ItemStack(block));
                            else {
                                List<ItemStack> itemdrops = block.getDrops(world, blockPos, world.getBlockState(blockPos), fortuneLvl);
                                if (!itemdrops.isEmpty()) {
                                    drops.addAll(itemdrops);
                                }
                            }

                            world.setBlockToAir(blockPos);
                        }
                    }
                }
            }
        }
        dropStacks(drops, world, pos.add(0, 1, 0), manaCap, (EntityPlayerMP) player);

    }

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB);

    private void dropStacks(HashMultiset<ItemStack> drops, World world, BlockPos posToDrop, IMana manaCap, EntityPlayerMP playerMP) {
        for (ItemStack stack: drops) {
            int count = stack.getCount();
            int maxStackSize = stack.getMaxStackSize();

            while (count >= maxStackSize) {
                world.spawnEntity(new EntityItem(world, posToDrop.getX(), posToDrop.getY(), posToDrop.getZ(), new ItemStack(stack.getItem(), maxStackSize)));
                count -= maxStackSize;
            }

            if (count > 0) {
                world.spawnEntity(new EntityItem(world, posToDrop.getX(), posToDrop.getY(), posToDrop.getZ(), new ItemStack(stack.getItem(), count)));
            }
        }
        if (!world.isRemote) {
            manaCap.extractMana(100, "Earth");
            PacketInstance.INSTANCE.sendTo(new ClientManaPacket("Earth", manaCap.getMana("Earth")), playerMP);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            IMana manaCap = player.getCapability(ManaProvider.MANA_CAP, null);
            Item item = stack.getItem();
            if (item == ModItems.shardPickaxeAir) {
                    doMagnet(worldIn, player, manaCap);
            }
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
